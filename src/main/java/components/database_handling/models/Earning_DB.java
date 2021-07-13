package components.database_handling.models;
/*
Меня не интересуют внешние ключи, так как это нужно лишь для удобства составления запросов.
В объектах этого класса мне уже не важно как они взялись.
 */

import java.sql.Date;

public class Earning_DB {

    private String earning_name;
    private String earning_type;
    private float earning_cost;
    private int count;
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

    public float getEarning_cost() {
        return earning_cost;
    }

    public void setEarning_cost(float earning_cost) {
        this.earning_cost = earning_cost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Earning_DB(){}

    public Earning_DB(String earning_name, String earning_type, float earning_cost, int count, Date day){
        setEarning_name(earning_name);
        setEarning_type(earning_type);
        setEarning_cost(earning_cost);
        setCount(count);
        setDay(day);
    }
}
