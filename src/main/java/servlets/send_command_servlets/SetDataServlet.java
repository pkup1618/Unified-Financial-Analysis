package servlets.send_command_servlets;


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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        requestBody = getRequestBody(request);
        jsonRequestBody = parseJsonFromString(requestBody);

        makeResponseToDatabase();

        jsonAnswer = new JSONObject();
        jsonAnswer.put("status" , true);

        responseWriter = getResponsePrintWriter(response);
        responseWriter.println(jsonAnswer);
    }

    protected abstract void makeResponseToDatabase();
}
