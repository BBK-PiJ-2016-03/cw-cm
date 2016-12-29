import org.junit.Test;
import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestAddNewContact {

    /**
     * Create a new contact with the specified name and notes.
     *
     * @param name the name of the contact.
     * @param notes notes to be added about the contact.
     * @return the ID for the new contact
     * @throws IllegalArgumentException if the name or the notes are empty strings
     * @throws NullPointerException if the name or the notes are null
     */

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testGetId(){
        int returnedId = data.manager.addNewContact("Name", "Notes");
        assertTrue(returnedId > 0);

        int returnedId2 = data.manager.addNewContact("Name", "Notes");
        assertTrue(returnedId2 > 0 && returnedId2 != returnedId);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNameEmpty(){
        data.manager.addNewContact("", "Notes");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNotesEmpty(){
        data.manager.addNewContact("Name", "");
    }

    @Test(expected=NullPointerException.class)
    public void testNameNull(){
        data.manager.addNewContact(null, "Notes");
    }

    @Test(expected=NullPointerException.class)
    public void testNotesNull(){
        data.manager.addNewContact("Name", null);
    }

}
