package components.database_handling;


import components.database_handling.models.Date_DB;
import components.database_handling.models.Earning_DB;
import components.database_handling.models.Purchase_DB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public ResultSet getAllPurchases() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM purchase");
        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public ResultSet getAllEarnings() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM earning");
        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public ResultSet getAllDays() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM date");
        ResultSet rs = statement.executeQuery();
        return rs;
    }

    //todo Проверить работоспособность
    public void setPurchase(Purchase_DB purchaseDB) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("INSERT INTO purchase VALUES(?, ?, ?, ?, ?)");

        statement.setString(1, purchaseDB.getPurchase_name());
        statement.setString(2, purchaseDB.getPurchase_type());
        statement.setDouble(3, purchaseDB.getPurchase_cost());
        statement.setLong(4, purchaseDB.getCount());
        statement.setDate(5, purchaseDB.getDay());

        statement.executeUpdate();
    }

    public void setEarning(Earning_DB earningDB) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("INSERT INTO earning VALUES(?, ?, ?, ?, ?)");

        statement.setString(1, earningDB.getEarning_name());
        statement.setString(2, earningDB.getEarning_type());
        statement.setDouble(3, earningDB.getEarning_cost());
        statement.setLong(4, earningDB.getCount());
        statement.setDate(5, earningDB.getDay());

        statement.executeUpdate();
    }

    public void setDay(Date_DB dateDB) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("INSERT INTO date VALUES(?, ?, ?, ?, ?)");

        statement.setDate(1, dateDB.getDay());
        statement.setDouble(2, dateDB.getCash_value_on_day_start());
        statement.setDouble(3, dateDB.getCash_value_on_day_end());
        statement.setDouble(4, dateDB.getCashless_value_on_day_start());
        statement.setDouble(5, dateDB.getCashless_value_on_day_end());

        statement.executeUpdate();
    }

    //todo удостовериться, нет ли способа удобнее
    public boolean checkDateForExistence(Date date) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("SELECT * FROM date WHERE day = ?");
        statement.setDate(1, date);
        ResultSet rs = statement.executeQuery();

        rs.last();
        int size = rs.getRow();
        rs.beforeFirst();

        return (size == 1);
    }

    public ResultSet getDayPurchases(Date date) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("SELECT * FROM purchase WHERE day = ?");
        statement.setDate(1, date);

        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public ResultSet getDayEarnings(Date date) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("SELECT * FROM earning WHERE day = ?");
        statement.setDate(1, date);

        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public ResultSet getPurchasesInTimePeriod(Date lessDate, Date moreDate) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("SELECT * FROM purchase WHERE day BETWEEN ? AND ?");
        statement.setDate(1, lessDate);
        statement.setDate(2, moreDate);

        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public ResultSet getEarningsInTimePeriod(Date lessDate, Date moreDate) throws SQLException {
        PreparedStatement statement = connection.
                prepareStatement("SELECT * FROM earning WHERE day BETWEEN ? AND ?");
        statement.setDate(1, lessDate);
        statement.setDate(2, moreDate);

        ResultSet rs = statement.executeQuery();
        return rs;
    }


    public String convertResultSetToJson(ResultSet resultSet) throws SQLException {

        JSONArray allJsonRows = new JSONArray();
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
        return allJsonRows.toJSONString();
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
