package controller;

import com.mysql.cj.result.ZonedDateTimeValueFactory;
import com.sun.javafx.event.DirectEvent;
import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    public TableView appointmentsTable;
    public TableColumn appointmentIDColumn;
    public TableColumn appointmentTitleColumn;
    public TableColumn appointmentLocationColumn;
    public TableColumn appointmentDescriptionColumn;
    public TableColumn appointmentTypeColumn;
    public TableColumn appointmentStartColumn;
    public TableColumn appointmentEndColumn;
    public TableColumn appointmentContactColumn;
    public TableColumn appointmentCustomerIDColumn;
    public TableColumn appointmentUserIDColumn;
    public ToggleGroup monthWeek;
    public RadioButton viewMonthRadio;
    public RadioButton viewWeekRadio;
    public Button editBTN;
    public Button deleteBTN;
    public Button homeBTN;

    private static Appointment A = null;
    public static Appointment getAppointment() {return A;}
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
    ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        for(Appointment appointment : allAppointments){
            Month currentMonth = LocalDate.now().getMonth();
            LocalDateTime ADateTime = appointment.getStart();

            if (ADateTime.getMonth().equals(currentMonth)) {filteredAppointments.add(appointment);}
        }

        appointmentsTable.setItems(filteredAppointments);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("zst"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("zet"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));



    }

    public void viewMonth(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        for(Appointment appointment : allAppointments){
            Month currentMonth = LocalDate.now().getMonth();
            LocalDateTime ADateTime = appointment.getStart();

            if (ADateTime.getMonth().equals(currentMonth)) {filteredAppointments.add(appointment);}
        }

        appointmentsTable.setItems(filteredAppointments);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("zst"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("zet"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

    }

    public void viewWeek(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(Appointment appointment : allAppointments){
            int currentDay = LocalDate.now().getDayOfMonth();
            int sevenDaysLater = currentDay + 7;
            int ADay = appointment.getStart().getDayOfMonth();

            if (ADay >= currentDay && ADay <= sevenDaysLater) {filteredAppointments.add(appointment);}
        }

        appointmentsTable.setItems(filteredAppointments);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("zst"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("zet"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }


    public void edit(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Appointment> appointmentSelection = appointmentsTable.getSelectionModel();
        appointmentSelection.setSelectionMode(SelectionMode.SINGLE);
        A = appointmentSelection.getSelectedItem();

        if (A == null) {
            Err.alertOk("Must select an Appointment to Modify");
        }else {
            LoadPage.toOther(homeBTN, "EditAppointment");
        }
    }

    public void delete(ActionEvent actionEvent) {

        TableView.TableViewSelectionModel<Appointment> appointmentSelection = appointmentsTable.getSelectionModel();
        appointmentSelection.setSelectionMode(SelectionMode.SINGLE);
        A = appointmentSelection.getSelectedItem();

        if (A == null) {
            Err.alertOk("Must select an Appointment to Modify");
        }else{
            ButtonType B = Err.alertConfirm("Are you sure you want to delete this appointment?");
            if (B == ButtonType.YES) {
                allAppointments.remove(A);
                filteredAppointments.remove(A);
                Query.deleteAppointment(A.getId());
            }
        }

    }

    public void goHome(ActionEvent actionEvent) {
        LoadPage.toDashboard(homeBTN);
    }
}
