import org.junit.Test;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsIds {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testGetExistingContact(){
        data.setManager(new ContactManagerImpl());
        int contactId = data.getManager().addNewContact("New", "Notes");
        Set<Contact> contacts = data.getManager().getContacts(contactId);
        assertTrue(verifyContactIdsReturned(contacts, contactId));
    }

    private boolean verifyContactIdsReturned(Set<Contact> contacts, int... contactIds) {
        return contacts.stream()
                .map(c -> IntStream.of(contactIds).anyMatch(i -> i == c.getId()))
                .reduce((b1, b2) -> b1 && b2)
                .get();
    }

    @Test
    public void testGetExistingContacts(){
        int[] contactIds = ContactManagerImplTestsFns.generateValidContactIds(100, data.getManager());
        Set<Contact> contacts = data.getManager().getContacts(contactIds);
        assertTrue(verifyContactIdsReturned(contacts, contactIds));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetEmptySet(){
        data.setManager(new ContactManagerImpl());
        int[] contactIds = new int[0];
        data.getManager().getContacts(contactIds);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetUnknownContact(){
        data.setManager(new ContactManagerImpl());
        int[] contactIds = {1,9876543,2324253,323537};
        data.getManager().getContacts(contactIds);
    }
}
