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

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }



    @Test
    public void testIdReturned(){
        int id = data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        assertTrue(id > 0);

        int id2 = data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        int id = data.manager.addFutureMeeting(data.populatedSetWithNullContact, data.futureDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        int id = data.manager.addFutureMeeting(data.populatedSetWithInvalidContact, data.futureDate);
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        int id = data.manager.addFutureMeeting(null, data.futureDate);
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        int id = data.manager.addFutureMeeting(data.populatedSet, null);
    }
}
