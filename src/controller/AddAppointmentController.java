package controller;

import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    public TextField appointmentIDInput;
    @FXML
    public TextField appointmentTitleInput;
    public TextField appointmentDescriptionInput;
    public TextField appointmentLocationInput;
    public ComboBox<Contact> appointmentContactInput;
    public TextField appointmentTypeInput;
    public DatePicker appointmentStartDateInput;
    public DatePicker appointmentEndDateInput;
    public ComboBox<LocalTime> appointmentStartTimeInput;
    public ComboBox<LocalTime> appointmentEndTimeInput;
    public ComboBox<Customer> appointmentCustomerIDInput;
    public ComboBox<User> appointmentUserIDInput;
    public Button saveNewAppointmentBTN;
    public Button cancelNewAppointmentBTN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        allCustomers = Query.getAllCustomers();
        appointmentCustomerIDInput.setItems(allCustomers);


        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        allContacts = Query.getAllContacts();
        appointmentContactInput.setItems(allContacts);

        ObservableList<User> allUsers = FXCollections.observableArrayList();
        allUsers = Query.getAllUsers();
        appointmentUserIDInput.setItems(allUsers);


        ObservableList<LocalTime> time = FXCollections.observableArrayList();
        for (int i = 0; i < 24; i++) {
            time.add(LocalTime.of(i,0));
            //time.add(LocalTime.of(i,15));
            //time.add(LocalTime.of(i,30));
            //time.add(LocalTime.of(i,45));
        }

        appointmentStartTimeInput.setItems(time);

        ObservableList<LocalTime> endTime = FXCollections.observableArrayList();
        for (int i = 1; i < 24; i++) {
            endTime.add(LocalTime.of(i,0));
            //endTime.add(LocalTime.of(i,15));
            //endTime.add(LocalTime.of(i,30));
            //endTime.add(LocalTime.of(i,45));
        }
        endTime.add(LocalTime.of(0,0));
        appointmentEndTimeInput.setItems(endTime);

    }

    public void saveNewAppointment(ActionEvent actionEvent) {
        // Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By
        int overlappingID = 0;
        boolean overlapping = false;
        String title = appointmentTitleInput.getText();
        String description = appointmentDescriptionInput.getText();
        String location = appointmentLocationInput.getText();
        String type = appointmentTypeInput.getText();

        LocalDate localStartDate = appointmentStartDateInput.getValue();
        LocalDate localEndDate = appointmentEndDateInput.getValue();
        LocalTime startTime = appointmentStartTimeInput.getValue();
        LocalTime endTime = appointmentEndTimeInput.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(localStartDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(localEndDate, endTime);

        int userID = appointmentUserIDInput.getValue().getId();
        String userName = appointmentUserIDInput.getValue().getName();
        int contactID = appointmentContactInput.getValue().getContactID();
        int customerID = appointmentCustomerIDInput.getValue().getId();

        System.out.println("START DATE TIME: " + startDateTime);

        ZonedDateTime ZStart = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime ZEnd = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime ESTStart = ZStart.withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("EST")));
        ZonedDateTime ESTEnd = ZEnd.withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("EST")));

        if(ESTStart.getHour() < 8 || ESTStart.getHour() > 22 || ESTEnd.getHour() < 8 || ESTEnd.getHour() > 22) {
            Err.alertOk("Appointment times may NOT be outside business hours of 8AM and 10PM EST (Hour: 22)\nPlease adjust times");
        }else {

            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
            for (Appointment appointment : allAppointments) {
                LocalDateTime AStart = appointment.getStart();
                LocalDateTime AEnd = appointment.getEnd();
                LocalDateTime myStart = startDateTime;
                LocalDateTime myEnd = endDateTime;

                if (appointment.getCustomerID() == customerID){
                    if(myStart.isEqual(AStart)){overlapping = true; overlappingID = appointment.getId();}
                    if((myStart.isAfter(AStart) && (myStart.isBefore(AEnd)))){overlapping = true; overlappingID = appointment.getId();}
                    if((myEnd.isAfter(AStart)) && (myEnd.isBefore(AEnd))){overlapping = true; overlappingID = appointment.getId();}
                    if((myStart.isBefore(AStart)) && (myEnd.isAfter(AEnd))){overlapping = true; overlappingID = appointment.getId();}
                }

            }

            if (overlapping == false) {

                int rows = Query.createAppointment(title, description, location, type, startDateTime, endDateTime, userName, userID, contactID, customerID);
                if (rows == 1) {
                    String message = "Appointment has been Created! Return to main page?";
                    System.out.println(message);
                    ButtonType B = Err.alertConfirm(message);
                    if (B == ButtonType.YES) {
                        LoadPage.toDashboard(cancelNewAppointmentBTN);
                    }
                } else {
                    Err.alertOk("Something went wrong.");
                }
            }else{
                Err.alertOk("This appointment overlaps with another appointment. Overlapping appointmentID = " + overlappingID);
            }
        }
    }

    public void cancelNewAppointment(ActionEvent actionEvent) {
        ButtonType B = Err.alertConfirm("All input will be discarded. Return to main page?");
        if (B == ButtonType.YES) { LoadPage.toDashboard(cancelNewAppointmentBTN); }
    }

    //Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
}
