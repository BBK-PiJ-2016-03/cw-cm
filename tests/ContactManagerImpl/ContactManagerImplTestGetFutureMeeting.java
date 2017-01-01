import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetFutureMeeting {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Returns the FUTURE meeting with the requested ID, or null if there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalStateException if there is a meeting with that ID happening
     *         in the past
     */

    @Test
    public void testGetNonExistMeeting(){
        Meeting meeting = data.manager.getFutureMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }

    @Test
    public void testGetMeetingFuture(){
        int id = data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        Meeting meeting = data.manager.getFutureMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.futureDate, meeting.getDate());
        assertEquals(data.populatedSet, meeting.getContacts());
    }

    @Test
    public void testGetMeetingSlightlyFuture(){
        int id = data.manager.addFutureMeeting(data.populatedSet, data.slightlyFutureDate);
        Meeting meeting = data.manager.getFutureMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.slightlyFutureDate, meeting.getDate());
        assertEquals(data.populatedSet, meeting.getContacts());
    }

    @Test(expected=IllegalStateException.class)
    public void testGetMeetingPast(){
        int id = data.manager.addNewPastMeeting(data.populatedSet, data.futureDate, "");
        data.manager.getFutureMeeting(id);
    }
}
