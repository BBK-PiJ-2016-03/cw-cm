import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class FutureMeetingImplTestConstructor {

    private FutureMeeting meeting;
    private Date date;

    private Set<Contact> emptySet;
    private Set<Contact> populatedSet;

    {
        date = new Date();
        emptySet = new HashSet<>();
        populatedSet = new HashSet<>();
    }

    @Before
    public void before(){
        populatePopulatedSet();
    }

    private void populatePopulatedSet(){
        populatedSet.add(new ContactImpl(1, "Name Of"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorIDZero(){ meeting = new FutureMeetingImpl(0, date, populatedSet); }

    @Test(expected=IllegalArgumentException.class)
    public void constructorIDNegative(){ meeting = new FutureMeetingImpl(-1000, date, populatedSet); }

    @Test(expected=IllegalArgumentException.class)
    public void constructorSetEmpty(){ meeting = new FutureMeetingImpl(1, date, emptySet); }

    @Test(expected=NullPointerException.class)
    public void constructorDateNull(){ meeting = new FutureMeetingImpl(1, null, populatedSet); }

    @Test(expected=NullPointerException.class)
    public void constructorSetNull(){ meeting = new FutureMeetingImpl(1, date, null); }


}
