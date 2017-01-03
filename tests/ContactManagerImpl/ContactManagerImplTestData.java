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
    public int[] excludedSetIds;
    public int selectedContactId;
    public int excludedContactId;

    {
        manager = new ContactManagerImpl();
        ContactManagerImplTestsFns.createValidContacts(50, manager);
        ContactManagerImplTestsFns.createInvalidContacts(50, manager);
        pastDate = DateFns.getPastDate();
        futureDate = DateFns.getFutureDate();
        slightlyPastDate = DateFns.getSlightlyPastDate();
        slightlyFutureDate = DateFns.getSlightlyFutureDate();
        emptySet = new HashSet<>();
        selectedContactId = 5;
        excludedContactId = 3;
        excludedSetIds = ContactManagerImplTestsFns.generateExcludedSetIds(getpopulatedSet(), excludedContactId);
    }

    public Contact getSelectedContact(){ return (Contact)manager.getContacts(selectedContactId).toArray()[0]; }
    public Contact getExcludedContact(){ return (Contact)manager.getContacts(excludedContactId).toArray()[0]; }
    public Set<Contact> getpopulatedSet(){ return manager.getContacts(""); }
    public Set<Contact> getExcludedSet(){ return manager.getContacts(excludedSetIds); }
    public Set<Contact> getpopulatedSetWithInvalidContact(){ return ContactManagerImplTestsFns.generateInvalidContacts(); }
    public Set<Contact> getpopulatedSetWithNullContact(){ return ContactManagerImplTestsFns.generateNullContacts(); }
}