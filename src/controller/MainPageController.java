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
import java.net.URL;
import java.sql.SQLException;
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
     * This is used to get locale and translate labels and whatnot.
     * @param url url
     * @param resourceBundle resource bundle
     */

    /**
     * Resource Bundle
     */
    ResourceBundle rb = ResourceBundle.getBundle("resources/MessageBundle", Locale.getDefault());

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


    public void login(ActionEvent actionEvent) {
        ObservableList<User> allUsers = Query.getAllUsers();
        boolean success = false;

        try {
            int id = Integer.parseInt(usernameInput.getText());
            String pwd = passwordInput.getText();
            success = Query.login(id, pwd);

        } catch (IllegalArgumentException e) {
            Err.alertOk(rb.getString("loginUnsuccessfulInteger"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Err.alertOk(rb.getString("loginUnsuccessful"));
        }

        if (success = false) {
            Err.alertOk(rb.getString("loginUnsuccessful"));
        } else {
            LoadPage.toDashboard(loginButton);
        }


    }
}
