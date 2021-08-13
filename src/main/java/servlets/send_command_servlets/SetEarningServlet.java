package servlets.send_command_servlets;

import components.database_handling.models.Earning_DB;
import org.json.simple.JSONObject;

import java.sql.Date;


public class SetEarningServlet extends SetDataServlet {

    @Override
    protected void makeResponseToDatabase() {

        String earningName = (String) jsonRequestBody.get("earning-name");
        String earningType = (String) jsonRequestBody.get("earning-type");
        Double earningCost = (Double) jsonRequestBody.get("earning-cost");
        Long count = (Long) jsonRequestBody.get("count");

        String stringDateForModel = (String) jsonRequestBody.get("day");
        Date dateForModel = convertDateFromStringDate(stringDateForModel);

        Earning_DB earningModel = new Earning_DB(earningName,
                earningType, earningCost, count, dateForModel);

        databaseHandler.setEarning(earningModel);
    }


    @Override
    protected void setRequestJsonTemplate() {
        requestJsonTemplate = new JSONObject();

        requestJsonTemplate.put("earning-name", "null");
        requestJsonTemplate.put("earning-type", 0);
        requestJsonTemplate.put("earning-cost", 0);
        requestJsonTemplate.put("count", 0);
        requestJsonTemplate.put("day", "00/00/0000");
    }
}