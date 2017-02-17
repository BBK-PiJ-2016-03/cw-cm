import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestData {

    public ContactManager manager;
    public final Calendar pastDate;
    public final Calendar slightlyPastDate;
    public final Calendar futureDate;
    public final Calendar slightlyFutureDate;
    public final Set<Contact> emptySet;
    public final int[] excludedSetIds;
    public final int selectedContactId;
    public final int excludedContactId;

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
        excludedSetIds = ContactManagerImplTestsFns.generateExcludedSetIds(getPopulatedSet(), excludedContactId);
    }

    public Contact getSelectedContact(){ return (Contact)manager.getContacts(selectedContactId).toArray()[0]; }
    public Contact getExcludedContact(){ return (Contact)manager.getContacts(excludedContactId).toArray()[0]; }
    public Set<Contact> getPopulatedSet(){ return manager.getContacts(""); }
    public Set<Contact> getExcludedSet(){ return manager.getContacts(excludedSetIds); }
    public Set<Contact> getPopulatedSetWithInvalidContact(){ return ContactManagerImplTestsFns.generateInvalidContacts(); }
    public Set<Contact> getPopulatedSetWithNullContact(){ return ContactManagerImplTestsFns.generateNullContacts(); }
}