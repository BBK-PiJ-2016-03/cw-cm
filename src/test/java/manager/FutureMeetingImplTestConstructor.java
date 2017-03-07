package manager;

import spec.Contact;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class FutureMeetingImplTestConstructor {
    private final Calendar date;

    private final Set<Contact> emptySet;
    private final Set<Contact> populatedSet;

    {
        date = Calendar.getInstance();
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
    public void constructorIDZero(){ new FutureMeetingImpl(0, date, populatedSet); }

    @Test(expected=IllegalArgumentException.class)
    public void constructorIDNegative(){ new FutureMeetingImpl(-1000, date, populatedSet); }

    @Test(expected=IllegalArgumentException.class)
    public void constructorSetEmpty(){ new FutureMeetingImpl(1, date, emptySet); }

    @Test(expected=NullPointerException.class)
    public void constructorDateNull(){ new FutureMeetingImpl(1, null, populatedSet); }

    @Test(expected=NullPointerException.class)
    public void constructorSetNull(){ new FutureMeetingImpl(1, date, null); }
}
