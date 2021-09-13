package servlets.send_command_servlets;


import components.support_classes.exceptions.IncorrectBodyFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import servlets.ParentServlet;


import java.io.PrintWriter;
import java.sql.Date;


public abstract class SetDataServlet extends ParentServlet {

    String requestBody;
    JSONObject jsonRequestBody;
    JSONObject jsonAnswer;
    String stringDate;
    Date date;
    PrintWriter responseWriter;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        responseWriter = getResponsePrintWriter(response);

        requestBody = getRequestBody(request);

        try{
            setRequestJsonTemplate();

            validateRequest(request, requestBody, requestJsonTemplate);
            jsonRequestBody = parseJsonFromString(requestBody);

            makeResponseToDatabase();

            jsonAnswer = new JSONObject();
            jsonAnswer.put("status" , true);


            responseWriter.println(jsonAnswer);
        }
        catch (IncorrectBodyFormatException e) {

            JSONObject failureAnswer = new JSONObject();
            failureAnswer.put("status", "bad request");
            responseWriter.println(failureAnswer);
        }
    }

    protected abstract void makeResponseToDatabase();
}
