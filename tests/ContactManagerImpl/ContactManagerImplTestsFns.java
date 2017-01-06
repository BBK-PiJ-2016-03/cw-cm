import tests.DateFns;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestsFns {

    public static Set<Contact> generateNullContacts() {
        Set<Contact> set = new HashSet<>();
        set.add(null);
        return set;
    }

    public static int[] generateValidContactIds(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+i, " "))
                .toArray();

        return contactIds;
    }

    public static Set<Contact> generateInvalidContacts(){
        Set<Contact> set = new HashSet<>();
        set.add(new ContactImpl(Integer.MAX_VALUE, "Invalid Contact", "No Notes"));
        return set;
    }

    public static int[] createValidContacts(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+i, " "))
                .toArray();

        return contactIds;
    }

    public static int[] createInvalidContacts(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+i, " "))
                .toArray();

        return contactIds;
    }

    public static void wait2Secs() {
        try {
            Thread.sleep(2_000);
        }
        catch(InterruptedException e){
            //wait less
        }
    }

    public static int[] generateExcludedSetIds(Set<Contact> contacts, int excludedContactId){
        return contacts.stream()
                .mapToInt(e -> e.getId())
                .filter(e -> !(e == excludedContactId))
                .toArray();
    }

    public static void generateMeetingsInclusiveAndExclusiveOfContact(ContactManagerImplTestData data){
        data.manager.addFutureMeeting(data.getpopulatedSet(), data.futureDate);
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(2));
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(7));
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(3));
        data.manager.addFutureMeeting(data.getExcludedSet(), DateFns.getFutureDate(6));
        data.manager.addFutureMeeting(data.getExcludedSet(), DateFns.getFutureDate(4));
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(8));
        data.manager.addFutureMeeting(data.getpopulatedSet(), DateFns.getFutureDate(5));
        data.manager.addFutureMeeting(data.getExcludedSet(), DateFns.getFutureDate(1));

        data.manager.addNewPastMeeting(data.getpopulatedSet(), data.pastDate, "");
        data.manager.addNewPastMeeting(data.getExcludedSet(), DateFns.getPastDate(8), "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(7), "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(6), "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(5), "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(4), "");
        data.manager.addNewPastMeeting(data.getExcludedSet(), DateFns.getPastDate(3), "");
        data.manager.addNewPastMeeting(data.getExcludedSet(), DateFns.getPastDate(2), "");
        data.manager.addNewPastMeeting(data.getpopulatedSet(), DateFns.getPastDate(1), "");
    }

    public static boolean testChronologicallySorted(List<Meeting> meetings){
        boolean sorted = true;
        MEETINGS_ITERATION: for (int i = 1; i < meetings.size(); i++){
            if(meetings.get(i-1).getDate().after(meetings.get(i).getDate())){
                sorted = false;
                break MEETINGS_ITERATION;
            }
        }
        return sorted;
    }

    public static boolean testChronologicallySortedPastMeetings(List<PastMeeting> meetings){
        boolean sorted = true;
        MEETINGS_ITERATION: for (int i = 1; i < meetings.size(); i++){
            if(meetings.get(i-1).getDate().after(meetings.get(i).getDate())){
                sorted = false;
                break MEETINGS_ITERATION;
            }
        }
        return sorted;
    }

//    public static boolean testDuplicateMeetings(List<Meeting> meetings){
//        return meetings.size() ==  meetings.stream()
//                .map(m -> m.getDate().getTimeInMillis())
//                .distinct()
//                .collect(Collectors.toList()).size();
//    }

    public static boolean testDuplicateMeetings(List<Meeting> meetings){
        return meetings.size() ==  meetings.stream()
                .map(m -> m.getId())
                .distinct()
                .collect(Collectors.toList()).size();
    }

//    public static boolean testDuplicatePastMeetings(List<PastMeeting> meetings){
//        return meetings.size() == meetings.stream()
//                .map(m -> m.getDate().getTimeInMillis())
//                .distinct()
//                .collect(Collectors.toList()).size();
//    }

    public static boolean testDuplicatePastMeetings(List<PastMeeting> meetings){
        return meetings.size() == meetings.stream()
                .map(m -> m.getId())
                .distinct()
                .collect(Collectors.toList()).size();
    }
}
