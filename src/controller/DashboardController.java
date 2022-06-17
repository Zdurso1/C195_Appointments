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

    public TableView<Appointment> appointmentTable;
    public TableColumn appointmentIDColumn;
    public TableColumn appointmentTitleColumn;
    public TableColumn appointmentTypeColumn;
    public TableColumn appointmentStartColumn;
    public Label appointmentTableLabel;
    public Button appointmentAddBTN;
    public Button appointmentUpdateBTN;
    public Button appointmentDeleteBTN;
    public Label customerTableLabel;
    public TableView<Customer> customerTable;
    public TableColumn customerIDColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerDivisionIDColumn;
    public Button customerAddBTN;
    public Button customerUpdateBTN;
    public Button customerDeleteBTN;

    private static Appointment A = null;
    public static Appointment getAppointment() {return A;}

    private static Customer C = null;
    public static Customer getCustomer() {return C;}



    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());


    ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Query.getAllCustomers());


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

        boolean within15 = false;
        int withinID = 0;
        ZonedDateTime withinZST = ZonedDateTime.now();
        for (Appointment appointment : allAppointments) {
            // does this need to be 16 minutes to be within the 15 minute timespan?

            if (appointment.getUserID() == Query.getCurrentUser().getId()) {
                if (appointment.getZst().isBefore(ZonedDateTime.now().plusMinutes(16)) && appointment.getZst().isAfter(ZonedDateTime.now())) {
                    within15 = true;
                    withinID = appointment.getId();
                    withinZST = appointment.getZst();
                }
            }
        }
        if (within15) {
            Err.alertOk("Better Hurry! Theres an appointment in the next 15 minutes.\nID: " + withinID + "\nStart: " + withinZST);
        }else{
            Err.alertOk("No appointments within 15 minutes.");
        }

    }

    public void addAppointment(ActionEvent actionEvent) {
        LoadPage.toOther(appointmentAddBTN, "AddAppointment");
    }

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

    public void addCustomer(ActionEvent actionEvent) {
        LoadPage.toOther(customerAddBTN, "AddCustomer");
    }

    public void updateCustomer(ActionEvent actionEvent) {
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Customer c = customerTable.getSelectionModel().getSelectedItem();
        C = c;

        LoadPage.toOther(customerUpdateBTN, "UpdateCustomer");

    }

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

    public void viewFiltered(ActionEvent actionEvent) {
        LoadPage.toOther(appointmentAddBTN,"Appointments");
    }

}
