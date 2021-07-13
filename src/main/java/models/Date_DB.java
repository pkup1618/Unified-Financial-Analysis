package models;

import java.sql.Date;

public class Date_DB {

    private Date day;
    private float cash_value_on_day_start;
    private float cash_value_on_day_end;
    private float cashless_value_on_day_start;
    private float cashless_value_on_day_end;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public float getCash_value_on_day_start() {
        return cash_value_on_day_start;
    }

    public void setCash_value_on_day_start(float cash_value_on_day_start) {
        this.cash_value_on_day_start = cash_value_on_day_start;
    }

    public float getCash_value_on_day_end() {
        return cash_value_on_day_end;
    }

    public void setCash_value_on_day_end(float cash_value_on_day_end) {
        this.cash_value_on_day_end = cash_value_on_day_end;
    }

    public float getCashless_value_on_day_start() {
        return cashless_value_on_day_start;
    }

    public void setCashless_value_on_day_start(float cashless_value_on_day_start) {
        this.cashless_value_on_day_start = cashless_value_on_day_start;
    }

    public float getCashless_value_on_day_end() {
        return cashless_value_on_day_end;
    }

    public void setCashless_value_on_day_end(float cashless_value_on_day_end) {
        this.cashless_value_on_day_end = cashless_value_on_day_end;
    }
    public Date_DB(){}

    public Date_DB(Date day, float cs, float ce, float bs, float be) {
        setDay(new Date(System.currentTimeMillis()));
        setCash_value_on_day_start(cs);
        setCash_value_on_day_end(ce);
        setCashless_value_on_day_start(bs);
        setCashless_value_on_day_end(be);
    }
}
