import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {

    private String notes;

    public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
        super(id, date, contacts);
        setNotes(notes);
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    private void setNotes(String notes) {
        Validation.validateObjectNotNull(notes, "Notes");
        this.notes = notes;
    }
}
