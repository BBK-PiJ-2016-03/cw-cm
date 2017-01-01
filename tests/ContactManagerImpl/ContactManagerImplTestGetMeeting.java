import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
    public void testNotes(){
        String note = "These are the Notes";
        int id = data.manager.addNewPastMeeting(data.populatedSet, data.pastDate, note);

        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(id);
        assertTrue(meeting.getNotes().equals(note));
    }
}
