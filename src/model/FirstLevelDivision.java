package model;

public class FirstLevelDivision {
    /**
     * First Level Division ID
     */
    private int id;

    /**
     * Division Name
     */
    private String division;

    /**
     * Divisions country ID -- country in which division resides
     */
    private int countryID;

    /**
     * Constructor for first level division
     * @param id Division ID
     * @param division Division Name
     * @param countryID Division Country ID
     */
    public FirstLevelDivision(int id, String division, int countryID) {
        this.id = id;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Getter for Division ID
     * @return Division ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Division ID
     * @param id Division ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Division Name
     * @return Division Name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for division Name
     * @param division Division Name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for Division Country ID
     * @return Division Country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for Division Country ID
     * @param countryID Country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Overridden toString Method
     * @return Returns division name instead of location in memory
     */
    @Override
    public String toString() {
        return this.getDivision();
    }
}
