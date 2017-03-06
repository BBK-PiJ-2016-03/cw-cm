import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImplTestAddNewPastMeeting {

    private final ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    @Test
    public void testIdReturned(){
        String note = "These are the Notes";
        int id = data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, note);
        assertTrue(id > 0);

        int id2 = data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, "");
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        data.manager.addNewPastMeeting(data.getPopulatedSetWithNullContact(), data.pastDate, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        data.manager.addNewPastMeeting(data.getPopulatedSetWithInvalidContact(), data.pastDate, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateInFuture(){
        data.manager.addNewPastMeeting(data.getPopulatedSet(), data.futureDate, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateCurrent(){
        data.manager.addNewPastMeeting(data.getPopulatedSet(), Calendar.getInstance(), "");
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        data.manager.addNewPastMeeting(null, data.pastDate, "");
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        data.manager.addNewPastMeeting(data.getPopulatedSet(), null, "");
    }

    @Test(expected=NullPointerException.class)
    public void testTextNull(){
        data.manager.addNewPastMeeting(data.getPopulatedSet(), data.pastDate, null);
    }

}
