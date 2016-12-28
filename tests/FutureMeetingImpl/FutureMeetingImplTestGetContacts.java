import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class FutureMeetingImplTestGetContacts {

    private FutureMeeting meeting;
    private Calendar date;

    private Set<Contact> emptySet;
    private Set<Contact> populatedSet;

    {
        date = Calendar.getInstance();
        emptySet = new HashSet<>();
        populatedSet = new HashSet<>();
    }

    @Test
    public void getContactsTestSingle(){
        ContactImpl contact = new ContactImpl(1, "Name Of");
        populatedSet.add(contact);

        int id = 1;
        meeting = new FutureMeetingImpl(id, date, populatedSet);

        assertEquals(1, meeting.getContacts().size());
        assertTrue(meeting.getContacts().contains(contact));
    }

    @Test
    public void getContactsTestMultiple(){

        Set<Contact> verifySet = IntStream.range(1,8)
                .mapToObj(i -> new ContactImpl(i, "Name"))
                .peek(populatedSet::add)
                .collect(Collectors.toSet());

        int id = 1;
        meeting = new FutureMeetingImpl(id, date, populatedSet);

        assertEquals(8, meeting.getContacts().size());
        assertTrue(meeting.getContacts().containsAll(verifySet));
    }
}
