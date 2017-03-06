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
        Meeting meeting = data.manager.getFutureMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }

    @Test
    public void testGetMeetingFuture(){
        int id = data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        Meeting meeting = data.manager.getFutureMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.futureDate, meeting.getDate());
        assertEquals(data.getPopulatedSet(), meeting.getContacts());
    }

    @Test
    public void testGetMeetingSlightlyFuture(){
        int id = data.manager.addFutureMeeting(data.getPopulatedSet(), data.slightlyFutureDate);
        Meeting meeting = data.manager.getFutureMeeting(id);
        assertEquals(id, meeting.getId());
        assertEquals(data.slightlyFutureDate, meeting.getDate());
        assertEquals(data.getPopulatedSet(), meeting.getContacts());
    }

    @Test(expected=IllegalStateException.class)
    public void testGetMeetingPast(){
        int id = data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, "");
        data.manager.getFutureMeeting(id);
    }
}
