import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetMeeting {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testGetMeetingExisting(){
        String note = "note";
        int id = data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, note);

        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(id);
        assertTrue(meeting.getContacts().equals(data.getPopulatedSet()));
        assertTrue(meeting.getDate().equals(data.pastDate));
        assertTrue(meeting.getNotes().equals(note));
    }

    @Test
    public void testGetMeetingNonExisting(){
        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }
}
