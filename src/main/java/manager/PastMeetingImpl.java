package manager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.PastMeeting;

/**
  * @author Alexander Worton.
  */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {

  private String notes;

  /**
   * Constructor for the past meeting.
   * @param id the id of the meeting
   * @param date the date the meeting took place
   * @param contacts the associated contacts
   * @param notes attached notes
   */
  public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
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
  private void setNotes(String notes) {
    Validation.validateObjectNotNull(notes, "Notes");
    this.notes = notes;
  }
}
