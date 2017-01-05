import org.junit.Test;
import tests.DateFns;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        assertEquals(id2, contact2.getId());
        assertEquals(name1, contact1.getName());
        assertEquals(name2, contact2.getName());
        assertEquals(notes1, contact1.getNotes());
        assertEquals(notes2, contact2.getNotes());
        assertEquals(initialSize+2, data.manager.getContacts("").size());
    }

    @Test
    public void testRestoreOfMeetings(){
        Contact selectedContact = (Contact)data.getpopulatedSet().toArray()[2];
        Calendar selectedPastDate = DateFns.getPastDate(12);
        Calendar selectedFutureDate = DateFns.getFutureDate(12);

        int initialPastSize = data.manager.getPastMeetingListFor(selectedContact).size();
        int initialFutureSize = data.manager.getFutureMeetingList(selectedContact).size();

        int id1 = data.manager.addNewPastMeeting(data.getpopulatedSet(), selectedPastDate, "");
        int id2 = data.manager.addFutureMeeting(data.getpopulatedSet(), selectedFutureDate);

        flushAndReload();

        Contact newSelectedContact = (Contact)data.manager.getContacts(selectedContact.getId()).toArray()[0];

        Meeting pastMeeting = data.manager.getPastMeeting(id1);
        Meeting futureMeeting = data.manager.getFutureMeeting(id2);

        int id3 = data.manager.addNewPastMeeting(data.getpopulatedSet(), selectedPastDate, "");
        int id4 = data.manager.addFutureMeeting(data.getpopulatedSet(), selectedFutureDate);

        assertNotNull(pastMeeting);
        assertNotNull(futureMeeting);
        assertEquals(id1, pastMeeting.getId());
        assertEquals(id2, futureMeeting.getId());
        assertEquals(data.getpopulatedSet().size(), pastMeeting.getContacts().size());
        assertEquals(data.getpopulatedSet().size(), futureMeeting.getContacts().size());
        assertEquals(selectedPastDate, pastMeeting.getDate());
        assertEquals(selectedFutureDate, futureMeeting.getDate());
        assertEquals(initialPastSize+2, data.manager.getPastMeetingListFor(newSelectedContact).size());
        assertEquals(initialFutureSize+2, data.manager.getFutureMeetingList(newSelectedContact).size());

    }

    @Test
    public void testRestoreOfMeetingId(){
        Calendar selectedFutureDate = DateFns.getFutureDate(48);
        int id1 = data.manager.addFutureMeeting(data.getpopulatedSet(), selectedFutureDate);
        flushAndReload();
        int id2 = data.manager.addFutureMeeting(data.getpopulatedSet(), selectedFutureDate);

        assertTrue(id2 > id1);
    }

    @Test
    public void testRestoreOfContactId(){
        String name1 = "Contact ID 1", notes1 = "notes1";
        String name2 = "Contact ID 1", notes2 = "notes2";
        int id1 = data.manager.addNewContact(name1, notes1);
        flushAndReload();
        int id2 = data.manager.addNewContact(name2, notes2);

        assertTrue(id2 > id1);
    }


}
