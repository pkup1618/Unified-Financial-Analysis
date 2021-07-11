package components.data_handling;

import java.sql.Date;


public class DataHandler {
    public static Date get_current_date(){
        return new Date(System.currentTimeMillis());
    }
}



