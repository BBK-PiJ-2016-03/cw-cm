import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsName {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
        data.setManager(new ContactManagerImpl());
        data.getManager().addNewContact("GetContactsName1", "Notes");
        data.getManager().addNewContact("GetContactsName2", "Notes");
        data.getManager().addNewContact("GetContactsName2", "Notes");
    }

    @Test
    public void testGetExistingContact(){
        Set<Contact> contacts = data.getManager().getContacts("GetContactsName1");
        assertEquals(1, contacts.size());
    }

    @Test
    public void testGetExistingContacts(){
        Set<Contact> contacts = data.getManager().getContacts("GetContactsName2");
        assertEquals(2, contacts.size());
    }

    @Test
    public void testGetExistingContactEmpty(){
        int beforeSize = data.getManager().getContacts("").size();
        data.getManager().addNewContact("Name X", "Notes X");
        data.getManager().addNewContact("Name Y", "Notes Y");

        int afterSize = data.getManager().getContacts("").size();
        assertEquals(beforeSize+2, afterSize);
    }

    @Test
    public void testGetExistingContactUnknown(){
        int contactsSize = data.getManager().getContacts("GetContactsName-Name5").size();
        assertTrue(contactsSize == 0);
    }

    @Test(expected=NullPointerException.class)
    public void testGetContactNullName(){
        data.getManager().getContacts((String)null);
    }
}
