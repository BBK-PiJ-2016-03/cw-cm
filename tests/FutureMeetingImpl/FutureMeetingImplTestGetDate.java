import org.junit.Before;
import org.junit.Test;
import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class FutureMeetingImplTestGetDate {

    private FutureMeeting meeting;
    private final Calendar pastDate;
    private final Calendar futureDate;
    private final Set<Contact> populatedSet;

    {
        populatedSet = new HashSet<>();
        futureDate = DateFns.getFutureDate();
        pastDate = DateFns.getPastDate();
    }

    @Before
    public void before(){
        populatePopulatedSet();
    }

    private void populatePopulatedSet(){
        populatedSet.add(new ContactImpl(1, "Name Of"));
    }

    @Test
    public void getDateTestPast(){
        int id = 1;
        meeting = new FutureMeetingImpl(id, pastDate, populatedSet);
        assertEquals(pastDate, meeting.getDate());
    }

    @Test
    public void getDateTestFuture(){
        int id = 1;
        meeting = new FutureMeetingImpl(id, futureDate, populatedSet);
        assertEquals(futureDate, meeting.getDate());
    }
}
