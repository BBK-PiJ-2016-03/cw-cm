package manager;

import spec.Contact;
import org.junit.Before;
import org.junit.Test;
import spec.FutureMeeting;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class FutureMeetingImplTestGetId {

    private FutureMeeting meeting;
    private final Calendar date;

    private final Set<Contact> populatedSet;

    {
        date = Calendar.getInstance();
        populatedSet = new HashSet<>();
    }

    @Before
    public void before(){
        populatePopulatedSet();
    }

    private void populatePopulatedSet(){
        populatedSet.add(new ContactImpl(1, "Name Of"));
    }

    @Test
    public void getIdTestMin(){
        int id = 1;
        meeting = new FutureMeetingImpl(id, date, populatedSet);
        assertEquals(id, meeting.getId());
    }

    @Test
    public void getIdTestMax(){
        int id = Integer.MAX_VALUE;
        meeting = new FutureMeetingImpl(id, date, populatedSet);
        assertEquals(id, meeting.getId());
    }
}
