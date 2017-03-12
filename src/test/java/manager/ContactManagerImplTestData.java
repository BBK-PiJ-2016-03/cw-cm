package manager;

import manager.library.ContactManagerImplTestFns;
import spec.Contact;
import spec.ContactManager;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Worton.
 */
public class ContactManagerImplTestData {

  private ContactManager manager;
  private final Calendar pastDate;
  private final Calendar slightlyPastDate;
  private final Calendar futureDate;
  private final Calendar slightlyFutureDate;
  private final Set<Contact> emptySet;
  private final int[] excludedSetIds;
  private final int selectedContactId;
  private final int excludedContactId;
  private final int NUMBER_OF_CONTACTS = 50;

  {
    manager = new ContactManagerImpl();
    ContactManagerImplTestFns.createValidContacts(NUMBER_OF_CONTACTS, manager);
    ContactManagerImplTestFns.createInvalidContacts(NUMBER_OF_CONTACTS, manager);
    pastDate = DateFns.getPastDate();
    futureDate = DateFns.getFutureDate();
    slightlyPastDate = DateFns.getSlightlyPastDate();
    slightlyFutureDate = DateFns.getSlightlyFutureDate();
    emptySet = new HashSet<>();
    selectedContactId = 5;
    excludedContactId = 3;
    excludedSetIds = ContactManagerImplTestFns.generateExcludedSetIds(getPopulatedSet(), excludedContactId);
  }

  public Contact getSelectedContact() {
    return (Contact)manager.getContacts(selectedContactId).toArray()[0];
  }

  public Contact getExcludedContact() {
    return (Contact)manager.getContacts(excludedContactId).toArray()[0];
  }

  public Set<Contact> getPopulatedSet() {
    return manager.getContacts("");
  }

  public Set<Contact> getExcludedSet(){
    return manager.getContacts(excludedSetIds);
  }

  public Set<Contact> getPopulatedSetWithInvalidContact(){
    return ContactManagerImplTestFns.generateInvalidContacts();
  }

  public Set<Contact> getPopulatedSetWithNullContact(){
    return ContactManagerImplTestFns.generateNullContacts();
  }

  public ContactManager getManager() {
    return manager;
  }

  public Calendar getPastDate() {
    return pastDate;
  }

  public Calendar getSlightlyPastDate() {
    return slightlyPastDate;
  }

  public Calendar getFutureDate() {
    return futureDate;
  }

  public Calendar getSlightlyFutureDate() {
    return slightlyFutureDate;
  }

  public Set<Contact> getEmptySet() {
    return emptySet;
  }

  public int[] getExcludedSetIds() {
    return excludedSetIds;
  }

  public int getSelectedContactId() {
    return selectedContactId;
  }

  public int getExcludedContactId() {
    return excludedContactId;
  }

  public void setManager(ContactManagerImpl manager) {
    this.manager = manager;
  }
}