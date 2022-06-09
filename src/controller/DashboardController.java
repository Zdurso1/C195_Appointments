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
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));


    }

    public void addAppointment(ActionEvent actionEvent) {
    }

    public void updateAppointment(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Appointment> appointmentSelection = appointmentTable.getSelectionModel();
        appointmentSelection.setSelectionMode(SelectionMode.SINGLE);
        A = appointmentSelection.getSelectedItem();

        if (A == null) {
            Err.alertOk("Must select an Appointment to Modify");
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
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }
}
