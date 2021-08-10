package components.database_handling.models;

import java.sql.Date;

public class Date_DB {

    private Date day;
    private double cash_value_on_day_start;
    private double cash_value_on_day_end;
    private double cashless_value_on_day_start;
    private double cashless_value_on_day_end;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public double getCash_value_on_day_start() {
        return cash_value_on_day_start;
    }

    public void setCash_value_on_day_start(double cash_value_on_day_start) {
        this.cash_value_on_day_start = cash_value_on_day_start;
    }

    public double getCash_value_on_day_end() {
        return cash_value_on_day_end;
    }

    public void setCash_value_on_day_end(double cash_value_on_day_end) {
        this.cash_value_on_day_end = cash_value_on_day_end;
    }

    public double getCashless_value_on_day_start() {
        return cashless_value_on_day_start;
    }

    public void setCashless_value_on_day_start(double cashless_value_on_day_start) {
        this.cashless_value_on_day_start = cashless_value_on_day_start;
    }

    public double getCashless_value_on_day_end() {
        return cashless_value_on_day_end;
    }

    public void setCashless_value_on_day_end(double cashless_value_on_day_end) {
        this.cashless_value_on_day_end = cashless_value_on_day_end;
    }
    public Date_DB(){}

    public Date_DB(Date day, double cash_value_on_day_start,
                   double cash_value_on_day_end,
                   double cashless_value_on_day_start,
                   double cashless_value_on_day_end) {
        setDay(day);
        setCash_value_on_day_start(cash_value_on_day_start);
        setCash_value_on_day_end(cash_value_on_day_end);
        setCashless_value_on_day_start(cashless_value_on_day_start);
        setCashless_value_on_day_end(cash_value_on_day_end);
    }
}