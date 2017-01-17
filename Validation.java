import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexander Worton on 28/12/2016.
 * Validation is a helper class to aid with implementing required validation checks in a DRY manner
 */
public class Validation {

    /**
     * throws an exception if the supplied parameter isn't greater than 0
     * @param id the id provided
     */
    public static void validateIdPositive(int id) {
        if(id < 1)
            throw new IllegalArgumentException("Id must be greater than 0");
    }

    /**
     * throws an exception if the supplied parameter is null
     * @param obj the object provided
     */
    public static void validateObjectNotNull(Object obj) {
        validateObjectNotNull(obj, "Object");
    }

    /**
     * throws an exception if the supplied parameter is null using a supplied description
     * @param obj the object provided
     * @param variableName a String used in the exception description
     */
    public static void validateObjectNotNull(Object obj, String variableName) {
        if(obj == null)
            throw new NullPointerException(variableName + " supplied was null");
    }

    /**
     * throws an exception if the supplied set isn't populated
     * @param collection
     * @param collectionName
     */
    public static void validateSetPopulated(Set collection, String collectionName) {
        if(collection.size() < 1)
            throw new IllegalArgumentException(collectionName + " supplied is empty");
    }

    /**
     * throws an exception if the supplied int array isn't populated
     * @param collection
     * @param collectionName
     */
    public static void validateSetPopulated(int[] collection, String collectionName) {
        if(collection.length < 1)
            throw new IllegalArgumentException(collectionName + " supplied is empty");
    }

    /**
     * throws an exception if the supplied arguments are not the same size
     * @param length
     * @param size
     */
    public static void validateArgumentSizeMatch(int length, int size) {
        if(length != size)
            throw new IllegalArgumentException("Not all input values are known");
    }

    /**
     * throws an exception if the supplied string is either null or empty
     * @param str
     * @param variableName
     */
    public static void validateStringNotNullOrEmpty(String str, String variableName) {
        if(str == null)
            throw new NullPointerException(variableName + " supplied was null");

        if(str.equals(""))
            throw new IllegalArgumentException(variableName + " supplied is empty: "+str);
    }

    /**
     * throws an exception if any element in the contacts collection are not in the knownContacts collection
     * @param contacts
     * @param knownContacts
     */
    public static void validateAllContactsKnown(Set<Contact> contacts, Map<Integer, Contact> knownContacts) {
        Set<Contact> filtered = knownContacts.values().stream()
                .filter(e -> contacts.contains(e))
                .collect(Collectors.toSet());

        validateArgumentSizeMatch(filtered.size(), contacts.size());
    }

    /**
     * throws an argument exception if the supplied date is not in the future
     * @param date
     */
    public static void validateDateInFuture(Calendar date) {
        if(!date.after(Calendar.getInstance()))
            throw new IllegalArgumentException("Supplied date is not in the future");
    }

    /**
     * throws an argument exception if the supplied date is not in the past
     * @param date
     */
    public static void validateDateInPast(Calendar date) {
        if(!date.before(Calendar.getInstance()))
            throw new IllegalArgumentException("Supplied date is not in the past");
    }

    /**
     * throws a state exception if the supplied date is not in the future
     * @param date
     */
    public static void validateStateInFuture(Calendar date) {
        if(!date.after(Calendar.getInstance()))
            throw new IllegalStateException("Supplied date is not in the future");
    }

    /**
     * throws a state exception if the supplied date is not in the past
     * @param date
     */
    public static void validateStateInPast(Calendar date) {
        if(!date.before(Calendar.getInstance()))
            throw new IllegalStateException("Supplied date is not in the past");
    }

    /**
     * throws an argument exception if the supplied object is null
     * @param obj
     * @param argumentName
     */
    public static void validateArgumentNotNull(Object obj, String argumentName) {
        if(obj == null)
            throw new IllegalArgumentException(argumentName + " supplied was null");
    }

    /**
     * throws an argument exception if the supplied contact is not in the contacts collection
     * @param contact
     * @param contacts
     */
    public static void validateContactKnown(Contact contact, Map<Integer, Contact> contacts) {
        Contact verify = contacts.get(contact.getId());
        if(!contact.equals(verify))
            throw new IllegalArgumentException("Contact not known");
    }
}
