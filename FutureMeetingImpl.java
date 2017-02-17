import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

    /**
     * Constructor for future meeting to set id, date and contacts
     * @param id the id of the meeting
     * @param date the date of the meeting
     * @param contacts contacts attached to the meeting
     */
    public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        super(id, date, contacts);
    }
}
