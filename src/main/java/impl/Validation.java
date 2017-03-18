package impl;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import spec.Contact;

/**
  * Validation is a helper class to aid with implementing validation checks in a DRY manner.
  * @author Alexander Worton.
  */
final class Validation {

  private Validation() {
  }

  /**
   * throws an exception if the supplied parameter isn't greater than 0.
   * @param id the id provided
   */
  public static void validateIdPositive(final int id) {
    if (id < 1) {
      throw new IllegalArgumentException("Id must be greater than 0");
    }
  }

  /**
   * throws an exception if the supplied parameter is null.
   * @param obj the object provided
   */
  public static void validateObjectNotNull(final Object obj) {
    final String variableName = "Object";
    validateObjectNotNull(obj, variableName);
  }

  /**
   * throws an exception if the supplied parameter is null using a supplied description.
   * @param obj the object provided
   * @param variableName a String used in the exception description
   */
  public static void validateObjectNotNull(final Object obj, final String variableName) {
    if (obj == null) {
      throw new NullPointerException(variableName + " supplied was null");
    }
  }

  /**
   * throws an exception if the supplied set isn't populated.
   * @param collection the set to validate
   * @param collectionName the name of the collection
   */
  public static void validateSetPopulated(final Set collection, final String collectionName) {
    if (collection.isEmpty()) {
      throw new IllegalArgumentException(collectionName + " supplied is empty");
    }
  }

  /**
   * throws an exception if the supplied int array isn't populated.
   * @param collection the collection to validate
   * @param collectionName the name of the collection
   */
  public static void validateSetPopulated(final int[] collection, final String collectionName) {
    if (collection.length < 1) {
      throw new IllegalArgumentException(collectionName + " supplied is empty");
    }
  }

  /**
   * throws an exception if the supplied arguments are not the same size.
   * @param length the first argument to match
   * @param size the second argument to match
   */
  public static void validateArgumentSizeMatch(final int length, final int size) {
    if (length != size) {
      throw new IllegalArgumentException("Not all input values are known");
    }
  }

  /**
   * throws an exception if the supplied string is either null or empty.
   * @param str the supplied string value
   * @param variableName the name of the variable
   */
  public static void validateStringNotNullOrEmpty(final String str, final String variableName) {
    if (str == null) {
      throw new NullPointerException(variableName + " supplied was null");
    }

    if ("".equals(str)) {
      throw new IllegalArgumentException(variableName + " supplied is empty: " + str);
    }
  }

  /**
   * throws an exception if any element in the contacts collection are not in the knownContacts
   * collection.
   * @param contacts the set of supplied contacts
   * @param knownContacts the set of known contacts
   */
  public static void validateAllContactsKnown(final Set<Contact> contacts,
                                              final Map<Integer, Contact> knownContacts) {
    final Set<Contact> filtered = knownContacts.values().stream()
            .filter(contacts::contains)
            .collect(Collectors.toSet());

    validateArgumentSizeMatch(filtered.size(), contacts.size());
  }

  /**
   * throws an argument exception if the supplied date is not in the future.
   * @param date the date to check
   */
  public static void validateDateInFuture(final Calendar date) {
    if (!date.after(Calendar.getInstance())) {
      throw new IllegalArgumentException("Supplied date is not in the future");
    }
  }

  /**
   * throws an argument exception if the supplied date is not in the past.
   * @param date the date to check
   */
  public static void validateDateInPast(final Calendar date) {
    if (!date.before(Calendar.getInstance())) {
      throw new IllegalArgumentException("Supplied date is not in the past");
    }
  }

  /**
   * throws a state exception if the supplied date is not in the future.
   * @param date the date to check
   */
  public static void validateStateInFuture(final Calendar date) {
    if (!date.after(Calendar.getInstance())) {
      throw new IllegalStateException("Supplied date is not in the future");
    }
  }

  /**
   * throws a state exception if the supplied date is not in the past.
   * @param date the date to check
   */
  public static void validateStateInPast(final Calendar date) {
    if (!date.before(Calendar.getInstance())) {
      throw new IllegalStateException("Supplied date is not in the past");
    }
  }

  /**
   * throws an argument exception if the supplied object is null.
   * @param obj the supplied object
   * @param argumentName the name of the supplied argument
   */
  public static void validateArgumentNotNull(final Object obj, final String argumentName) {
    if (obj == null) {
      throw new IllegalArgumentException(argumentName + " supplied was null");
    }
  }

  /**
   * throws an argument exception if the supplied contact is not in the contacts collection.
   * @param contact the supplied contact
   * @param contacts the collection of known contacts
   */
  public static void validateContactKnown(final Contact contact,
                                          final Map<Integer, Contact> contacts) {
    final Contact verify = contacts.get(contact.getId());
    if (!contact.equals(verify)) {
      throw new IllegalArgumentException("Contact not known");
    }
  }
}
