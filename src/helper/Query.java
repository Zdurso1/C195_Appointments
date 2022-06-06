package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Query {
    /*
    public static void select() throws SQLException {
        String sql = "select * from appointments";
        PreparedStatement statement = JDBC.connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String description = rs.getString("Type");
            System.out.println(description);
        }

    }
    */

    public static ObservableList<Countries> getAllCountries() {

        ObservableList<Countries> countriesList = FXCollections.observableArrayList();



    }



}
