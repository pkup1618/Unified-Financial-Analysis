package components.database_handling;

import components.database_handling.row_mappers.DateRowMapper;
import components.database_handling.row_mappers.EarningRowMapper;
import components.database_handling.row_mappers.PurchaseRowMapper;
import components.database_handling.models.Date_DB;
import components.database_handling.models.Earning_DB;
import components.database_handling.models.Purchase_DB;
import components.support_classes.MoneyDifferenceInTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

/** Класс, содержащий <b>все</b> методы для взаимодействия с базой данных, используемые приложением. <br> <br>
 * Методы этого класса должны делать только обращения к базе данных
 * (выборку из таблиц), но не должны обрабатывать эти данные)
 * */
@Component
@Scope("singleton")
public class DatabaseRequestsHandler {

    /** Текущее число */
    private static final Date CURRENT_DATE;

    static {
        CURRENT_DATE = new Date(System.currentTimeMillis());
    }

    /** Поле jdbcTemplate, для содержания драйвер-прослойки, обеспечивающей доступ к базе данных */
    private final JdbcTemplate jdbcTemplate;

    /** Конструктор - создание нового объекта  */
    @Autowired
    public DatabaseRequestsHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Метод, который возвращает все строки из таблицы "purchase" */
    public List<Purchase_DB> getAllPurchases() {
        return jdbcTemplate.query("SELECT * FROM purchase", new PurchaseRowMapper());
    }

    /** Метод, который возвращает все строки из таблицы "earning"" */
    public List<Earning_DB> getAllEarnings() {
        return jdbcTemplate.query("SELECT * FROM earning", new EarningRowMapper());
    }

    /** Метод, который возвращает все строки из таблицы "date" */
    public List<Date_DB> getAllDays() {
        return jdbcTemplate.query("SELECT * FROM date", new DateRowMapper());
    }

    /**
     * Метод, который добавляет строку в таблицу "purchase"
     * @param purchaseDB модель, преобразовываемая в строку
     * */
    public void setPurchase(Purchase_DB purchaseDB) {
        jdbcTemplate.update("INSERT INTO purchase VALUES(?, ?, ?, ?, ?)",
                purchaseDB.getPurchase_name(),
                purchaseDB.getPurchase_type(),
                purchaseDB.getPurchase_cost(),
                purchaseDB.getCount(),
                purchaseDB.getDay());
    }

    /**
     * Метод, который добавляет строку в таблицу "earnings"
     * @param earningDB модель, преобразовываемая в строку
     * */
    public void setEarning(Earning_DB earningDB) {
        jdbcTemplate.update("INSERT INTO earning VALUES(?, ?, ?, ?, ?)",
                earningDB.getEarning_name(),
                earningDB.getEarning_type(),
                earningDB.getEarning_cost(),
                earningDB.getCount(),
                earningDB.getDay());
    }

    /**
     * Метод, который добавляет строку в таблицу "date"
     * @param dateDB модель, преобразовываемая в строку
     * */
    public void setDay(Date_DB dateDB) {
        jdbcTemplate.update("INSERT INTO date VALUES(?,?,?,?,?)",
                dateDB.getDay(),
                dateDB.getCash_value_on_day_start(),
                dateDB.getCash_value_on_day_end(),
                dateDB.getCashless_value_on_day_start(),
                dateDB.getCashless_value_on_day_end());
    }

    /**
     * Проверка, существует ли в базе данных хотя-бы одна запись с числом date
     * @param date день календаря
     * @return #true - если да, #false - если нет
     */
    public boolean checkDateForExistence(Date date) {
        List<Date_DB> daysList;
        daysList = jdbcTemplate.query("SELECT * FROM date WHERE day = ?", new DateRowMapper(), date);
        return (daysList.size() > 0);
    }

    /**
     * Получить все расходы за определенный день
     * @param date день календаря
     * @return Список объектов, представляющих расходы
     */
    public List<Purchase_DB> getDayPurchases(Date date) {
        return jdbcTemplate.query("SELECT * FROM purchase WHERE day = ?", new PurchaseRowMapper(), date);
    }

    /**
     * Получить все доходы за определенный день
     * @param date день календаря
     * @return Список объектов, представляющих доходы
     */
    public List<Earning_DB> getDayEarnings(Date date) {
        return jdbcTemplate.query("SELECT * FROM earning WHERE day = ?", new EarningRowMapper(), date);
    }

    /**
     * Получить все расходы за определенный промежуток времени
     * @param lessDate календарное число (то, которое хронологически идёт раньше)
     * @param moreDate календарное число (то, которое хронологически идёт позже)
     * @return список объектов, представляющих расходы
     */
    public List<Purchase_DB> getPurchasesInTimePeriod(Date lessDate, Date moreDate) {
        return jdbcTemplate.query("SELECT * FROM purchase WHERE day BETWEEN (? AND ?)", new PurchaseRowMapper(), lessDate, moreDate);
    }

    /**
     * Получить все доходы за определенный промежуток времени
     * @param lessDate календарное число (то, которое хронологически идёт раньше)
     * @param moreDate календарное число (то, которое хронологически идёт позже)
     * @return список объектов, представляющих доходы
     */
    public List<Earning_DB> getEarningsInTimePeriod(Date lessDate, Date moreDate) {
        return jdbcTemplate.query("SELECT * FROM earning WHERE day BETWEEN (? AND ?)", new EarningRowMapper(), lessDate, moreDate);
    }

    /**
     * Получить общую сумму доходов за промежуток времени
     * @param lessDate календарное число (то, которое хронологически идёт раньше)
     * @param moreDate календарное число (то, которое хронологически идёт позже)
     * @return объект-контейнер, содержащий суммарные стоимости, разделенные по типу оплаты
     */
    public MoneyDifferenceInTime getTotalMoneyEarningsInTimePeriod(Date lessDate, Date moreDate) {
        List<Earning_DB> earnings = getEarningsInTimePeriod(lessDate, moreDate);
        MoneyDifferenceInTime moneyDifferenceInTime = new MoneyDifferenceInTime();

        for (Earning_DB earning: earnings) {
            if (earning.getPayment_type() == "cash")
                moneyDifferenceInTime.addCash_total_value(earning.getEarning_cost() * earning.getCount());
            else
                moneyDifferenceInTime.addCashless_total_value(earning.getEarning_cost() * earning.getCount());
        }
        return moneyDifferenceInTime;
    }

    /**
     * Получить общую сумму расходов за промежуток времени. Различает типы оплаты "cash", "cashless"
     * @param lessDate календарное число (то, которое хронологически идёт раньше)
     * @param moreDate календарное число (то, которое хронологически идёт позже)
     * @return
     */
    public MoneyDifferenceInTime getTotalMoneyPurchasesInTimePeriod(Date lessDate, Date moreDate) {
        List<Purchase_DB> purchases = getPurchasesInTimePeriod(lessDate, moreDate);
        MoneyDifferenceInTime moneyDifferenceInTime = new MoneyDifferenceInTime();

        for (Purchase_DB purchase: purchases) {
            if (purchase.getPayment_type() == "cashless")
                moneyDifferenceInTime.addCash_total_value(purchase.getPurchase_cost() * purchase.getCount());
            else
                moneyDifferenceInTime.addCashless_total_value(purchase.getPurchase_cost() * purchase.getCount());
        }
        return moneyDifferenceInTime;
    }
}