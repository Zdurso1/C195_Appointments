package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Contact;

public class AddAppointmentController {
    public TextField appointmentIDInput;
    public TextField appointmentTitleInput;
    public TextField appointmentDescriptionInput;
    public TextField appointmentLocationInput;
    public ComboBox<Contact> appointmentContactInput;
    public TextField appointmentTypeInput;
    public DatePicker appointmentStartDateInput;
    public DatePicker appointmentEndDateInput;
    public TextField appointmentStartTimeInput;
    public TextField appointmentEndTimeInput;
    public TextField appointmentCustomerIDInput;
    public TextField appointmentUserIDInput;
    public Button saveNewAppointmentBTN;
    public Button cancelNewAppointmentBTN;

    public void saveNewAppointment(ActionEvent actionEvent) {
    }

    public void cancelNewAppointment(ActionEvent actionEvent) {
    }
    //Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
}
