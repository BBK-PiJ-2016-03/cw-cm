package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.PastMeeting;

/**
  * @author Alexander Worton.
  */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {
  /**
   * serialVersionUID holds the version for serialization. Increment when changes
   * to the data model occur.
   */
  private static final long serialVersionUID = Long.MIN_VALUE;

  private String notes;

  /**
   * Constructor for the past meeting.
   * @param id the id of the meeting
   * @param date the date the meeting took place
   * @param contacts the associated contacts
   * @param notes attached notes
   */
  public PastMeetingImpl(final int id,
                         final Calendar date,
                         final Set<Contact> contacts,
                         final String notes) {
    super(id, date, contacts);
    setNotes(notes);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getNotes() {
    return this.notes;
  }

  /**
   * Setter for the notes.
   * Validate that the notes aren't null, else throw a NullPointerException
   * @param notes the new notes to replace the existing notes
   */
  private void setNotes(final String notes) {
    Validation.validateObjectNotNull(notes, "Notes");
    this.notes = notes;
  }
}
