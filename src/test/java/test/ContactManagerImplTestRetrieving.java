package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
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

  private final transient ContactManagerImplTestData data;
  private transient int numFutureMeetingsOnFutureDateAfter;
  private transient int numPastMeetingsOnFutureDateAfter;
  private transient int numFutureMeetingsOnFutureDateBefore;
  private transient int numPastMeetingsOnFutureDateBefore;
  private final transient Contact nonExistContact;
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
    final String contactName = "I Don't Exist";
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
    final int contactId = data.getManager().addNewContact(NAME_1, NOTE);
    final Set<Contact> contacts = data.getManager().getContacts(contactId);
    assertTrue(verifyContactIdsReturned(contacts, contactId));
  }

  private boolean verifyContactIdsReturned(final Set<Contact> contacts, final int... contactIds) {
    return contacts.stream()
        .allMatch(c -> IntStream.of(contactIds).anyMatch(i -> i == c.getId()));
  }

  @Test
  public void testGetNewContactsById() {
    final int numberOfContactsToGenerate = 100;
    final int[] contactIds = ContactManagerImplTestFns
                              .generateValidContactIds(numberOfContactsToGenerate,
                                                       data.getManager());
    final Set<Contact> contacts = data.getManager().getContacts(contactIds);
    assertTrue(verifyContactIdsReturned(contacts, contactIds));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetEmptySetById() {
    data.setManager(new ContactManagerImpl());
    final int[] contactIds = new int[0];
    data.getManager().getContacts(contactIds);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetUnknownContact() {
    data.setManager(new ContactManagerImpl());
    final int[] contactIds = {1,9876543,2324253,323537};
    data.getManager().getContacts(contactIds);
  }

  @Test
  public void testGetExistingContactByName() {
    data.getManager().addNewContact(NAME_1, NOTE);
    final Set<Contact> contacts = data.getManager().getContacts(NAME_1);
    final int expectedSize = 1;
    assertEquals(expectedSize, contacts.size());
  }

  @Test
  public void testGetExistingContactsByName() {
    data.getManager().addNewContact(NAME_2, NOTE);
    data.getManager().addNewContact(NAME_2, NOTE);
    final Set<Contact> contacts = data.getManager().getContacts(NAME_2);
    final int expectedSize = 2;
    assertEquals(expectedSize, contacts.size());
  }

  @Test
  public void testGetExistingContactEmpty() {
    final int beforeSize = data.getManager().getContacts(EMPTY_NAME).size();
    data.getManager().addNewContact(NAME_1, NOTE);
    data.getManager().addNewContact(NAME_2, NOTE);

    final int afterSize = data.getManager().getContacts(EMPTY_NAME).size();
    final int expectedSize = beforeSize + 2;
    assertEquals(expectedSize, afterSize);
  }

  @Test
  public void testGetExistingContactUnknown() {
    final String unknownName = "here is a name which we hope remains unknown";
    final int contactsSize = data.getManager().getContacts(unknownName).size();
    final int actual = 0;
    assertSame(contactsSize, actual);
  }

  @Test(expected = NullPointerException.class)
  public void testGetContactNullName() {
    data.getManager().getContacts(NULL_NAME);
  }

  @Test
  public void testGetFutureMeetingNonExist() {
    final Meeting meeting = data.getManager().getFutureMeeting(Integer.MAX_VALUE);
    assertNull(meeting);
  }

  @Test
  public void testGetFutureMeeting() {
    final int id = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                data.getFutureDate());
    final Meeting meeting = data.getManager().getFutureMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getFutureDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  @Test
  public void testGetFutureMeetingSlightlyFuture() {
    final int id = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                data.getSlightlyFutureDate());
    final Meeting meeting = data.getManager().getFutureMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getSlightlyFutureDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFutureMeetingPast() {
    final int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
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

  private void addNewFutureMeetings(final int numPopulatedSet, final int numExcludedSet) {
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
    final List<Meeting> meetings = data.getManager()
                                       .getFutureMeetingList(data.getSelectedContact());
    final List<Meeting> noDupes = meetings.stream()
                                  .distinct()
                                  .collect(Collectors.toList());

    assertEquals(meetings.size(), noDupes.size());
  }

  @Test
  public void testGetFutureMeetingListChronologicallySorted() {
    final List<Meeting> meetings = data.getManager()
                                       .getFutureMeetingList(data.getSelectedContact());
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
    final int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                       data.getPastDate(), NOTE);

    final PastMeeting meeting = (PastMeeting)data.getManager().getMeeting(id);
    assertTrue(meeting.getContacts().equals(data.getPopulatedSet()));
    assertTrue(meeting.getDate().equals(data.getPastDate()));
    assertTrue(meeting.getNotes().equals(NOTE));
  }

  @Test
  public void testGetMeetingListNonExisting() {
    final PastMeeting meeting = (PastMeeting)data.getManager().getMeeting(Integer.MAX_VALUE);
    assertNull(meeting);
  }

  @Test(expected = NullPointerException.class)
  public void testGetMeetingListOnNullDate() {
    data.getManager().getMeetingListOn(NULL_DATE);
  }

  @Test
  public void testGetMeetingListOnFutureDate() {
    final int expected = 6;
    populateFutureAndPastMeetings();
    final int actual = numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore;
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
    final int expected = 4;
    populateFutureAndPastMeetings();
    final int actual = numPastMeetingsOnFutureDateAfter - numPastMeetingsOnFutureDateBefore;
    assertEquals(expected, actual);
  }

  @Test
  public void testGetMeetingListOnNoDuplicates() {
    final List<Meeting> meetingsFuture = data.getManager().getMeetingListOn(data.getFutureDate());
    assertTrue(ContactManagerImplTestFns.checkNoDuplicateMeetings(meetingsFuture));

    final List<Meeting> meetingsPast = data.getManager().getMeetingListOn(data.getPastDate());
    assertTrue(ContactManagerImplTestFns.checkNoDuplicateMeetings(meetingsPast));
  }

  @Test
  public void testGetMeetingListOnSorted() {
    final List<Meeting> meetingsFuture = data.getManager().getMeetingListOn(data.getFutureDate());
    final int expected = 6;
    populateFutureAndPastMeetings();
    final int actual = numFutureMeetingsOnFutureDateAfter - numFutureMeetingsOnFutureDateBefore;
    assertEquals(expected, actual);
    assertTrue(ContactManagerImplTestFns.checkChronologicallySorted(meetingsFuture));

    final List<Meeting> meetingsPast = data.getManager().getMeetingListOn(data.getPastDate());
    assertTrue(ContactManagerImplTestFns.checkChronologicallySorted(meetingsPast));
  }

  @Test
  public void testGetNonExistMeeting() {
    final Meeting meeting = data.getManager().getPastMeeting(Integer.MAX_VALUE);
    assertNull(meeting);
  }

  @Test
  public void testGetMeetingPast() {
    final int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                 data.getPastDate(),
                                                 EMPTY_NOTE);
    final PastMeeting meeting = data.getManager().getPastMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getPastDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  @Test
  public void testGetMeetingSlightlyPast() {
    final int id = data.getManager().addNewPastMeeting(data.getPopulatedSet(),
                                                  data.getSlightlyPastDate(),
                                                  EMPTY_NOTE);
    final PastMeeting meeting = data.getManager().getPastMeeting(id);
    assertEquals(id, meeting.getId());
    assertEquals(data.getSlightlyPastDate(), meeting.getDate());
    assertEquals(data.getPopulatedSet(), meeting.getContacts());
  }

  // can we assume that future events that now have a date in the past, but have not had
  // notes added to them did not actually take place?
  @Test
  public void testGetMeetingNotHappened() {
    final int id = data.getManager().addFutureMeeting(data.getPopulatedSet(),
                                                data.getSlightlyFutureDate());
    ContactManagerImplTestFns.wait2Secs();
    final Meeting meeting = data.getManager().getPastMeeting(id);
    assertNull(meeting);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetMeetingFuture() {
    final int id = data.getManager().addFutureMeeting(data.getPopulatedSet(), data.getFutureDate());
    data.getManager().getPastMeeting(id);
  }

  @Test
  public void testGetPastMeetingListForAllMeetingsReturned() {
    final int meetingsSizeBefore = data.getManager()
                                 .getPastMeetingListFor(data.getSelectedContact()).size();
    final int unSelectedMeetingsSizeBefore = data.getManager()
                                 .getPastMeetingListFor(data.getExcludedContact()).size();

    addPastMeetings();

    final int meetingsSizeAfter = data.getManager()
                                .getPastMeetingListFor(data.getSelectedContact()).size();
    final int unSelectedMeetingsSizeAfter = data.getManager()
                                          .getPastMeetingListFor(data.getExcludedContact()).size();

    final int expectedMeetingSizeChange = 7;
    final int actualMeetingsChange = meetingsSizeAfter - meetingsSizeBefore;
    final int expectedUnselectedMeetingSizeChange = 4;
    final int actualUnSelectedChange = unSelectedMeetingsSizeAfter - unSelectedMeetingsSizeBefore;

    assertEquals(expectedMeetingSizeChange, actualMeetingsChange);
    assertEquals(expectedUnselectedMeetingSizeChange, actualUnSelectedChange);
  }

  private void addPastMeetings() {
    final Set<Contact> bothSet = new HashSet<>();
    bothSet.add(data.getSelectedContact());
    bothSet.add(data.getExcludedContact());

    final Set<Contact> selectedSet = new HashSet<>();
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

    final List<PastMeeting> meetingsPastSelected = data.getManager()
                                                 .getPastMeetingListFor(data.getSelectedContact());
    assertTrue(ContactManagerImplTestFns.checkNoDuplicatePastMeetings(meetingsPastSelected));

    final List<PastMeeting> meetingsPastExcluded = data.getManager()
                                                 .getPastMeetingListFor(data.getExcludedContact());
    assertTrue(ContactManagerImplTestFns.checkNoDuplicatePastMeetings(meetingsPastExcluded));
  }

  @Test
  public void testGetPastMeetingListForSorted() {
    addPastMeetings();

    final List<PastMeeting> meetingsPastSelected = data.getManager()
                                                 .getPastMeetingListFor(data.getSelectedContact());
    assertTrue(ContactManagerImplTestFns
                .checkChronologicallySortedPastMeetings(meetingsPastSelected));

    final List<PastMeeting> meetingsPastExcluded = data.getManager()
                                                 .getPastMeetingListFor(data.getExcludedContact());
    assertTrue(ContactManagerImplTestFns
                .checkChronologicallySortedPastMeetings(meetingsPastExcluded));
  }

}
