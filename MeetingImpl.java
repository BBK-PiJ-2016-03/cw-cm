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
    }

    private void setId(int id){
        this.id = id;
    }

    private void setDate(Date date){
        this.date = date;
    }

    private void setContacts(Set<Contact> contacts){
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
