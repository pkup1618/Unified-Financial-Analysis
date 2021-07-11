package components;

import components.row_mappers.DateRowMapper;
import components.row_mappers.EarningRowMapper;
import components.row_mappers.PurchaseRowMapper;
import models.Date_DB;
import models.Earning_DB;
import models.Purchase_DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataAccessObject {

    private final JdbcTemplate jdbcTemplate;

    //Всё создано с помощью бинов
    @Autowired
    public DataAccessObject(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Purchase_DB> getAllPurchases(){
        return jdbcTemplate.query("SELECT * FROM purchase", new PurchaseRowMapper());
    }

    public List<Earning_DB> getAllEarnings(){
        return jdbcTemplate.query("SELECT * FROM earning", new EarningRowMapper());
    }

    public List<Date_DB> getAllDays(){
        return jdbcTemplate.query("SELECT * FROM date", new DateRowMapper());
    }

    //Теперь создам методы для внесения данных в таблицу.
    public void setPurchase(Purchase_DB purchaseDB){
        jdbcTemplate.update("INSERT INTO purchase VALUES(?, ?, ?, ?, ?)",
                purchaseDB.getPurchase_name(),
                purchaseDB.getPurchase_type(),
                purchaseDB.getPurchase_cost(),
                purchaseDB.getCount(),
                purchaseDB.getDay());
    }

    public void setEarning(Earning_DB earningDB){
        jdbcTemplate.update("INSERT INTO earning VALUES(?, ?, ?, ?, ?)",
                earningDB.getEarning_name(),
                earningDB.getEarning_type(),
                earningDB.getEarning_cost(),
                earningDB.getCount(),
                earningDB.getDay());
    }

    public void setDay(Date_DB dateDB){
        jdbcTemplate.update("INSERT INTO date VALUES(?,?,?,?,?)",
                dateDB.getDay(),
                dateDB.getCash_value_on_day_start(),
                dateDB.getCash_value_on_day_end(),
                dateDB.getCashless_value_on_day_start(),
                dateDB.getCashless_value_on_day_end());
    }
}
