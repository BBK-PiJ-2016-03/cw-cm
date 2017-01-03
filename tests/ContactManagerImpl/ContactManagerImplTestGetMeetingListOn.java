import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetMeetingListOn {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
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
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);

        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
    }

    @Test(expected=NullPointerException.class)
    public void testGetMeetingListOnNullDate(){
        List<Meeting> meetings = data.manager.getMeetingListOn(null);
    }

    @Test
    public void testGetMeetingListOnFutureDate(){
        List<Meeting> meetings = data.manager.getMeetingListOn(data.futureDate);
        assertEquals(6, meetings.size());
    }

    @Test
    public void testGetMeetingListOnPastDate(){
        List<Meeting> meetings = data.manager.getMeetingListOn(data.pastDate);
        assertEquals(4, meetings.size());
    }

}
