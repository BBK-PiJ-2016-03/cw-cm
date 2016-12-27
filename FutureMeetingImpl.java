import java.util.Date;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting{

    public FutureMeetingImpl(int id, Date date, Set<Contact> contacts) {
        super(id, date, contacts);
    }
}
