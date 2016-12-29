import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestData {

    public ContactManager manager;
    public Calendar pastDate;
    public Calendar futureDate;
    public Set<Contact> emptySet;
    public Set<Contact> populatedSet;
    public Set<Contact> populatedSetWithInvalidContact;
    public Set<Contact> populatedSetWithNullContact;

    {
        manager = new ContactManagerImpl();
        pastDate = DateFns.getPastDate();
        futureDate = DateFns.getFutureDate();
        emptySet = new HashSet<>();
        populatedSet = ContactManagerImplTestsFns.generateValidContacts(500_000, manager);
        populatedSetWithInvalidContact = ContactManagerImplTestsFns.generateInvalidContacts(500_000, manager);
        populatedSetWithNullContact = ContactManagerImplTestsFns.generateNullContacts(10_000, manager);
    }
}
