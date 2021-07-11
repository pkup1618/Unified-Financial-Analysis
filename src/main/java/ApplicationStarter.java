import components.DataAccessObject;
import components.data_handling.DataHandler;
import models.Date_DB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        for (String name : applicationContext.getBeanDefinitionNames())
        {
            System.out.println(name);
        }
    }
    public void db_access_check(){ //works!
        // need app ctx
        Date_DB dateDB = new Date_DB();
        dateDB.setDay(DataHandler.get_current_date());
        dateDB.setCash_value_on_day_start(30);
        dateDB.setCash_value_on_day_end(30);
        dateDB.setCashless_value_on_day_start(50);
        dateDB.setCashless_value_on_day_end(50);
        // need dao bean
    }
}
//Сюда я буду писать задачи, которые необходимо решить.

//Мне нужно поставить систему контроля версий GIT