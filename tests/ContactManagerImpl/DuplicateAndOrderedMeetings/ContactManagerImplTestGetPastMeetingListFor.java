import org.junit.Test;
import tests.DateFns;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetPastMeetingListFor {

    private final ContactManagerImplTestData data;
    private final Contact nonExistContact;

    {
        data = new ContactManagerImplTestData();
        nonExistContact = new ContactImpl(Integer.MAX_VALUE, "I Don't Exist");
    }

    @Test
    public void testAllMeetingsReturned(){
        int meetingsSizeBefore = data.manager.getPastMeetingListFor(data.getSelectedContact()).size();
        int unSelectedMeetingsSizeBefore = data.manager.getPastMeetingListFor(data.getExcludedContact()).size();

        addPastMeetings();

        int meetingsSizeAfter = data.manager.getPastMeetingListFor(data.getSelectedContact()).size();
        int unSelectedMeetingsSizeAfter = data.manager.getPastMeetingListFor(data.getExcludedContact()).size();

        int expectedMeetingSizeChange = 7;
        int expectedUnselectedMeetingSizeChange = 4;

        assertEquals(expectedMeetingSizeChange, meetingsSizeAfter - meetingsSizeBefore);
        assertEquals(expectedUnselectedMeetingSizeChange, unSelectedMeetingsSizeAfter - unSelectedMeetingsSizeBefore);
    }

    private void addPastMeetings(){
        Set<Contact> bothSet = new HashSet<>();
        bothSet.add(data.getSelectedContact());
        bothSet.add(data.getExcludedContact());

        Set<Contact> selectedSet = new HashSet<>();
        selectedSet.add(data.getSelectedContact());

        data.manager.addNewPastMeeting(bothSet, data.pastDate, " ");
        int minuteOffset = 12;
        data.manager.addNewPastMeeting(bothSet, DateFns.getPastDate(minuteOffset), " ");
        data.manager.addNewPastMeeting(bothSet, data.pastDate, " ");
        minuteOffset = 7;
        data.manager.addNewPastMeeting(bothSet, DateFns.getPastDate(minuteOffset), " ");

        data.manager.addNewPastMeeting(selectedSet, data.pastDate, " ");
        minuteOffset = 2;
        data.manager.addNewPastMeeting(selectedSet, DateFns.getPastDate(minuteOffset), " ");
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
