package components.database_handling;


import components.database_handling.models.DateDB;
import components.database_handling.models.EarningDB;
import components.database_handling.models.PurchaseDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;


//todo singleton?
/**
 * Класс для взаимодействия с базой данных через JDBC Driver
 */
public class DatabaseHandler {

    private static Driver postgreSqlDriver;
    private static Connection connection;

    static {
        postgreSqlDriver = null;
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            postgreSqlDriver = DriverManager.getDriver("jdbc:postgresql://localhost:5709/money_management_db");
            DriverManager.registerDriver(postgreSqlDriver);

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5709/money_management_db",
                    "postgres", "Pautina111");

            if (connection == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            //todo что делать в нормальном случае?
            e.printStackTrace();
        }
    }

    /**
     * Получить все записи о расходах из базы данных
     * @return записи из базы данных
     */
    public ResultSet getAllPurchases() {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM purchase");
            resultSet = statement.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Получить все записи о доходах из базы данных
     * @return записи из базы данных
     */
    public ResultSet getAllEarnings() {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM earning");
            resultSet = statement.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Получить все записи о днях из базы данных
     * @return записи из базы данных
     */
    public ResultSet getAllDays() {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM date");
            resultSet = statement.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Добавить запись о расходе в базу данных
     * @param purchaseDB объект расхода
     */
    public void setPurchase(PurchaseDB purchaseDB) {
        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO purchase VALUES(?, ?, ?, ?, ?, ?)");

            statement.setString(1, purchaseDB.getPurchaseName());
            statement.setString(2, purchaseDB.getPurchaseType());
            statement.setDouble(3, purchaseDB.getPurchaseCost());
            statement.setLong(4, purchaseDB.getCount());
            statement.setDate(5, purchaseDB.getDay());
            statement.setString(6, purchaseDB.getPaymentType());

            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Добавить запись о доходе в базу данных
     * @param earningDB объект дохода
     */
    public void setEarning(EarningDB earningDB) {
        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO earning VALUES(?, ?, ?, ?, ?, ?)");

            statement.setString(1, earningDB.getEarningName());
            statement.setString(2, earningDB.getEarningType());
            statement.setDouble(3, earningDB.getEarningCost());
            statement.setLong(4, earningDB.getCount());
            statement.setDate(5, earningDB.getDay());
            statement.setString(6, earningDB.getPaymentType());

            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Добавить запись о дне в базу данных
     * @param dateDB объект дня
     */
    public void setDay(DateDB dateDB) {
        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO date VALUES(?, ?, ?, ?, ?)");

            statement.setDate(1, dateDB.getDay());
            statement.setDouble(2, dateDB.getCashValueOnDayStart());
            statement.setDouble(3, dateDB.getCashValueOnDayEnd());
            statement.setDouble(4, dateDB.getCashlessValueOnDayStart());
            statement.setDouble(5, dateDB.getCashlessValueOnDayEnd());

            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Проверить наличие записи о дне в базе данных
     * @param date объект дня
     * @return true - запись есть, false - записи нет
     */
    public boolean checkDateForExistence(Date date) {
        Boolean result = null;
        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM date WHERE day = ?");
            statement.setDate(1, date);
            ResultSet resultSet = statement.executeQuery();

            resultSet.last();
            int size = resultSet.getRow();
            resultSet.beforeFirst();
            result = (size == 1);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Получить все записи о расходах за день из базы данных
     * @param date объект дня
     * @return записи из базы данных
     */
    public ResultSet getDayPurchases(Date date) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM purchase WHERE day = ?");
            statement.setDate(1, date);

            resultSet = statement.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Получить все записи о доходах за день из базы данных
     * @param date объект дня
     * @return записи из базы данных
     */
    public ResultSet getDayEarnings(Date date) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM earning WHERE day = ?");
            statement.setDate(1, date);

            resultSet = statement.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Получить все записи о расходах за временной промежуток
     * от дня до дня (включительно) из базы данных
     * @param lessDate объект дня (от которого отсчёт)
     * @param moreDate объект дня (докоторого отсчёт)
     * @return записи из базы данных
     */
    public ResultSet getPurchasesInTimePeriod(Date lessDate, Date moreDate) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM purchase WHERE day BETWEEN ? AND ?");
            statement.setDate(1, lessDate);
            statement.setDate(2, moreDate);

            resultSet = statement.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Получить все записи о доходах за временной промежуток
     * от дня до дня (включительно) из базы данных
     * @param lessDate объект дня (от которого отсчёт)
     * @param moreDate объект дня (докоторого отсчёт)
     * @return записи из базы данных
     */
    public ResultSet getEarningsInTimePeriod(Date lessDate, Date moreDate) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM earning WHERE day BETWEEN ? AND ?");
            statement.setDate(1, lessDate);
            statement.setDate(2, moreDate);

            resultSet = statement.executeQuery();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Преобразовать записи из формата ResultSet в список JSON
     * @param resultSet записи из базы данных
     * @return записи из базы данных в виде списка JSON
     */
    public JSONArray convertResultSetToJsonArray(ResultSet resultSet) {
        JSONArray allJsonRows = new JSONArray();
        try {
            int numberColumns = resultSet.getMetaData().getColumnCount();
            ResultSetMetaData metaData = resultSet.getMetaData();

            while(resultSet.next()) {
                JSONObject jsonRow = new JSONObject();

                for(int i = 1; i <= numberColumns; i++) {
                    String RowElem = resultSet.getString(i);
                    String ColumnName = metaData.getColumnName(i);

                    jsonRow.put(ColumnName, RowElem);
                }
                allJsonRows.add(jsonRow);
            }
            resultSet.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return allJsonRows;
    }
}
