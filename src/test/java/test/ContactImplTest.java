package test;

import static org.junit.Assert.assertEquals;

import impl.ContactImpl;
import org.junit.Test;

/**
 * @author Alexander Worton.
 */
public class ContactImplTest {

  private transient ContactImpl contact;
  private static final int LARGE_NEGATIVE_ID = -10000;
  private static final String EMPTY_CONTACT_NAME = "";
  private static final String NULL_CONTACT_NAME = null;
  private static final String EMPTY_NOTE = "";
  private static final String NULL_NOTES = null;
  private static final int ID_0 = 0;
  private static final int ID_1 = 1;
  private static final String NAME = "Test";
  private static final ContactManagerImplTestData DATA = new ContactManagerImplTestData();
  private static final String NOTE = "This is a note!";

  @Test
  public void addNotesTestNoExisting() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    contact.addNotes(NOTE);
    assertEquals(NOTE, contact.getNotes());
  }

  @Test
  public void addNotesTestExisting() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME, EMPTY_NOTE);
    contact.addNotes(NOTE);
    assertEquals(NOTE, contact.getNotes());
  }

  @Test
  public void addNotesTestEmptyNoExisting() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    contact.addNotes(EMPTY_NOTE);
    assertEquals(EMPTY_NOTE, contact.getNotes());
  }

  @Test
  public void addNotesTestEmptyExisting() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME, EMPTY_NOTE);
    contact.addNotes(EMPTY_NOTE);
    assertEquals(EMPTY_NOTE, contact.getNotes());
  }

  @Test(expected = NullPointerException.class)
  public void addNotesTestNullNoExisting() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    contact.addNotes(NULL_NOTES);
    assertEquals(NULL_NOTES, contact.getNotes());
  }

  @Test(expected = NullPointerException.class)
  public void addNotesTestNullExisting() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME, EMPTY_NOTE);
    contact.addNotes(NULL_NOTES);
    assertEquals(NULL_NOTES, contact.getNotes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor3IdZero() {
    new ContactImpl(ID_0, EMPTY_CONTACT_NAME, EMPTY_NOTE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor2IdZero() {
    new ContactImpl(ID_0, EMPTY_CONTACT_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor3IdNegative() {
    new ContactImpl(LARGE_NEGATIVE_ID, EMPTY_CONTACT_NAME, EMPTY_NOTE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor2IdNegative() {
    new ContactImpl(LARGE_NEGATIVE_ID, EMPTY_CONTACT_NAME);
  }

  @Test(expected = NullPointerException.class)
  public void constructor3NameNull() {
    new ContactImpl(ID_1, NULL_CONTACT_NAME, EMPTY_NOTE);
  }

  @Test(expected = NullPointerException.class)
  public void constructor2NameNull() {
    new ContactImpl(ID_1, NULL_CONTACT_NAME);
  }

  @Test(expected = NullPointerException.class)
  public void constructor3NotesNull() {
    new ContactImpl(ID_1, EMPTY_CONTACT_NAME, NULL_NOTES);
  }

  @Test
  public void getIdTestMin() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    assertEquals(ID_1, contact.getId());
  }

  @Test
  public void getIdTestMax() {
    final int id = Integer.MAX_VALUE;
    contact = new ContactImpl(id, EMPTY_CONTACT_NAME);
    assertEquals(id, contact.getId());
  }

  @Test
  public void getNameTestSingle() {
    contact = new ContactImpl(ID_1, NAME);
    assertEquals(NAME, contact.getName());
  }

  @Test
  public void getNameTestSpaced() {
    final String name = "Test Test";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestSpacedMultiple() {
    final String name = "Test Test Test Test Test";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestEmpty() {
    contact = new ContactImpl(ID_1, EMPTY_CONTACT_NAME);
    assertEquals(EMPTY_CONTACT_NAME, contact.getName());
  }

  @Test
  public void getNameTestSpecialChars() {
    final String name = "*&^%£\"'@";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestNewLine() {
    final String name = "A\nB\nC";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNameTestTab() {
    final String name = "A\tB\tC";
    contact = new ContactImpl(ID_1, name);
    assertEquals(name, contact.getName());
  }

  @Test
  public void getNotesTestSingle() {
    final String notes = "getNotesTestSingleNotes";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestSpaced() {
    final String notes = "Test Test";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestSpacedMultiple() {
    final String notes = "Test Test Test Test Test";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestEmpty() {
    contact = new ContactImpl(ID_1, NAME, EMPTY_NOTE);
    assertEquals(EMPTY_NOTE, contact.getNotes());
  }

  @Test
  public void getNotesTestSpecialChars() {
    final String notes = "*&^%£\"'@";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestNewLine() {
    final String notes = "A\nB\nC";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }

  @Test
  public void getNotesTestTab() {
    final String notes = "A\tB\tC";
    contact = new ContactImpl(ID_1, NAME, notes);
    assertEquals(notes, contact.getNotes());
  }
}
