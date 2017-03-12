package manager;

import org.junit.Test;
import spec.Contact;
import spec.Meeting;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestFlush {

  private final ContactManagerImplTestData DATA;
  private final int SMALL_OFFSET = 12;
  private final int LARGE_OFFSET = 48;

  {
    DATA = new ContactManagerImplTestData();
  }


  /**
   * input DATA.
   * flush
   * reload
   * check DATA
   */
  private void flushAndReload() {
    DATA.getManager().flush();
    DATA.setManager(new ContactManagerImpl());
  }

  @Test
  public void testRestoreOfContacts() {
    int initialSize = DATA.getManager().getContacts("").size();
    String name1 = "Name for the test", notes1 = "NOTES";
    String name2 = "Alt name", notes2 = "Some notes";
    int id1 = DATA.getManager().addNewContact(name1, notes1);
    int id2 = DATA.getManager().addNewContact(name2, notes2);

    flushAndReload();

    Contact contact1 = (Contact)DATA.getManager().getContacts(id1).toArray()[0];
    Contact contact2 = (Contact)DATA.getManager().getContacts(id2).toArray()[0];

    assertNotNull(contact1);
    assertNotNull(contact2);
    assertEquals(id1, contact1.getId());
    assertEquals(id2, contact2.getId());
    assertEquals(name1, contact1.getName());
    assertEquals(name2, contact2.getName());
    assertEquals(notes1, contact1.getNotes());
    assertEquals(notes2, contact2.getNotes());
    assertEquals(initialSize+2, DATA.getManager().getContacts("").size());
  }

  @Test
  public void testRestoreOfMeetings() {
    Contact selectedContact = (Contact)DATA.getPopulatedSet().toArray()[2];
    Calendar selectedPastDate = DateFns.getPastDate(SMALL_OFFSET);
    Calendar selectedFutureDate = DateFns.getFutureDate(SMALL_OFFSET);

    int initialPastSize = DATA.getManager().getPastMeetingListFor(selectedContact).size();
    int initialFutureSize = DATA.getManager().getFutureMeetingList(selectedContact).size();

    int id1 = DATA.getManager().addNewPastMeeting(DATA.getPopulatedSet(), selectedPastDate, "");
    int id2 = DATA.getManager().addFutureMeeting(DATA.getPopulatedSet(), selectedFutureDate);

    flushAndReload();

    Contact newSelectedContact = (Contact)DATA.getManager().getContacts(selectedContact.getId()).toArray()[0];

    Meeting pastMeeting = DATA.getManager().getPastMeeting(id1);
    Meeting futureMeeting = DATA.getManager().getFutureMeeting(id2);

    DATA.getManager().addNewPastMeeting(DATA.getPopulatedSet(), selectedPastDate, "");
    DATA.getManager().addFutureMeeting(DATA.getPopulatedSet(), selectedFutureDate);

    assertNotNull(pastMeeting);
    assertNotNull(futureMeeting);
    assertEquals(id1, pastMeeting.getId());
    assertEquals(id2, futureMeeting.getId());
    assertEquals(DATA.getPopulatedSet().size(), pastMeeting.getContacts().size());
    assertEquals(DATA.getPopulatedSet().size(), futureMeeting.getContacts().size());
    assertEquals(selectedPastDate, pastMeeting.getDate());
    assertEquals(selectedFutureDate, futureMeeting.getDate());
    assertEquals(initialPastSize+2, DATA.getManager().getPastMeetingListFor(newSelectedContact).size());
    assertEquals(initialFutureSize+2, DATA.getManager().getFutureMeetingList(newSelectedContact).size());

  }

  @Test
  public void testRestoreOfMeetingId() {
    Calendar selectedFutureDate = DateFns.getFutureDate(LARGE_OFFSET);
    int id1 = DATA.getManager().addFutureMeeting(DATA.getPopulatedSet(), selectedFutureDate);
    flushAndReload();
    int id2 = DATA.getManager().addFutureMeeting(DATA.getPopulatedSet(), selectedFutureDate);

    assertTrue(id2 > id1);
  }

  @Test
  public void testRestoreOfContactId() {
    String name1 = "Contact ID 1", notes1 = "notes1";
    String name2 = "Contact ID 2", notes2 = "notes2";
    int id1 = DATA.getManager().addNewContact(name1, notes1);
    flushAndReload();
    int id2 = DATA.getManager().addNewContact(name2, notes2);

    assertTrue(id2 > id1);
  }

  @Test
  public void testNoWriteExceptionRaised() {
    File file = new File("contacts.txt");
    if(!file.exists()){
      try{
        file.createNewFile();
      }
      catch(IOException e){
        e.printStackTrace();
      }
    }

    file.setWritable(false);
    file.setReadable(false);

    flushAndReload();
  }
}
