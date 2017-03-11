package manager;

import spec.Contact;
import java.io.Serializable;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImpl implements Contact, Serializable {

    private int id;
    private String name;
    private String notes;

    /**
     * Overload constructor to apply a default value for notes.
     * @param suppliedId the id of the contact
     * @param contactName the name of the contact
     */
    public ContactImpl(final int suppliedId, final String contactName) {
        this(suppliedId, contactName, "");
    }

    /**
     * Constructor to apply id, name and notes.
     * @param suppliedId the id of the contact
     * @param contactName the name of the contact
     * @param attachedNotes attached notes for the contact
     */
    public ContactImpl(final int suppliedId, final String contactName,
                       final String attachedNotes) {
        Validation.validateIdPositive(suppliedId);
        Validation.validateObjectNotNull(contactName, "name");
        Validation.validateObjectNotNull(attachedNotes, "notes");

        setId(suppliedId);
        setName(contactName);
        setNotes(attachedNotes);
    }

    /**
     * Setter for id.
     * @param suppliedId the supplied Id
     */
    private void setId(final int suppliedId) {
        this.id = suppliedId;
    }

    /**
     * Setter for name.
     * @param suppliedName new name
     */
    private void setName(final String suppliedName) {
        this.name = suppliedName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Getter for name.
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for notes.
     * @param suppliedNotes the new notes
     */
    private void setNotes(final String suppliedNotes) {
        this.notes = suppliedNotes;
    }

    /**
     * Getter for Notes.
     * @return notes
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * {@inheritDoc}
     * since there is no ability to delete notes, I have made the assumption
     * that addNotes replaces the existing notes rather than appends to it.
     * Appending is still supported externally by utilising getNotes, appending
     * and then addNotes.
     */
    @Override
    public void addNotes(final String note) {
        Validation.validateObjectNotNull(note, "notes");
        setNotes(note);
    }

}
