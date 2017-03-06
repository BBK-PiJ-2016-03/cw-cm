import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetFutureMeeting {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testGetNonExistMeeting(){
        Meeting meeting = data.getManager().getFutureMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }

    @Test
    public void testGetMeetingFuture(){
        int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        Meeting meeting = data.getManager().getFutureMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.getFutureDate(), meeting.getDate());
        assertEquals(data.getPopulatedSet(), meeting.getContacts());
    }

    @Test
    public void testGetMeetingSlightlyFuture(){
        int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getSlightlyFutureDate());
        Meeting meeting = data.getManager().getFutureMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.getSlightlyFutureDate(), meeting.getDate());
        assertEquals(data.getPopulatedSet(), meeting.getContacts());
    }

    @Test(expected=IllegalStateException.class)
    public void testGetMeetingPast(){
        int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), "");
        data.getManager().getFutureMeeting(id);
    }
}
