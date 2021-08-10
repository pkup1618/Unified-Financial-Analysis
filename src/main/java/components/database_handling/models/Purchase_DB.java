package components.database_handling.models;

import java.sql.Date;

public class Purchase_DB {

    private String purchase_name;
    private String purchase_type;
    private Double purchase_cost;
    private String payment_type;
    private Long count;
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

    public Double getPurchase_cost() {
        return purchase_cost;
    }

    public void setPurchase_cost(Double purchase_cost) {
        this.purchase_cost = purchase_cost;
    }

    public String getPayment_type() { return payment_type; }

    public void setPayment_type(String payment_type) { this.payment_type = payment_type; }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Purchase_DB(){}

    public Purchase_DB(String purchase_name, String purchase_type, double purchase_cost, long count, Date day){
        setPurchase_name(purchase_name);
        setPurchase_type(purchase_type);
        setPurchase_cost(purchase_cost);
        setCount(count);
        setDay(day);
    }
}