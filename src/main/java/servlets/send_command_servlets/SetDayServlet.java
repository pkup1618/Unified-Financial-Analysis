package servlets.send_command_servlets;

import components.database_handling.DatabaseHandler;
import components.database_handling.models.Date_DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class SetDayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
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
        Date_DB day_model;
        double cvds;
        double cvde;
        double clvds;
        double clvde;
        Date date;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            jsonObject = (JSONObject) JSONValue.parseWithException(requestBody);

            stringDate = (String) jsonObject.get("day");
            cvds = (double) jsonObject.get("cvds");
            cvde = (double) jsonObject.get("cvds");
            clvds = (double) jsonObject.get("clvds");
            clvde = (double) jsonObject.get("clvde");
            java.util.Date utilDate = format.parse(stringDate);
            Date sqlDate = new Date(utilDate.getTime());

            day_model = new Date_DB(sqlDate, cvds, cvde, clvds, clvde);

            databaseHandler.setDay(day_model);
            Writer writer = response.getWriter();
            writer.write("succeed");
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