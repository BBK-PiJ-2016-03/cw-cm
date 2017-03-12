package manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Set;

import manager.library.ContactManagerImplTestFns;
import org.junit.Before;
import org.junit.Test;

import spec.Contact;
import spec.PastMeeting;


/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestAdding {

  private static final Calendar NULL_DATE = null;
  private static final Set<Contact> NULL_CONTACTS = null;
  private static final String NULL_NOTES = null;
  private static final String EMPTY_NOTES = "";
  private static final String NOTES = "Notes";
  private static final String NAME = "Contact Name";
  private static final String EMPTY_NAME = "";
  private static final String NULL_NAME = null;
  private int futureToPastMeetingId;
  private ContactManagerImplTestData data;

  /**
   * Setup method for all tests.
   */
  @Before
  public void before() {
    data = new ContactManagerImplTestData();
    futureToPastMeetingId = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                               data.getSlightlyFutureDate());
  }

  @Test
  public void testAddFutureMeetingIdReturned() {
    int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    assertTrue(id > 0);

    int id2 = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    assertTrue(id2 > 0 && id != id2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFutureMeetingContactNull() {
    data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), data.getFutureDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFutureMeetingContactUnknown() {
    data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), data.getFutureDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFutureMeetingDateInPast() {
    data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(), data.getPastDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFutureMeetingDateCurrent() {
    data.getManager().addFutureMeeting(data.getPopulatedSetWithNullContact(),
                                       Calendar.getInstance());
  }

  @Test(expected = NullPointerException.class)
  public void testAddFutureMeetingContactsNull() {
    data.getManager().addFutureMeeting(NULL_CONTACTS, data.getFutureDate());
  }

  @Test(expected = NullPointerException.class)
  public void testAddFutureMeetingDateNull() {
    data.getManager().addFutureMeeting(data.getPopulatedSet(), NULL_DATE);
  }

  @Test
  public void testAddNotesToExistingPastMeeting() {
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                 data.getPastDate(),
                                                 EMPTY_NOTES);
    PastMeeting meeting = (PastMeeting) data.getManager().getMeeting(id);
    assertEquals(EMPTY_NOTES, meeting.getNotes());

    meeting = data.getManager().addMeetingNotes(id, NOTES);

    assertEquals(id, meeting.getId());
    assertEquals(NOTES, meeting.getNotes());
  }

  @Test
  public void testAddNotesToExistingFutureMeeting() {
    ContactManagerImplTestFns.wait2Secs();
    PastMeeting meeting = data.getManager().addMeetingNotes(futureToPastMeetingId, NOTES);
    assertEquals(futureToPastMeetingId, meeting.getId());
    assertEquals(NOTES, meeting.getNotes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNotesNoExistMeeting() {
    data.getManager().addMeetingNotes(Integer.MAX_VALUE, NOTES);
  }

  @Test(expected = IllegalStateException.class)
  public void testAddNotesFutureMeeting() {
    int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    data.getManager().addMeetingNotes(id, NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void testAddNotesNull() {
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                 data.getPastDate(),
                                                 EMPTY_NOTES);
    data.getManager().addMeetingNotes(id, NULL_NOTES);
  }

  @Test
  public void testAddContactGetId() {
    int returnedId = data.getManager().addNewContact(NAME, NOTES);
    assertTrue(returnedId > 0);

    int returnedId2 = data.getManager().addNewContact(NAME, NOTES);
    assertTrue(returnedId2 > 0 && returnedId2 != returnedId);
  }

  @Test
  public void testAddContact() {
    String name = "Barry White";
    String notes = "These are the notes";
    int id = data.getManager().addNewContact(name, notes);
    int[] ids = new int[1];
    ids[0] = id;
    Set<Contact> contacts = data.getManager().getContacts(ids);
    assertTrue(contacts.stream()
        .anyMatch(c -> name.equals(c.getName())));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddContactNameEmpty() {
    data.getManager().addNewContact(EMPTY_NAME, NOTES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddContactNotesEmpty() {
    data.getManager().addNewContact(NAME, EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void testAddContactNameNull() {
    data.getManager().addNewContact(NULL_NAME, NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void testAddContactNotesNull() {
    data.getManager().addNewContact(NAME, NULL_NOTES);
  }

  @Test
  public void testAddPastMeetingIdReturned() {
    String note = "These are the Notes";
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), note);
    assertTrue(id > 0);

    int id2 = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                  data.getPastDate(),
                                                  EMPTY_NOTES);
    assertTrue(id2 > 0 && id != id2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPastMeetingContactNull() {
    data.getManager().addNewPastMeeting(data.getPopulatedSetWithNullContact(),
                                        data.getPastDate(),
                                        EMPTY_NOTES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPastMeetingContactUnknown() {
    data.getManager().addNewPastMeeting(data.getPopulatedSetWithInvalidContact(),
                                        data.getPastDate(),
                                        EMPTY_NOTES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPastMeetingDateInFuture() {
    data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                        data.getFutureDate(),
                                        EMPTY_NOTES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPastMeetingDateCurrent() {
    data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                        Calendar.getInstance(),
                                        EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void testAddPastMeetingContactsNull() {
    data.getManager().addNewPastMeeting(null, data.getPastDate(), EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void testAddPastMeetingDateNull() {
    data.getManager().addNewPastMeeting(data.getPopulatedSet(), null, EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void testAddPastMeetingTextNull() {
    data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), NULL_NOTES);
  }
}
