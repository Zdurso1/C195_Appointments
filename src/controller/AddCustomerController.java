package controller;

import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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


        //customerFirstLevelDivisionInput.setItems(relevantDivisions);



        FirstLevelDivision customerDivision = (FirstLevelDivision) customerFirstLevelDivisionInput.getValue();


    }

    public void saveNewCustomer(ActionEvent actionEvent) {
    }

    public void cancelNewCustomer(ActionEvent actionEvent) {
    }



}
