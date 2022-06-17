package controller;

import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class EditAppointmentController implements Initializable {
    public TextField appointmentIDInput;
    public TextField appointmentTitleInput;
    public TextField appointmentDescriptionInput;
    public TextField appointmentLocationInput;
    public ComboBox<Contact> appointmentContactInput;
    public TextField appointmentTypeInput;
    public DatePicker appointmentStartDateInput;
    public DatePicker appointmentEndDateInput;
    public Button saveNewAppointmentBTN;
    public Button cancelNewAppointmentBTN;
    public ComboBox<LocalTime> appointmentStartTimeInput;
    public ComboBox<LocalTime> appointmentEndTimeInput;
    public ComboBox<User> appointmentUserIDInput;
    public ComboBox<Customer> appointmentCustomerIDInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment A = DashboardController.getAppointment();
        if (A == null) {A = AppointmentsController.getAppointment();}

        appointmentIDInput.setText(String.valueOf(A.getId()));
        appointmentTitleInput.setText(A.getTitle());
        appointmentDescriptionInput.setText(A.getDescription());
        appointmentLocationInput.setText(A.getLocation());

        ObservableList<Contact> allContacts = FXCollections.observableArrayList(Query.getAllContacts());
        appointmentContactInput.setItems(allContacts);

        for(Contact C : allContacts){if(C.getContactID() == A.getContactID()){appointmentContactInput.setValue(C);}}

        appointmentTypeInput.setText(A.getType());
        appointmentStartDateInput.setValue(A.getStart().toLocalDate());
        appointmentEndDateInput.setValue(A.getEnd().toLocalDate());


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

        appointmentStartTimeInput.setValue(A.getStart().toLocalTime());
        appointmentEndTimeInput.setValue(A.getEnd().toLocalTime());

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Query.getAllCustomers());
        appointmentCustomerIDInput.setItems(allCustomers);
        for(Customer C : allCustomers){if(C.getId() == A.getCustomerID()){appointmentCustomerIDInput.setValue(C);}}

        ObservableList<User> allUsers = FXCollections.observableArrayList(Query.getAllUsers());
        appointmentUserIDInput.setItems(allUsers);
        for(User U : allUsers){if(U.getId() == A.getUserID()){appointmentUserIDInput.setValue(U);}}

    }

    public void saveModAppointment(ActionEvent actionEvent) {
        Appointment A = DashboardController.getAppointment();
        if (A == null) {A = AppointmentsController.getAppointment();}


        try {
            int overlappingID = 0;
            boolean overlapping = false;
            int id = Integer.parseInt(appointmentIDInput.getText());
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

            ZonedDateTime ZStart = startDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime ZEnd = endDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime ESTStart = ZStart.withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("EST")));
            ZonedDateTime ESTEnd = ZEnd.withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("EST")));

            if(ESTStart.getHour() < 8 || ESTStart.getHour() > 22 || ESTEnd.getHour() < 8 || ESTEnd.getHour() > 22) {
                Err.alertOk("Appointment times may NOT be outside business hours of 8AM and 10PM EST (Hour: 22)\nPlease adjust times");
            } else {

                ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(Query.getAllAppointments());
                for (Appointment appointment : allAppointments) {
                    LocalDateTime AStart = appointment.getStart();
                    LocalDateTime AEnd = appointment.getEnd();
                    LocalDateTime myStart = startDateTime;
                    LocalDateTime myEnd = endDateTime;

                    if (appointment.getCustomerID() == customerID && id != appointment.getId()){
                        if(myStart.isEqual(AStart)){overlapping = true; overlappingID = appointment.getId();}
                        if((myStart.isAfter(AStart) && (myStart.isBefore(AEnd)))){overlapping = true; overlappingID = appointment.getId();}
                        if((myEnd.isAfter(AStart)) && (myEnd.isBefore(AEnd))){overlapping = true; overlappingID = appointment.getId();}
                        if((myStart.isBefore(AStart)) && (myEnd.isAfter(AEnd))){overlapping = true; overlappingID = appointment.getId();}
                    }

                }

                if (overlapping == false){
                    int rows = Query.updateAppointment(id, title, description, location, type, startDateTime, endDateTime, userName, userID, contactID, customerID);
                    if (rows == 1) {
                        ButtonType B = Err.alertConfirm("Appointment Updated Successfully! \nReturn to home page?");
                        if (B == ButtonType.YES) {
                            LoadPage.toDashboard(saveNewAppointmentBTN);
                        }
                    }
                }else{
                    Err.alertOk("This appointment overlaps with another appointment.\nOverlapping appointmentID = " + overlappingID);
                }

            }
        } catch(Exception E){
            System.out.println("Exception Cause: " + E.getCause());
            System.out.println("Exception Message: "+E.getMessage());
            E.printStackTrace();
        }


    }

    public void cancelModAppointment(ActionEvent actionEvent) {
        ButtonType B = Err.alertConfirm("All input will be discarded. Are you sure you want to go back to the home page?");
        if(B == ButtonType.YES) {
            LoadPage.toDashboard(cancelNewAppointmentBTN);
        }
    }
}
