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
        numFutureMeetingsOnFutureDateBefore = data.manager.getMeetingListOn(data.futureDate).size();
        numPastMeetingsOnFutureDateBefore = data.manager.getMeetingListOn(data.pastDate).size();
    }

    @Before
    public void before() {
        data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(3));
        data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(1));
        data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);

        data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getPopulatedSet(), DateFns.getPastDate(8), "");
        data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getPopulatedSet(), DateFns.getPastDate(3), "");
        numFutureMeetingsOnFutureDateAfter = data.manager.getMeetingListOn(data.futureDate).size();
        numPastMeetingsOnFutureDateAfter = data.manager.getMeetingListOn(data.pastDate).size();
    }

    @Test(expected = NullPointerException.class)
    public void testGetMeetingListOnNullDate() {
        data.manager.getMeetingListOn(null);
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
        List<Meeting> meetingsFuture = data.manager.getMeetingListOn(data.futureDate);
        assertTrue(ContactManagerImplTestsFns.testDuplicateMeetings(meetingsFuture));

        List<Meeting> meetingsPast = data.manager.getMeetingListOn(data.pastDate);
        assertTrue(ContactManagerImplTestsFns.testDuplicateMeetings(meetingsPast));
    }

    @Test
    public void testSorted(){
        List<Meeting> meetingsFuture = data.manager.getMeetingListOn(data.futureDate);

        assertEquals(6, numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore);
        assertTrue(ContactManagerImplTestsFns.testChronologicallySorted(meetingsFuture));

        List<Meeting> meetingsPast = data.manager.getMeetingListOn(data.pastDate);
        assertTrue(ContactManagerImplTestsFns.testChronologicallySorted(meetingsPast));
    }
}
