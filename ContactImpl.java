/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImpl implements Contact{

    private int id;
    private String name;
    private String notes;

    public ContactImpl(int id, String name){
        this(id, name, "");
    }

    public ContactImpl(int id, String name, String notes){
        validateId(id);
        validateNull(name, "name");
        validateNull(notes, "notes");

        setId(id);
        setName(name);
        setNotes(notes);
    }

    private void validateId(int id) {
        if(id < 1)
            throw new IllegalArgumentException("Id must be greater than 0");
    }

    private void validateNull(String string, String variable) {
        if(string == null)
            throw new NullPointerException(variable + " supplied was null");
    }

    private void setId(int id){
        this.id = id;
    }

    private void setName(String name){
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
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