import org.junit.Before;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImplTestAddNewPastMeeting {

    private Date pastDate;
    private Date futureDate;

    @Before
    public void before(){
        setDates();
    }

    private void setDates() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        cal.set(year+1, month, day);
        futureDate = cal.getTime();

        cal.set(year-1, month, day);
        pastDate = cal.getTime();
    }
}
