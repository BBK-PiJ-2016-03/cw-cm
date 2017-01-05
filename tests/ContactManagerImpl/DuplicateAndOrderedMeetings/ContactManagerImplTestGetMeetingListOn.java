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

    {
        data = new ContactManagerImplTestData();
        numFutureMeetingsOnFutureDateBefore = data.manager.getMeetingListOn(data.futureDate).size();
    }

    /**
     * Returns the list of meetings that are scheduled for, or that took
     * place on, the specified date
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param date the date
     * @return the list of meetings
     * @throws NullPointerException if the date are null
     */

    @Before
    public void before(){
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
    }

    @Test(expected=NullPointerException.class)
    public void testGetMeetingListOnNullDate(){
        List<Meeting> meetings = data.manager.getMeetingListOn(null);
    }

    @Test
    public void testGetMeetingListOnFutureDate(){
        List<Meeting> meetings = data.manager.getMeetingListOn(data.futureDate);
        assertEquals(6, numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore);
    }

    @Test
    public void testGetMeetingListOnPastDate(){
        List<Meeting> meetings = data.manager.getMeetingListOn(data.pastDate);
        assertEquals(4, meetings.size());
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
        System.out.println("Sorted check: "+meetingsFuture.size());
        System.out.println("data.futureDate: "+data.futureDate.getTime());
        System.out.println("Date offset 3: "+DateFns.getFutureDate(3));


        assertEquals(6, numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore);
        assertTrue(ContactManagerImplTestsFns.testChronologicallySorted(meetingsFuture));

        List<Meeting> meetingsPast = data.manager.getMeetingListOn(data.pastDate);
        assertTrue(ContactManagerImplTestsFns.testChronologicallySorted(meetingsPast));
    }



}
