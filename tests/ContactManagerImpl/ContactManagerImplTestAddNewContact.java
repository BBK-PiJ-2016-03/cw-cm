import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;


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
        int returnedId = data.manager.addNewContact("Name1", "Notes1");
        assertTrue(returnedId > 0);

        int returnedId2 = data.manager.addNewContact("Name2", "Notes2");
        assertTrue(returnedId2 > 0 && returnedId2 != returnedId);
    }

    @Test
    public void testContactAdded(){
        String name = "Barry White";
        String notes = "These are the notes";
        int id = data.manager.addNewContact(name, notes);
        int[] ids = new int[1];
        ids[0] = id;
        Set<Contact> contacts = data.manager.getContacts(ids);
        assertTrue(contacts.stream()
                .anyMatch(c -> c.getName() == name));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNameEmpty(){ data.manager.addNewContact("", "Notes"); }

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
