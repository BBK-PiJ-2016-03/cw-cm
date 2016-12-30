import org.junit.Test;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsName {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Returns a set with the contacts whose name contains that string.
     *
     * If the string is the empty string, this methods returns the set
     * that contains all current contacts.
     *
     * @param name the string to search for
     * @return a set with the contacts whose name contains that string.
     * @throws NullPointerException if the parameter is null
     */

    @Test
    public void testGetExistingContact(){

    }

    @Test
    public void testGetExistingContacts(){

    }

    @Test(expected=NullPointerException.class)
    public void testGetContactNullName(){

    }
}
