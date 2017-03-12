package manager;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * @author Alexander Worton.
 */
public class ContactImplTest {

  private ContactImpl contact;
  private final int LARGE_NEGATIVE_ID;
  private final String EMPTY_CONTACT_NAME;
  private final String NULL_CONTACT_NAME;
  private final String EMPTY_NOTES;
  private final String NULL_NOTES;
  private final int ID_0;
  private final int ID_1;
  private final String NAME;

  {
    LARGE_NEGATIVE_ID = -10000;
    EMPTY_CONTACT_NAME = "";
    NULL_CONTACT_NAME = null;
    EMPTY_NOTES = "";
    NULL_NOTES = null;
    ID_0 = 0;
    ID_1 = 1;
    NAME = "Test";
  }

  @Test
  public void addNotesTestNoExisting(){
    String note = "This is a note!";
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    contact.addNotes(note);
    assertEquals(note, contact.getNotes());
  }

  @Test
  public void addNotesTestExisting(){
    String note = "This is a note!";
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME, EMPTY_NOTES);
    contact.addNotes(note);
    assertEquals(note, contact.getNotes());
  }

  @Test
  public void addNotesTestEmptyNoExisting(){
    String note = "";
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    contact.addNotes(note);
    assertEquals(note, contact.getNotes());
  }

  @Test
  public void addNotesTestEmptyExisting(){
    String note = "";
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME, EMPTY_NOTES);
    contact.addNotes(note);
    assertEquals(note, contact.getNotes());
  }

  @Test(expected = NullPointerException.class)
  public void addNotesTestNullNoExisting(){
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    contact.addNotes(NULL_NOTES);
    assertEquals(NULL_NOTES, contact.getNotes());
  }

  @Test(expected = NullPointerException.class)
  public void addNotesTestNullExisting(){
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME, EMPTY_NOTES);
    contact.addNotes(NULL_NOTES);
    assertEquals(NULL_NOTES, contact.getNotes());
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructor3IDZero(){
    new ContactImpl(ID_0, EMPTY_CONTACT_NAME, EMPTY_NOTES);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructor2IDZero(){
    new ContactImpl(ID_0, EMPTY_CONTACT_NAME);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructor3IDNegative(){
    new ContactImpl(LARGE_NEGATIVE_ID, EMPTY_CONTACT_NAME, EMPTY_NOTES);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructor2IDNegative(){
    new ContactImpl(LARGE_NEGATIVE_ID, EMPTY_CONTACT_NAME);
  }

  @Test(expected=NullPointerException.class)
  public void constructor3NameNull(){
    new ContactImpl(ID_1, NULL_CONTACT_NAME, EMPTY_NOTES);
  }

  @Test(expected=NullPointerException.class)
  public void constructor2NameNull(){
    new ContactImpl(ID_1, NULL_CONTACT_NAME);
  }

  @Test(expected=NullPointerException.class)
  public void constructor3NotesNull(){
    new ContactImpl(ID_1, EMPTY_CONTACT_NAME, NULL_NOTES);
  }

  @Test
  public void getIdTestMin(){
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    assertEquals(ID_1, contact.getId());
  }

  @Test
  public void getIdTestMax(){
    int id = Integer.MAX_VALUE;
    contact = new ContactImpl(id, EMPTY_CONTACT_NAME);
    assertEquals(id, contact.getId());
  }

  @Test
  public void getNameTestSingle(){
    String name = "Test";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestSpaced(){
    String name = "Test Test";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestSpacedMultiple(){
    String name = "Test Test Test Test Test";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestEmpty(){
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    assertEquals(EMPTY_CONTACT_NAME, contact.getName());
  }

  @Test
  public void getNameTestSpecialChars(){
    String name = "*&^%£\"'@";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestNewLine(){
    String name = "A\nB\nC";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestTab(){
    String name = "A\tB\tC";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNotesTestSingle(){
    String notes = "getNotesTestSingleNotes";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestSpaced(){
    String notes = "Test Test";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestSpacedMultiple(){
    String notes = "Test Test Test Test Test";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestEmpty(){
    String notes = "";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestSpecialChars(){
    String notes = "*&^%£\"'@";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestNewLine(){
    String notes = "A\nB\nC";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestTab(){
    String notes = "A\tB\tC";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }
}
