package test.library;

import impl.ContactImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import spec.Contact;
import spec.ContactManager;
import spec.Meeting;
import spec.PastMeeting;

/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestFns {

  private static final String BLANK_NOTES = " ";
  private static final String INVALID_NAME = "Invalid Contact";
  private static final String NOTES = "Some Notes";
  private static final String NAME_PREFIX = "Name";

  /**
   * Create a set of null contacts.
   * @return the set of null contacts
   */
  public static Set<Contact> generateNullContacts() {
    final Set<Contact> set = new HashSet<>();
    set.add(null);
    return set;
  }

  /**
   * Generate a set of valid contacts.
   * @param number the number of valid contacts to generate
   * @param manager  the contact manager to use for the generation
   * @return an array of the generated contact's ids
   */
  public static int[] generateValidContactIds(final int number, final ContactManager manager) {
    return IntStream.range(0,number)
        .map(iteration -> manager.addNewContact(NAME_PREFIX + iteration, BLANK_NOTES))
        .toArray();
  }

  /**
   * Generate a set of invalid contacts.
   * @return the set of invalid contacts
   */
  public static Set<Contact> generateInvalidContacts() {
    final Set<Contact> set = new HashSet<>();
    set.add(new ContactImpl(Integer.MAX_VALUE, INVALID_NAME, NOTES));
    return set;
  }

  /**
   * Create a set of valid contacts.
   * @param number the number of contacts to create
   * @param manager the manager to use in the creation
   * @return an array holding the valid contact ids
   */
  public static int[] createValidContacts(final int number, final ContactManager manager) {
    return IntStream.range(0,number)
        .map(iteration -> manager.addNewContact(NAME_PREFIX + iteration, BLANK_NOTES))
        .toArray();
  }

  /**
   * Create a set of invalid contacts.
   * @return the an array of the ids of the invalid contacts
   */
  public static int[] createInvalidContacts(final int number, final ContactManager manager) {
    return IntStream.range(0,number)
        .map(iteration -> manager.addNewContact(NAME_PREFIX + iteration, BLANK_NOTES))
        .toArray();
  }

  /**
   * implement a 2 second delay.
   */
  public static void wait2Secs() {
    try {
      Thread.sleep(2_000);
    } catch (InterruptedException e) {
      //wait less
    }
  }

  /**
   * Generate a set of ids which exclude the provided id.
   * @param contacts the set of contacts
   * @param excludedContactId the provided id
   * @return an array of ids excluding the omitted one.
   */
  public static int[] generateExcludedSetIds(final Set<Contact> contacts,
                                             final int excludedContactId) {
    return contacts.stream()
        .mapToInt(Contact::getId)
        .filter(e -> !(e == excludedContactId))
        .toArray();
  }

  /**
   * Perform check that the provided meetings are in chronological order.
   * @param meetings the provided meetings
   * @return true if they are sorted, false otherwise
   */
  public static boolean checkChronologicallySorted(final List<Meeting> meetings) {
    boolean sorted = true;
    for (int index = 1; index < meetings.size(); index++) {
      if (meetings.get(index - 1).getDate().after(meetings.get(index).getDate())) {
        sorted = false;
        break;
      }
    }
    return sorted;
  }

  /**
   * Perform check that the provided past meetings are in chronological order.
   * @param meetings the provided past meetings
   * @return true if they are sorted, false otherwise
   */
  public static boolean checkChronologicallySortedPastMeetings(final List<PastMeeting> meetings) {
    boolean sorted = true;
    for (int index = 1; index < meetings.size(); index++) {
      if (meetings.get(index - 1).getDate().after(meetings.get(index).getDate())) {
        sorted = false;
        break;
      }
    }
    return sorted;
  }

  /**
   * Test for duplicate meetings.
   * @param meetings the provided meetings
   * @return true if there are no duplicate meetings, false otherwise
   */
  public static boolean checkNoDuplicateMeetings(final List<Meeting> meetings) {
    return meetings.size() == meetings.stream()
        .map(Meeting::getId)
        .distinct()
        .collect(Collectors.toList()).size();
  }

  /**
   * Test for duplicate past meetings.
   * @param meetings the provided meetings
   * @return true if there are no duplicate past meetings, false otherwise
   */
  public static boolean checkNoDuplicatePastMeetings(final List<PastMeeting> meetings) {
    return meetings.size() == meetings.stream()
        .map(Meeting::getId)
        .distinct()
        .collect(Collectors.toList()).size();
  }
}
