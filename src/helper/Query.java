package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

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



}
