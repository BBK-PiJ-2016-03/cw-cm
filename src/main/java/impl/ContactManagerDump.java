package impl;

import java.io.Serializable;
import java.util.Map;

import spec.Contact;
import spec.Meeting;

/**
 * ContactManagerDump is a tightly coupled pojo used to serialize data from ContactManagerImpl.
 * Its purpose is to reduce the size of data written out to disc by dropping all non data aspects
 * of the serialized object.
 *
 * <p>Since the class is completely dependent on the ContactManagerImpl class, and needs to be
 * updated to keep in sync with any changes to the data structure, it is included as a nested
 * class.
 *
 * @author Alexander Worton.
 */
class ContactManagerDump implements Serializable {
  /**
   * serialVersionUID holds the version for the dump. Increment when changes
   * to the data model occur.
   */
  private static final long serialVersionUID = Long.MIN_VALUE;

  /** lastContactId field. */
  private int lastContactId;
  /** contacts field. Collection of known contacts. */
  private Map<Integer, Contact> contacts;
  /** lastMeetingId field. */
  private int lastMeetingId;
  /** meetings field. Collection of known meetings. */
  private Map<Integer, Meeting> meetings;

  /**
   * Getter for lastContactId.
   * @return lastContactId
   */
  int getLastContactId() {
    return lastContactId;
  }

  /**
   * Setter for lastContactId.
   * @param suppliedContactId the supplied contact id
   */
  void setLastContactId(int suppliedContactId) {
    this.lastContactId = suppliedContactId;
  }

  /**
   * Getter for contacts.
   * @return all known contacts
   */
  public Map<Integer, Contact> getContacts() {
    return contacts;
  }

  /**
   * Setter for contacts.
   * @param suppliedContacts supplied contacts
   */
  public void setContacts(Map<Integer, Contact> suppliedContacts) {
    this.contacts = suppliedContacts;
  }

  /**
   * Getter for the last meeting id.
   * @return lastMeetingId
   */
  int getLastMeetingId() {
    return lastMeetingId;
  }

  /**
   * Setter for the last meeting id.
   * @param suppliedMeetingId the supplied meeting id
   */
  void setLastMeetingId(int suppliedMeetingId) {
    this.lastMeetingId = suppliedMeetingId;
  }

  /**
   * Getter for known meetings.
   * @return all known meetings
   */
  Map<Integer, Meeting> getMeetings() {
    return meetings;
  }

  /**
   * Setter for known meetings.
   * @param suppliedMeetings the supplied meetings
   */
  void setMeetings(Map<Integer, Meeting> suppliedMeetings) {
    this.meetings = suppliedMeetings;
  }
}
