package manager;

import org.junit.Test;

import java.util.Calendar;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestAddFutureMeeting {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testIdReturned(){
        int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        assertTrue(id > 0);

        int id2 = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), data.getFutureDate());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), data.getFutureDate());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateInPast(){
        data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), data.getPastDate());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateCurrent(){
        data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), Calendar.getInstance());
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        data.getManager().addFutureMeeting(null, data.getFutureDate());
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        data.getManager().addFutureMeeting(data.getPopulatedSet(), null);
    }
}
