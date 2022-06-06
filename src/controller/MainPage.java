package controller;

import helper.Err;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
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
     * This is or will be used to get locale and translate labels and whatnot.
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("resources/MessageBundle",Locale.getDefault());
        System.out.println(Locale.getDefault());
        System.out.println(rb.getString("confirmDelete"));

        LocaleLable.setText(Locale.getDefault().getDisplayCountry());
        usernameLabel.setText(rb.getString("username"));
        usernameInput.setPromptText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        passwordInput.setPromptText(rb.getString("password"));
        LoginLabel.setText(rb.getString("login"));
        loginButton.setText(rb.getString("login"));



    }


    public void login(ActionEvent actionEvent) {
    }
}
