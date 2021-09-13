package components.support_classes;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


/**
 * Класс для обработки тел запросов в разных ситуациях
 */
public class RequestBodyHandler {

    /**
     * Прочесть и сохранить в строку тело запроса
     * @param request запрос
     * @return тело запроса
     */
    public static String getRequestBody(HttpServletRequest request) {

        String requestBody = null;
        try(Reader reader = request.getReader();
            BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder requestBodyBuilder = new StringBuilder();
            for (String requestBodyPart; (requestBodyPart = bufferedReader.readLine()) != null;) {
                requestBodyBuilder.append(requestBodyPart);
            }
            requestBody = requestBodyBuilder.toString();
        }
        //todo как обработать?
        catch (IOException e) {
            e.printStackTrace();
        }
        return requestBody;
    }
}
