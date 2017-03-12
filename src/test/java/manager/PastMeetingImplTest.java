package manager;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import spec.Contact;
import spec.PastMeeting;

import static org.junit.Assert.assertEquals;

/**
  * @author Alexander Worton.
  */
public class PastMeetingImplTest {

  private final int ID_0;
  private final int ID_1;
  private final Calendar DATE;
  private final Calendar NULL_DATE;
  private final String EMPTY_NOTES;
  private final String NULL_NOTES;
  private final Set<Contact> EMPTY_SET;
  private final Set<Contact> POPULATED_SET;
  private final Set<Contact> NULL_CONTACTS;
  private final String CONTACT_NAME;
  private PastMeeting meeting;

  {
    ID_0 = 0;
    ID_1 = 1;
    DATE = Calendar.getInstance();
    NULL_DATE = null;
    EMPTY_NOTES = "";
    NULL_NOTES = null;
    EMPTY_SET = new HashSet<>();
    POPULATED_SET = new HashSet<>();
    NULL_CONTACTS = null;
    CONTACT_NAME = "Contact Name";
  }

  @Before
  public void before() {
    populatePopulatedSet();
  }

  private void populatePopulatedSet() {
    POPULATED_SET.add(new ContactImpl(ID_1, CONTACT_NAME));
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructorIDZero() {
    new PastMeetingImpl(ID_0, DATE, POPULATED_SET, EMPTY_NOTES);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructorIDNegative() {
    int negativeId = -200_000;
    new PastMeetingImpl(negativeId, DATE, POPULATED_SET, EMPTY_NOTES);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructorSetEmpty() {
    new PastMeetingImpl(ID_1, DATE, EMPTY_SET, EMPTY_NOTES);
  }

  @Test(expected=NullPointerException.class)
  public void constructorDateNull() {
    new PastMeetingImpl(ID_1, NULL_DATE, POPULATED_SET, EMPTY_NOTES);
  }

  @Test(expected=NullPointerException.class)
  public void constructorSetNull() {
    new PastMeetingImpl(ID_1, DATE, NULL_CONTACTS, EMPTY_NOTES);
  }

  @Test(expected=NullPointerException.class)
  public void constructorNotesNull() {
    new PastMeetingImpl(ID_1, DATE, POPULATED_SET, NULL_NOTES);
  }

  @Test
  public void getNotesTestSingle() {
    String notes = "Test";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestSpaced() {
    String notes = "Test Test";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestSpacedMultiple() {
    String notes = "Test Test Test Test Test";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestEmpty() {
    String notes = "";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestSpecialChars() {
    String notes = "*&^%£\"'@";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestNewLine() {
    String notes = "A\nB\nC";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestTab() {
    String notes = "A\tB\tC";
    meeting = new PastMeetingImpl(ID_1, DATE, POPULATED_SET, notes);
    assertEquals(notes, meeting.getNotes());
  }
}
