import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.util.HashSet;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class PastMeetingImplTestConstructor {

    private PastMeeting meeting;
    private Date date = new Date();

    private Set<Contact> emptySet = new HashSet<>();
    private Set<Contact> populatedSet = new HashSet<>();

    @Before
    public void before(){
        populatePopulatedSet();
    }

    private void populatePopulatedSet(){
        populatedSet.add(new ContactImpl(1, "Name Of"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorIDZero(){ meeting = new PastMeetingImpl(0, date, populatedSet); }

    @Test(expected=IllegalArgumentException.class)
    public void constructorIDNegative(){ meeting = new PastMeetingImpl(-1000, date, populatedSet); }

    @Test(expected=IllegalArgumentException.class)
    public void constructorSetEmpty(){ meeting = new PastMeetingImpl(1, date, emptySet); }

    @Test(expected=NullPointerException.class)
    public void constructorDateNull(){ meeting = new PastMeetingImpl(1, null, populatedSet); }

    @Test(expected=NullPointerException.class)
    public void constructorSetNull(){ meeting = new PastMeetingImpl(1, date, null); }


}
