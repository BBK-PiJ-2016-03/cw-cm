import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImpl implements ContactManager{

    private int lastContactId;
    private Map<Integer, Contact> contacts;

    private int lastMeetingId;
    private Map<Integer, Meeting> meetings;

    {
        lastContactId = 0;
        lastMeetingId = 0;
        contacts = new HashMap<>();
        meetings = new HashMap<>();
    }

    public ContactManagerImpl(){
        //load data from file if exists
    }


    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        validateAddNewFutureMeeting(contacts, date);
        return createNewFutureMeeting(contacts, date);
    }

    private void validateAddNewFutureMeeting(Set<Contact> contacts, Calendar date) {
        Validation.validateObjectNotNull(contacts, "Contacts");
        Validation.validateSetPopulated(contacts, "Contacts");
        Validation.validateObjectNotNull(date, "Date");
        Validation.validateDateInFuture(date);
        Validation.validateAllContactsKnown(contacts, this.contacts); //last as computationally intensive O(n)
    }

    private int createNewFutureMeeting(Set<Contact> contacts, Calendar date){
        int id = getNewMeetingId();
        Meeting meeting = new FutureMeetingImpl(id, date, contacts);
        meetings.put(id, meeting);
        return id;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Meeting meeting = meetings.get(id);
        if(meeting != null)
            Validation.validateStateInFuture(meeting.getDate());

        return (FutureMeeting)meeting;
    }

    @Override
    public Meeting getMeeting(int id) {
        return meetings.get(id);
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        Validation.validateObjectNotNull(contact, "Contact");
        Validation.validateContactKnown(contact, this.contacts); //last as computationally intensive O(n)
        return getContactsFutureMeetingsAsList(contact);
    }

    private List<Meeting> getContactsFutureMeetingsAsList(Contact contact) {
        return meetings.values().stream()
                .filter(e -> e.getDate().after(Calendar.getInstance()))
                .filter(e -> e.getContacts().contains(contact))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        return null;
    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return null;
    }

    @Override
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        validateAddNewPastMeeting(contacts, date, text);
        return createNewPastMeeting(contacts, date, text);
    }

    private void validateAddNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
        Validation.validateObjectNotNull(contacts, "Contacts");
        Validation.validateObjectNotNull(date, "Date");
        Validation.validateDateInPast(date);
        Validation.validateObjectNotNull(text, "Text");
        Validation.validateAllContactsKnown(contacts, this.contacts); //last as computationally intensive O(n)
    }

    private int createNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        int id = getNewMeetingId();
        Meeting meeting = new PastMeetingImpl(id, date, contacts, text);
        meetings.put(id, meeting);
        return id;
    }


    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
        Validation.validateObjectNotNull(text, "Text");
        Meeting meeting = meetings.get(id);
        Validation.validateArgumentNotNull(meeting, "Meeting");
        Validation.validateStateInPast(meeting.getDate());
        return addNotesToPastMeeting(meeting, text);
    }

    private PastMeeting addNotesToPastMeeting(Meeting meeting, String text) {
        PastMeeting meetingWithNotes = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), text);
        meetings.put(meetingWithNotes.getId(), meetingWithNotes); //overwrite previous meeting without notes
        return meetingWithNotes;
    }

    @Override
    public int addNewContact(String name, String notes) {
        Validation.validateStringNotNullOrEmpty(name, "name");
        Validation.validateStringNotNullOrEmpty(notes, "notes");

        int id = getNewContactId();
        contacts.put(id, new ContactImpl(id, name, notes));
        return id;
    }

    private int getNewContactId() {
        return ++this.lastContactId;
    }

    private int getNewMeetingId() {
        return ++this.lastMeetingId;
    }

    @Override
    public Set<Contact> getContacts(String name) {
        Validation.validateObjectNotNull(name, "Name");
        if(name.equals(""))
            return getContactsAsSet();

        return getContactsFromName(name);
    }

    private Set<Contact> getContactsAsSet() {
        return contacts.values().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Contact> getContacts(int... ids) {
        Validation.validateSetPopulated(ids, "Contact Ids array");
        Set<Contact> result = getContactsFromIds(ids);
        Validation.validateArgumentSizeMatch(ids.length, result.size());
        return result;
    }

    private Set<Contact> getContactsFromIds(int[] ids) {
        return contacts.entrySet().stream()
                .filter(e -> IntStream.of(ids).anyMatch(i -> i == e.getKey()))
                .map(e -> e.getValue())
                .collect(Collectors.toSet());
    }

    private Set<Contact> getContactsFromName(String name) {
        return contacts.entrySet().stream()
                .filter(e -> e.getValue().getName().equals(name))
                .map(e -> e.getValue())
                .collect(Collectors.toSet());
    }

    @Override
    public void flush() {

    }
}
