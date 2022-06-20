package controller;

import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.FirstLevelDivision;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class AddCustomerController implements Initializable {
    /**
     * Customer Name input field
     */
    public TextField customerNameInput;

    /**
     * Customer Address input field
     */
    public TextField customerAddressInput;

    /**
     * Customer Postal Code input field
     */
    public TextField customerPostalCodeInput;

    /**
     * Customer Phone Number input field
     */
    public TextField customerPhoneNumberInput;

    /**
     * Save Button -- Adds new customer to the database
     */
    public Button addCustomerSaveBTN;

    /**
     * Cancel Button -- Throws away all user input and returns user to main dashboard
     */
    public Button addCustomerCancelBTN;

    /**
     * Dropdown menu with all available countries to select from
     */
    public ComboBox<Country> customerCountryInput;

    /**
     * Dropdown menu with all relevant first level divisions for selected country
     */
    public ComboBox<FirstLevelDivision> customerFirstLevelDivisionInput;

    /**
     * Initialize method -- Prepares the page for use.
     * LAMBDA -- Uses a lambda to populate first level division dropdown list once a country is selected.
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> relevantDivisions = FXCollections.observableArrayList();

        try {
            allCountries = Query.getAllCountries();
            allDivisions = Query.getAllDivisions();

            customerCountryInput.setItems(allCountries);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ObservableList<FirstLevelDivision> finalAllDivisions = allDivisions;
        customerCountryInput.setOnAction(ActionEvent -> {
            relevantDivisions.remove(0,relevantDivisions.size());
            for (FirstLevelDivision F : finalAllDivisions) {
                if (F.getCountryID() == customerCountryInput.getValue().getId()) {
                    relevantDivisions.add(F);
                }
            }
            customerFirstLevelDivisionInput.setItems(relevantDivisions);
        });


    }

    /**
     * Save New Customer Method -- adds the new customer to the database.
     * @param actionEvent action event
     * @throws SQLException SQL Exception
     */
    // had a class cast exception here for FirstLevelDivision
    public void saveNewCustomer(ActionEvent actionEvent) throws SQLException {
        System.out.println(customerFirstLevelDivisionInput.getSelectionModel().getSelectedItem());

        // name, address, postalCode, phone, divisionID
        String name = customerNameInput.getText();
        String address = customerAddressInput.getText();
        String postalCode = customerPostalCodeInput.getText();
        String phone = customerPhoneNumberInput.getText();
        int divID = customerFirstLevelDivisionInput.getValue().getId();
        System.out.println(divID);


        try {
            int success = Query.addCustomer(name, address, postalCode, phone, divID);
            if (success == 1) {
                Err.alertConfirm("Customer Created Successfully");
                LoadPage.toDashboard(addCustomerCancelBTN);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * Cancel Button's Method -- Discards all user input and sends user back to main dashboard.
     * @param actionEvent Button Click action event
     */
    public void cancelNewCustomer(ActionEvent actionEvent) {
        ButtonType B = Err.alertConfirm("Input will be discarded. Return to Dashboard?");
        if (B == ButtonType.YES) {
            LoadPage.toDashboard(addCustomerCancelBTN);
        }
    }



}
