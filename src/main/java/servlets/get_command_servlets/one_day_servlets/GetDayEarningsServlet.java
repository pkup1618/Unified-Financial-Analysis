package servlets.get_command_servlets.one_day_servlets;


/**
 * Сервлет, принимающий http-запрос, содержащий в теле JSON
 */
public class GetDayEarningsServlet extends GetDayElemsServlet {

    @Override
    protected void makeResponseToDatabase() {
        resultSet = databaseHandler.getDayEarnings(date);
    }
}
