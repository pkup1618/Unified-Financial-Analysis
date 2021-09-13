package servlets.get_command_servlets.one_day_servlets;

import components.support_classes.exceptions.IncorrectBodyFormatException;
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


    protected void setRequestJsonTemplate() {
        requestJsonTemplate = new JSONObject();
        requestJsonTemplate.put("date-without-bank", "00/00/0000");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        responseWriter = getResponsePrintWriter(response);

        setRequestJsonTemplate();

        requestBody = getRequestBody(request);

        try {
            validateRequest(request, requestBody, requestJsonTemplate);

            jsonRequestBody = parseJsonFromString(requestBody);

            stringDate = (String) jsonRequestBody.get("date-without-bank");
            date = convertDateFromStringDate(stringDate);

            makeResponseToDatabase();

            jsonAnswer = databaseHandler.convertResultSetToJsonArray(resultSet);

            responseWriter.println(jsonAnswer.toJSONString());
        }
        catch(IncorrectBodyFormatException e) {

            JSONObject failureAnswer = new JSONObject();
            failureAnswer.put("status", "bad request");
            responseWriter.println(failureAnswer);
        }
    }


    protected abstract void makeResponseToDatabase();
}
