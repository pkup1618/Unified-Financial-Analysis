package components.data_handling;

import java.sql.Date;

/**
 * Класс для работы с java-реализацией времени.
 * (Предполагается, что он потребует большого расширения в дальнейшем)
 */
public class DataHandler {
    public static Date get_current_date(){
        return new Date(System.currentTimeMillis());
    }
}



