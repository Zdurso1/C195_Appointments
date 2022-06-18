package controller;

import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    /**
     * Appointments table -- displays all appointments
     */
    public TableView<Appointment> appointmentTable;

    /**
     * Appointment ID Column
     */
    public TableColumn appointmentIDColumn;

    /**
     * Appointment Title Column
     */
    public TableColumn appointmentTitleColumn;

    /**
     * Appointment Type Column
     */
    public TableColumn appointmentTypeColumn;

    /**
     * Appointment Start Date Time Column
     */
    public TableColumn appointmentStartColumn;

    /**
     * Appointment Table Label
     */
    public Label appointmentTableLabel;

    /**
     * Add Appointment Button
     */
    public Button appointmentAddBTN;

    /**
     * Update Appointment Button
     */
    public Button appointmentUpdateBTN;

    /**
     * Delete Appointment Button
     */
    public Button appointmentDeleteBTN;

    /**
     * Customer Table Label
     */
    public Label customerTableLabel;

    /**
     * Customer Table -- displays all customers in database
     */
    public TableView<Customer> customerTable;

    /**
     * Customer ID Column
     */
    public TableColumn customerIDColumn;

    /**
     * Customer Name Column
     */
    public TableColumn customerNameColumn;

    /**
     * Customer Phone Number Column
     */
    public TableColumn customerPhoneColumn;

    /**
     * Customer Division ID Column
     */
    public TableColumn customerDivisionIDColumn;

    /**
     * Add Customer Button
     */
    public Button customerAddBTN;

    /**
     * Update Customer Record Button
     */
    public Button customerUpdateBTN;

    /**
     * Delete Customer Button
     */
    public Button customerDeleteBTN;

    /**
     * Selected Appointment for update or deletion -- also a getter for selected Appointment Record
     */
    private static Appointment A = null;
    public static Appointment getAppointment() {return A;}

    /**
     * Selected Customer for update or deletion -- also getter for selected Customer Record
     */
    private static Customer C = null;
    public static Customer getCustomer() {return C;}


    /**
     * List of All Appointments
     */
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());


    /**
     * List of All Customers
     */
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Query.getAllCustomers());


    /**
     * Initialize Method -- Prepares page for view and use by customer
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        appointmentTable.setItems(allAppointments);
        customerTable.setItems(allCustomers);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("zst"));

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

    }

    /**
     * Add appointment Method
     * @param actionEvent click
     */
    public void addAppointment(ActionEvent actionEvent) {
        LoadPage.toOther(appointmentAddBTN, "AddAppointment");
    }

    /**
     * Update Appointment Method -- sends user to Update Appointment Page if appointment to edit has been selected
     * @param actionEvent click
     */
    public void updateAppointment(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Appointment> appointmentSelection = appointmentTable.getSelectionModel();
        appointmentSelection.setSelectionMode(SelectionMode.SINGLE);
        A = appointmentSelection.getSelectedItem();

        if (A == null) {
            Err.alertOk("Must select an Appointment to Modify");
        }else{
            LoadPage.toOther(appointmentUpdateBTN, "EditAppointment");
        }
    }

    /**
     * Delete Appointment Method -- Deletes appointment record if one has been selected first
     * @param actionEvent click
     */
    public void deleteAppointment(ActionEvent actionEvent) {

        TableView.TableViewSelectionModel<Appointment> appointmentSelection = appointmentTable.getSelectionModel();
        appointmentSelection.setSelectionMode(SelectionMode.SINGLE);
        A = appointmentSelection.getSelectedItem();

        if (A == null) {
            Err.alertOk("Must select an Appointment to Delete.");
        } else {
            ButtonType B = Err.alertConfirm("Are you sure you want to Delete this Appointment?");
            if (B == ButtonType.YES) {
                allAppointments.remove(A);
                Query.deleteAppointment(A.getId());
            }else{
                Err.alertOk("Nothing will be Deleted");
            }
        }
    }

    /**
     * Add Customer Method -- sends user to Add Customer Page
     * @param actionEvent Click
     */
    public void addCustomer(ActionEvent actionEvent) {
        LoadPage.toOther(customerAddBTN, "AddCustomer");
    }

    /**
     * Update Customer Method -- Sends user to Update Customer Page
     * @param actionEvent
     */
    public void updateCustomer(ActionEvent actionEvent) {
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Customer c = customerTable.getSelectionModel().getSelectedItem();
        C = c;

        LoadPage.toOther(customerUpdateBTN, "UpdateCustomer");

    }

    /**
     * Delete Customer Method -- deletes customer record if one has been selected first
     * @param actionEvent click
     */
    public void deleteCustomer(ActionEvent actionEvent) {

        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Customer C = customerTable.getSelectionModel().getSelectedItem();
        int customerID = C.getId();
        int appointmentCount = 0;

        for (Appointment A : allAppointments) {
            if (A.getCustomerID() == customerID) {
                Err.alertOk(C.getName() + " Still has appointments with us! \n Please Delete Appointment \n Appointment ID: " + A.getId() + "\nDescription: " + A.getDescription());
                appointmentCount += 1;
            }
        }

        if (appointmentCount == 0) {
            ButtonType B = Err.alertConfirm("Are you sure you want to delete " + C.getName() + " permanently?");

            if (B == ButtonType.YES) {

                int result = 0;
                try {
                    result = Query.deleteCustomer(C.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result == 1) {
                    Err.alertConfirm("Customer " + C.getName() + " has been Deleted successfully.");
                    allCustomers.remove(C);
                }
            } else {
                Err.alertOk("No records will be Deleted");
            }
        } else {Err.alertOk("Some appointments must be deleted.");}
    }

    /**
     * View Filtered Method -- sends user to Appointments Page where different reports may be viewed
     * @param actionEvent click
     */
    public void viewFiltered(ActionEvent actionEvent) {
        LoadPage.toOther(appointmentAddBTN,"Appointments");
    }

}
