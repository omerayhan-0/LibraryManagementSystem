package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MemberRegisterController {

    @FXML private TextField fullNameField;   // username olarak kayıt olacak
    @FXML private TextField emailField;
    @FXML private TextField tcField;         // TC KİMLİK NUMARASI
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleRegister() {

        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String tc = tcField.getText();
        String password = passwordField.getText();

        // Boş alan kontrolü
        if (fullName.isEmpty() || email.isEmpty() || tc.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Lütfen tüm alanları doldurun!");
            return;
        }

        try {
            // Singleton MySQL bağlantısı
            Connection conn = DatabaseConnection.getInstance().getConnection();

            // Tablo yapına uygun INSERT sorgusu
            String sql = "INSERT INTO users (username, email, tc, password) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, fullName);   // username
            ps.setString(2, email);      // email
            ps.setString(3, tc);         // tc
            ps.setString(4, password);   // password

            // Sorgu çalıştır
            ps.executeUpdate();

            messageLabel.setText("Üye kaydı başarıyla oluşturuldu!");

            // Alanları temizleme
            fullNameField.clear();
            emailField.clear();
            tcField.clear();
            passwordField.clear();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Kayıt oluşturulamadı!");
        }
    }
}
