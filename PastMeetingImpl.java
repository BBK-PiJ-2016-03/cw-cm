import java.util.Date;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    public PastMeetingImpl(int id, Date date, Set<Contact> contacts) {
        super(id, date, contacts);
    }

    @Override
    public String getNotes() {
        return null;
    }
}
