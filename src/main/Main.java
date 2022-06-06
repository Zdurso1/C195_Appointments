package main;
import helper.JDBC;
import helper.Query;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainPage.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 850, 650));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        // for testing purposes
        //Locale.setDefault(new Locale("fr"));
        Query.select();
        JDBC.closeConnection();
    }
}
