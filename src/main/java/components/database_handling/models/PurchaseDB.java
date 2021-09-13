package components.database_handling.models;

import java.sql.Date;

public class PurchaseDB {

    private String purchaseName;
    public String getPurchaseName() {
        return purchaseName;
    }
    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    private String purchaseType;
    public String getPurchaseType() {
        return purchaseType;
    }
    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    private Double purchaseCost;
    public Double getPurchaseCost() {
        return purchaseCost;
    }
    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    private Long count;
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }

    private Date day;
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }

    private String paymentType;
    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }


    //todo а нельзя ли внедрить структуры?
    /**
     * Сконструировать запись о расходе в базу данных
     * @param purchaseName название
     * @param purchaseType тип
     * @param purchaseCost цена (за штуку)
     * @param count количество
     * @param day день
     * @param paymentType способ оплаты (cash/card)
     */
    public PurchaseDB(String purchaseName,
                      String purchaseType,
                      double purchaseCost,
                      long count,
                      Date day,
                      String paymentType) {

        setPurchaseName(purchaseName);
        setPurchaseType(purchaseType);
        setPurchaseCost(purchaseCost);
        setCount(count);
        setDay(day);
        setPaymentType(paymentType);
    }
}