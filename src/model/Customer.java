package model;

public class Customer {
    /**
     * Customer ID
     */
    int id;

    /**
     * Customer Name
     */
    String name;

    /**
     * Customer Address
     */
    String address;

    /**
     * Customer Postal Code
     */
    String postalCode;

    /**
     * Customer Phone Number
     */
    String phone;

    /**
     * Customer Division ID (Location)
     */
    int divisionID;

    /**
     * Constructor for Customer
     * @param id Customer ID
     * @param name Customer Name
     * @param address Customer Address
     * @param postalCode Customer Postal Code
     * @param phone Customer Phone Number
     * @param divisionID Customer Division ID (Location)
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * Getter for Customer ID
     * @return Customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Customer ID
     * @param id Customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Customer Name
     * @return Customer Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Customer Name
     * @param name Customer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for Customer Address
     * @return Customer Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for Customer Address
     * @param address Customer Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for Customer Postal Code
     * @return Customer Postal Code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for Customer Postal Code
     * @param postalCode Customer Postal Code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for Customer Phone Number
     * @return Customer Phone Number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for Customer Phone Number
     * @param phone Customer Phone Number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for Customer Division ID (Location)
     * @return Division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for Customer Division ID
     * @param divisionID Division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
