package servlets.send_command_servlets;

import components.database_handling.models.Date_DB;
import org.json.simple.JSONObject;


public class SetDayServlet extends SetDataServlet {

    @Override
    protected void makeResponseToDatabase() {

        stringDate = (String) jsonRequestBody.get("day");
        date = convertDateFromStringDate(stringDate);

        double cashValueOnDayStart = (Double) jsonRequestBody.get("cash-value-on-day-start");
        double cashValueOnDayEnd = (Double) jsonRequestBody.get("cash-value-on-day-end");
        double cashlessValueOnDayStart = (Double) jsonRequestBody.get("cashless-value-on-day-start");
        double cashlessValueOnDayEnd = (Double) jsonRequestBody.get("cashless-value-on-day-end");


        Date_DB dateModel = new Date_DB(date,
                cashValueOnDayStart, cashValueOnDayEnd,
                cashlessValueOnDayStart, cashlessValueOnDayEnd);

        databaseHandler.setDay(dateModel);
    }

    protected void setRequestJsonTemplate() {
        requestJsonTemplate = new JSONObject();

        requestJsonTemplate.put("day", "00/00/0000");
        requestJsonTemplate.put("cash-value-on-day-start", 0);
        requestJsonTemplate.put("cash-value-on-day-end", 0);
        requestJsonTemplate.put("cashless-value-on-day-start", 0);
        requestJsonTemplate.put("cashless-value-on-day-end", 0);
    }
}