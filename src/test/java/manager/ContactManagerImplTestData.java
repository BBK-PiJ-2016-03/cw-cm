package manager;

import java.util.Calendar;
import java.util.Set;

import manager.library.ContactManagerImplTestFns;

import spec.Contact;
import spec.ContactManager;

/**
 * @author Alexander Worton.
 */
class ContactManagerImplTestData {

  private ContactManager manager;
  private final Calendar pastDate;
  private final Calendar slightlyPastDate;
  private final Calendar futureDate;
  private final Calendar slightlyFutureDate;
  private final int[] excludedSetIds;
  private final int excludedContactId;
  private static final int NUMBER_OF_CONTACTS = 50;
  private static final String EMPTY_NAME = "";

  {
    manager = new ContactManagerImpl();
    ContactManagerImplTestFns.createValidContacts(NUMBER_OF_CONTACTS, manager);
    ContactManagerImplTestFns.createInvalidContacts(NUMBER_OF_CONTACTS, manager);
    pastDate = DateFns.getPastDate();
    futureDate = DateFns.getFutureDate();
    slightlyPastDate = DateFns.getSlightlyPastDate();
    slightlyFutureDate = DateFns.getSlightlyFutureDate();
    excludedContactId = 3;
    excludedSetIds = ContactManagerImplTestFns.generateExcludedSetIds(getPopulatedSet(),
                                                                      getExcludedContactId());
  }

  public Contact getSelectedContact() {
    return (Contact)manager.getContacts(getExcludedSetIds()).toArray()[0];
  }

  public Contact getExcludedContact() {
    return (Contact)manager.getContacts(getExcludedContactId()).toArray()[0];
  }

  public Set<Contact> getPopulatedSet() {
    return manager.getContacts(EMPTY_NAME);
  }

  public Set<Contact> getExcludedSet() {
    return manager.getContacts(getExcludedSetIds());
  }

  public Set<Contact> getPopulatedSetWithInvalidContact() {
    return ContactManagerImplTestFns.generateInvalidContacts();
  }

  public Set<Contact> getPopulatedSetWithNullContact() {
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

  public int[] getExcludedSetIds() {
    return excludedSetIds;
  }

  public int getExcludedContactId() {
    return excludedContactId;
  }

  public void setManager(ContactManagerImpl manager) {
    this.manager = manager;
  }
}