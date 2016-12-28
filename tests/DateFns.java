import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class DateFns {

    private static final Calendar calendar = Calendar.getInstance();
    private static final int year = calendar.get(Calendar.YEAR);
    private static final int month = calendar.get(Calendar.MONTH);
    private static final int day = calendar.get(Calendar.DAY_OF_WEEK);

    public static Date getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getPastDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(year-1, month, day);
        return cal.getTime();
    }

    public static Date getFutureDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(year+1, month, day);
        return cal.getTime();
    }
}
