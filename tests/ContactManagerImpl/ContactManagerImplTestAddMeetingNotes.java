import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestAddMeetingNotes {

    private final ContactManagerImplTestData data;
    private final String notes;
    private int futureToPastMeetingId;

    {
        data = new ContactManagerImplTestData();
        notes = "Some notes added to the meeting\n\n\nYeah!";
    }

    @Before
    public void before(){
        futureToPastMeetingId = data.manager.addFutureMeeting(data.getPopulatedSet(), data.slightlyFutureDate);
    }

    @Test
    public void testAddNotesToExistingPastMeeting(){
        int id = data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, "");
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
        int id = data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        data.manager.addMeetingNotes(id, this.notes);
    }

    @Test(expected=NullPointerException.class)
    public void testAddNotesNull(){
        int id = data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, "");
        data.manager.addMeetingNotes(id, null);
    }
}
