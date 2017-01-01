import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class Validation {

    public static void validateIdPositive(int id) {
        if(id < 1)
            throw new IllegalArgumentException("Id must be greater than 0");
    }

    public static void validateObjectNotNull(Object obj) {
        validateObjectNotNull(obj, "Object");
    }

    public static void validateObjectNotNull(Object obj, String variableName) {
        if(obj == null)
            throw new NullPointerException(variableName + " supplied was null");
    }

    public static void validateSetPopulated(Set collection) {
        validateSetPopulated(collection, "Collection");
    }

    public static void validateSetPopulated(Set collection, String collectionName) {
        if(collection.size() < 1)
            throw new IllegalArgumentException(collectionName + " supplied is empty");
    }

    public static void validateSetPopulated(int[] collection, String collectionName) {
        if(collection.length < 1)
            throw new IllegalArgumentException(collectionName + " supplied is empty");
    }


    public static void validateArgumentSizeMatch(int length, int size) {
        if(length != size)
            throw new IllegalArgumentException("Not all input values are known");
    }

    public static void validateStringNotNullOrEmpty(String str, String variableName) {
        if(str == null)
            throw new NullPointerException(variableName + " supplied was null");

        if(str.equals(""))
            throw new IllegalArgumentException(variableName + " supplied is empty: "+str);
    }

    public static void validateAllContactsKnown(Set<Contact> contacts, Map<Integer, Contact> knownContacts) {
        Set<Contact> filtered = knownContacts.values().stream()
                .filter(e -> contacts.contains(e))
                .collect(Collectors.toSet());

        validateArgumentSizeMatch(filtered.size(), contacts.size());
    }

    public static void validateDateInFuture(Calendar date) {
        if(!date.after(Calendar.getInstance()))
            throw new IllegalArgumentException("Supplied date is not in the future");
    }

    public static void validateDateInPast(Calendar date) {
        if(!date.before(Calendar.getInstance()))
            throw new IllegalArgumentException("Supplied date is not in the past");
    }

    public static void validateStateInFuture(Calendar date) {
        if(!date.after(Calendar.getInstance()))
            throw new IllegalStateException("Supplied date is not in the future");
    }

    public static void validateStateInPast(Calendar date) {
        if(!date.before(Calendar.getInstance()))
            throw new IllegalStateException("Supplied date is not in the past");
    }

    public static void validateArgumentNotNull(Object obj, String argumentName) {
        if(obj == null)
            throw new IllegalArgumentException(argumentName + " supplied was null");
    }

    public static void validateContactKnown(Contact contact, Map<Integer, Contact> contacts) {
        Contact verify = contacts.get(contact.getId());
        if(!contact.equals(verify))
            throw new IllegalArgumentException("Contact not known");
    }
}
