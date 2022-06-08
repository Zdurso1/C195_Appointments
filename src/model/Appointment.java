package model;

import java.time.LocalDateTime;

public class Appointment {
    /**
     * Appointment ID
     */
    private int id;

    /**
     * Appointment Title
     */
    private String title;

    /**
     * Appointment Description
     */
    private String description;

    /**
     * Appointment Location
     */
    private String location;

    /**
     * Appointment Type
     */
    private String type;

    /**
     * Appointment start Date and Time
     */
    private LocalDateTime start;

    /**
     * Appointment end Date and "Time
     */
    private LocalDateTime end;

    /**
     * Appointment's Customer ID
     */
    private int customerID;

    /**
     * Appointment's User ID
     */
    private int userID;

    /**
     * Appointment's Contact ID
     */
    private int contactID;

    /**
     * Constructor for Appointment
     * @param id id
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start time and date
     * @param end end date and time
     * @param customerID Customer ID
     * @param userID User ID
     * @param contactID Contact ID
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Getter for Appointment ID
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Appointment ID
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Appointment Title
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for Appointment Title
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for Appointment Description
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for Appointment Description
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for Appointment Location
     * @return Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for Appointment Location
     * @param location Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for Appointment Type
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for Appointment Type
     * @param type Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Appointment Start Date and Time
     * @return Start Date and Time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter for Appointment Start Date and Time
     * @param start Start Date and Time
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter for Appointment End Date and Time
     * @return End Date and Time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter for Appointment End Date and Time
     * @param end End Date and Time
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Getter for Appointment Customer ID
     * @return Customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for Appointment Customer ID
     * @param customerID Customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for Appointment User ID
     * @return User ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for Appointment User ID
     * @param userID User ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for Appointment Contact ID
     * @return Contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for Appointment Contact ID
     * @param contactID Contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
