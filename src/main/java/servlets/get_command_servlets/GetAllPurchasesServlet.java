package servlets.get_command_servlets;


import components.database_handling.DatabaseHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetAllPurchasesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = null;
        String json = null;

        try {
            resultSet = databaseHandler.getAllPurchases();
            json = databaseHandler.convertResultSetToJson(resultSet);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(json);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
