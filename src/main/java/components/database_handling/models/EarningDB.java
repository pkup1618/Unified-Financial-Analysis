package components.database_handling.models;

import java.sql.Date;

/**
 * Класс, представляющий из себя запись о доходе из базы данных
 */
public class EarningDB {

    private String earningName;
    public String getEarningName() {
        return earningName;
    }
    public void setEarningName(String earningName) {
        this.earningName = earningName;
    }

    private String earningType;
    public String getEarningType() {
        return earningType;
    }
    public void setEarningType(String earningType) {
        this.earningType = earningType;
    }

    private Double earningCost;
    public Double getEarningCost() {
        return earningCost;
    }
    public void setEarningCost(Double earningCost) {
        this.earningCost = earningCost;
    }

    private Long count;
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
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
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    //todo а нельзя ли внедрить структуры?
    /**
     * Сконструировать запись о доходе в базу данных
     * @param earningName название
     * @param earningType тип
     * @param earningCost цена (за штуку)
     * @param count количество
     * @param day день
     * @param paymentType способ оплаты (cash/card)
     */
    public EarningDB(String earningName,
                     String earningType,
                     Double earningCost,
                     Long count,
                     Date day,
                     String paymentType) {

        setEarningName(earningName);
        setEarningType(earningType);
        setEarningCost(earningCost);
        setCount(count);
        setDay(day);
        setPaymentType(paymentType);
    }
}