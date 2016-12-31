import org.junit.Test;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsIds {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Returns a set containing the contacts that correspond to the IDs.
     * Note that this method can be used to retrieve just one contact by passing only one ID.
     *
     * @param ids an arbitrary number of contact IDs
     * @return a set containing the contacts that correspond to the IDs.
     * @throws IllegalArgumentException if no IDs are provided or if
     *     any of the provided IDs does not correspond to a real contact
     */

    @Test
    public void testGetExistingContact(){
        data.manager = new ContactManagerImpl();
        int[] contactIds = ContactManagerImplTestsFns.generateValidContactIds(1, data.manager);
        Set<Contact> contacts = data.manager.getContacts(contactIds);
        assertTrue(verifyContactIdsReturned(contacts, contactIds));
    }

    private boolean verifyContactIdsReturned(Set<Contact> contacts, int[] contactIds) {
        return contacts.stream()
                .map(c -> IntStream.of(contactIds).anyMatch(i -> i == c.getId()))
                .reduce((b1, b2) -> b1 && b2)
                .get();
    }

    @Test
    public void testGetExistingContacts(){
        data.manager = new ContactManagerImpl();
        int[] contactIds = ContactManagerImplTestsFns.generateValidContactIds(100, data.manager);
        Set<Contact> contacts = data.manager.getContacts(contactIds);
        assertTrue(verifyContactIdsReturned(contacts, contactIds));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetEmptySet(){
        data.manager = new ContactManagerImpl();
        int[] contactIds = new int[0];
        Set<Contact> contacts = data.manager.getContacts(contactIds);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetUnknownContact(){
        data.manager = new ContactManagerImpl();
        int[] contactIds = {1,9876543,2324253,323537};
        Set<Contact> contacts = data.manager.getContacts(contactIds);
    }
}
