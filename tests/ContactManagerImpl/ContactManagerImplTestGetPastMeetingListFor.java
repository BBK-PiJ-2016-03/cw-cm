import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetPastMeetingListFor {

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
     * Returns the list of past meetings in which this contact has participated.
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

        List<Meeting> unSelectedMeetings = data.manager.getFutureMeetingList(unSelectedContact);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNonExistContact(){
        data.manager.getFutureMeetingList(nonExistContact);
    }

    @Test(expected=NullPointerException.class)
    public void testNullContact(){
        data.manager.getFutureMeetingList(null);
    }
}
