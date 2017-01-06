import org.junit.Before;
import org.junit.Test;
import tests.DateFns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetMeetingListOn {

    private ContactManagerImplTestData data;
    int numFutureMeetingsOnFutureDateBefore;
    int numFutureMeetingsOnFutureDateAfter;
    int numPastMeetingsOnFutureDateBefore;
    int numPastMeetingsOnFutureDateAfter;

    {
        data = new ContactManagerImplTestData();
        numFutureMeetingsOnFutureDateBefore = data.manager.getMeetingListOn(data.futureDate).size();
        numPastMeetingsOnFutureDateBefore = data.manager.getMeetingListOn(data.pastDate).size();
    }

    /**
     * Returns the list of meetings that are scheduled for, or that took
     * place on, the specified date
     * <p>
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param date the date
     * @return the list of meetings
     * @throws NullPointerException if the date are null
     */

    @Before
    public void before() {
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(3));
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(1));
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);

        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(8), "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(3), "");
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
