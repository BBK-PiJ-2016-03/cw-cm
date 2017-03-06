import org.junit.Test;

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
    private final int DATE_OFFSET = 2;

    {
        data = new ContactManagerImplTestData();
        nonExistContact = new ContactImpl(Integer.MAX_VALUE, "I Don't Exist");
    }

    @Test
    public void testAllMeetingsReturned(){
        int selectedBefore = data.getManager().getFutureMeetingList(data.getSelectedContact()).size();
        int unSelectedBefore = data.getManager().getFutureMeetingList(data.getExcludedContact()).size();

        final int numberOfPopulatedSetMeetings = 3;
        final int numberOfExcludedSetMeetings = 2;
        final int totalMeetingsAdded = numberOfPopulatedSetMeetings + numberOfExcludedSetMeetings;
        addNewFutureMeetings(numberOfPopulatedSetMeetings, numberOfExcludedSetMeetings);

        int selectedAfter = data.getManager().getFutureMeetingList(data.getSelectedContact()).size();
        int unSelectedAfter = data.getManager().getFutureMeetingList(data.getExcludedContact()).size();

        assertEquals(totalMeetingsAdded, selectedAfter - selectedBefore);
        assertEquals(numberOfPopulatedSetMeetings, unSelectedAfter - unSelectedBefore);
    }

    private void addNewFutureMeetings(int numPopulatedSet, int numExcludedSet) {
        final int totalMeetings = numPopulatedSet + numExcludedSet;
        for(int iteration = 0; iteration < totalMeetings; iteration++) {
            if(iteration < numPopulatedSet){
                data.getManager().addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(DATE_OFFSET));
            }
            else{
                data.getManager().addFutureMeeting(data.getExcludedSet(), DateFns.getFutureDate(DATE_OFFSET));
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNonExistContact(){
        data.getManager().getFutureMeetingList(nonExistContact);
    }

    @Test(expected=NullPointerException.class)
    public void testNullContact(){
        data.getManager().getFutureMeetingList(null);
    }

    @Test
    public void testNoDuplicates(){
        List<Meeting> meetings = data.getManager().getFutureMeetingList(data.getSelectedContact());
        List<Meeting> noDupes = meetings.stream()
                .distinct()
                .collect(Collectors.toList());

        assertEquals(meetings.size(), noDupes.size());
    }

    @Test
    public void testChronologicallySorted(){
        List<Meeting> meetings = data.getManager().getFutureMeetingList(data.getSelectedContact());
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
