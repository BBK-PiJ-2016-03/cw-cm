import java.util.Set;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestsFns {

    public static Set<Contact> generateNullContacts(int number, ContactManager manager) {
        int[] contactIds = IntStream.range(1,number)
                .map(i -> manager.addNewContact("Name"+i, ""))
                .toArray();

        Set<Contact> contacts =  manager.getContacts(contactIds);
        contacts.add(null);
        return contacts;
    }

    public static Set<Contact> generateValidContacts(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+i, ""))
                .toArray();

        return manager.getContacts(contactIds);
    }

    public static Set<Contact> generateInvalidContacts(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+(number+i), ""))
                .toArray();

        Set<Contact> contacts =  manager.getContacts(contactIds);
        contacts.add(new ContactImpl(number*3,"Unknown", ""));
        return contacts;
    }

    public static int[] generateInvalidContactIds(int number, ContactManager manager) {
        int[] contactIds = IntStream.range(1,number)
                .map(i -> {
                    if(i > 1)
                        return manager.addNewContact("Name"+i, "");
                    return Integer.MAX_VALUE;
                })
                .toArray();

        return contactIds;
    }

    public static int[] generateValidContactIds(int number, ContactManager manager){
        int[] contactIds = IntStream.range(0,number)
                .map(i -> manager.addNewContact("Name"+i, ""))
                .toArray();

        return contactIds;
    }
}
