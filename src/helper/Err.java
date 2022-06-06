package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Helper functions to display error messages either confirm or just alert
 */
public abstract class Err {

    /**
     * Alert the user to an error. Pass in a string message to be displayed to the user.
     * @param message The message to be displayed in the alert.
     */
    public static void alertOk (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Confirm an action with the user.
     * Returns user response as: ButtonType [text=No, buttonData=NO] || ButtonType [text=Yes, buttonData=YES]
     * @param message The message to be displayed to the user.
     * @return ButtonType = user's response.
     */
    public static ButtonType alertConfirm (String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        ButtonType result = alert.getResult();
        return result;
    }

}
