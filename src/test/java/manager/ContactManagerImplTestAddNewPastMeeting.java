package manager;

import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImplTestAddNewPastMeeting {

    private final ContactManagerImplTestData data;
    private static final String EMPTY = "";

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testIdReturned(){
        String note = "These are the Notes";
        int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), note);
        assertTrue(id > 0);

        int id2 = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), EMPTY);
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        data.getManager().addNewPastMeeting(data.getPopulatedSetWithNullContact(), data.getPastDate(), EMPTY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        data.getManager().addNewPastMeeting(data.getPopulatedSetWithInvalidContact(), data.getPastDate(), EMPTY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateInFuture(){
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getFutureDate(), EMPTY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateCurrent(){
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), Calendar.getInstance(), EMPTY);
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        data.getManager().addNewPastMeeting(null, data.getPastDate(), EMPTY);
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), null, EMPTY);
    }

    @Test(expected=NullPointerException.class)
    public void testTextNull(){
        data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), null);
    }

}
