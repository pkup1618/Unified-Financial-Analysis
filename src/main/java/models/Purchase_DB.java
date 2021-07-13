package models;


import java.sql.Date;

public class Purchase_DB {

    private String purchase_name;
    private String purchase_type;
    private float purchase_cost;
    private int count;
    private Date day;

    public String getPurchase_name() {
        return purchase_name;
    }

    public void setPurchase_name(String purchase_name) {
        this.purchase_name = purchase_name;
    }

    public String getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(String purchase_type) {
        this.purchase_type = purchase_type;
    }

    public float getPurchase_cost() {
        return purchase_cost;
    }

    public void setPurchase_cost(float purchase_cost) {
        this.purchase_cost = purchase_cost;
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

    public Purchase_DB(){}

    Purchase_DB(String purchase_name, String purchase_type, float purchase_cost, int count, Date day){
        setPurchase_name(purchase_name);
        setPurchase_type(purchase_type);
        setPurchase_cost(purchase_cost);
        setCount(count);
        setDay(day);
    }
}
