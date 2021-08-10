package servlets.send_command_servlets;

import components.database_handling.DatabaseHandler;
import components.database_handling.models.Earning_DB;
import components.database_handling.models.Purchase_DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class SetPurchaseServlet extends HttpServlet {
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

        Purchase_DB purchase_model;

        String purchase_name;
        String purchase_type;
        double purchase_cost;
        Long count;
        Date day;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            jsonObject = (JSONObject) JSONValue.parseWithException(requestBody);

            purchase_name = (String) jsonObject.get("purchase-name");
            purchase_type = (String) jsonObject.get("purchase-type");
            purchase_cost = (double) jsonObject.get("purchase-cost");
            count = (Long) jsonObject.get("count");

            stringDate = (String) jsonObject.get("day");
            java.util.Date utilDate = format.parse(stringDate);
            day = new Date(utilDate.getTime());

            purchase_model = new Purchase_DB(purchase_name, purchase_type, purchase_cost, count, day);

            databaseHandler.setPurchase(purchase_model);
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