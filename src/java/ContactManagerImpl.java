import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
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

    private final String fileName;
    private final File file;

    {
        lastContactId = 0;
        lastMeetingId = 0;
        contacts = new HashMap<>();
        meetings = new HashMap<>();
        fileName = "contacts.txt";
        file = new File(fileName);
    }

    public ContactManagerImpl(){
        //load data from file if exists
        readDumpFromFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        validateAddNewFutureMeeting(contacts, date);
        return createNewFutureMeeting(contacts, date);
    }

    /**
     * Perform all validation on the public addFutureMeting method
     * @param contacts
     * @param date
     */
    private void validateAddNewFutureMeeting(Set<Contact> contacts, Calendar date) {
        Validation.validateObjectNotNull(contacts, "Contacts");
        Validation.validateSetPopulated(contacts, "Contacts");
        Validation.validateObjectNotNull(date, "Date");
        Validation.validateDateInFuture(date);
        Validation.validateAllContactsKnown(contacts, this.contacts); //last as computationally intensive O(n)
    }

    /**
     *
     * @param contacts
     * @param date
     * @return
     */
    private int createNewFutureMeeting(Set<Contact> contacts, Calendar date){
        int id = getNewMeetingId();
        Meeting meeting = new FutureMeetingImpl(id, date, contacts);
        this.meetings.put(id, meeting);
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
        Meeting meeting = this.meetings.get(id);
        if(meeting == null) return null;
        Validation.validateStateInPast(meeting.getDate());

        if(!meeting.getClass().equals(PastMeetingImpl.class)) //enforces the event must have occurred and had notes added
            return null;

        return (PastMeeting)meeting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Meeting meeting = this.meetings.get(id);
        if(meeting != null)
            Validation.validateStateInFuture(meeting.getDate());

        return (FutureMeeting)meeting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeeting(int id) {
        return meetings.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        Validation.validateObjectNotNull(contact, "Contact");
        Validation.validateContactKnown(contact, this.contacts); //last as computationally intensive O(n)
        return getSortedElementsFromMapAsList(this.meetings,
                (k,v) ->
                        v.getContacts().contains(contact)
                        && v.getDate().after(Calendar.getInstance()),
                Comparator.comparing(Meeting::getDate)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        Validation.validateObjectNotNull(date);
        LocalDate dateOnly = getDateOnly(date);
        return getSortedElementsFromMapAsList(this.meetings,
                (k,v) -> getDateOnly(v.getDate()).equals(dateOnly),
                Comparator.comparing(Meeting::getDate));
    }

    /**
     * Get the date component from a calendar object
     * @param date the calendar object
     * @return a LocalDate holding the date component only
     */
    private LocalDate getDateOnly(Calendar date) {
        return LocalDate.from(date.getTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        Validation.validateObjectNotNull(contact);
        Validation.validateContactKnown(contact, this.contacts); //last as computationally intensive O(n)
        return getPastMeetingsFromMapAsList(this.meetings,
                meeting -> meeting.getContacts().contains(contact)
                            && meeting.getDate().before(Calendar.getInstance()),
                Comparator.comparing(PastMeeting::getDate));
    }

    /**
     * {@inheritDoc}
     */
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
        this.meetings.put(id, meeting);
        return id;
    }

    /**
     * {@inheritDoc}
     */
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
        this.meetings.put(meetingWithNotes.getId(), meetingWithNotes); //overwrite previous meeting without notes
        return meetingWithNotes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addNewContact(String name, String notes) {
        Validation.validateStringNotNullOrEmpty(name, "name");
        Validation.validateStringNotNullOrEmpty(notes, "notes");

        int id = getNewContactId();
        this.contacts.put(id, new ContactImpl(id, name, notes));
        return id;
    }

    /**
     * pre increment to return the next contact id
     * @return next contact id
     */
    private int getNewContactId() {
        return ++this.lastContactId;
    }

    /**
     * pre increment to return the next meeting id
     * @return next meeting id
     */
    private int getNewMeetingId() {
        return ++this.lastMeetingId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(String name) {
        Validation.validateObjectNotNull(name, "Name");
        if(name.equals(""))
            return getContactsAsSet();

        return getElementsFromMapAsSet(this.contacts, (k, v) -> v.getName().equals(name));
    }

    /**
     * convert the contacts map to a set
     * @return contacts set
     */
    private Set<Contact> getContactsAsSet() {
        return contacts.values().stream()
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(int... ids) {
        Validation.validateSetPopulated(ids, "Contact Ids array");
        Set<Contact> result = getElementsFromMapAsSet(this.contacts, (k, v) -> IntStream.of(ids).anyMatch(i -> i == k));
        Validation.validateArgumentSizeMatch(ids.length, result.size());
        return result;
    }

    /**
     * A generic method to allow retrieval of filtered elements from a provided set
     * @param map the provided map
     * @param predicate the filter predicate
     * @param <T> the generic type held in both the map and as the return set
     * @return
     */
    private <T> Set<T> getElementsFromMapAsSet(Map<Integer, T> map, BiPredicate<Integer, T> predicate) {
        return map.entrySet().stream()
                .filter(e -> predicate.test(e.getKey(), e.getValue()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    /**
     * A generic method to allow retrieval of filtered and sorted elements from a provided set
     * @param map the provided map
     * @param predicate the filter bi-predicate
     * @param comparator the comparator to use for sorting
     * @param <T> the generic type held in both the map and as the return set
     * @return
     */
    private <T> List<T> getSortedElementsFromMapAsList(Map<Integer, T> map, BiPredicate<Integer, T> predicate, Comparator<T> comparator) {
        return map.entrySet().stream()
                .filter(e -> predicate.test(e.getKey(), e.getValue()))
                .map(Map.Entry::getValue)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * A Meeting specific method to allow retrieval of filtered and sorted elements from a provided Map of meetings in order to
     * allow filtering on the class type
     * @param map the provided map of Meetings
     * @param predicate the meeting filter predicate
     * @param comparator the comparator to use for sorting
     * @return
     */
    private List<PastMeeting> getPastMeetingsFromMapAsList(Map<Integer, Meeting> map, Predicate<PastMeeting> predicate, Comparator<PastMeeting> comparator) {
        return map.entrySet().stream()
                .filter(e -> PastMeetingImpl.class.equals(e.getValue().getClass()))
                .map(e -> (PastMeeting)e.getValue())
                .filter(predicate)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        ContactManagerDump dump = new ContactManagerDump();
        storeDataInDump(dump);
    }

    /**
     * Store the persistent values required in the dup pojo
     * @param dump the instance of the dump pojo to write to
     */
    private void storeDataInDump(ContactManagerDump dump) {
        dump.setLastContactId(this.lastContactId);
        dump.setLastMeetingId(this.lastMeetingId);
        dump.setContacts(this.contacts);
        dump.setMeetings(this.meetings);
        writeDumpToFile(dump);
    }

    /**
     * write the state of the object out to disc
     * @param dump the instance of the dump pojo to serialize
     */
    private void writeDumpToFile(ContactManagerDump dump) {
        createFileIfNotExists();
        handleExistingFilePermissions();

        try (FileOutputStream fileStream = new FileOutputStream(this.fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileStream))
        {
            out.writeObject(dump);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * if no file exists at the instance variable path, create it
     */
    private void createFileIfNotExists(){
        if(!file.exists()){
            createFile();
        }
    }

    /**
     * create the file at the instance variable path
     */
    private void createFile(){
        try{
            Boolean result = file.createNewFile();
            if(result){
                handleExistingFilePermissions();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * ensure read and write permissions are set for the instance file
     */
    private void handleExistingFilePermissions(){
        if(!file.canRead())
            file.setReadable(true);

        if(!file.canWrite())
            file.setWritable(true);
    }

    /**
     * attempt to read stored data in from the file
     */
    private void readDumpFromFile() {
        if(!file.exists()){
            System.out.println("Restore file does not exist.");
            return;
        }

        handleExistingFilePermissions();

        try(FileInputStream fileStream = new FileInputStream(this.fileName);
            ObjectInputStream in = new ObjectInputStream(fileStream))
        {
            ContactManagerDump restoredData = (ContactManagerDump)in.readObject();
            restoreValuesFromDump(restoredData);
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println("Unable to restore from file. Skipping restore.");
        }
    }

    /**
     * restore data from the restored dump file to the instance variables
     * @param restored the restored dump instance
     */
    private void restoreValuesFromDump(ContactManagerDump restored) {
        this.lastContactId = restored.getLastContactId();
        this.lastMeetingId = restored.getLastMeetingId();
        this.contacts = restored.getContacts();
        this.meetings = restored.getMeetings();
    }
}

/**
 * ContactManagerDump is a tightly coupled pojo used to serialize data from ContactManagerImpl.
 * Its purpose is to reduce the size of data written out to disc by dropping all non data aspects
 * of the serialized object.
 * Since the class is completely dependent on the ContactManagerImpl class, and needs to be updated
 * to keep in sync with any changes to the data structure, it is included as a nested class.
 */
class ContactManagerDump implements Serializable
{
    /**
     * serialVersionUID holds the version for the dump. Increment when changes to the data model occur.
     */
    private static final long serialVersionUID = Long.MIN_VALUE;

    private int lastContactId;
    private Map<Integer, Contact> contacts;

    private int lastMeetingId;
    private Map<Integer, Meeting> meetings;

    public int getLastContactId() {
        return lastContactId;
    }

    public void setLastContactId(int lastContactId) {
        this.lastContactId = lastContactId;
    }

    public Map<Integer, Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Map<Integer, Contact> contacts) {
        this.contacts = contacts;
    }

    public int getLastMeetingId() {
        return lastMeetingId;
    }

    public void setLastMeetingId(int lastMeetingId) {
        this.lastMeetingId = lastMeetingId;
    }

    public Map<Integer, Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Map<Integer, Meeting> meetings) {
        this.meetings = meetings;
    }
}
