import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        unSelectedContact = (Contact)data.manager.getContacts(3).toArray()[0];
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
        excludedSet = data.populatedSet.stream()
                .filter(e -> !e.equals(unSelectedContact))
                .collect(Collectors.toSet());

        data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        data.manager.addFutureMeeting(excludedSet, data.futureDate);
        data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        data.manager.addFutureMeeting(excludedSet, data.futureDate);
        data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        data.manager.addFutureMeeting(excludedSet, data.futureDate);
        data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
    }

    @Test
    public void testAllMeetingsReturned(){
        List<Meeting> meetings = data.manager.getFutureMeetingList(selectedContact);
        System.out.println(String.format("expected 8, got %d", meetings.size()));

        List<Meeting> unSelectedMeetings = data.manager.getFutureMeetingList(unSelectedContact);
        System.out.println(String.format("expected 3, got %d", unSelectedMeetings.size()));
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
