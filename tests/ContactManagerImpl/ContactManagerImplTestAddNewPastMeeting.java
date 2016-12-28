import org.junit.Before;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImplTestAddNewPastMeeting {

    private Date pastDate;
    private Date futureDate;

    {
        futureDate = DateFns.getFutureDate();
        pastDate = DateFns.getPastDate();
    }

}
