package controller;

import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    /**
     * Label for Log In Page.
     */
    public Label LoginLabel;

    /**
     * Label for Username Input.
     */
    public Label usernameLabel;

    /**
     * Label for Password Input.
     */
    public Label passwordLabel;

    /**
     * Username Input Field.
     */
    public TextField usernameInput;

    /**
     * Password Input Field.
     */
    public PasswordField passwordInput;

    /**
     * Log In Button.
     */
    public Button loginButton;

    /**
     * Label to display user's Locale
     */
    public Label LocaleLable;

    /**
     * Current User ID
     */
    int currentUserID;


    /**
     * Resource Bundle -- This is used to get locale and translate labels and whatnot.
     * @param url url
     * @param resourceBundle resource bundle
     */
    ResourceBundle rb = ResourceBundle.getBundle("resources/MessageBundle", Locale.getDefault());

    /**
     * Initialize Method -- prepares page for use and viewing
     * @param url url
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("resources/MessageBundle", Locale.getDefault());
        System.out.println("Locale = " + Locale.getDefault() + "\n");


        LocaleLable.setText(Locale.getDefault().getDisplayCountry());
        usernameLabel.setText(rb.getString("username"));
        usernameInput.setPromptText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        passwordInput.setPromptText(rb.getString("password"));
        LoginLabel.setText(rb.getString("login"));
        loginButton.setText(rb.getString("login"));


    }

    /**
     * Login Method -- Validates user ID and password to allow user into the system.
     * Then sends user to main dashboard if input was correct.
     * @param actionEvent click
     */
    public void login(ActionEvent actionEvent) {
        ObservableList<User> allUsers = Query.getAllUsers();
        boolean success = false;

        try {
            int id = Integer.parseInt(usernameInput.getText());
            String pwd = passwordInput.getText();
            currentUserID = id;
            success = Query.login(id, pwd);

        } catch (IllegalArgumentException e) {
            Err.alertOk(rb.getString("loginUnsuccessfulInteger"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Err.alertOk(rb.getString("loginUnsuccessful"));
        }

        // login activity logger
        try {
            FileWriter loginActivity = new FileWriter("login_activity.txt", true);
            String text = "Somebody tried logging in with an id of: " + currentUserID
                    + "\nAttempt Success: " + success + "\nTimestamp: " + LocalDateTime.now(ZoneId.of("UTC")) + " UTC\n\n";
            loginActivity.write(text);
            loginActivity.close();
        }catch (IOException i){
            i.printStackTrace();
        }
        //

        if (!success) {
            Err.alertOk(rb.getString("loginUnsuccessful"));
        } else {
            LoadPage.toDashboard(loginButton);
        }


    }
}
