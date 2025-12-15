package controller;

import factory.UserFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {

        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            statusLabel.setText("Lütfen tüm alanları doldurun!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            /* =======================
               1️⃣ PERSONEL KONTROLÜ
               ======================= */
            String staffSQL = "SELECT * FROM staff WHERE (username=? OR email=?) AND password=?";
            PreparedStatement staffStmt = conn.prepareStatement(staffSQL);
            staffStmt.setString(1, usernameInput);
            staffStmt.setString(2, usernameInput);
            staffStmt.setString(3, passwordInput);

            ResultSet staffResult = staffStmt.executeQuery();

            if (staffResult.next()) {

                int staffId = staffResult.getInt("staff_id");
                String fullName = staffResult.getString("full_name");
                String role = staffResult.getString("role");

                // ✅ FACTORY → STAFF NESNESİ
                AbstractUser user = UserFactory.createUser(
                        "STAFF",
                        staffId,
                        fullName,
                        null,
                        null,
                        role
                );

                // ✅ SESSION
                StaffSession.setStaff(
                        user.getId(),
                        user.getName(),
                        user.getRole()
                );

                statusLabel.setText("Personel girişi başarılı!");
                statusLabel.setStyle("-fx-text-fill: green;");

                // ✅ STAFF DASHBOARD
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

                return; // ❗ Personel girişinden sonra devam etme
            }

            /* =======================
               2️⃣ ÜYE KONTROLÜ
               ======================= */
            String userSQL = "SELECT * FROM users WHERE (username=? OR email=? OR tc=?) AND password=?";
            PreparedStatement userStmt = conn.prepareStatement(userSQL);

            userStmt.setString(1, usernameInput);
            userStmt.setString(2, usernameInput);
            userStmt.setString(3, usernameInput);
            userStmt.setString(4, passwordInput);

            ResultSet result = userStmt.executeQuery();

            if (result.next()) {

                int userId = result.getInt("id");
                String username = result.getString("username");
                String email = result.getString("email");
                String tc = result.getString("tc");

                // ✅ FACTORY → MEMBER NESNESİ
                AbstractUser user = UserFactory.createUser(
                        "MEMBER",
                        userId,
                        username,
                        email,
                        tc,
                        null
                );

                // ✅ SESSION
                UserSession.setUser(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        ((Member) user).getTc()
                );

                MemberSession.setMemberId(user.getId());

                statusLabel.setText("Üye girişi başarılı!");
                statusLabel.setStyle("-fx-text-fill: green;");

                // ✅ MEMBER DASHBOARD
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
                Parent root = loader.load();

                DashboardController controller = loader.getController();
                controller.setUserData(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        ((Member) user).getTc()
                );

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                statusLabel.setText("Giriş bilgileri yanlış!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Sunucu hatası!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
