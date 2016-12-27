import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestGetNotes {
    private ContactImpl contact;

    @Test
    public void getNotesTestSingle(){
        String name = "Test";
        String notes = "Test";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNameTestSpaced(){
        String name = "Test";
        String notes = "Test Test";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNameTestSpacedMultiple(){
        String name = "Test";
        String notes = "Test Test Test Test Test";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNameTestEmpty(){
        String name = "Test";
        String notes = "";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNameTestSpecialChars(){
        String name = "Test";
        String notes = "*&^%£\"'@";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNameTestNewLine(){
        String name = "Test";
        String notes = "A\nB\nC";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNameTestTab(){
        String name = "Test";
        String notes = "A\tB\tC";
        contact = new ContactImpl(1, name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }
}
