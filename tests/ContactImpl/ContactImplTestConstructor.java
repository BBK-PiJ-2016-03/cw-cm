import org.junit.Test;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestConstructor{

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDZero(){ new ContactImpl(0, "", ""); }

    @Test(expected=IllegalArgumentException.class)
    public void constructor2IDZero(){
        new ContactImpl(0, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDNegative(){
        new ContactImpl(-10000, "", "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor2IDNegative(){
        new ContactImpl(-10000, "");
    }

    @Test(expected=NullPointerException.class)
    public void constructor3NameNull(){
        new ContactImpl(1, null, "");
    }

    @Test(expected=NullPointerException.class)
    public void constructor2NameNull(){
        new ContactImpl(1, null);
    }

    @Test(expected=NullPointerException.class)
    public void constructor3NotesNull(){
        new ContactImpl(1, "", null);
    }
}