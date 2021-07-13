package components.database_handling.row_mappers;

import components.database_handling.models.Date_DB;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * (ORM) Класс, реализующий отображение из модели в строку таблицы базы данных
 */
public class DateRowMapper implements RowMapper<Date_DB> {

    @Override
    public Date_DB mapRow(ResultSet resultSet, int i) throws SQLException {

        Date_DB dateDB = new Date_DB();

        dateDB.setDay(resultSet.getDate("day"));
        dateDB.setCash_value_on_day_start(resultSet.getFloat("cash_value_on_day_start"));
        dateDB.setCash_value_on_day_end(resultSet.getFloat("cash_value_on_day_end"));
        dateDB.setCashless_value_on_day_start(resultSet.getFloat("cashless_value_on_day_start"));
        dateDB.setCashless_value_on_day_end(resultSet.getFloat("cashless_value_on_day_end"));

        return dateDB;
    }
}
