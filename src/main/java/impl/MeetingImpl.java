package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.Meeting;

/**
  * @author Alexander Worton.
  */
public abstract class MeetingImpl implements Meeting, Serializable {
  /**
   * serialVersionUID holds the version for serialization. Increment when changes
   * to the data model occur.
   */
  private static final long serialVersionUID = Long.MIN_VALUE;

  private int id;
  private Calendar date;
  private Set<Contact> contacts;

  /**
   * Constructor for the meeting to set id, date and attached contacts.
   * @param id the id of the meeting
   * @param date the date of the meeting
   * @param contacts contacts attached to the meeting
   */
  MeetingImpl(final int id, final Calendar date, final Set<Contact> contacts) {
    setId(id);
    setDate(date);
    setContacts(contacts);
  }

  /**
   * Setter for the id. Validate that the id is positive and throw an
   * IllegalArgumentException otherwise.
   * @param id the id value to be set
   */
  private void setId(final int id) {
    Validation.validateIdPositive(id);
    this.id = id;
  }

  /**
   * Setter for the date. Validate that the date is not null and throw a
   * NullPointerException otherwise.
   * @param date the new date to set
   */
  private void setDate(final Calendar date) {
    final String variableName = "Date";
    Validation.validateObjectNotNull(date, variableName);
    this.date = date;
  }

  /**
   * Setter for the associated contacts.
   * Validate that contacts aren't null, or throw a NullPointerException
   * Validate that the contacts collection is populated  or throw an
   * IllegalArgumentException.
   * @param contacts the set of contacts
   */
  private void setContacts(final Set<Contact> contacts) {
    final String outputName = "Contacts";
    Validation.validateObjectNotNull(contacts, outputName);
    Validation.validateSetPopulated(contacts, outputName);
    this.contacts = contacts;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Calendar getDate() {
    return this.date;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Set<Contact> getContacts() {
    return this.contacts;
  }
}
