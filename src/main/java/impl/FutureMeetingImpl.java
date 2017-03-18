package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.FutureMeeting;

/**
  * @author Alexander Worton.
  */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {
  /**
   * serialVersionUID holds the version for serialization. Increment when changes
   * to the data model occur.
   */
  private static final long serialVersionUID = Long.MIN_VALUE;

  /**
   * Constructor for future meeting to set id, date and contacts.
   * @param id the id of the meeting
   * @param date the date of the meeting
   * @param contacts contacts attached to the meeting
   */
  public FutureMeetingImpl(final int id, final Calendar date, final Set<Contact> contacts) {
    super(id, date, contacts);
  }
}
