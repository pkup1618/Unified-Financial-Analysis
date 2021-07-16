package components.database_handling.row_mappers;

import components.database_handling.models.Earning_DB;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * (ORM) Класс, реализующий отображение из модели в строку таблицы базы данных
 */
@Component
@Scope("singleton")
public class EarningRowMapper implements RowMapper<Earning_DB> {

    @Override
    public Earning_DB mapRow(ResultSet resultSet, int i) throws SQLException {

        Earning_DB earningDB = new Earning_DB();

        earningDB.setEarning_name(resultSet.getString("earning_name"));
        earningDB.setEarning_type(resultSet.getString("earning_type"));
        earningDB.setEarning_cost(resultSet.getFloat("earning_cost"));
        earningDB.setPayment_type(resultSet.getString("payment_type"));
        earningDB.setCount(resultSet.getInt("count"));
        earningDB.setDay(resultSet.getDate("day"));

        return earningDB;
    }
}
