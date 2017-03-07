package manager;

import org.junit.Test;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestConstructor{

    private final int LARGE_NEGATIVE_ID = -10000;
    private final String EMPTY = "";

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDZero(){ new ContactImpl(0, EMPTY, EMPTY); }

    @Test(expected=IllegalArgumentException.class)
    public void constructor2IDZero(){
        new ContactImpl(0, EMPTY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDNegative(){
        new ContactImpl(LARGE_NEGATIVE_ID, EMPTY, EMPTY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor2IDNegative(){
        new ContactImpl(LARGE_NEGATIVE_ID, EMPTY);
    }

    @Test(expected=NullPointerException.class)
    public void constructor3NameNull(){
        new ContactImpl(1, null, EMPTY);
    }

    @Test(expected=NullPointerException.class)
    public void constructor2NameNull(){
        new ContactImpl(1, null);
    }

    @Test(expected=NullPointerException.class)
    public void constructor3NotesNull(){
        new ContactImpl(1, EMPTY, null);
    }
}