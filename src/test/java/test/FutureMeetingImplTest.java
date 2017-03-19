package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import impl.ContactImpl;
import impl.DateFns;
import impl.FutureMeetingImpl;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import spec.Contact;
import spec.FutureMeeting;


/**
  * @author Alexander Worton.
  */
public class FutureMeetingImplTest {

  private static final int ID_0 = 0;
  private static final int ID_1 = 1;
  private static final Calendar DATE = Calendar.getInstance();
  private static final Set<Contact> EMPTY_SET = new HashSet<>();
  private final transient Set<Contact> populatedSet;
  private transient FutureMeeting futureMeeting;
  private static final Calendar PAST_DATE = DateFns.getPastDate();
  private static final Calendar FUTURE_DATE = DateFns.getFutureDate();
  private static final String CONTACT_NAME = "Name Of";
  private static final Calendar NULL_DATE = null;
  private static final Set<Contact> NULL_CONTACTS = null;

  {
    populatedSet = new HashSet<>();
  }

  /**
   * Setup environment prior to each test.
   */
  @Before
  public void before() {
    populatePopulatedSet();
  }

  private void populatePopulatedSet() {
    populatedSet.add(new ContactImpl(ID_1, CONTACT_NAME));
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorIdZero() {
    new FutureMeetingImpl(ID_0, DATE, populatedSet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorIdNegative() {
    final int negativeValue = -1000;
    new FutureMeetingImpl(negativeValue, DATE, populatedSet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorSetEmpty() {
    new FutureMeetingImpl(ID_1, DATE, EMPTY_SET);
  }

  @Test(expected = NullPointerException.class)
  public void constructorDateNull() {
    new FutureMeetingImpl(ID_1, NULL_DATE, populatedSet);
  }

  @Test(expected = NullPointerException.class)
  public void constructorSetNull() {
    new FutureMeetingImpl(ID_1, DATE, NULL_CONTACTS);
  }

  @Test
  public void getContactsTestSingle() {
    final ContactImpl contact = new ContactImpl(ID_1, CONTACT_NAME);
    final Set<Contact> verifySet = new HashSet<>();
    verifySet.add(contact);

    final FutureMeeting meeting = new FutureMeetingImpl(ID_1, DATE, verifySet);

    assertEquals(ID_1, meeting.getContacts().size());
    assertTrue(meeting.getContacts().contains(contact));
  }

  @Test
  public void getContactsTestMultiple() {
    final int numContacts = 8;
    final Set<Contact> verifySet = generateContacts(numContacts);

    final FutureMeeting meeting = new FutureMeetingImpl(ID_1, DATE, verifySet);

    assertEquals(numContacts, meeting.getContacts().size());
    assertTrue(meeting.getContacts().containsAll(verifySet));
  }

  private Set<Contact> generateContacts(final int number) {
    final int start = 1;
    return IntStream.range(start, number + 1)
        .mapToObj(i -> new ContactImpl(i, CONTACT_NAME))
        //side effect populating the class scoped set with the same elements
        .peek(populatedSet::add)
        .collect(Collectors.toSet());
  }

  @Test
  public void getDateTestPast() {
    futureMeeting = new FutureMeetingImpl(ID_1, PAST_DATE, populatedSet);
    assertEquals(PAST_DATE, futureMeeting.getDate());
  }

  @Test
  public void getDateTestFuture() {
    futureMeeting = new FutureMeetingImpl(ID_1, FUTURE_DATE, populatedSet);
    assertEquals(FUTURE_DATE, futureMeeting.getDate());
  }

  @Test
  public void getIdTestMin() {
    futureMeeting = new FutureMeetingImpl(ID_1, DATE, populatedSet);
    assertEquals(ID_1, futureMeeting.getId());
  }

  @Test
  public void getIdTestMax() {
    final int id = Integer.MAX_VALUE;
    futureMeeting = new FutureMeetingImpl(id, DATE, populatedSet);
    assertEquals(id, futureMeeting.getId());
  }
}
