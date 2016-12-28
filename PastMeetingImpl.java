import java.util.Calendar;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
        super(id, date, contacts);
    }

    @Override
    public String getNotes() {
        return null;
    }
}
