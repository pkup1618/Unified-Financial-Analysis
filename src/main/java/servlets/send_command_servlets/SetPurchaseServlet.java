package servlets.send_command_servlets;

import components.database_handling.models.Purchase_DB;
import java.sql.Date;


public class SetPurchaseServlet extends SetDataServlet {

    @Override
    protected void makeResponseToDatabase() {

        String purchaseName = (String) jsonRequestBody.get("purchase-name");
        String purchaseType = (String) jsonRequestBody.get("purchase-type");
        Double purchaseCost = (Double) jsonRequestBody.get("purchase-cost");
        Long count = (Long) jsonRequestBody.get("count");

        String stringDateForModel = (String) jsonRequestBody.get("day");
        Date dateForModel = convertDateFromStringDate(stringDateForModel);

        Purchase_DB purchaseModel = new Purchase_DB(purchaseName,
                purchaseType, purchaseCost, count, dateForModel);

        databaseHandler.setPurchase(purchaseModel);
    }
}