package test;

import static org.junit.Assert.assertEquals;

import impl.ContactImpl;
import impl.PastMeetingImpl;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import spec.Contact;
import spec.PastMeeting;

/**
  * @author Alexander Worton.
  */
public class PastMeetingImplTest {

  private static final int ID_0 = 0;
  private static final int ID_1 = 1;
  private static final Calendar DATE = Calendar.getInstance();
  private static final Calendar NULL_DATE = null;
  private static final String EMPTY_NOTES = "";
  private static final String NULL_NOTES = null;
  private static final Set<Contact> EMPTY_SET = new HashSet<>();
  private final transient Set<Contact> populatedSet = new HashSet<>();
  private static final Set<Contact> NULL_CONTACTS = null;
  private static final String CONTACT_NAME = "Contact Name";
  private transient PastMeeting meeting;

  @Before
  public void before() {
    populatePopulatedSet();
  }

  private void populatePopulatedSet() {
    populatedSet.add(new ContactImpl(ID_1, CONTACT_NAME));
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorIdZero() {
    new PastMeetingImpl(ID_0, DATE, populatedSet, EMPTY_NOTES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorIdNegative() {
    final int negativeId = -200_000;
    new PastMeetingImpl(negativeId, DATE, populatedSet, EMPTY_NOTES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorSetEmpty() {
    new PastMeetingImpl(ID_1, DATE, EMPTY_SET, EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void constructorDateNull() {
    new PastMeetingImpl(ID_1, NULL_DATE, populatedSet, EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void constructorSetNull() {
    new PastMeetingImpl(ID_1, DATE, NULL_CONTACTS, EMPTY_NOTES);
  }

  @Test(expected = NullPointerException.class)
  public void constructorNotesNull() {
    new PastMeetingImpl(ID_1, DATE, populatedSet, NULL_NOTES);
  }

  @Test
  public void getNotesTestSingle() {
    final String notes = "Test";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestSpaced() {
    final String notes = "Test Test";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestSpacedMultiple() {
    final String notes = "Test Test Test Test Test";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestEmpty() {
    final String notes = "";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestSpecialChars() {
    final String notes = "*&^%£\"'@";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestNewLine() {
    final String notes = "A\nB\nC";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }

  @Test
  public void getNameTestTab() {
    final String notes = "A\tB\tC";
    meeting = new PastMeetingImpl(ID_1, DATE, populatedSet, notes);
    assertEquals(notes, meeting.getNotes());
  }
}
