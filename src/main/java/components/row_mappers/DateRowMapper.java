package components.row_mappers;

import models.Date_DB;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * (ORM) Класс, реализующий отображение из модели в строку таблицы базы данных
 */
public class DateRowMapper implements RowMapper<Date_DB> {

    @Override
    public Date_DB mapRow(ResultSet resultSet, int i) throws SQLException {

        Date_DB date = new Date_DB();

        date.setDay(resultSet.getDate("day"));
        date.setCash_value_on_day_start(resultSet.getFloat("cash_value_on_day_start"));
        date.setCash_value_on_day_end(resultSet.getFloat("cash_value_on_day_end"));
        date.setCashless_value_on_day_start(resultSet.getFloat("cashless_value_on_day_start"));
        date.setCashless_value_on_day_end(resultSet.getFloat("cashless_value_on_day_end"));

        return date;
    }
}
