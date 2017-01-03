import org.junit.Test;
import tests.DateFns;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestFlush {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    //input data
    //flush
    //reload
    //check data

    private void flushAndReload(){
        data.manager.flush();
        data.manager = new ContactManagerImpl();
    }

    @Test
    public void testRestoreOfContacts(){
        int initialSize = data.manager.getContacts("").size();
        String name1 = "Name for the test", notes1 = "NOTEYS";
        String name2 = "Alt name", notes2 = "Some notes";
        int id1 = data.manager.addNewContact(name1, notes1);
        int id2 = data.manager.addNewContact(name2, notes2);

        flushAndReload();

        Contact contact1 = (Contact)data.manager.getContacts(id1).toArray()[0];
        Contact contact2 = (Contact)data.manager.getContacts(id2).toArray()[0];

        assertNotNull(contact1);
        assertNotNull(contact2);
        assertEquals(id1, contact1.getId());
        assertEquals(id1, contact2.getId());
        assertEquals(name1, contact1.getName());
        assertEquals(name2, contact2.getName());
        assertEquals(notes1, contact1.getNotes());
        assertEquals(notes2, contact2.getNotes());
        assertEquals(data.manager.getContacts("").size(), initialSize+2);
    }

    @Test
    public void testRestoreOfMeetings(){
        Calendar selectedPastDate = DateFns.getPastDate(12);
        Calendar selectedFutureDate = DateFns.getFutureDate(12);
        int initialSize = data.manager.getFutureMeetingList(data.excludedContact).size();
        int id1 = data.manager.addFutureMeeting(data.populatedSet, selectedFutureDate);
        int id2 = data.manager.addNewPastMeeting(data.populatedSet, selectedPastDate, "");

        flushAndReload();

        Meeting futureMeeting = data.manager.getFutureMeeting(id1);
        Meeting pastMeeting = data.manager.getPastMeeting(id1);

        assertNotNull(pastMeeting);
        assertNotNull(futureMeeting);
        assertEquals(id2, pastMeeting.getId());
        assertEquals(id1, futureMeeting.getId());
        assertEquals(data.populatedSet.size(), pastMeeting.getContacts().size());
        assertEquals(data.populatedSet.size(), futureMeeting.getContacts().size());
        assertEquals(selectedPastDate, pastMeeting.getDate());
        assertEquals(selectedFutureDate, futureMeeting.getDate());
        assertEquals(data.manager.getPastMeetingListFor(data.excludedContact).size(), initialSize+1);
        assertEquals(data.manager.getFutureMeetingList(data.excludedContact).size(), initialSize+1);

    }


}
