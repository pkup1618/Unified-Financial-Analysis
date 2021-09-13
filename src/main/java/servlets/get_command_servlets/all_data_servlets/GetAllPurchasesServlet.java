package servlets.get_command_servlets.all_data_servlets;


public class GetAllPurchasesServlet extends GetAllElemsServlet {

    @Override
    protected void makeResponseToDatabase() {
        resultSet = databaseHandler.getAllPurchases();
    }
}

