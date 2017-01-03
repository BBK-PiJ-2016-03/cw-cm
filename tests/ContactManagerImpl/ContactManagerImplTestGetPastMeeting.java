import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetPastMeeting {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Returns the PAST meeting with the requested ID, or null if it there is none.
     *
     * The meeting must have happened at a past date.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalStateException if there is a meeting with that ID happening
     *         in the future
     */

    @Test
    public void testGetNonExistMeeting(){
        Meeting meeting = data.manager.getPastMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }

    @Test
    public void testGetMeetingPast(){
        int id = data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        PastMeeting meeting = data.manager.getPastMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.pastDate, meeting.getDate());
        assertEquals(data.getpopulatedSet(), meeting.getContacts());
    }

    @Test
    public void testGetMeetingSlightlyPast(){
        int id = data.manager.addNewPastMeeting(data.getpopulatedSet(), data.slightlyPastDate, "");
        PastMeeting meeting = data.manager.getPastMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.slightlyPastDate, meeting.getDate());
        assertEquals(data.getpopulatedSet(), meeting.getContacts());
    }

    // can we assume that future events that now have a date in the past, but have not had
    // notes added to them did not actually take place?
    @Test
    public void testGetMeetingNotHappened(){
        int id = data.manager.addFutureMeeting(data.getpopulatedSet(), data.slightlyFutureDate);
        ContactManagerImplTestsFns.wait2Secs();
        Meeting meeting = data.manager.getPastMeeting(id);
        assertNull(meeting);
    }

    @Test(expected=IllegalStateException.class)
    public void testGetMeetingFuture(){
        int id = data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.getPastMeeting(id);
    }
}
