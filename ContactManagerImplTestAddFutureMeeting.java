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
        int id = data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        assertTrue(id > 0);

        int id2 = data.manager.addFutureMeeting(data.getPopulatedSet(), data.futureDate);
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        data.manager.addFutureMeeting(data.getPopulatedSetWithNullContact(), data.futureDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        data.manager.addFutureMeeting(data.getPopulatedSetWithNullContact(), data.futureDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateInPast(){
        data.manager.addFutureMeeting(data.getPopulatedSetWithNullContact(), data.pastDate);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateCurrent(){
        data.manager.addFutureMeeting(data.getPopulatedSetWithNullContact(), Calendar.getInstance());
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        data.manager.addFutureMeeting(null, data.futureDate);
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        data.manager.addFutureMeeting(data.getPopulatedSet(), null);
    }
}
