package components.support_classes;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class JsonHandler {
    public static JSONObject parseJsonFromString(String stringJson) {

        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = (JSONObject) JSONValue.parseWithException(stringJson);
        }
        catch(org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return requestJsonObject;
    }
}
