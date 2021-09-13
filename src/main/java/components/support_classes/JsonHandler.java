package components.support_classes;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


/**
 * Класс с фунционалом для обработки JSON-объектов
 */
public class JsonHandler {

    /**
     * Преобразовать JSON-строку в JSON-объект
     * @param stringJson строка ( например { "field": "value" } )
     * @return JSON-объект
     */
    public static JSONObject parseJsonFromString(String stringJson) {

        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = (JSONObject) JSONValue.parseWithException(stringJson);
        }
        //todo Как нормально обработать?
        catch(org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return requestJsonObject;
    }
}
