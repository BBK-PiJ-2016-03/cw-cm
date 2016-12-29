import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestAddFutureMeeting {

    /**
     * Add a new meeting to be held in the future.
     *
     * An ID is returned when the meeting is put into the system. This
     * ID must be positive and non-zero.
     *
     * @param contacts a set of contacts that will participate in the meeting
     * @param date the date on which the meeting will take place
     * @return the ID for the meeting
     * @throws IllegalArgumentException if the meeting is set for a time
     *       in the past, of if any contact is unknown / non-existent.
     * @throws NullPointerException if the meeting or the date are null
     */

    private ContactManager manager;
    private Calendar pastDate;
    private Calendar futureDate;
    private Set<Contact> emptySet;
    private Set<Contact> populatedSet;
    private Set<Contact> populatedSetWithNullContact;

    {
        manager = new ContactManagerImpl();
        pastDate = DateFns.getPastDate();
        futureDate = DateFns.getFutureDate();
        emptySet = new HashSet<>();
        populatedSet = generateValidContacts(1_000_000);
        populatedSetWithNullContact = generateInvalidContacts(1_000_000);
    }

    private Set<Contact> generateValidContacts(int number){
        int[] contactIds = IntStream.range(0,number)
                    .map(i -> manager.addNewContact("Name"+i, ""))
                    .toArray();

        return manager.getContacts(contactIds);
    }

    private Set<Contact> generateInvalidContacts(int number){
        int[] contactIds = IntStream.range(0,number)
                    .map(i -> manager.addNewContact("Name"+(number+i), ""))
                    .toArray();

        Set<Contact> contacts =  manager.getContacts(contactIds);
        contacts.add(new ContactImpl(number*3,"Unknown", ""));
        return contacts;
    }

    @Test
    public void testIdReturned(){
        int id = manager.addFutureMeeting(populatedSet, futureDate);
        assertTrue(id > 0);

        int id2 = manager.addFutureMeeting(populatedSet, futureDate);
        assertTrue(id > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){

    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){

    }

    @Test(expected=NullPointerException.class)
    public void testMeetingNull(){

    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){

    }
}
