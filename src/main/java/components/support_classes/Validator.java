package components.support_classes;

import components.support_classes.exceptions.IncorrectBodyFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Set;


/**
 * Класс, который проверяет корректность http-запроса, содержащего JSON
 */
public class Validator {

    public static void validateRequest(HttpServletRequest request, String requestBody, JSONObject template) throws IncorrectBodyFormatException {

        JSONObject jsonRequestBody;

        checkContentType(request);

        checkRequestBodyParseAbility(requestBody);

        jsonRequestBody = JsonHandler.parseJsonFromString(requestBody);
        checkJsonSize(jsonRequestBody, template);
        checkJsonFieldNames(jsonRequestBody, template);
    }


    private static void checkJsonFieldNames(JSONObject jsonObject, JSONObject template) throws IncorrectBodyFormatException {
        Set<Object> keys = template.keySet();

        for (Object key : keys) {
            if (!jsonObject.containsKey(key)) {
                throw new IncorrectBodyFormatException();
            }
        }
    }


    private static void checkJsonSize(JSONObject jsonObject, JSONObject template) throws IncorrectBodyFormatException {
        if (!(jsonObject.size() == template.size())) {
            throw new IncorrectBodyFormatException();
        }
    }


    private static void checkRequestBodyParseAbility(String stringJson) throws IncorrectBodyFormatException {
        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = (JSONObject) JSONValue.parseWithException(stringJson);
        }
        catch(org.json.simple.parser.ParseException e) {
            throw new IncorrectBodyFormatException();
        }
    }


    private static void checkContentType(HttpServletRequest request) throws IncorrectBodyFormatException {
        String contentType = request.getHeader("Content-Type");
        if (!isJsonContent(contentType)) {
            throw new IncorrectBodyFormatException();
        }
    }


    private static boolean isJsonContent(String contentType) {
        return (contentType.compareTo("application/json") == 0);
    }
}