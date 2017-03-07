package manager;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestGetNotes {
    private ContactImpl contact;
    private final String name;

    {
        name = "Test";
    }

    @Test
    public void getNotesTestSingle(){
        String notes = "getNotesTestSingleNotes";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNotesTestSpaced(){
        String notes = "Test Test";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNotesTestSpacedMultiple(){
        String notes = "Test Test Test Test Test";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNotesTestEmpty(){
        String notes = "";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNotesTestSpecialChars(){
        String notes = "*&^%£\"'@";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNotesTestNewLine(){
        String notes = "A\nB\nC";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }

    @Test
    public void getNotesTestTab(){
        String notes = "A\tB\tC";
        contact = new ContactImpl(1, this.name, notes);
        Assert.assertEquals(notes, contact.getNotes());
    }
}
