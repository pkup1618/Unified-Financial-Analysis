package components.database_handling;


import components.database_handling.models.Date_DB;
import components.database_handling.models.Earning_DB;
import components.database_handling.models.Purchase_DB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;

/*
DriverManager - все его методы статические, а значит не надо создавать его экземпляр
https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/DriverManager.html
 */
public class DatabaseHandler {

    static Driver postgreSqlDriver;
    static Connection connection;
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
            e.printStackTrace();
        }
    }

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

    //todo Проверить работоспособность
    public void setPurchase(Purchase_DB purchaseDB) {
        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO purchase VALUES(?, ?, ?, ?, ?)");

            statement.setString(1, purchaseDB.getPurchase_name());
            statement.setString(2, purchaseDB.getPurchase_type());
            statement.setDouble(3, purchaseDB.getPurchase_cost());
            statement.setLong(4, purchaseDB.getCount());
            statement.setDate(5, purchaseDB.getDay());

            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void setEarning(Earning_DB earningDB) {
        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO earning VALUES(?, ?, ?, ?, ?)");

            statement.setString(1, earningDB.getEarning_name());
            statement.setString(2, earningDB.getEarning_type());
            statement.setDouble(3, earningDB.getEarning_cost());
            statement.setLong(4, earningDB.getCount());
            statement.setDate(5, earningDB.getDay());

            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void setDay(Date_DB dateDB) {
        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO date VALUES(?, ?, ?, ?, ?)");

            statement.setDate(1, dateDB.getDay());
            statement.setDouble(2, dateDB.getCash_value_on_day_start());
            statement.setDouble(3, dateDB.getCash_value_on_day_end());
            statement.setDouble(4, dateDB.getCashless_value_on_day_start());
            statement.setDouble(5, dateDB.getCashless_value_on_day_end());

            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }

    //todo удостовериться, нет ли способа удобнее
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

/*
    public MoneyDifferenceInTime getTotalMoneyEarningsInTimePeriod(Date lessDate, Date moreDate) {
        List<Earning_DB> earnings = getEarningsInTimePeriod(lessDate, moreDate);
        MoneyDifferenceInTime moneyDifferenceInTime = new MoneyDifferenceInTime();

        for (Earning_DB earning: earnings) {
            if (earning.getPayment_type() == "cash")
                moneyDifferenceInTime.addCash_total_value(earning.getEarning_cost() * earning.getCount());
            else
                moneyDifferenceInTime.addCashless_total_value(earning.getEarning_cost() * earning.getCount());
        }
        return moneyDifferenceInTime;
    }


    public MoneyDifferenceInTime getTotalMoneyPurchasesInTimePeriod(Date lessDate, Date moreDate) {
        List<Purchase_DB> purchases = getPurchasesInTimePeriod(lessDate, moreDate);
        MoneyDifferenceInTime moneyDifferenceInTime = new MoneyDifferenceInTime();

        for (Purchase_DB purchase: purchases) {
            if (purchase.getPayment_type() == "cashless")
                moneyDifferenceInTime.addCash_total_value(purchase.getPurchase_cost() * purchase.getCount());
            else
                moneyDifferenceInTime.addCashless_total_value(purchase.getPurchase_cost() * purchase.getCount());
        }
        return moneyDifferenceInTime;
    }
     */
}
