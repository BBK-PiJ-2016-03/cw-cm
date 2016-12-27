import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestAddNotes {

    private ContactImpl contact;

    @Test
    public void addNotesTestNoExisting(){
        String note = "This is a note!";
        contact = new ContactImpl(0, "");
        contact.addNotes(note);
        assertEquals(note, contact.getNotes());
    }

    @Test
    public void addNotesTestExisting(){
        String note = "This is a note!";
        contact = new ContactImpl(0, "", "");
        contact.addNotes(note);
        assertEquals(note, contact.getNotes());
    }

    @Test
    public void addNotesTestEmptyNoExisting(){
        String note = "";
        contact = new ContactImpl(0, "");
        contact.addNotes(note);
        assertEquals(note, contact.getNotes());
    }

    @Test
    public void addNotesTestEmptyExisting(){
        String note = "";
        contact = new ContactImpl(0, "", "");
        contact.addNotes(note);
        assertEquals(note, contact.getNotes());
    }

    @Test(expected = NullPointerException.class)
    public void addNotesTestNullNoExisting(){
        String note = null;
        contact = new ContactImpl(0, "");
        contact.addNotes(note);
        assertEquals(note, contact.getNotes());
    }

    @Test(expected = NullPointerException.class)
    public void addNotesTestNullExisting(){
        String note = null;
        contact = new ContactImpl(0, "", "");
        contact.addNotes(note);
        assertEquals(note, contact.getNotes());
    }
}
