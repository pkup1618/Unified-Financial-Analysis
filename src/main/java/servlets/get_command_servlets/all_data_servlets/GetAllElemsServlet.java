package servlets.get_command_servlets.all_data_servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import servlets.ParentServlet;

import java.io.PrintWriter;
import java.sql.ResultSet;


public abstract class GetAllElemsServlet extends ParentServlet {

    JSONArray jsonAnswer;
    PrintWriter responseWriter;
    ResultSet resultSet;

    protected abstract void makeResponseToDatabase();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        makeResponseToDatabase();
        jsonAnswer = databaseHandler.convertResultSetToJsonArray(resultSet);

        responseWriter = getResponsePrintWriter(response);
        responseWriter.println(jsonAnswer);
    }


}
