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
        int id = data.manager.addFutureMeeting(data.populatedSet, data.pastDate);
        Meeting meeting = data.manager.getPastMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.pastDate, meeting.getDate());
        assertEquals(data.populatedSet, meeting.getContacts());
    }

    @Test
    public void testGetMeetingSlightlyPast(){
        int id = data.manager.addFutureMeeting(data.populatedSet, data.slightlyPastDate);
        Meeting meeting = data.manager.getPastMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.slightlyPastDate, meeting.getDate());
        assertEquals(data.populatedSet, meeting.getContacts());
    }

    // can we assume that only events that occurred has notes added to them
    // and if en event was scheduled for the future, but did not happen, it's
    // listed as a futureMeeting with a past date?
//    @Test
//    public void testGetMeetingNotHappened(){
//        int id = data.manager.addFutureMeeting(data.populatedSet, data.slightlyFutureDate);
//        ContactManagerImplTestsFns.wait2Secs();
//        Meeting meeting = data.manager.getPastMeeting(id);
//        assertNull(meeting);
//    }

    @Test(expected=IllegalStateException.class)
    public void testGetMeetingFuture(){
        int id = data.manager.addNewPastMeeting(data.populatedSet, data.futureDate, "");
        data.manager.getPastMeeting(id);
    }
}
