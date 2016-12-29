import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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

    private ContactManager manager;
    private Calendar pastDate;
    private Calendar futureDate;
    private Set<Contact> emptySet;
    private Set<Contact> populatedSet;
    private Set<Contact> populatedSetWithInvalidContact;
    private Set<Contact> populatedSetWithNullContact;

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
