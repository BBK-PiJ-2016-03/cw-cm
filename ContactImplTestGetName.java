import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestGetName {

    private ContactImpl contact;

    @Test
    public void getNameTestSingle(){
        String name = "Test";
        contact = new ContactImpl(0, name);
        assertEquals(name, contact.getName());
    }

    @Test
    public void getNameTestSpaced(){
        String name = "Test Test";
        contact = new ContactImpl(0, name);
        assertEquals(name, contact.getName());
    }

    @Test
    public void getNameTestSpacedMultiple(){
        String name = "Test Test Test Test Test";
        contact = new ContactImpl(0, name);
        assertEquals(name, contact.getName());
    }

    @Test
    public void getNameTestEmpty(){
        String name = "";
        contact = new ContactImpl(0, name);
        assertEquals(name, contact.getName());
    }

    @Test
    public void getNameTestSpecialChars(){
        String name = "*&^%£\"'@";
        contact = new ContactImpl(0, name);
        assertEquals(name, contact.getName());
    }
}
