package takeout.yummy.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/7
 */
public class TimeFormatChange {

    public static LocalDate getLocalDateTime(String str){
        LocalDate time = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return  time;
    }
}
