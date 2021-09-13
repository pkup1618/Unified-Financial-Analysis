package components.database_handling.models;

import java.sql.Date;

/**
 * Класс, представляющий из себя запись о дате из базы данных
 */
public class DateDB {

    private Date day;
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }

    private double cashValueOnDayStart;
    public double getCashValueOnDayStart() {
        return cashValueOnDayStart;
    }
    public void setCashValueOnDayStart(double cashValueOnDayStart) {
        this.cashValueOnDayStart = cashValueOnDayStart;
    }

    private double cashValueOnDayEnd;
    public double getCashValueOnDayEnd() {
        return cashValueOnDayEnd;
    }
    public void setCashValueOnDayEnd(double cashValueOnDayEnd) {
        this.cashValueOnDayEnd = cashValueOnDayEnd;
    }

    private double cashlessValueOnDayStart;
    public double getCashlessValueOnDayStart() {
        return cashlessValueOnDayStart;
    }
    public void setCashlessValueOnDayStart(double cashlessValueOnDayStart) {
        this.cashlessValueOnDayStart = cashlessValueOnDayStart;
    }

    private double cashlessValueOnDayEnd;
    public double getCashlessValueOnDayEnd() {
        return cashlessValueOnDayEnd;
    }
    public void setCashlessValueOnDayEnd(double cashlessValueOnDayEnd) {
        this.cashlessValueOnDayEnd = cashlessValueOnDayEnd;
    }

    /**
     * Сконструировать запись о дне в базу данных
     * @param day день
     * @param cashValueOnDayStart состояние счёта наличными в начале дня
     * @param cashValueOnDayEnd состояние счёта наличными в конце дня
     * @param cashlessValueOnDayStart состояние счёта безналичными в начале дня
     * @param cashlessValueOnDayEnd состояние счёта безналичными в конце дня
     */
    public DateDB(Date day,
                  double cashValueOnDayStart,
                  double cashValueOnDayEnd,
                  double cashlessValueOnDayStart,
                  double cashlessValueOnDayEnd) {

        setDay(day);
        setCashValueOnDayStart(cashValueOnDayStart);
        setCashValueOnDayEnd(cashValueOnDayEnd);
        setCashlessValueOnDayStart(cashlessValueOnDayStart);
        setCashlessValueOnDayEnd(cashlessValueOnDayEnd);
    }
}