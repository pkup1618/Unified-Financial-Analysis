package servlets.send_command_servlets;

import components.database_handling.models.Earning_DB;
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
}