import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public abstract class MeetingImpl implements Meeting{

    private int id;
    private Date date;
    private Set<Contact> contacts;


    public MeetingImpl(int id, Date date, Set<Contact> contacts){
        setId(id);
        setDate(date);
        setContacts(contacts);
        if(contacts == null)
            System.out.println("contacts are null");
    }

    private void setId(int id){
        Validation.validateIdPositive(id);
        this.id = id;
    }

    private void setDate(Date date){
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
        return 0;
    }

    @Override
    public Calendar getDate() {
        return null;
    }

    @Override
    public Set<Contact> getContacts() {
        return null;
    }
}
