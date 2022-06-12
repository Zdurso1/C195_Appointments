package controller;

import helper.Err;
import helper.LoadPage;
import helper.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Contact;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddAppointmentController implements Initializable {
    public TextField appointmentIDInput;
    public TextField appointmentTitleInput;
    public TextField appointmentDescriptionInput;
    public TextField appointmentLocationInput;
    public ComboBox appointmentContactInput;
    public TextField appointmentTypeInput;
    public DatePicker appointmentStartDateInput;
    public DatePicker appointmentEndDateInput;
    public ComboBox appointmentStartTimeInput;
    public ComboBox appointmentEndTimeInput;
    public TextField appointmentCustomerIDInput;
    public TextField appointmentUserIDInput;
    public Button saveNewAppointmentBTN;
    public Button cancelNewAppointmentBTN;


    ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    TimeZone userTimeZone = TimeZone.getDefault();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // contact combo boxes
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        allContacts = Query.getAllContacts();

        for (Contact C : allContacts) {
            contactNames.add(C.getContactName());
            appointmentContactInput.getItems().add(contactNames.get(contactNames.size()-1));
        }


        // time combo boxes
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                String withZero = String.format("%02d", i);
                for (int M = 0; M <= 59; M++) {

                    if (M < 10) {
                        String MwithZero = String.format("%02d", M);
                        appointmentStartTimeInput.getItems().add(withZero + ":" + MwithZero);
                        appointmentEndTimeInput.getItems().add(withZero + ":" + MwithZero);
                    }else {
                        appointmentStartTimeInput.getItems().add(withZero + ":" + M);
                        appointmentEndTimeInput.getItems().add(withZero + ":" + M);
                    }

                }
            }else {
                for (int M = 0; M <= 59; M++) {
                    if(M < 10) {
                        String MwithZero = String.format("%02d", M);
                        appointmentStartTimeInput.getItems().add(i + ":" + MwithZero);
                        appointmentEndTimeInput.getItems().add(i + ":" + MwithZero);
                    }else{
                        appointmentStartTimeInput.getItems().add(i + ":" + M);
                        appointmentEndTimeInput.getItems().add(i + ":" + M);

                    }
                }
            }
        }



    }

    public void saveNewAppointment(ActionEvent actionEvent) {
        // Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By
        String title = appointmentTitleInput.getText();
        String description = appointmentDescriptionInput.getText();
        String location = appointmentLocationInput.getText();
        String type = appointmentTypeInput.getText();

        LocalDate localStartDate = appointmentStartDateInput.getValue();
        Instant startInstant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
        Date startDate = (Date) Date.from(startInstant);

        LocalDate localEndDate = appointmentEndDateInput.getValue();
        Instant endInstant = Instant.from(localEndDate.atStartOfDay(ZoneId.systemDefault()));
        Date endDate = (Date) Date.from(endInstant);

        Timestamp start = Timestamp.valueOf(startDate.toString() + appointmentStartTimeInput.getValue() + ":00");
        Timestamp end = Timestamp.valueOf(endDate.toString() + appointmentEndTimeInput.getValue() + ":00");

        System.out.println("Start: " + start + "\nEnd: " + end);

        ZonedDateTime zonedStart= start.toLocalDateTime().atZone(ZoneOffset.UTC);
        ZonedDateTime zonedEnd = end.toLocalDateTime().atZone(ZoneOffset.UTC);

        Query.createAppointment(title,description,location,type,start,end);

    }

    public void cancelNewAppointment(ActionEvent actionEvent) {
        ButtonType B = Err.alertConfirm("All input will be discarded. Return to main page?");
        if (B == ButtonType.YES) { LoadPage.toDashboard(cancelNewAppointmentBTN); }
    }

    //Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
}
