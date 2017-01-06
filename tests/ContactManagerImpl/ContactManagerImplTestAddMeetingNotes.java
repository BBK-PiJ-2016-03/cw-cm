import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestAddMeetingNotes {

    private ContactManagerImplTestData data;
    private String notes;
    int futureToPastMeetingId;

    {
        data = new ContactManagerImplTestData();
        notes = "Some notes added to the meeting\n\n\nYeah!";
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
        futureToPastMeetingId = data.manager.addFutureMeeting(data.getpopulatedSet(), data.slightlyFutureDate);
    }

    @Test
    public void testAddNotesToExistingPastMeeting(){
        int id = data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        PastMeeting meeting = (PastMeeting)data.manager.getMeeting(id);
        assertEquals("", meeting.getNotes());

        meeting = data.manager.addMeetingNotes(id, this.notes);

        assertEquals(id, meeting.getId());
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void testAddNotesToExistingFutureMeeting() {
        ContactManagerImplTestsFns.wait2Secs();
        PastMeeting meeting = data.manager.addMeetingNotes(futureToPastMeetingId, this.notes);
        assertEquals(futureToPastMeetingId, meeting.getId());
        assertEquals(notes, meeting.getNotes());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddNotesNoExistMeeting(){
        data.manager.addMeetingNotes(Integer.MAX_VALUE, this.notes);
    }

    @Test(expected=IllegalStateException.class)
    public void testAddNotesFutureMeeting(){
        int id = data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addMeetingNotes(id, this.notes);
    }

    @Test(expected=NullPointerException.class)
    public void testAddNotesNull(){
        int id = data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addMeetingNotes(id, null);
    }
}
