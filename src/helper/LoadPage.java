package helper;

import controller.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class LoadPage {

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
