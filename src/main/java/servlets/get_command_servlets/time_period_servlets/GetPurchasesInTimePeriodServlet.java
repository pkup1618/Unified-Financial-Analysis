package servlets.get_command_servlets.time_period_servlets;


import java.sql.Date;


public class GetPurchasesInTimePeriodServlet extends GetElemsInTimePeriodServlet {


    @Override
    protected void makeResponseToDatabase(Date precedingDate, Date followingDate) {
        resultSet = databaseHandler.getPurchasesInTimePeriod(precedingDate, followingDate);
    }
}