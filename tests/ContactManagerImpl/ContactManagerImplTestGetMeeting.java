import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetMeeting {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Returns the meeting with the requested ID, or null if it there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     */

    @Test
    public void testGetMeetingExisting(){
        String note = "note";
        int id = data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, note);

        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(id);
        assertTrue(meeting.getContacts().equals(data.getpopulatedSet()));
        assertTrue(meeting.getDate().equals(data.pastDate));
        assertTrue(meeting.getNotes().equals(note));
    }

    @Test
    public void testGetMeetingNonExisting(){
        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(Integer.MAX_VALUE);
        assertNull(meeting);
    }
}
