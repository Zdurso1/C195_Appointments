package model;

public class Contact {
    /**
     * Contact ID
     */
    private int contactID;

    /**
     * Contact Name
     */
    private String contactName;

    /**
     * Contact Email Address
     */
    private String contactEmail;

    /**
     * Contact Constructor
     * @param contactID ID
     * @param contactName Name
     * @param contactEmail Email Address
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Getter for ID
     * @return Contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for Contact ID
     * @param contactID Contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for Contact Name
     * @return Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for Contact Name
     * @param contactName Contact Name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for Contact Email Address
     * @return Contact Email Address
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter for Contact Email Address
     * @param contactEmail Email Address
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Overridden toString Method
     * @return Returns name and contact ID instead of object's location
     */
    @Override
    public String toString() {
        return (contactName + " ID: " + contactID);
    }
}
