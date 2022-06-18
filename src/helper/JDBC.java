package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper function to connect to the database
 */
public abstract class JDBC {

    /**
     * Protocol
     */
    private static final String protocol = "jdbc";

    /**
     * DB Vendor
     */
    private static final String vendor = ":mysql:";

    /**
     * DB Location
     */
    private static final String location = "//localhost/";

    /**
     * DB Name
     */
    private static final String databaseName = "client_schedule";

    /**
     * DB full URL
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    /**
     * Driver Reference
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference

    /**
     * Username for logging into DB
     */
    private static final String userName = "sqlUser"; // Username

    /**
     * Password for logging into DB
     */
    private static String password = "Passw0rd!"; // Password

    /**
     * Full DB Connection
     */
    public static Connection connection;  // Connection Interface

    /**
     * Open Connection Method -- opens connection
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(SQLException | ClassNotFoundException e)
        {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Close Connection Method -- closes connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());

        }
    }
}
