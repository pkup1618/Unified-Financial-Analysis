package servlets.get_command_servlets;

import components.database_handling.DatabaseHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class GetEarningsInTimePeriodServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = null;
        String json = null;

        StringBuilder requestBodyBuilder = new StringBuilder();
        String requestBody;

        try(Reader reader = request.getReader(); BufferedReader bufferedReader = new BufferedReader(reader)) {
            for (String requestBodyPart; (requestBodyPart = bufferedReader.readLine()) != null;) {
                requestBodyBuilder.append(requestBodyPart);
            }
        }

        requestBody = requestBodyBuilder.toString();
        JSONObject jsonObject = null;
        String stringDate;

        java.util.Date utilLessDate;
        java.sql.Date sqlLessDate;

        java.util.Date utilMoreDate;
        java.sql.Date sqlMoreDate;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            jsonObject = (JSONObject) JSONValue.parseWithException(requestBody);

            stringDate = (String) jsonObject.get("less-date");
            utilLessDate = format.parse(stringDate);
            sqlLessDate = new java.sql.Date(utilLessDate.getTime());

            stringDate = (String) jsonObject.get("more-date");
            utilMoreDate = format.parse(stringDate);
            sqlMoreDate = new java.sql.Date(utilMoreDate.getTime());



            resultSet = databaseHandler.getEarningsInTimePeriod(sqlLessDate, sqlMoreDate);
            json = databaseHandler.convertResultSetToJson(resultSet);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(json);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}