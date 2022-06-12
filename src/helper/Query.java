package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public abstract class Query {

    private static int currentUserID;
    public static int getCurrentUserID() {return currentUserID;}

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

    public static boolean login(int id, String pwd) throws SQLException {
        boolean success = false;
        ObservableList allRB = FXCollections.observableArrayList();
        try {
            String sql = "select User_ID,User_Name, Password from users where ? = User_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, String.valueOf(id));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String password = rs.getString("Password");
                int userID = rs.getInt("User_ID");

                if (password.equals(pwd)) {
                    currentUserID = userID;
                    success = true;
                    System.out.println(password + " " + userID);
                }
            }

            System.out.println(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


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

                String formattedStart = start.toLocalDateTime().format(formatter);
                String formattedEnd = end.toLocalDateTime().format(formatter);

                Appointment a = new Appointment(id,title,description,location,type,formattedStart,formattedEnd,customerID,userID,contactID);

                allAppointments.add(a);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
    }

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


    public static void createAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end) {

        try {
            // Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By
            String sql = "INSERT INTO appointments VALUES (NULL,?,?,?,?,?,?,NOW(),?,NOW(),?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, getCurrentUserID());
            ps.setInt(8, getCurrentUserID());



            ps.executeUpdate(sql);


            String message = "Appointment has been Created! Return to main page?";
            System.out.println(message);
            Err.alertConfirm(message);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }



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

    public static int addCustomer(String name, String address, String postalCode, String phone, int divisionID) throws SQLException {
        // Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
        // name, address, postalCode, phone, divisionID
        try {
            int userID = getCurrentUserID();
            String sql = "INSERT INTO customers VALUES (NULL,'" +name+ "','" +address+ "','" +postalCode+ "','"+phone+"',CURRENT_TIMESTAMP()," + userID + ",NOW()," + userID + "," + divisionID + ")";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            int rs = ps.executeUpdate(sql);

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
