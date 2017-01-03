import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetFutureMeetingList {

    private ContactManagerImplTestData data;
    private Contact selectedContact;
    private Contact unSelectedContact;
    private Contact nonExistContact;
    private Set<Contact> excludedSet;

    {
        data = new ContactManagerImplTestData();
        selectedContact = (Contact)data.manager.getContacts(5).toArray()[0];
        unSelectedContact = data.excludedContact;
        excludedSet = data.excludedSet;
        nonExistContact = new ContactImpl(Integer.MAX_VALUE, "I Don't Exist");
    }

    /**
     * Returns the list of future meetings scheduled with this contact.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist
     * @throws NullPointerException if the contact is null
     */

    @Before
    public void before(){
        ContactManagerImplTestsFns.generateMeetingsInclusiveAndExclusiveOfContact(data);
    }

    @Test
    public void testAllMeetingsReturned(){
        List<Meeting> meetings = data.manager.getFutureMeetingList(selectedContact);
        assertEquals(9, meetings.size());

        List<Meeting> unSelectedMeetings = data.manager.getFutureMeetingList(unSelectedContact);
        assertEquals(6, unSelectedMeetings.size());

    }

    @Test(expected=IllegalArgumentException.class)
    public void testNonExistContact(){
        data.manager.getFutureMeetingList(nonExistContact);
    }

    @Test(expected=NullPointerException.class)
    public void testNullContact(){
        data.manager.getFutureMeetingList(null);
    }

    @Test
    public void testNoDuplicates(){
        List<Meeting> meetings = data.manager.getFutureMeetingList(selectedContact);
        List<Meeting> noDupes = meetings.stream()
                .distinct()
                .collect(Collectors.toList());

        assertEquals(meetings.size(), noDupes.size());
    }

    @Test
    public void testChronologicallySorted(){
        List<Meeting> meetings = data.manager.getFutureMeetingList(selectedContact);
        boolean sorted = true;
        MEETINGS_ITERATION: for (int i = 1; i < meetings.size(); i++){
            if(meetings.get(i-1).getDate().after(meetings.get(i).getDate())){
                sorted = false;
                break MEETINGS_ITERATION;
            }
        }
        assertTrue(sorted);
    }
}
