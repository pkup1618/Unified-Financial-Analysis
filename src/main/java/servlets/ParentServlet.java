package servlets;

import components.database_handling.DatabaseHandler;
import components.support_classes.JsonHandler;
import components.support_classes.RequestBodyHandler;
import components.support_classes.Validator;
import components.support_classes.exceptions.IncorrectBodyFormatException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Класс, инкапсулирующий некоторые общие методы, требуемые сервлетами
 * @author Андрей Осинцев
 */
public abstract class ParentServlet extends HttpServlet {

    /** Поле обработчика базы данных */
    protected final DatabaseHandler databaseHandler = new DatabaseHandler();
    protected JSONObject requestJsonTemplate;

    /**
     * Устанавливает шаблон для дальнейшей валидации JSON-объектов
     */
    protected abstract void setRequestJsonTemplate();


    /**
     * Возвращает тело http-запроса
     * @param request - http-запрос
     */
    protected String getRequestBody(HttpServletRequest request) {

        return RequestBodyHandler.getRequestBody(request);
    }


    protected void validateRequest(HttpServletRequest request, String  requestBody, JSONObject template) throws IncorrectBodyFormatException {
        Validator.validateRequest(request, requestBody, template);
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
     * Преобразует json-объект-строку в формат JSON-Object
     * @param stringJson - json-объект в виде строки
     */
    protected JSONObject parseJsonFromString(String stringJson) {

        return JsonHandler.parseJsonFromString(stringJson);
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
