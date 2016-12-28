import java.util.Calendar;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public abstract class MeetingImpl implements Meeting{

    private int id;
    private Calendar date;
    private Set<Contact> contacts;


    public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
        setId(id);
        setDate(date);
        setContacts(contacts);
    }

    private void setId(int id){
        Validation.validateIdPositive(id);
        this.id = id;
    }

    private void setDate(Calendar date){
        Validation.validateObjectNotNull(date, "Date");
        this.date = date;
    }

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
        return null;
    }
}
