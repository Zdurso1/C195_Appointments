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
import model.Contact;
import model.Customer;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class AppointmentsController implements Initializable {

    /**
     * Appointments Table -- displays filtered appointment records
     */
    public TableView appointmentsTable;

    /**
     * Appointment ID Column
     */
    public TableColumn appointmentIDColumn;

    /**
     * Appointment Title Column
     */
    public TableColumn appointmentTitleColumn;

    /**
     * Appointment Location Column
     */
    public TableColumn appointmentLocationColumn;

    /**
     * Appointment Description Column
     */
    public TableColumn appointmentDescriptionColumn;

    /**
     * Appointment Type Column
     */
    public TableColumn appointmentTypeColumn;

    /**
     * Appointment Start Date Time Column
     */
    public TableColumn appointmentStartColumn;

    /**
     * Appointment End Date Time Column
     */
    public TableColumn appointmentEndColumn;

    /**
     * Appointment Contact Column
     */
    public TableColumn appointmentContactColumn;

    /**
     * Appointment Customer ID Column
     */
    public TableColumn appointmentCustomerIDColumn;

    /**
     * Appointment User ID Column
     */
    public TableColumn appointmentUserIDColumn;

    /**
     * Toggle group for current month or next 7 days (week) Filter
     */
    public ToggleGroup monthWeek;

    /**
     * Radio Button to filter appointments by current month
     */
    public RadioButton viewMonthRadio;

    /**
     * Radio button to filter appointments for the next 7 days
     */
    public RadioButton viewWeekRadio;

    /**
     * Edit button -- Sends user to edit page if appointment was selected first
     */
    public Button editBTN;

    /**
     * Delete button -- deletes appointment record if appointment was selected first
     */
    public Button deleteBTN;

    /**
     * Home button -- sends user to main dashboard
     */
    public Button homeBTN;

    /**
     * Dropdown list of users to filter appointments by specific user record
     */
    public ComboBox<Customer> customerComboInput;

    /**
     * Dropdown menu to filter appointments by specific month
     */
    public ComboBox<Month> eachMonthComboInput;

    /**
     * Dropdown menu to filter appointments by selected type
     */
    public ComboBox byTypeComboInput;

    /**
     * Dropdown menu to filter appointments by selected contact record
     */
    public ComboBox<Contact> byContactComboInput;

    /**
     * Dialog pane for displaying count of records that match selected filter
     */
    public DialogPane dialogPane;

    /**
     * Selected appointment and a getter for selected appointment
     */
    private static Appointment A = null;
    public static Appointment getAppointment() {return A;}

    /**
     * List of all appointments
     */
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());

    /**
     * List of filtered appointments
     */
    ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();


    /**
     * Initialize Method  -- prepares page for use and view by user
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Query.getAllCustomers());
        customerComboInput.setItems(allCustomers);

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

        dialogPane.setContentText("Number of appointments for this month: " + filteredAppointments.size());

        // populate months dropdown
        ObservableList<Month> allMonths = FXCollections.observableArrayList();
        for (int m = 1; m <= 12; m++) {
            allMonths.add(Month.of(m));
            System.out.println(Month.of(m));
        }
        eachMonthComboInput.setItems(allMonths);

        // populate types dropdown
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        for (Appointment a : allAppointments) {
            String type = a.getType();
            if(allTypes.contains(type)){
                System.out.println("types already contains "+type+"\n");
            }else{allTypes.add(type);
                System.out.println(allTypes);}
        }
        byTypeComboInput.setItems(allTypes);

        // populate contact dropdown
        ObservableList<Contact> allContacts = FXCollections.observableArrayList(Query.getAllContacts());
        byContactComboInput.setItems(allContacts);

    }


    /**
     * View by current month filter method
     * @param actionEvent click
     */
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

        dialogPane.setContentText("Number of appointments for this month: " + filteredAppointments.size());



    }


    /**
     * View by next 7 days filter method
     * @param actionEvent click
     */
    public void viewWeek(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(Appointment appointment : allAppointments){
            int currentDay = LocalDate.now().getDayOfMonth();
            int sevenDaysLater = currentDay + 7;
            int ADay = appointment.getStart().getDayOfMonth();

            if (ADay >= currentDay && ADay <= sevenDaysLater && appointment.getStart().getMonth().equals(LocalDate.now().getMonth())) {
                filteredAppointments.add(appointment);
            }
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

        dialogPane.setContentText("Number of appointments for this week: " + filteredAppointments.size());
    }


    /**
     * Edit method -- sends user to edit page and sets selected appointment for editing
     * @param actionEvent click
     */
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


    /**
     * Delete Method -- deletes appointment record if one has been selected
     * @param actionEvent
     */
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


    /**
     * Sends user to main dashboard
     * @param actionEvent click
     */
    public void goHome(ActionEvent actionEvent) {
        LoadPage.toDashboard(homeBTN);
    }


    /**
     * Filter appointments by selected customer
     * @param actionEvent click
     */
    public void byCustomer(ActionEvent actionEvent) {
        Customer c = customerComboInput.getValue();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        for (Appointment a : allAppointments){
            if (a.getCustomerID() == c.getId()){customerAppointments.add(a);}

        }
        appointmentsTable.setItems(customerAppointments);

        dialogPane.setContentText("Number of appointments for this customer: " + customerAppointments.size());

    }


    /**
     * Filter appointments by type method
     * @param actionEvent click
     */
    public void byType(ActionEvent actionEvent) {
        String type = byTypeComboInput.getValue().toString();
        System.out.println(type);
        for (Appointment a : allAppointments) {
            filteredAppointments.remove(a);
            if (a.getType().equals(type)){filteredAppointments.add(a);}
        }
        appointmentsTable.setItems(filteredAppointments);
        dialogPane.setContentText("Number of appointments for this type: " + filteredAppointments.size());

    }


    /**
     * Filter appointments by selected contact method
     * @param actionEvent click
     */
    public void byContact(ActionEvent actionEvent) {
        Contact selectedContact = byContactComboInput.getValue();
        for (Appointment a : allAppointments){
            filteredAppointments.remove(a);
            if (a.getContactID() == selectedContact.getContactID()){filteredAppointments.add(a);}
        }
        appointmentsTable.setItems(filteredAppointments);
        dialogPane.setContentText("Number of appointments for this Contact: " + filteredAppointments.size());
    }


    /**
     * Filter appointments by selected month method
     * @param actionEvent click
     */
    public void monthSelected(ActionEvent actionEvent) {
        Month selectedMonth = eachMonthComboInput.getValue();
        System.out.println("Selected Month: "+selectedMonth);

        for (Appointment a : allAppointments) {
            filteredAppointments.remove(a);
            if(a.getStart().getMonth().equals(selectedMonth)){filteredAppointments.add(a);}
        }
        appointmentsTable.setItems(filteredAppointments);
        dialogPane.setContentText("Number of appointments for this month: " + filteredAppointments.size());

    }
}
