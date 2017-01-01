import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.Set;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsName {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
        data.manager.addNewContact("Name1", "Notes");
        data.manager.addNewContact("Name2", "Notes");
        data.manager.addNewContact("Name2", "Notes");
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
        Set<Contact> contacts = data.manager.getContacts("Name1");
        assertTrue(contacts.size() == 1);
    }

    @Test
    public void testGetExistingContacts(){
        Set<Contact> contacts = data.manager.getContacts("Name1");
        assertTrue(contacts.size() == 2);
    }

    @Test
    public void testGetExistingContactEmpty(){
        Set<Contact> contacts = data.manager.getContacts("");
        assertTrue(contacts.size() == 3);
    }

    @Test
    public void testGetExistingContactUnknown(){
        Set<Contact> contacts = data.manager.getContacts("Name5");
        assertTrue(contacts.size() == 0);
    }

    @Test(expected=NullPointerException.class)
    public void testGetContactNullName(){
        Set<Contact> contacts = data.manager.getContacts((String)null);
    }
}
