import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetMeetingListOn {

    private final ContactManagerImplTestData data;
    private final int numFutureMeetingsOnFutureDateBefore;
    private int numFutureMeetingsOnFutureDateAfter;
    private final int numPastMeetingsOnFutureDateBefore;
    private int numPastMeetingsOnFutureDateAfter;

    {
        data = new ContactManagerImplTestData();
        numFutureMeetingsOnFutureDateBefore = data.getManager().getMeetingListOn(data.getFutureDate()).size();
        numPastMeetingsOnFutureDateBefore = data.getManager().getMeetingListOn(data.getPastDate()).size();
    }

    @Before
    public void before() {
        data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        data.getManager().addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(3));
        data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        data.getManager().addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(1));
        data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());

        data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), "");
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), DateFns.getPastDate(8), "");
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), "");
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), DateFns.getPastDate(3), "");
        numFutureMeetingsOnFutureDateAfter = data.getManager().getMeetingListOn(data.getFutureDate()).size();
        numPastMeetingsOnFutureDateAfter = data.getManager().getMeetingListOn(data.getPastDate()).size();
    }

    @Test(expected = NullPointerException.class)
    public void testGetMeetingListOnNullDate() {
        data.getManager().getMeetingListOn(null);
    }

    @Test
    public void testGetMeetingListOnFutureDate() {
        assertEquals(6, numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore);
    }

    @Test
    public void testGetMeetingListOnPastDate() {
        assertEquals(4, numPastMeetingsOnFutureDateAfter - numPastMeetingsOnFutureDateBefore);
    }

    @Test
    public void testNoDuplicates(){
        List<Meeting> meetingsFuture = data.getManager().getMeetingListOn(data.getFutureDate());
        assertTrue(ContactManagerImplTestsFns.testDuplicateMeetings(meetingsFuture));

        List<Meeting> meetingsPast = data.getManager().getMeetingListOn(data.getPastDate());
        assertTrue(ContactManagerImplTestsFns.testDuplicateMeetings(meetingsPast));
    }

    @Test
    public void testSorted(){
        List<Meeting> meetingsFuture = data.getManager().getMeetingListOn(data.getFutureDate());

        assertEquals(6, numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore);
        assertTrue(ContactManagerImplTestsFns.testChronologicallySorted(meetingsFuture));

        List<Meeting> meetingsPast = data.getManager().getMeetingListOn(data.getPastDate());
        assertTrue(ContactManagerImplTestsFns.testChronologicallySorted(meetingsPast));
    }
}
