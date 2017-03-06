import org.junit.Test;

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
        int meetingsSizeBefore = data.getManager().getPastMeetingListFor(data.getSelectedContact()).size();
        int unSelectedMeetingsSizeBefore = data.getManager().getPastMeetingListFor(data.getExcludedContact()).size();

        addPastMeetings();

        int meetingsSizeAfter = data.getManager().getPastMeetingListFor(data.getSelectedContact()).size();
        int unSelectedMeetingsSizeAfter = data.getManager().getPastMeetingListFor(data.getExcludedContact()).size();

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

        data.getManager().addNewPastMeeting(bothSet, data.getPastDate(), " ");
        int minuteOffset = 12;
        data.getManager().addNewPastMeeting(bothSet, DateFns.getPastDate(minuteOffset), " ");
        data.getManager().addNewPastMeeting(bothSet, data.getPastDate(), " ");
        minuteOffset = 7;
        data.getManager().addNewPastMeeting(bothSet, DateFns.getPastDate(minuteOffset), " ");

        data.getManager().addNewPastMeeting(selectedSet, data.getPastDate(), " ");
        minuteOffset = 2;
        data.getManager().addNewPastMeeting(selectedSet, DateFns.getPastDate(minuteOffset), " ");
        data.getManager().addNewPastMeeting(selectedSet, data.getPastDate(), " ");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNonExistContact(){
        data.getManager().getPastMeetingListFor(nonExistContact);
    }

    @Test(expected=NullPointerException.class)
    public void testNullContact(){
        data.getManager().getPastMeetingListFor(null);
    }

    @Test
    public void testNoDuplicates(){
        addPastMeetings();

        List<PastMeeting> meetingsPastSelected = data.getManager().getPastMeetingListFor(data.getSelectedContact());
        assertTrue(ContactManagerImplTestsFns.testDuplicatePastMeetings(meetingsPastSelected));

        List<PastMeeting> meetingsPastExcluded = data.getManager().getPastMeetingListFor(data.getExcludedContact());
        assertTrue(ContactManagerImplTestsFns.testDuplicatePastMeetings(meetingsPastExcluded));
    }

    @Test
    public void testSorted(){
        addPastMeetings();

        List<PastMeeting> meetingsPastSelected = data.getManager().getPastMeetingListFor(data.getSelectedContact());
        assertTrue(ContactManagerImplTestsFns.testChronologicallySortedPastMeetings(meetingsPastSelected));

        List<PastMeeting> meetingsPastExcluded = data.getManager().getPastMeetingListFor(data.getExcludedContact());
        assertTrue(ContactManagerImplTestsFns.testChronologicallySortedPastMeetings(meetingsPastExcluded));
    }
}
