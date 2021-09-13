package servlets.get_command_servlets.time_period_servlets;

import components.support_classes.exceptions.IncorrectBodyFormatException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servlets.ParentServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;


public abstract class GetElemsInTimePeriodServlet extends ParentServlet {

    String requestBody;
    JSONObject jsonRequestBody;
    JSONArray jsonAnswer;

    String stringPrecedingDate;
    Date precedingDate;

    String stringFollowingDate;
    Date followingDate;

    ResultSet resultSet;
    PrintWriter responseWriter;


    protected void setRequestJsonTemplate() {
        requestJsonTemplate = new JSONObject();
        requestJsonTemplate.put("preceding-date", "00/00/0000");
        requestJsonTemplate.put("following-date", "00/00/0000");

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        responseWriter = getResponsePrintWriter(response);

        setRequestJsonTemplate();

        requestBody = getRequestBody(request);

        try{
            validateRequest(request, requestBody, requestJsonTemplate);

            jsonRequestBody = parseJsonFromString(requestBody);

            stringPrecedingDate = (String) jsonRequestBody.get("preceding-date");
            precedingDate = convertDateFromStringDate(stringPrecedingDate);

            stringFollowingDate = (String) jsonRequestBody.get("following-date");
            followingDate = convertDateFromStringDate(stringFollowingDate);

            makeResponseToDatabase(precedingDate, followingDate);

            jsonAnswer = databaseHandler.convertResultSetToJsonArray(resultSet);

            responseWriter.println(jsonAnswer);
        }
        catch(IncorrectBodyFormatException e) {

            JSONObject failureAnswer = new JSONObject();
            failureAnswer.put("status", "bad request");
            responseWriter.println(failureAnswer);
        }

    }

    protected abstract void makeResponseToDatabase(Date lessDate, Date moreDate);
}
