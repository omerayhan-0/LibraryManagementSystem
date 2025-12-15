package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import model.DatabaseConnection;
import model.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserEditController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField tcField;
    @FXML private Label statusLabel;

    private DashboardController dashboardController;
    private int userId;

    public void setUserData(int id, String username, String email, String tc, DashboardController controller) {
        userId = id;
        usernameField.setText(username);
        emailField.setText(email);
        tcField.setText(tc);
        this.dashboardController = controller;
    }

    @FXML
    private void handleSave() {

        String newName = usernameField.getText();
        String newEmail = emailField.getText();
        String newTc = tcField.getText();

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            String sql = "UPDATE users SET username = ?, email = ?, tc = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, newEmail);
            ps.setString(3, newTc);
            ps.setInt(4, userId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                statusLabel.setText("Başarıyla güncellendi!");

                // Dashboard ekranını yenile
                dashboardController.setUserData(userId, newName, newEmail, newTc);

                // Popup kapat
                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.close();
            }



        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Hata oluştu!");
        }
        UserSession.setUser(userId, newName, newEmail, newTc);

    }
}
