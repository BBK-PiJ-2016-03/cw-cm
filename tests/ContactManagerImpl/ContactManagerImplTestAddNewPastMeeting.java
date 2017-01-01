import org.junit.Test;

import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImplTestAddNewPastMeeting {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Create a new record for a meeting that took place in the past.
     *
     * @param contacts a set of participants
     * @param date the date on which the meeting took place
     * @param text messages to be added about the meeting.
     * @return the ID for the meeting
     * @throws IllegalArgumentException if the list of contacts is
     *     empty, if any of the contacts does not exist, or if
     *     the date provided is in the future
     * @throws NullPointerException if any of the arguments is null
     */

    @Test
    public void testIdReturned(){
        String note = "These are the Notes";
        int id = data.manager.addPastMeeting(data.populatedSet, data.pastDate, note);
        assertTrue(id > 0);

        Set<Contact> contacts = data.manager.getContacts(id);
        assertTrue(((Contact)(contacts.toArray()[0])).getNotes().equals(note));

        int id2 = data.manager.addPastMeeting(data.populatedSet, data.pastDate, "");
        assertTrue(id2 > 0 && id != id2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactNull(){
        data.manager.addPastMeeting(data.populatedSetWithNullContact, data.pastDate, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContactUnknown(){
        data.manager.addPastMeeting(data.populatedSetWithInvalidContact, data.pastDate, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateInFuture(){
        data.manager.addPastMeeting(data.populatedSetWithInvalidContact, data.futureDate, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDateCurrent(){
        data.manager.addPastMeeting(data.populatedSetWithInvalidContact, Calendar.getInstance());
    }

    @Test(expected=NullPointerException.class)
    public void testContactsNull(){
        data.manager.addPastMeeting(null, data.pastDate, "");
    }

    @Test(expected=NullPointerException.class)
    public void testDateNull(){
        data.manager.addPastMeeting(data.populatedSet, null, "");
    }

    @Test(expected=NullPointerException.class)
    public void testTextNull(){
        data.manager.addPastMeeting(data.populatedSet, data.pastDate, null);
    }

}
