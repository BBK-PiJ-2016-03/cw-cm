import tests.DateFns;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestsFns {

    public static Set<Contact> generateNullContacts(int number, ContactManager manager) {
        int[] contactIds = IntStream.range(1,number)
                .map(i -> manager.addNewContact("Name"+i, " "))
                .toArray();

        Set<Contact> contacts =  manager.getContacts(contactIds);
        contacts.add(null);
        return contacts;
    }

    public static Set<Contact> generateValidContacts(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+i, " "))
                .toArray();

        return manager.getContacts(contactIds);
    }

    public static Set<Contact> generateInvalidContacts(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+(number+i), " "))
                .toArray();

        Set<Contact> contacts =  manager.getContacts(contactIds);
        contacts.add(new ContactImpl(number*3,"Unknown", " "));
        return contacts;
    }

    public static int[] generateInvalidContactIds(int number, ContactManager manager) {
        int[] contactIds = IntStream.range(1,number)
                .map(i -> {
                    if(i > 1)
                        return manager.addNewContact("Name"+i, " ");
                    return Integer.MAX_VALUE;
                })
                .toArray();

        return contactIds;
    }

    public static int[] generateValidContactIds(int number, ContactManager manager){
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

    public static Set<Contact> generateExcludedSet(Set<Contact> contacts, Contact excludedContact){
        return contacts.stream()
                .filter(e -> !e.equals(excludedContact))
                .collect(Collectors.toSet());
    }

    public static void generateMeetingsInclusiveAndExclusiveOfContact(ContactManagerImplTestData data){
        data.manager.addFutureMeeting(data.populatedSet, data.futureDate);
        data.manager.addFutureMeeting(data.populatedSet, DateFns.getFutureDate(2));
        data.manager.addFutureMeeting(data.populatedSet, DateFns.getFutureDate(7));
        data.manager.addFutureMeeting(data.populatedSet, DateFns.getFutureDate(3));
        data.manager.addFutureMeeting(data.excludedSet, DateFns.getFutureDate(6));
        data.manager.addFutureMeeting(data.excludedSet, DateFns.getFutureDate(4));
        data.manager.addFutureMeeting(data.populatedSet, DateFns.getFutureDate(8));
        data.manager.addFutureMeeting(data.populatedSet, DateFns.getFutureDate(5));
        data.manager.addFutureMeeting(data.excludedSet, DateFns.getFutureDate(1));

        data.manager.addNewPastMeeting(data.populatedSet, data.pastDate, "");
        data.manager.addNewPastMeeting(data.excludedSet, DateFns.getPastDate(1), "");
        data.manager.addNewPastMeeting(data.populatedSet, DateFns.getPastDate(2), "");
        data.manager.addNewPastMeeting(data.populatedSet, DateFns.getPastDate(5), "");
        data.manager.addNewPastMeeting(data.populatedSet, DateFns.getPastDate(3), "");
        data.manager.addNewPastMeeting(data.populatedSet, DateFns.getPastDate(8), "");
        data.manager.addNewPastMeeting(data.excludedSet, DateFns.getPastDate(4), "");
        data.manager.addNewPastMeeting(data.excludedSet, DateFns.getPastDate(6), "");
        data.manager.addNewPastMeeting(data.populatedSet, DateFns.getPastDate(7), "");
    }
}
