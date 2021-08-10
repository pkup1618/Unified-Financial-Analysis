package servlets;


import components.database_handling.DatabaseHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;


public class MainServlet extends HttpServlet {

    /*
     * HttpServlet - абстрактный класс, в котором реализованы все
     * методы, кроме конкретных для взаимодействия, например doGet.
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        ResultSet rs = null;
        try {
            Driver postgreSqlDriver = DriverManager.getDriver("jdbc:postgresql://localhost:5709/money_management_db");
            DriverManager.registerDriver(postgreSqlDriver);

            rs = databaseHandler.getAllPurchases();
            PrintWriter pw = response.getWriter();
            rs.next();
            pw.println(rs.getDate("day"));

            pw.println(rs.getClass().getName());



        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
