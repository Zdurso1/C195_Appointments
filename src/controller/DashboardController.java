package controller;

import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

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
    public TableView customerTable;
    public TableColumn customerIDColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerDivisionIDColumn;
    public Button customerAddBTN;
    public Button customerUpdateBTN;
    public Button customerDeleteBTN;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
        appointmentTable.setItems(allAppointments);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));

    }
}
