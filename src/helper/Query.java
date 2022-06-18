package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import model.*;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Query -- Helper function -- abstract class containing all database queries used throughout the program
 */
public abstract class Query {

    /**
     * Current user is needed throughout the program, so this comes in handy
     * Current user assigned in the login method if login was successful
     * -- also a getter for the current user
     */
    private static User currentUser;
    public static User getCurrentUser() {return currentUser;}

    /**
     * Get all countries method -- returns a list of all available countries
     * @return returns a list of all available countries
     * @throws SQLException SQL Exception
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {

        ObservableList<Country> countriesList = FXCollections.observableArrayList();
        try {
            String sql = "select * from countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Country c = new Country(id, name);
                countriesList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countriesList;
    }

    /**
     * Get All Users Method
     * @return Returns a list of all user records within the system
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try {
            String sql = "select * from users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(id,name,password);

                allUsers.add(u);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    /**
     * Login Method -- Validates that anyone trying to enter the system is a valid user
     * LAMBDA -- Uses a lambda to assign the current (Logged In) user
     * @param id User ID
     * @param pwd User Password
     * @return boolean successful
     * @throws SQLException SQL Exception
     */
    public static boolean login(int id, String pwd) throws SQLException {
        boolean success = false;
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        allUsers = Query.getAllUsers();
        try {
            String sql = "select User_ID,User_Name, Password from users where ? = User_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, String.valueOf(id));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String password = rs.getString("Password");
                int userID = rs.getInt("User_ID");

                if (password.equals(pwd)) {
                    // lambda
                    allUsers.forEach(User -> {if(User.getId() == userID){currentUser = User;}});
                    success = true;
                    System.out.println(password + " " + userID);

                    // check 15
                    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(getAllAppointments());
                    boolean within15 = false;
                    int withinID = 0;
                    ZonedDateTime withinZST = ZonedDateTime.now();
                    for (Appointment appointment : allAppointments) {
                        // does this need to be 16 minutes to be within the 15 minute timespan?

                        if (appointment.getUserID() == Query.getCurrentUser().getId()) {
                            if (appointment.getZst().isBefore(ZonedDateTime.now().plusMinutes(16)) && appointment.getZst().isAfter(ZonedDateTime.now())) {
                                within15 = true;
                                withinID = appointment.getId();
                                withinZST = appointment.getZst();
                            }
                        }
                    }
                    if (within15) {
                        Err.alertOk("Better Hurry! Theres an appointment in the next 15 minutes.\nID: " + withinID + "\nStart: " + withinZST);

                    }else{
                        Err.alertOk("No appointments within 15 minutes.");

                    }

                    //
                }
            }

            System.out.println(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    /**
     * Get All Customers Method
     * @return Returns a list of all customer records within the system
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            String sql = "select * from customers";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customer c = new Customer(id,name,address,postalCode,phone,divisionID);

                allCustomers.add(c);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }


    /**
     * Get All Appointments Method
     * @return Returns a list of all appointment records within the system
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "select * from appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                LocalDateTime Start = start.toLocalDateTime();
                LocalDateTime End = end.toLocalDateTime();


                Appointment a = new Appointment(id,title,description,location,type,Start,End,customerID,userID,contactID);

                allAppointments.add(a);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
    }


    /**
     * Delete Appointment Method -- Deletes the selected appointment record
     * @param appointmentID the selected appointment record's ID
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.executeUpdate(sql);


            String message = "Appointment " + appointmentID + " Has been Deleted";
            System.out.println(message);
            Err.alertOk(message);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Create Appointment Method -- creates a new appointment record
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param start Appointment Start DateTime
     * @param end Appointment End DateTime
     * @param userName Appointment creators User Name
     * @param userID Appointment creators User ID
     * @param contactID Appointment Contact ID
     * @param customerID Appointment Customer ID
     * @return Number of Rows Affected
     */
    public static int createAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,  String userName, int userID, int contactID, int customerID) {
        try {
            // Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID
            String sql = "INSERT INTO appointments "
                    + "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                    + "VALUES (?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?)";
            System.out.println("TIMESTAMP IN QUERY: " + Timestamp.valueOf(start));
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);       // Title VARCHAR(50)
            ps.setString(2, description); // Description VARCHAR(50)
            ps.setString(3, location);    // Location VARCHAR(50)
            ps.setString(4, type);        // Type VARCHAR(50)

            ps.setTimestamp(5, Timestamp.valueOf(start)); // Start DATETIME
            ps.setTimestamp(6, Timestamp.valueOf(end));   // End DATETIME
            // Create_Date covered by NOW()
            ps.setString(7, userName); // Created_By VARCHAR(50)
            // Last_Update covered by NOW()
            ps.setString(8, userName); // Last_Updated_By VARCHAR(50)
            ps.setInt(9, customerID);  // Customer_ID INT(10) (FK)
            ps.setInt(10, userID);     // User_ID INT(10) (FK)
            ps.setInt(11, contactID);  // Contact_ID INT(10) (FK)

            int rows = ps.executeUpdate();

            return rows;


        }catch(SQLException e) {
            System.out.println("SQL Error Cause: " + e.getCause());
            e.printStackTrace();
        }
        return 99;
    }


    /**
     * Update Appointment Method -- updates a selected appointment record
     * @param id Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param start Appointment Start DateTime
     * @param end Appointment End DateTime
     * @param userName Appointment User Name -- creator
     * @param userID Appointment User ID -- creator
     * @param contactID Appointment Contact ID
     * @param customerID Appointment Customer ID
     * @return Number of Rows Affected
     * @throws SQLException SQL Exception
     */
    public static int updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,  String userName, int userID, int contactID, int customerID) throws SQLException {
        try {
            String sql = "UPDATE appointments SET"
                    + " Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?"
                    + " WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1,title);
            ps.setString(2,description);
            ps.setString(3,location);
            ps.setString(4,type);
            ps.setTimestamp(5,Timestamp.valueOf(start));
            ps.setTimestamp(6,Timestamp.valueOf(end));
            ps.setString(7,userName);
            ps.setInt(8,customerID);
            ps.setInt(9,userID);
            ps.setInt(10,contactID);
            ps.setInt(11,id);

            return ps.executeUpdate();

        }catch(SQLException s) {
            System.out.println("Error Cause: " + s.getCause());
            s.printStackTrace();
        }

        return 9999;
    }


    /**
     * Get All Divisions Method
     * @return Returns a list of all first level divisions
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        try {
            String sql = "select * from first_level_divisions";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivision d = new FirstLevelDivision(id,name,countryID);

                allDivisions.add(d);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return allDivisions;
    }


    /**
     * Add Customer Method -- adds a new customer record to the database
     * @param name Customer Name
     * @param address Customer Address
     * @param postalCode Customer Postal Code
     * @param phone Customer Phone Number
     * @param divisionID Customer Division ID
     * @return Number of rows affected
     * @throws SQLException SQL Exception
     */
    public static int addCustomer(String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        // Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
        // name, address, postalCode, phone, divisionID
        try {

            String sql = "INSERT INTO customers "+
                    "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) "+
                    "VALUES(?,?,?,?,NOW(),?,NOW(),?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            /**/
            ps.setString(1,name);
            ps.setString(2,address);
            ps.setString(3,postalCode);
            ps.setString(4,phone);
            // NOW()
            ps.setString(5,currentUser.getName());
            // NOW()
            ps.setString(6, currentUser.getName());
            ps.setInt(7, divisionID);

            int rs = ps.executeUpdate();

            if (rs == 1) {
                return rs;
            }else {
                Err.alertOk("Whoopsie Daisies, Something Happened!");
                return 123456789;
            }

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return 99;
    }


    /**
     * Deletes a selected customer record from the database
     * @param customerID ID of selected Customer Record
     * @return Number of rows affected
     */
    public static int deleteCustomer (int customerID) {
        int result = 0;
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = " + customerID;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            result = ps.executeUpdate(sql);

        }catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Update Customer MEthod -- Updates a customer record
     * @param customerID Customer ID -- Disabled Auto-Populated
     * @param customerName Customer Name
     * @param customerAddress Customer Address
     * @param customerPostalCode Customer Postal Code
     * @param customerPhone Customer Phone Number
     * @param division Customer First Level Division
     * @return Number of rows affected
     * @throws SQLException SQL Exception
     */
    public static int updateCustomer (int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int division) throws SQLException {
        int rowsAffected = 0;
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPostalCode);
        ps.setString(4, customerPhone);
        ps.setInt(5, division);
        ps.setInt(6, customerID);

        rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }


    /**
     * Get All Contacts Method
     * @return Returns a list of all contact records within the system
     */
    public static ObservableList<Contact> getAllContacts () {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {
            String sql = "select * from contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(id,name,email);

                allContacts.add(c);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return allContacts;
    }



}
