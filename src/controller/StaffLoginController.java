package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.StaffSession;

import java.sql.*;

public class StaffLoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void handleStaffLogin() {

        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("Boş alan bırakmayın");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            // 🔥 ALIAS KULLAN (EN GÜVENLİSİ)
            String sql = """
            SELECT staff_id AS staff_id, full_name, role
            FROM staff
            WHERE (username=? OR email=?) AND password=?
        """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, user);
            ps.setString(3, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int staffId = rs.getInt("staff_id");
                String fullName = rs.getString("full_name");
                String role = rs.getString("role");

                // ✅ EN KRİTİK SATIR
                StaffSession.setStaff(staffId, fullName, role);


                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("/view/StaffDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                statusLabel.setText("Bilgiler yanlış");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Veritabanı hatası");
        }
    }

    @FXML
    private void goBack() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SelectLoginView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
