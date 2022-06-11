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
    public TextField customerNameInput;
    public TextField customerAddressInput;
    public TextField customerPostalCodeInput;
    public TextField customerPhoneNumberInput;
    public Button addCustomerSaveBTN;
    public Button addCustomerCancelBTN;
    public ComboBox customerCountryInput;
    public ComboBox customerFirstLevelDivisionInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> relevantDivisions = FXCollections.observableArrayList();

        try {
            allCountries = Query.getAllCountries();
            allDivisions = Query.getAllDivisions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        // first lambda .... this basically just made me write less.
        allCountries.forEach((c) -> customerCountryInput.getItems().add(c.getName()));

        ObservableList<FirstLevelDivision> finalAllDivisions = allDivisions;
        ObservableList<Country> finalAllCountries = allCountries;

        customerCountryInput.setOnAction(actionEvent -> {
            int index = customerCountryInput.getSelectionModel().getSelectedIndex();
            System.out.println("selected ID = " + finalAllCountries.get(index).getId() + "\n\n" + customerFirstLevelDivisionInput.getItems().size());

            customerFirstLevelDivisionInput.getItems().remove(0, customerFirstLevelDivisionInput.getItems().size());

            for (FirstLevelDivision D : finalAllDivisions) {

                if (D.getCountryID() == finalAllCountries.get(index).getId()) {

                            relevantDivisions.add(D);
                            customerFirstLevelDivisionInput.getItems().add(D.getDivision());



                }
            }
        });


        FirstLevelDivision customerDivision = (FirstLevelDivision) customerFirstLevelDivisionInput.getValue();


    }

    // had a class cast exception here for FirstLevelDivision
    public void saveNewCustomer(ActionEvent actionEvent) throws SQLException {
        System.out.println(customerFirstLevelDivisionInput.getSelectionModel().getSelectedItem());
        Object CFD = customerFirstLevelDivisionInput.getSelectionModel().getSelectedItem();

        int customerDivisionID = 999999999;
        System.out.println(customerDivisionID);

        for (FirstLevelDivision F : Query.getAllDivisions()) {
            if (F.getDivision().equals(CFD)) {
                customerDivisionID = F.getId();
                System.out.println(F.getId());
            }
        }


        // name, address, postalCode, phone, divisionID
        String name = customerNameInput.getText();
        String address = customerAddressInput.getText();
        String postalCode = customerPostalCodeInput.getText();
        String phone = customerPhoneNumberInput.getText();
        int divID = customerDivisionID;
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

    public void cancelNewCustomer(ActionEvent actionEvent) {
        ButtonType B = Err.alertConfirm("Input will be discarded. Return to Dashboard?");
        if (B == ButtonType.YES) {
            LoadPage.toDashboard(addCustomerCancelBTN);
        }
    }



}
