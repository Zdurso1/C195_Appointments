package main;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Main Class -- this is the heart of the application and is required for every java program
 */
public class Main extends Application{

    /**
     * Start Method -- Required for every JavaFX Program
     * @param stage Stage
     * @throws Exception Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 950, 600));
        stage.show();
    }

    /**
     * Main Class -- this is the heart of the application and is required for every java program
     * @param args args
     * @throws SQLException SQL Exception
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        //Locale.setDefault(Locale.FRANCE);
        launch();

        JDBC.closeConnection();
    }
}
