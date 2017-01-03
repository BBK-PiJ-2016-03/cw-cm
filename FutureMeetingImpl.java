import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

    public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        super(id, date, contacts);
    }
}
