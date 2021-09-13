package servlets.send_command_servlets;

import components.database_handling.models.PurchaseDB;
import org.json.simple.JSONObject;

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

        String paymentType = (String) jsonRequestBody.get("payment-type");

        PurchaseDB purchaseModel = new PurchaseDB(purchaseName,
                purchaseType, purchaseCost, count, dateForModel, paymentType);

        databaseHandler.setPurchase(purchaseModel);
    }


    @Override
    protected void setRequestJsonTemplate() {
        requestJsonTemplate = new JSONObject();

        requestJsonTemplate.put("purchase-name", "null");
        requestJsonTemplate.put("purchase-type", "null");
        requestJsonTemplate.put("purchase-cost", 0);
        requestJsonTemplate.put("count", 0);
        requestJsonTemplate.put("day", "00/00/0000");
        requestJsonTemplate.put("payment-type", "null");
    }
}