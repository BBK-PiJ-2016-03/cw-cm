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
    public Calendar slightlyPastDate;
    public Calendar futureDate;
    public Calendar slightlyFutureDate;
    public Set<Contact> emptySet;
    public Set<Contact> populatedSet;
    public Set<Contact> populatedSetWithInvalidContact;
    public Set<Contact> populatedSetWithNullContact;
    public Set<Contact> excludedSet;
    public Contact excludedContact;

    {
        manager = new ContactManagerImpl();
        pastDate = DateFns.getPastDate();
        futureDate = DateFns.getFutureDate();
        slightlyPastDate = DateFns.getSlightlyPastDate();
        slightlyFutureDate = DateFns.getSlightlyFutureDate();
        emptySet = new HashSet<>();
        populatedSet = ContactManagerImplTestsFns.generateValidContacts(50, manager);
        populatedSetWithInvalidContact = ContactManagerImplTestsFns.generateInvalidContacts(50, manager);
        populatedSetWithNullContact = ContactManagerImplTestsFns.generateNullContacts(50, manager);
        excludedContact = (Contact)manager.getContacts(3).toArray()[0];
        excludedSet = ContactManagerImplTestsFns.generateExcludedSet(populatedSet, excludedContact);
    }
}