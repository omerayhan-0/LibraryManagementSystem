package controller;

import dao.StaffDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStaffController {

    @FXML private TextField txtFullName; // Yeni eklenen alan
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private final StaffDAO staffDAO = new StaffDAO();

    @FXML
    private void handleSave() {
        // 1. Verileri Al
        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // 2. Boş Kontrolü (Ad Soyad dahil)
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Hata", "Lütfen tüm alanları doldurun!");
            return;
        }

        // 3. DAO ile Kaydet (fullName'i de gönderiyoruz)
        try {
            staffDAO.addStaff(username, email, password, fullName);

            showAlert("Başarılı", "Yeni personel başarıyla eklendi.");
            closeWindow();

        } catch (Exception e) {
            showAlert("Hata", "Kayıt başarısız: " + e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}