package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SelectLoginController {

    @FXML
    private void openMemberLogin() throws Exception {
        load("/view/LoginView.fxml");
    }

    @FXML
    private void openStaffLogin() throws Exception {
        load("/view/StaffLoginView.fxml");
    }

    private void load(String fxml) throws Exception {
        Stage stage = (Stage) javafx.stage.Window.getWindows()
                .stream().filter(w -> w.isShowing()).findFirst().get();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }
}
