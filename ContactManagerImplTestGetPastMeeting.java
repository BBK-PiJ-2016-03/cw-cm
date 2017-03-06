import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetPastMeeting {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testGetNonExistMeeting(){
        Meeting meeting = data.getManager().getPastMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }

    @Test
    public void testGetMeetingPast(){
        int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), "");
        PastMeeting meeting = data.getManager().getPastMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.getPastDate(), meeting.getDate());
        assertEquals(data.getPopulatedSet(), meeting.getContacts());
    }

    @Test
    public void testGetMeetingSlightlyPast(){
        int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getSlightlyPastDate(), "");
        PastMeeting meeting = data.getManager().getPastMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.getSlightlyPastDate(), meeting.getDate());
        assertEquals(data.getPopulatedSet(), meeting.getContacts());
    }

    // can we assume that future events that now have a date in the past, but have not had
    // notes added to them did not actually take place?
    @Test
    public void testGetMeetingNotHappened(){
        int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getSlightlyFutureDate());
        ContactManagerImplTestsFns.wait2Secs();
        Meeting meeting = data.getManager().getPastMeeting(id);
        assertNull(meeting);
    }

    @Test(expected=IllegalStateException.class)
    public void testGetMeetingFuture(){
        int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        data.getManager().getPastMeeting(id);
    }
}
