package manager;

import spec.Contact;
import org.junit.Before;
import org.junit.Test;
import spec.PastMeeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class PastMeetingImplTestGetNotes {

    private PastMeeting meeting;
    private final Calendar date;
    private final Set<Contact> populatedSet;

    {
        date = Calendar.getInstance();
        populatedSet = new HashSet<>();
    }

    @Before
    public void before(){
        populatePopulatedSet();
    }

    private void populatePopulatedSet(){
        populatedSet.add(new ContactImpl(1, "Name Of"));
    }

    @Test
    public void getNotesTestSingle(){
        int id = 1;
        String notes = "Test";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void getNameTestSpaced(){
        int id = 1;
        String notes = "Test Test";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void getNameTestSpacedMultiple(){
        int id = 1;
        String notes = "Test Test Test Test Test";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void getNameTestEmpty(){
        int id = 1;
        String notes = "";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void getNameTestSpecialChars(){
        int id = 1;
        String notes = "*&^%£\"'@";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void getNameTestNewLine(){
        int id = 1;
        String notes = "A\nB\nC";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }

    @Test
    public void getNameTestTab(){
        int id = 1;
        String notes = "A\tB\tC";
        meeting = new PastMeetingImpl(id, date, populatedSet, notes);
        assertEquals(notes, meeting.getNotes());
    }
}
