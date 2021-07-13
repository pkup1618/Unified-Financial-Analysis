package components.support_classes;

import java.sql.Date;

/**
 * Класс для работы с java-реализацией времени.
 * (Предполагается, что он потребует большого расширения в дальнейшем)
 */
public class TimeHandling {
    public static Date get_current_date(){
        return new Date(System.currentTimeMillis());
    }
}



