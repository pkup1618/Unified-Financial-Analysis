package servlets.get_command_servlets.one_day_servlets;


public class GetDayPurchasesServlet extends GetDayElemsServlet {

    @Override
    protected void makeResponseToDatabase() {
        resultSet = databaseHandler.getDayEarnings(date);
    }
}