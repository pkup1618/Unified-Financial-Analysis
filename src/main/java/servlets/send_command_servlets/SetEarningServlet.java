package servlets.send_command_servlets;

import components.database_handling.models.EarningDB;
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

        String paymentType = (String) jsonRequestBody.get("payment-type");

        EarningDB earningModel = new EarningDB(earningName,
                earningType, earningCost, count, dateForModel, paymentType);

        databaseHandler.setEarning(earningModel);
    }


    @Override
    protected void setRequestJsonTemplate() {
        requestJsonTemplate = new JSONObject();

        requestJsonTemplate.put("earning-name", "null");
        requestJsonTemplate.put("earning-type", "null");
        requestJsonTemplate.put("earning-cost", 0);
        requestJsonTemplate.put("count", 0);
        requestJsonTemplate.put("day", "00/00/0000");
        requestJsonTemplate.put("payment-type", "null");
    }
}