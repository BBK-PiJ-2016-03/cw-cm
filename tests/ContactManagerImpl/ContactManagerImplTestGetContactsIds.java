import org.junit.Test;

/**
 * Created by Alexander Worton on 29/12/2016.
 */
public class ContactManagerImplTestGetContactsIds {

    private ContactManagerImplTestData data;

    {
        data = new ContactManagerImplTestData();
    }

    /**
     * Returns a set containing the contacts that correspond to the IDs.
     * Note that this method can be used to retrieve just one contact by passing only one ID.
     *
     * @param ids an arbitrary number of contact IDs
     * @return a set containing the contacts that correspond to the IDs.
     * @throws  if no IDs are provided or if
     *     any of the provided IDs does not correspond to a real contact
     */

    @Test
    public void testGetExistingContact(){

    }

    @Test
    public void testGetExistingContacts(){

    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetEmptySet(){

    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetUnknownContact(){

    }
}
