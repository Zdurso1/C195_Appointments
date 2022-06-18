package helper;

import controller.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Helper function to load another page -- sends user to another page
 * -- Takes in a button on the current page as a parameter
 */
public abstract class LoadPage {

    /**
     * To Dashboard Method -- Sends user back to the main dashboard
     * Only takes in a button from the current page
     * @param btn Button from current page -- used to retrieve the current stage
     */
    public static void toDashboard(Button btn) {
        try {
            Parent root = FXMLLoader.load(MainPageController.class.getResource("/view/Dashboard.fxml"));
            Stage stage = (Stage) (btn).getScene().getWindow();
            Scene scene = new Scene(root, 950, 600);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            e.getMessage();

        }
    }


    /**
     * To Other Method -- Sends user to any page
     * Takes in a button from current page to retrieve current scene
     * Takes in a string for the name of the new page -- This acts both as the title and the path to the new page's file
     * @param btn Button
     * @param pathString Path String
     */
    public static void toOther(Button btn, String pathString) {
        String path = "/view/"+pathString+".fxml";

        try {
            Parent root = FXMLLoader.load(MainPageController.class.getResource(path));
            Stage stage = (Stage) (btn).getScene().getWindow();
            Scene scene = new Scene(root, 950, 600);
            stage.setTitle(pathString);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            e.getMessage();

        }
    }
}
