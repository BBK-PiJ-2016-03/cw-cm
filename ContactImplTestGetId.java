import org.junit.Test;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestGetId {

    private ContactImpl contact;

    @Test
    public void getIdTest(){

    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDZero(){ contact = new ContactImpl(0, "", ""); }
}
