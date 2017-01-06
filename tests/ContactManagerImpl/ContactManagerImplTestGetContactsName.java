import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsName {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
        data.manager = new ContactManagerImpl();
        data.manager.addNewContact("GetContactsName1", "Notes");
        data.manager.addNewContact("GetContactsName2", "Notes");
        data.manager.addNewContact("GetContactsName2", "Notes");
    }

    /**
     * Returns a set with the contacts whose name contains that string.
     *
     * If the string is the empty string, this methods returns the set
     * that contains all current contacts.
     *
     * @param name the string to search for
     * @return a set with the contacts whose name contains that string.
     * @throws NullPointerException if the parameter is null
     */

    @Test
    public void testGetExistingContact(){
        Set<Contact> contacts = data.manager.getContacts("GetContactsName1");
        assertEquals(1, contacts.size());
    }

    @Test
    public void testGetExistingContacts(){
        Set<Contact> contacts = data.manager.getContacts("GetContactsName2");
        assertEquals(2, contacts.size());
    }

    @Test
    public void testGetExistingContactEmpty(){
        int beforeSize = data.manager.getContacts("").size();
        data.manager.addNewContact("Name X", "Notes X");
        data.manager.addNewContact("Name Y", "Notes Y");

        int afterSize = data.manager.getContacts("").size();
        assertEquals(beforeSize+2, afterSize);
    }

    @Test
    public void testGetExistingContactUnknown(){
        int contactsSize = data.manager.getContacts("GetContactsName-Name5").size();
        assertTrue(contactsSize == 0);
    }

    @Test(expected=NullPointerException.class)
    public void testGetContactNullName(){
        data.manager.getContacts((String)null);
    }
}
