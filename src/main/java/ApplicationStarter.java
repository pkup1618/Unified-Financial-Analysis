import components.configures.SpringConfig;
import components.database_handling.DatabaseRequestsHandler;
import components.database_handling.models.Date_DB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.Date;

/**
 * Из этого класса запускается приложения и подтягивается вся необходимая конфигурация.
 * Корень приложения MonManApp
 * Также используется для быстрого тестирования кода
 */
public class ApplicationStarter {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        DatabaseRequestsHandler databaseRequestsHandler =
                applicationContext.getBean("databaseRequestsHandler", DatabaseRequestsHandler.class);

        Date_DB date_db = new Date_DB(new Date(System.currentTimeMillis()), 30, 30, 30, 30);
        databaseRequestsHandler.setDay(date_db);
        System.out.println(databaseRequestsHandler.checkDateForExistence(new Date(System.currentTimeMillis())));
    }
}
