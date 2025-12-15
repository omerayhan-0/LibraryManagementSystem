package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import observer.InventorySubject;
import observer.NotificationObserver;
import model.StaffSession;

public class StaffDashboardController {

    @FXML private AnchorPane contentArea;

    @FXML
    public void initialize() {
        // Bu kullanıcıya bildirim gönder
        InventorySubject.getInstance()
                .addObserver(new NotificationObserver("staff"));
    }

    // ==========================================
    // ÇIKIŞ YAP
    // ==========================================
    @FXML
    private void handleLogout(ActionEvent event) {
        /*StaffSession.clear();*/
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close(); // Login ekranına dönebilirsin veya kapatabilirsin
    }

    // ==========================================
    // KİTAP EKLEME PENCERESİ
    // ==========================================
    @FXML
    private void openAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddBookView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kitap Ekle");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // KİTAP YÖNETİMİ
    // ==========================================
    @FXML
    private void openBookManage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookManageView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kitap Yönetimi");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // ÖDÜNÇ LİSTESİ (Panele Gömülü)
    // ==========================================
    @FXML
    private void openBorrowList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffBorrowListView.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // ÜYE KAYDI PENCERESİ
    // ==========================================
    @FXML
    private void handleMemberRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberRegisterView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Üye Kaydı");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // ÜYE LİSTESİ PENCERESİ
    // ==========================================
    @FXML
    private void handleMemberList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberListView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Üye Listesi");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // 🔥 YENİ EKLENEN: PERSONEL EKLEME
    // ==========================================
    @FXML
    private void handleAddStaff() {
        try {
            // Dosya isminin StaffAddView.fxml olduğundan emin ol
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffAddView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Personel Ekle");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}