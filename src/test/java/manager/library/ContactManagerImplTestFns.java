package manager.library;

import manager.ContactImpl;
import spec.Contact;
import spec.ContactManager;
import spec.Meeting;
import spec.PastMeeting;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestFns {

  public static Set<Contact> generateNullContacts() {
    Set<Contact> set = new HashSet<>();
    set.add(null);
    return set;
  }

  public static int[] generateValidContactIds(int number, ContactManager manager){
    return IntStream.range(0,number)
        .map(i -> manager.addNewContact("Name"+i, " "))
        .toArray();
  }

  public static Set<Contact> generateInvalidContacts(){
    Set<Contact> set = new HashSet<>();
    set.add(new ContactImpl(Integer.MAX_VALUE, "Invalid Contact", "No Notes"));
    return set;
  }

  public static int[] createValidContacts(int number, ContactManager manager) {
    return IntStream.range(0,number)
        .map(i -> manager.addNewContact("Name"+i, " "))
        .toArray();
  }

  public static int[] createInvalidContacts(int number, ContactManager manager) {
    return IntStream.range(0,number)
        .map(i -> manager.addNewContact("Name"+i, " "))
        .toArray();
  }

  public static void wait2Secs() {
    try {
      Thread.sleep(2_000);
    }
    catch(InterruptedException e){
      //wait less
    }
  }

  public static int[] generateExcludedSetIds(Set<Contact> contacts, int excludedContactId) {
    return contacts.stream()
        .mapToInt(Contact::getId)
        .filter(e -> !(e == excludedContactId))
        .toArray();
  }

  public static boolean testChronologicallySorted(List<Meeting> meetings) {
    boolean sorted = true;
    for (int i = 1; i < meetings.size(); i++){
      if(meetings.get(i-1).getDate().after(meetings.get(i).getDate())){
        sorted = false;
        break;
      }
    }
    return sorted;
  }

  public static boolean testChronologicallySortedPastMeetings(List<PastMeeting> meetings) {
    boolean sorted = true;
    for (int i = 1; i < meetings.size(); i++) {
      if (meetings.get(i-1).getDate().after(meetings.get(i).getDate())) {
        sorted = false;
        break;
      }
    }
    return sorted;
  }

  public static boolean testDuplicateMeetings(List<Meeting> meetings) {
    return meetings.size() ==  meetings.stream()
        .map(Meeting::getId)
        .distinct()
        .collect(Collectors.toList()).size();
  }

  public static boolean testDuplicatePastMeetings(List<PastMeeting> meetings) {
    return meetings.size() == meetings.stream()
        .map(Meeting::getId)
        .distinct()
        .collect(Collectors.toList()).size();
  }
}
