import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertTrue;

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

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }



    @Test
    public void testIdReturned(){
        int id = data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        assertTrue(id > 0);

        int id2 = data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        data.manager.addFutureMeeting(data.getpopulatedSetWithNullContact(), data.futureDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        data.manager.addFutureMeeting(data.getpopulatedSetWithNullContact(), data.futureDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateInPast(){
        data.manager.addFutureMeeting(data.getpopulatedSetWithNullContact(), data.pastDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateCurrent(){
        data.manager.addFutureMeeting(data.getpopulatedSetWithNullContact(), Calendar.getInstance());
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        data.manager.addFutureMeeting(null, data.futureDate);
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        data.manager.addFutureMeeting(data.getpopulatedSet(), null);
    }
}
