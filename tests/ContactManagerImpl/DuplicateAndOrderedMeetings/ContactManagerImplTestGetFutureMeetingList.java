import org.junit.Test;
import tests.DateFns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetFutureMeetingList {

    private final ContactManagerImplTestData data;
    private final Contact nonExistContact;

    {
        data = new ContactManagerImplTestData();
        nonExistContact = new ContactImpl(Integer.MAX_VALUE, "I Don't Exist");
    }

    @Test
    public void testAllMeetingsReturned(){
        int selectedBefore = data.manager.getFutureMeetingList(data.getSelectedContact()).size();
        int unSelectedBefore = data.manager.getFutureMeetingList(data.getExcludedContact()).size();

        data.manager.addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(2));
        data.manager.addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(2));
        data.manager.addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(2));

        data.manager.addFutureMeeting(data.getExcludedSet(), DateFns.getFutureDate(2));
        data.manager.addFutureMeeting(data.getExcludedSet(), DateFns.getFutureDate(2));

        int selectedAfter = data.manager.getFutureMeetingList(data.getSelectedContact()).size();
        int unSelectedAfter = data.manager.getFutureMeetingList(data.getExcludedContact()).size();

        assertEquals(5, selectedAfter - selectedBefore);
        assertEquals(3, unSelectedAfter - unSelectedBefore);
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
        List<Meeting> meetings = data.manager.getFutureMeetingList(data.getSelectedContact());
        List<Meeting> noDupes = meetings.stream()
                .distinct()
                .collect(Collectors.toList());

        assertEquals(meetings.size(), noDupes.size());
    }

    @Test
    public void testChronologicallySorted(){
        List<Meeting> meetings = data.manager.getFutureMeetingList(data.getSelectedContact());
        boolean sorted = true;
        for (int i = 1; i < meetings.size(); i++){
            if(meetings.get(i-1).getDate().after(meetings.get(i).getDate())){
                sorted = false;
                break;
            }
        }
        assertTrue(sorted);
    }
}
