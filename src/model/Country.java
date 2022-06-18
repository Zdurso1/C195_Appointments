package model;

public class Country {
    /**
     * Country ID
     */
    private int id;

    /**
     * Country Name
     */
    private String name;

    /**
     * Country Constructor
     * @param id Country ID
     * @param name Country Name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for Country ID
     * @return Country ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Country ID
     * @param id Country ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Country Name
     * @return Country Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Country Name
     * @param name Country Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Overridden toString Method
     * @return Returns country name rather than countries location in memory
     */
    @Override
    public String toString() {
        return this.getName();
    }

}
