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
import model.Customer;
import model.FirstLevelDivision;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    /**
     * Customer Name input -- auto-populated
     */
    public TextField customerNameInput;

    /**
     * Customer Address input -- auto-populated
     */
    public TextField customerAddressInput;

    /**
     * Customer Postal Code input -- auto-populated
     */
    public TextField customerPostalCodeInput;

    /**
     * Customer Phone Number input -- auto-populated
     */
    public TextField customerPhoneNumberInput;

    /**
     * Customer ID Field -- Disabled auto-populated
     */
    public TextField customerIDField;

    /**
     * Save Button
     */
    public Button modCustomerSaveBTN;

    /**
     * Cancel Button
     */
    public Button modCustomerCancelBTN;

    /**
     * Customer Country input -- auto-populated dropdown
     */
    public ComboBox customerCountryInput;

    /**
     * Customer First Level Division Input -- auto-populated based on country selection
     */
    public ComboBox customerFirstLevelDivisionInput;


    /**
     * Initialize Method -- prepares page for use
     * LAMBDA -- uses a lambda to populate countries list
     * LAMBDA -- uses a lambda to populate First Level Division list
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer C = DashboardController.getCustomer();
        customerIDField.setPromptText(String.valueOf(C.getId()));
        customerNameInput.setText(C.getName());
        customerAddressInput.setText(C.getAddress());
        customerPostalCodeInput.setText(C.getPostalCode());
        customerPhoneNumberInput.setText(C.getPhone());

        //
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> relevantDivisions = FXCollections.observableArrayList();

        try {
            allCountries = Query.getAllCountries();
            allDivisions = Query.getAllDivisions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for(FirstLevelDivision D : allDivisions ) {
            if (D.getId() == C.getDivisionID()) {
                customerFirstLevelDivisionInput.getSelectionModel().select(D.getDivision());
                customerCountryInput.getSelectionModel().select(allCountries.get(D.getCountryID()-1).getName());
                break;
            }
        }



        // first lambda
        allCountries.forEach((c) -> customerCountryInput.getItems().add(c.getName()));

        ObservableList<FirstLevelDivision> finalAllDivisions = allDivisions;
        ObservableList<Country> finalAllCountries = allCountries;

        // second lambda
        customerCountryInput.setOnAction(actionEvent -> {
            int index = customerCountryInput.getSelectionModel().getSelectedIndex();
            //System.out.println("selected ID = " + finalAllCountries.get(index).getId() + "\n\n" + customerFirstLevelDivisionInput.getItems().size());

            customerFirstLevelDivisionInput.getItems().remove(0, customerFirstLevelDivisionInput.getItems().size());

            for (FirstLevelDivision D : finalAllDivisions) {

                if (D.getCountryID() == finalAllCountries.get(index).getId()) {

                    relevantDivisions.add(D);
                    customerFirstLevelDivisionInput.getItems().add(D.getDivision());

                }
            }
        });

    }

    /**
     * Save Method -- Saves changes made to user record
     * @param actionEvent click
     * @throws SQLException SQL Exception
     */
    public void saveModCustomer(ActionEvent actionEvent) throws SQLException {
        String custDivisionString = customerFirstLevelDivisionInput.getValue().toString();

        FirstLevelDivision customerDivision;
        String customerName = customerNameInput.getText();
        String customerAddress = customerAddressInput.getText();
        String customerPhone = customerPhoneNumberInput.getText();
        String customerPostalCode = customerPostalCodeInput.getText();
        int customerID = Integer.parseInt(customerIDField.getPromptText());
        int customerDivisionInt = 99999;

        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        allDivisions = Query.getAllDivisions();
        for (FirstLevelDivision D : allDivisions) {
            if (D.getDivision().equals(custDivisionString)){customerDivision = D; customerDivisionInt = D.getId();}
        }

        Customer C = DashboardController.getCustomer();

        try {
            int rowsAffected = Query.updateCustomer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, customerDivisionInt);
            if (rowsAffected == 1) {
                Err.alertConfirm(customerName + "'s record has been successfully updated!");
                LoadPage.toDashboard(modCustomerCancelBTN);
            }else{
                Err.alertOk("Something went wrong. please try again.");
            }
        }catch(SQLException s) {
            s.printStackTrace();
        }


    }


    /**
     * Cancel Method -- discards user input and sends user back to main dashboard
     * @param actionEvent click
     */
    public void cancelModCustomer(ActionEvent actionEvent) {
        Err.alertOk("All input will be discarded");
        ButtonType B = Err.alertConfirm("Would you like to return to the home page?");
        if (B == ButtonType.YES) {
            LoadPage.toDashboard(modCustomerCancelBTN);
        }
    }
}
