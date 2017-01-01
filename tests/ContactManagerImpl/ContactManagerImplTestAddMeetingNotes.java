import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestAddMeetingNotes {

    private ContactManagerImplTestData data;
    int futureToPastMeetingId;

    {
        data = new ContactManagerImplTestData();

    }

    /**
     * Add notes to a meeting.
     *
     * This method is used when a future meeting takes place, and is
     * then converted to a past meeting (with notes) and returned.
     *
     * It can be also used to add notes to a past meeting at a later date.
     *
     * @param id the ID of the meeting
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the meeting does not exist
     * @throws IllegalStateException if the meeting is set for a date in the future
     * @throws NullPointerException if the notes are null
     */

    @Before
    public void before(){
        futureToPastMeetingId = data.manager.addFutureMeeting(data.populatedSet, data.slightlyFutureDate);
    }

    @Test
    public void testAddNotesToExistingPastMeeting(){
        int id = data.manager.addNewPastMeeting(data.populatedSet, data.pastDate, "");
        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(id);
        assertEquals("", meeting.getNotes());

        String notes = "Some notes added to the meeting\n\n\nYeah!";
        meeting = data.manager.addMeetingNotes(id, notes);
        assertEquals(id, meeting.getId());
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void testAddNotesToExistingFutureMeeting() {
        ContactManagerImplTestsFns.wait2Secs();
        String notes = "Some notes added to the meeting\n\n\nYeah!!";
        PastMeeting meeting = data.manager.addMeetingNotes(futureToPastMeetingId, notes);
        assertEquals(futureToPastMeetingId, meeting.getId());
        assertEquals(notes, meeting.getNotes());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddNotesNoExistMeeting(){
        String notes = "Some notes added to the meeting\n\n\nYeah!!!";
        data.manager.addMeetingNotes(Integer.MAX_VALUE, notes);
    }

    @Test(expected=IllegalStateException.class)
    public void testAddNotesFutureMeeting(){
        int id = data.manager.addFutureMeeting(data.populatedSet, data.futureDate);

        String notes = "Some notes added to the meeting\n\n\nYeah!!!!";
        PastMeeting meeting = data.manager.addMeetingNotes(id, notes);
    }

    @Test(expected=NullPointerException.class)
    public void testAddNotesNull(){
        int id = data.manager.addNewPastMeeting(data.populatedSet, data.pastDate, "");

        String notes = "Some notes added to the meeting\n\n\nYeah!!!!!";
        PastMeeting meeting = data.manager.addMeetingNotes(id, null);
    }
}
