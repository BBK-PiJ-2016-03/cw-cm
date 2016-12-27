/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImpl implements Contact{

    private int id;
    private String name;
    private String notes;

    public ContactImpl(int id, String name){
        setId(id);
        setName(name);
    }

    public ContactImpl(int id, String name, String notes){
        this(id, name);
        setNotes(notes);
    }

    private void setId(int id){
        this.id = id;
    }

    private void setName(String name){
        this.name = name;
    }

    @Override
    public int getId() {
        return 0;
    }

    public String getName(){
        return "";
    }

    private void setNotes(String notes){
        this.notes = notes;
    }

    public String getNotes(){
        return "";
    }

    @Override
    public void addNotes(String note) {

    }

}