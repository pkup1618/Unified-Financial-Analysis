package servlets.get_command_servlets.one_day_servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servlets.ParentServlet;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;

public abstract class GetDayElemsServlet extends ParentServlet {

    String requestBody;
    JSONObject jsonRequestBody;
    JSONArray jsonAnswer;
    String stringDate;
    Date date;
    ResultSet resultSet;
    PrintWriter responseWriter;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestBody = getRequestBody(request);
        jsonRequestBody = parseJsonFromString(requestBody);

        stringDate = (String) jsonRequestBody.get("date-without-bank");
        date = convertDateFromStringDate(stringDate);

        makeResponseToDatabase();

        jsonAnswer = databaseHandler.convertResultSetToJsonArray(resultSet);

        responseWriter = getResponsePrintWriter(response);
        responseWriter.println(jsonAnswer.toJSONString());

    }


    protected abstract void makeResponseToDatabase();
}
