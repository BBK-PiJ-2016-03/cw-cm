package manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.stream.IntStream;

import org.junit.Test;
import spec.Contact;
import spec.Meeting;

/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestFlush {

  private final ContactManagerImplTestData data;
  private static final int SMALL_OFFSET = 12;
  private static final int LARGE_OFFSET = 48;
  private static final String EMPTY_NOTES = "";
  private static final String EMPTY_NAME = "";
  private static final String FILEPATH = "contacts.txt";

  {
    data = new ContactManagerImplTestData();
  }


  /**
   * input data.
   * flush
   * reload
   * check data
   */
  private void flushAndReload() {
    data.getManager().flush();
    data.setManager(new ContactManagerImpl());
  }

  @Test
  public void testRestoreOfContacts() {
    final int initialSize = data.getManager().getContacts(EMPTY_NAME).size();
    int quantity = 2; //retrieve two ids
    int[] ids = insertContactsAndGetIds(quantity);

    //clear data, then reload it from disc
    flushAndReload();

    //retrieve the contacts using the ids from before the flush and reload
    Contact contact1 = (Contact) data.getManager().getContacts(ids[0]).toArray()[0];
    Contact contact2 = (Contact) data.getManager().getContacts(ids[1]).toArray()[0];

    assertNotNull(contact1);
    assertNotNull(contact2);
    assertEquals(ids[0], contact1.getId());
    assertEquals(ids[1], contact2.getId());
    String index0 = "0";
    assertEquals(index0, contact1.getName());
    String index1 = "1";
    assertEquals(index1, contact2.getName());
    assertEquals(index0, contact1.getNotes());
    assertEquals(index1, contact2.getNotes());
    int expected = initialSize + quantity;
    assertEquals(expected, data.getManager().getContacts(EMPTY_NAME).size());
  }

  private int[] insertContactsAndGetIds(int number) {
    int[] ids = new int[number];
    IntStream.range(0,number).forEach((int index) -> {
      String name = "" + index;
      String notes = "" + index;
      ids[index] = data.getManager().addNewContact(name, notes);
    });
    return ids;
  }

  @Test
  public void testRestoreOfMeetings() {
    Contact selectedContact = (Contact) data.getPopulatedSet().toArray()[2];
    Calendar selectedPastDate = DateFns.getPastDate(SMALL_OFFSET);
    Calendar selectedFutureDate = DateFns.getFutureDate(SMALL_OFFSET);

    final int initialPastSize = data.getManager().getPastMeetingListFor(selectedContact).size();
    final int initialFutureSize = data.getManager().getFutureMeetingList(selectedContact).size();

    int id1 = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                  selectedPastDate,
                                                  EMPTY_NOTES);
    int id2 = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                  selectedFutureDate);

    flushAndReload();

    final Contact newSelectedContact = (Contact) data.getManager()
                                                      .getContacts(selectedContact.getId())
                                                      .toArray()[0];

    Meeting pastMeeting = data.getManager().getPastMeeting(id1);
    final Meeting futureMeeting = data.getManager().getFutureMeeting(id2);

    data.getManager().addNewPastMeeting(data.getPopulatedSet(), selectedPastDate, EMPTY_NOTES);
    data.getManager().addFutureMeeting(data.getPopulatedSet(), selectedFutureDate);

    assertNotNull(pastMeeting);
    assertNotNull(futureMeeting);
    assertEquals(id1, pastMeeting.getId());
    assertEquals(id2, futureMeeting.getId());
    assertEquals(data.getPopulatedSet().size(), pastMeeting.getContacts().size());
    assertEquals(data.getPopulatedSet().size(), futureMeeting.getContacts().size());
    assertEquals(selectedPastDate, pastMeeting.getDate());
    assertEquals(selectedFutureDate, futureMeeting.getDate());
    assertEquals(initialPastSize + 2, data.getManager()
                                                .getPastMeetingListFor(newSelectedContact).size());
    assertEquals(initialFutureSize + 2, data.getManager()
                                                .getFutureMeetingList(newSelectedContact).size());

  }

  @Test
  public void testRestoreOfMeetingId() {
    Calendar selectedFutureDate = DateFns.getFutureDate(LARGE_OFFSET);
    int id1 = data.getManager().addFutureMeeting(data.getPopulatedSet(), selectedFutureDate);
    flushAndReload();
    int id2 = data.getManager().addFutureMeeting(data.getPopulatedSet(), selectedFutureDate);

    assertTrue(id2 > id1);
  }

  @Test
  public void testRestoreOfContactId() {
    String name1 = "Contact ID 1";
    String notes1 = "notes1";
    String name2 = "Contact ID 2";
    String notes2 = "notes2";
    int id1 = data.getManager().addNewContact(name1, notes1);
    flushAndReload();
    int id2 = data.getManager().addNewContact(name2, notes2);

    assertTrue(id2 > id1);
  }

  @Test
  public void testNoWriteExceptionRaised() {
    File file = new File(FILEPATH);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    file.setWritable(false);
    file.setReadable(false);

    flushAndReload();
  }
}
