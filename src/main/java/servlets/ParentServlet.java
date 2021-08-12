package servlets;

import components.database_handling.DatabaseHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Класс, инкапсулирующий некоторые общие методы, требуемые сервлетами
 * @author Андрей Осинцев
 */
public abstract class ParentServlet extends HttpServlet {

    /** Поле обработчика базы данных */
    protected DatabaseHandler databaseHandler = new DatabaseHandler();


    /**
     * Возвращает тело http-запроса
     * @param request - http-запрос
     */
    protected String getRequestBody(HttpServletRequest request) {

        String requestBody = null;
        try(Reader reader = request.getReader();
            BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder requestBodyBuilder = new StringBuilder();
            for (String requestBodyPart; (requestBodyPart = bufferedReader.readLine()) != null;) {
                requestBodyBuilder.append(requestBodyPart);
            }
            requestBody = requestBodyBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return requestBody;
    }


    /**
     * устанавливает значение полю {@link java.sql.Date} из строки в формате yyyy-MM-dd
     * @param date - строка в формате yyyy-MM-dd
     */
    protected Date convertDateFromStringDate(String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = null;
        try {
            java.util.Date utilDate = format.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }


    /**
     * Преобразует json-объект-строку в формат JSONObject
     * @param stringJson - json-объект в виде строки
     */
    protected JSONObject parseJsonFromString(String stringJson) {

        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = (JSONObject) JSONValue.parseWithException(stringJson);
        }
        catch(org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return requestJsonObject;
    }


    /**
     * Достаёт поток для написания текста из ответа
     * @param response - ответ
     * @return - поток
     */
    protected PrintWriter getResponsePrintWriter(HttpServletResponse response) {

        PrintWriter responsePrintWriter = null;
        try {
            responsePrintWriter = response.getWriter();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return responsePrintWriter;
    }
}
