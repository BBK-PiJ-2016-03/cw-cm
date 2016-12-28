import tests.DateFns;

import java.util.Calendar;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImplTestAddNewPastMeeting {

    private Calendar pastDate;
    private Calendar futureDate;

    {
        futureDate = DateFns.getFutureDate();
        pastDate = DateFns.getPastDate();
    }

}
