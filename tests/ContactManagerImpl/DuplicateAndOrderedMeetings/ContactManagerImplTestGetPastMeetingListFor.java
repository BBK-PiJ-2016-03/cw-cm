import org.junit.Before;
import org.junit.Test;
import tests.DateFns;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        excludedSet = data.getExcludedSet();
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

    @Test
    public void testAllMeetingsReturned(){
        int meetingsSizeBefore = data.manager.getPastMeetingListFor(data.getSelectedContact()).size();
        int unSelectedMeetingsSizeBefore = data.manager.getPastMeetingListFor(data.getExcludedContact()).size();



        addPastMeetings();

        int meetingsSizeAfter = data.manager.getPastMeetingListFor(data.getSelectedContact()).size();
        int unSelectedMeetingsSizeAfter = data.manager.getPastMeetingListFor(data.getExcludedContact()).size();

        assertEquals(7, meetingsSizeAfter - meetingsSizeBefore);
        assertEquals(4, unSelectedMeetingsSizeAfter - unSelectedMeetingsSizeBefore);
    }

    private void addPastMeetings(){
        Set<Contact> bothSet = new HashSet<>();
        bothSet.add(data.getSelectedContact());
        bothSet.add(data.getExcludedContact());

        Set<Contact> selectedSet = new HashSet<>();
        selectedSet.add(data.getSelectedContact());

        data.manager.addNewPastMeeting(bothSet, data.pastDate, " ");
        data.manager.addNewPastMeeting(bothSet, DateFns.getPastDate(12), " ");
        data.manager.addNewPastMeeting(bothSet, data.pastDate, " ");
        data.manager.addNewPastMeeting(bothSet, DateFns.getPastDate(7), " ");

        data.manager.addNewPastMeeting(selectedSet, data.pastDate, " ");
        data.manager.addNewPastMeeting(selectedSet, DateFns.getPastDate(2), " ");
        data.manager.addNewPastMeeting(selectedSet, data.pastDate, " ");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNonExistContact(){
        data.manager.getPastMeetingListFor(nonExistContact);
    }

    @Test(expected=NullPointerException.class)
    public void testNullContact(){
        data.manager.getPastMeetingListFor(null);
    }

    @Test
    public void testNoDuplicates(){
        addPastMeetings();

        List<PastMeeting> meetingsPastSelected = data.manager.getPastMeetingListFor(data.getSelectedContact());
        assertTrue(ContactManagerImplTestsFns.testDuplicatePastMeetings(meetingsPastSelected));

        List<PastMeeting> meetingsPastExcluded = data.manager.getPastMeetingListFor(data.getExcludedContact());
        assertTrue(ContactManagerImplTestsFns.testDuplicatePastMeetings(meetingsPastExcluded));
    }

    @Test
    public void testSorted(){
        addPastMeetings();

        List<PastMeeting> meetingsPastSelected = data.manager.getPastMeetingListFor(data.getSelectedContact());
        assertTrue(ContactManagerImplTestsFns.testChronologicallySortedPastMeetings(meetingsPastSelected));

        List<PastMeeting> meetingsPastExcluded = data.manager.getPastMeetingListFor(data.getExcludedContact());
        assertTrue(ContactManagerImplTestsFns.testChronologicallySortedPastMeetings(meetingsPastExcluded));
    }
}
