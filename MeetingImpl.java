import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public abstract class MeetingImpl implements Meeting, Serializable{

    private int id;
    private Calendar date;
    private Set<Contact> contacts;


    /**
     * Constructor for the meeting to set id, date and attached contacts
     * @param id the id of the meeting
     * @param date the date of the meeting
     * @param contacts contacts attached to the meeting
     */
    public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
        setId(id);
        setDate(date);
        setContacts(contacts);
    }

    /**
     * Setter for the id. Validate that the id is positive and throw an
     * IllegalArgumentException otherwise
     * @param id the id value to be set
     */
    private void setId(int id){
        Validation.validateIdPositive(id);
        this.id = id;
    }

    /**
     * Setter for the date. Validate that the date is not null and throw a
     * NullPointerException otherwise
     * @param date the new date to set
     */
    private void setDate(Calendar date){
        Validation.validateObjectNotNull(date, "Date");
        this.date = date;
    }

    /**
     * Setter for the associated contacts.
     * Validate that contacts aren't null, or throw a NullPointerException
     * Validate that the contacts collection is populated  or throw an
     * IllegalArgumentException.
     * @param contacts the set of contacts
     */
    private void setContacts(Set<Contact> contacts){
        Validation.validateObjectNotNull(contacts, "Contacts");
        Validation.validateSetPopulated(contacts, "Contacts");
        this.contacts = contacts;
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Calendar getDate() {
        return this.date;
    }

    @Override
    public Set<Contact> getContacts() {
        return this.contacts;
    }
}
