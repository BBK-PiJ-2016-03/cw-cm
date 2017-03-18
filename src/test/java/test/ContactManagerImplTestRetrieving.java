package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import impl.ContactImpl;
import impl.ContactManagerImpl;
import impl.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import spec.Contact;
import spec.Meeting;
import spec.PastMeeting;

import test.library.ContactManagerImplTestFns;

/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestRetrieving {

  private final ContactManagerImplTestData data;
  private int numFutureMeetingsOnFutureDateAfter;
  private int numPastMeetingsOnFutureDateAfter;
  private int numFutureMeetingsOnFutureDateBefore;
  private int numPastMeetingsOnFutureDateBefore;
  private final Contact nonExistContact;
  private static final Contact NULL_CONTACT = null;
  private static final String NOTE = "Note";
  private static final String EMPTY_NOTE = "";
  private static final String BLANK_NON_EMPTY_NOTE = " ";
  private static final String NAME_1 = "GetContactsName1";
  private static final String NAME_2 = "GetContactsName2";
  private static final String EMPTY_NAME = "";
  private static final String NULL_NAME = null;
  private static final Calendar NULL_DATE = null;
  private static final int DATE_OFFSET = 2;

  {
    data = new ContactManagerImplTestData();
    String contactName = "I Don't Exist";
    nonExistContact = new ContactImpl(Integer.MAX_VALUE, contactName);
  }

  /**
   * Setup environment prior to each test.
   */
  @Before
  public void before() {
    numFutureMeetingsOnFutureDateBefore = data.getManager()
                                              .getMeetingListOn(data.getFutureDate()).size();
    numPastMeetingsOnFutureDateBefore = data.getManager()
                                              .getMeetingListOn(data.getPastDate()).size();
  }

  @Test
  public void testGetNewContact() {
    data.setManager(new ContactManagerImpl());
    int contactId = data.getManager().addNewContact(NAME_1, NOTE);
    Set<Contact> contacts = data.getManager().getContacts(contactId);
    assertTrue(verifyContactIdsReturned(contacts, contactId));
  }

  private boolean verifyContactIdsReturned(Set<Contact> contacts, int... contactIds) {
    return contacts.stream()
        .allMatch(c -> IntStream.of(contactIds).anyMatch(i -> i == c.getId()));
  }

  @Test
  public void testGetNewContactsById() {
    int numberOfContactsToGenerate = 100;
    int[] contactIds = ContactManagerImplTestFns.generateValidContactIds(numberOfContactsToGenerate,
                                                                          data.getManager());
    Set<Contact> contacts = data.getManager().getContacts(contactIds);
    assertTrue(verifyContactIdsReturned(contacts, contactIds));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetEmptySetById() {
    data.setManager(new ContactManagerImpl());
    int[] contactIds = new int[0];
    data.getManager().getContacts(contactIds);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetUnknownContact() {
    data.setManager(new ContactManagerImpl());
    int[] contactIds = {1,9876543,2324253,323537};
    data.getManager().getContacts(contactIds);
  }

  @Test
  public void testGetExistingContactByName() {
    data.getManager().addNewContact(NAME_1, NOTE);
    Set<Contact> contacts = data.getManager().getContacts(NAME_1);
    int expectedSize = 1;
    assertEquals(expectedSize, contacts.size());
  }

  @Test
  public void testGetExistingContactsByName() {
    data.getManager().addNewContact(NAME_2, NOTE);
    data.getManager().addNewContact(NAME_2, NOTE);
    Set<Contact> contacts = data.getManager().getContacts(NAME_2);
    int expectedSize = 2;
    assertEquals(expectedSize, contacts.size());
  }

  @Test
  public void testGetExistingContactEmpty() {
    int beforeSize = data.getManager().getContacts(EMPTY_NAME).size();
    data.getManager().addNewContact(NAME_1, NOTE);
    data.getManager().addNewContact(NAME_2, NOTE);

    int afterSize = data.getManager().getContacts(EMPTY_NAME).size();
    int expectedSize = beforeSize + 2;
    assertEquals(expectedSize, afterSize);
  }

  @Test
  public void testGetExistingContactUnknown() {
    String unknownName = "here is a name which we hope remains unknown";
    int contactsSize = data.getManager().getContacts(unknownName).size();
    assertTrue(contactsSize == 0);
  }

  @Test(expected = NullPointerException.class)
  public void testGetContactNullName() {
    data.getManager().getContacts(NULL_NAME);
  }

  @Test
  public void testGetFutureMeetingNonExist() {
    Meeting meeting = data.getManager().getFutureMeeting(Integer.MAX_VALUE);
    assertNull(meeting);
  }

  @Test
  public void testGetFutureMeeting() {
    int id = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                data.getFutureDate());
    Meeting meeting = data.getManager().getFutureMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getFutureDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  @Test
  public void testGetFutureMeetingSlightlyFuture() {
    int id = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                data.getSlightlyFutureDate());
    Meeting meeting = data.getManager().getFutureMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getSlightlyFutureDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFutureMeetingPast() {
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                  data.getPastDate(),
                                                  EMPTY_NOTE);
    data.getManager().getFutureMeeting(id);
  }

  @Test
  public void testGetFutureMeetingListOnAllReturned() {
    final int selectedBefore = data.getManager()
                                    .getFutureMeetingList(data.getSelectedContact()).size();
    final int unSelectedBefore = data.getManager()
                                    .getFutureMeetingList(data.getExcludedContact()).size();

    final int numberOfPopulatedSetMeetings = 3;
    final int numberOfExcludedSetMeetings = 2;
    final int totalMeetingsAdded = numberOfPopulatedSetMeetings + numberOfExcludedSetMeetings;
    addNewFutureMeetings(numberOfPopulatedSetMeetings, numberOfExcludedSetMeetings);

    final int selectedAfter = data.getManager()
                                  .getFutureMeetingList(data.getSelectedContact()).size();
    final int unSelectedAfter = data.getManager()
                                  .getFutureMeetingList(data.getExcludedContact()).size();

    final int totalMeetingsDifference = selectedAfter - selectedBefore;
    final int populatedMeetingsDifference = unSelectedAfter - unSelectedBefore;

    assertEquals(totalMeetingsAdded, totalMeetingsDifference);
    assertEquals(numberOfPopulatedSetMeetings, populatedMeetingsDifference);
  }

  private void addNewFutureMeetings(int numPopulatedSet, int numExcludedSet) {
    final int totalMeetings = numPopulatedSet + numExcludedSet;
    for (int iteration = 0; iteration < totalMeetings; iteration++) {
      if (iteration < numPopulatedSet) {
        data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                          DateFns.getFutureDate(DATE_OFFSET));
      } else {
        data.getManager().addFutureMeeting(data.getExcludedSet(),
                                          DateFns.getFutureDate(DATE_OFFSET));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFutureMeetingListNonExistContact() {
    data.getManager().getFutureMeetingList(nonExistContact);
  }

  @Test(expected = NullPointerException.class)
  public void testGetFutureMeetingListNullContact() {
    data.getManager().getFutureMeetingList(NULL_CONTACT);
  }

  @Test
  public void testGetFutureMeetingListNoDuplicates() {
    List<Meeting> meetings = data.getManager().getFutureMeetingList(data.getSelectedContact());
    List<Meeting> noDupes = meetings.stream()
        .distinct()
        .collect(Collectors.toList());

    assertEquals(meetings.size(), noDupes.size());
  }

  @Test
  public void testGetFutureMeetingListChronologicallySorted() {
    List<Meeting> meetings = data.getManager().getFutureMeetingList(data.getSelectedContact());
    boolean sorted = true;
    for (int index = 1; index < meetings.size(); index++) {
      if (meetings.get(index - 1).getDate().after(meetings.get(index).getDate())) {
        sorted = false;
        break;
      }
    }
    assertTrue(sorted);
  }

  @Test
  public void testGetMeetingListExisting() {
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), NOTE);

    PastMeeting meeting = (PastMeeting)data.getManager().getMeeting(id);
    assertTrue(meeting.getContacts().equals(data.getPopulatedSet()));
    assertTrue(meeting.getDate().equals(data.getPastDate()));
    assertTrue(meeting.getNotes().equals(NOTE));
  }

  @Test
  public void testGetMeetingListNonExisting() {
    PastMeeting meeting = (PastMeeting)data.getManager().getMeeting(Integer.MAX_VALUE);
    assertNull(meeting);
  }

  @Test(expected = NullPointerException.class)
  public void testGetMeetingListOnNullDate() {
    data.getManager().getMeetingListOn(NULL_DATE);
  }

  @Test
  public void testGetMeetingListOnFutureDate() {
    int expected = 6;
    populateFutureAndPastMeetings();
    int actual = numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore;
    assertEquals(expected, actual);
  }

  private void populateFutureAndPastMeetings() {
    data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    data.getManager().addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(3));
    data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    data.getManager().addFutureMeeting(data.getPopulatedSet(), DateFns.getFutureDate(1));
    data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());

    data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), EMPTY_NOTE);
    data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                        DateFns.getPastDate(8),
                                        EMPTY_NOTE);
    data.getManager().addNewPastMeeting(data.getPopulatedSet(), data.getPastDate(), EMPTY_NOTE);
    data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                        DateFns.getPastDate(3),
                                        EMPTY_NOTE);
    numFutureMeetingsOnFutureDateAfter = data.getManager()
                                              .getMeetingListOn(data.getFutureDate()).size();
    numPastMeetingsOnFutureDateAfter = data.getManager()
                                              .getMeetingListOn(data.getPastDate()).size();
  }

  @Test
  public void testGetMeetingListOnPastDate() {
    int expected = 4;
    populateFutureAndPastMeetings();
    int actual = numPastMeetingsOnFutureDateAfter - numPastMeetingsOnFutureDateBefore;
    assertEquals(expected, actual);
  }

  @Test
  public void testGetMeetingListOnNoDuplicates() {
    List<Meeting> meetingsFuture = data.getManager().getMeetingListOn(data.getFutureDate());
    assertTrue(ContactManagerImplTestFns.testNoDuplicateMeetings(meetingsFuture));

    List<Meeting> meetingsPast = data.getManager().getMeetingListOn(data.getPastDate());
    assertTrue(ContactManagerImplTestFns.testNoDuplicateMeetings(meetingsPast));
  }

  @Test
  public void testGetMeetingListOnSorted() {
    List<Meeting> meetingsFuture = data.getManager().getMeetingListOn(data.getFutureDate());
    int expected = 6;
    populateFutureAndPastMeetings();
    int actual = numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore;
    assertEquals(expected, actual);
    assertTrue(ContactManagerImplTestFns.testChronologicallySorted(meetingsFuture));

    List<Meeting> meetingsPast = data.getManager().getMeetingListOn(data.getPastDate());
    assertTrue(ContactManagerImplTestFns.testChronologicallySorted(meetingsPast));
  }

  @Test
  public void testGetNonExistMeeting() {
    Meeting meeting = data.getManager().getPastMeeting(Integer.MAX_VALUE);
    assertNull(meeting);
  }

  @Test
  public void testGetMeetingPast() {
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                 data.getPastDate(),
                                                 EMPTY_NOTE);
    PastMeeting meeting = data.getManager().getPastMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getPastDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  @Test
  public void testGetMeetingSlightlyPast() {
    int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                  data.getSlightlyPastDate(),
                                                  EMPTY_NOTE);
    PastMeeting meeting = data.getManager().getPastMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getSlightlyPastDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  // can we assume that future events that now have a date in the past, but have not had
  // notes added to them did not actually take place?
  @Test
  public void testGetMeetingNotHappened() {
    int id = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                data.getSlightlyFutureDate());
    ContactManagerImplTestFns.wait2Secs();
    Meeting meeting = data.getManager().getPastMeeting(id);
    assertNull(meeting);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetMeetingFuture() {
    int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    data.getManager().getPastMeeting(id);
  }

  @Test
  public void testGetPastMeetingListForAllMeetingsReturned() {
    int meetingsSizeBefore = data.getManager()
                                 .getPastMeetingListFor(data.getSelectedContact()).size();
    int unSelectedMeetingsSizeBefore = data.getManager()
                                 .getPastMeetingListFor(data.getExcludedContact()).size();

    addPastMeetings();

    int meetingsSizeAfter = data.getManager()
                                .getPastMeetingListFor(data.getSelectedContact()).size();
    int unSelectedMeetingsSizeAfter = data.getManager()
                                          .getPastMeetingListFor(data.getExcludedContact()).size();

    int expectedMeetingSizeChange = 7;
    int actualMeetingsChange = meetingsSizeAfter - meetingsSizeBefore;
    int expectedUnselectedMeetingSizeChange = 4;
    int actualUnSelectedChange = unSelectedMeetingsSizeAfter - unSelectedMeetingsSizeBefore;

    assertEquals(expectedMeetingSizeChange, actualMeetingsChange);
    assertEquals(expectedUnselectedMeetingSizeChange, actualUnSelectedChange);
  }

  private void addPastMeetings() {
    Set<Contact> bothSet = new HashSet<>();
    bothSet.add(data.getSelectedContact());
    bothSet.add(data.getExcludedContact());

    Set<Contact> selectedSet = new HashSet<>();
    selectedSet.add(data.getSelectedContact());

    data.getManager().addNewPastMeeting(bothSet, data.getPastDate(), BLANK_NON_EMPTY_NOTE);
    int minuteOffset = 12;
    data.getManager().addNewPastMeeting(bothSet,
                                        DateFns.getPastDate(minuteOffset),
                                        BLANK_NON_EMPTY_NOTE);
    data.getManager().addNewPastMeeting(bothSet, data.getPastDate(), BLANK_NON_EMPTY_NOTE);
    minuteOffset = 7;
    data.getManager().addNewPastMeeting(bothSet,
                                        DateFns.getPastDate(minuteOffset),
                                        BLANK_NON_EMPTY_NOTE);

    data.getManager().addNewPastMeeting(selectedSet, data.getPastDate(), BLANK_NON_EMPTY_NOTE);
    minuteOffset = 2;
    data.getManager().addNewPastMeeting(selectedSet,
                                        DateFns.getPastDate(minuteOffset),
                                        BLANK_NON_EMPTY_NOTE);
    data.getManager().addNewPastMeeting(selectedSet, data.getPastDate(), BLANK_NON_EMPTY_NOTE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPastMeetingListForNonExistContact() {
    data.getManager().getPastMeetingListFor(nonExistContact);
  }

  @Test(expected = NullPointerException.class)
  public void testGetPastMeetingListForNullContact() {
    data.getManager().getPastMeetingListFor(null);
  }

  @Test
  public void testGetPastMeetingListForNoDuplicates() {
    addPastMeetings();

    List<PastMeeting> meetingsPastSelected = data.getManager()
                                                 .getPastMeetingListFor(data.getSelectedContact());
    assertTrue(ContactManagerImplTestFns.testNoDuplicatePastMeetings(meetingsPastSelected));

    List<PastMeeting> meetingsPastExcluded = data.getManager()
                                                 .getPastMeetingListFor(data.getExcludedContact());
    assertTrue(ContactManagerImplTestFns.testNoDuplicatePastMeetings(meetingsPastExcluded));
  }

  @Test
  public void testGetPastMeetingListForSorted() {
    addPastMeetings();

    List<PastMeeting> meetingsPastSelected = data.getManager()
                                                 .getPastMeetingListFor(data.getSelectedContact());
    assertTrue(ContactManagerImplTestFns
                .testChronologicallySortedPastMeetings(meetingsPastSelected));

    List<PastMeeting> meetingsPastExcluded = data.getManager()
                                                 .getPastMeetingListFor(data.getExcludedContact());
    assertTrue(ContactManagerImplTestFns
                .testChronologicallySortedPastMeetings(meetingsPastExcluded));
  }

}
