package tests;

import java.util.Calendar;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class DateFns {

    private static final Calendar calendar = Calendar.getInstance();
    private static final int year = calendar.get(Calendar.YEAR);
    private static final int month = calendar.get(Calendar.MONTH);
    private static final int day = calendar.get(Calendar.DAY_OF_WEEK);

    public static Calendar getCurrentDate(){
        return Calendar.getInstance();
    }

    public static Calendar getPastDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(year-1, month, 1);
        return cal;
    }

    public static Calendar getPastDate(int offset){
        Calendar cal = Calendar.getInstance();
        cal.set(year-1, month, 1);
        cal.add(Calendar.HOUR, offset);
        return cal;
    }

    public static Calendar getFutureDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(year+1, month, 1);
        return cal;
    }

    public static Calendar getFutureDate(int offset){
        Calendar cal = Calendar.getInstance();
        cal.set(year+1, month, 1);
        cal.add(Calendar.HOUR, offset);
        return cal;
    }

    public static Calendar getSlightlyFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 1);
        return cal;
    }

    public static Calendar getSlightlyPastDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -1);
        return cal;
    }
}
