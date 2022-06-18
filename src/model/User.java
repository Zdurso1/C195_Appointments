package model;

public class User {
    /**
     * User ID
     */
    int id;

    /**
     * User Name
     */
    String name;

    /**
     * User Password
     */
    String password;

    /**
     * Constructor for User
     * @param id User ID
     * @param name User Name
     * @param password User Password
     */
    public User(int id, String name, String password) {
        this.id=id;
        this.name=name;
        this.password=password;
    }


    /**
     * Getter for User ID
     * @return User ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for User ID
     * @param id User ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for User Name
     * @return USer Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for User Name
     * @param name USer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for User Password
     * @return User Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for User Password
     * @param password User Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Overridden toString Method
     * @return Returns User ID and User Name instead of location in memory
     */
    @Override
    public String toString() {return "ID: " + id + " Name: " + name;}
}
