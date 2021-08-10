package components.database_handling.models;

import java.sql.Date;

public class Earning_DB {

    private String earning_name;
    private String earning_type;
    private Double earning_cost;
    private String payment_type;
    private Long count;
    private Date day;

    public String getEarning_name() {
        return earning_name;
    }

    public void setEarning_name(String earning_name) {
        this.earning_name = earning_name;
    }

    public String getEarning_type() {
        return earning_type;
    }

    public void setEarning_type(String earning_type) {
        this.earning_type = earning_type;
    }

    public Double getEarning_cost() {
        return earning_cost;
    }

    public void setEarning_cost(Double earning_cost) {
        this.earning_cost = earning_cost;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Earning_DB() { }

    public Earning_DB(String earning_name, String earning_type, Double earning_cost, Long count, Date day) {
        setEarning_name(earning_name);
        setEarning_type(earning_type);
        setEarning_cost(earning_cost);
        setCount(count);
        setDay(day);
    }
}