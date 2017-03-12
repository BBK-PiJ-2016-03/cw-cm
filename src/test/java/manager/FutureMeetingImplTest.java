package manager;

import spec.Contact;
import org.junit.Before;
import org.junit.Test;
import spec.FutureMeeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
  * @author Alexander Worton.
  */
public class FutureMeetingImplTest {

  private final int ID_0;
  private final int ID_1;
  private final Calendar DATE;
  private final Set<Contact> EMPTY_SET;
  private final Set<Contact> POPULATED_SET;
  private FutureMeeting FUTURE_MEETING;
  private final Calendar PAST_DATE;
  private final Calendar FUTURE_DATE;
  private final String CONTACT_NAME;
  private final Calendar NULL_DATE;
  private final Set<Contact> NULL_CONTACTS;

  {
    ID_0 = 0;
    ID_1 = 1;
    DATE = Calendar.getInstance();
    EMPTY_SET = new HashSet<>();
    POPULATED_SET = new HashSet<>();
    PAST_DATE = DateFns.getPastDate();
    FUTURE_DATE = DateFns.getFutureDate();
    CONTACT_NAME = "Name Of";
    NULL_DATE = null;
    NULL_CONTACTS = null;
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
    new FutureMeetingImpl(ID_0, DATE, POPULATED_SET);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructorIDNegative() {
    int negativeValue = -1000;
    new FutureMeetingImpl(negativeValue, DATE, POPULATED_SET);
  }

  @Test(expected=IllegalArgumentException.class)
  public void constructorSetEmpty() {
    new FutureMeetingImpl(ID_1, DATE, EMPTY_SET);
  }

  @Test(expected=NullPointerException.class)
  public void constructorDateNull() {
    new FutureMeetingImpl(ID_1, NULL_DATE, POPULATED_SET);
  }

  @Test(expected=NullPointerException.class)
  public void constructorSetNull() {
    new FutureMeetingImpl(ID_1, DATE, NULL_CONTACTS);
  }

  @Test
  public void getContactsTestSingle() {
    ContactImpl contact = new ContactImpl(ID_1, CONTACT_NAME);
    Set<Contact> verifySet = new HashSet<>();
    verifySet.add(contact);

    FutureMeeting meeting = new FutureMeetingImpl(ID_1, DATE, verifySet);

    assertEquals(ID_1, meeting.getContacts().size());
    assertTrue(meeting.getContacts().contains(contact));
  }

  @Test
  public void getContactsTestMultiple() {
    int numContacts = 8;
    Set<Contact> verifySet = generateContacts(numContacts);

    FutureMeeting meeting = new FutureMeetingImpl(ID_1, DATE, verifySet);

    assertEquals(numContacts, meeting.getContacts().size());
    assertTrue(meeting.getContacts().containsAll(verifySet));
  }

  private Set<Contact> generateContacts(int number) {
    int start = 1;
    return IntStream.range(start, number + 1)
        .mapToObj(i -> new ContactImpl(i, CONTACT_NAME))
        //side effect populating the class scoped set with the same elements
        .peek(POPULATED_SET::add)
        .collect(Collectors.toSet());
  }

  @Test
  public void getDateTestPast() {
    FUTURE_MEETING = new FutureMeetingImpl(ID_1, PAST_DATE, POPULATED_SET);
    assertEquals(PAST_DATE, FUTURE_MEETING.getDate());
  }

  @Test
  public void getDateTestFuture() {
    FUTURE_MEETING = new FutureMeetingImpl(ID_1, FUTURE_DATE, POPULATED_SET);
    assertEquals(FUTURE_DATE, FUTURE_MEETING.getDate());
  }

  @Test
  public void getIdTestMin() {
    FUTURE_MEETING = new FutureMeetingImpl(ID_1, DATE, POPULATED_SET);
    assertEquals(ID_1, FUTURE_MEETING.getId());
  }

  @Test
  public void getIdTestMax() {
    int id = Integer.MAX_VALUE;
    FUTURE_MEETING = new FutureMeetingImpl(id, DATE, POPULATED_SET);
    assertEquals(id, FUTURE_MEETING.getId());
  }
}
