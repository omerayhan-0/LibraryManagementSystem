package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.DatabaseConnection;
import observer.InventorySubject;


public class AddBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField categoryField;
    @FXML private Label messageLabel;
    @FXML private TextField quantityField;

    @FXML
    private void handleSave() {
        String title = titleField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();
        String quantityText = quantityField.getText();

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || quantityText.isEmpty()) {
            messageLabel.setText("Tüm alanları doldurun");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) {
                messageLabel.setText("Adet negatif olamaz!");
                return;
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Adet sayısal bir değer olmalıdır!");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            String sql = "INSERT INTO books (title, author, category, quantity, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, quantity);
            ps.setString(5, "AVAILABLE");  // veya "ACTIVE"

            ps.executeUpdate();

            // 🔔 BİLDİRİMİ TETİKLE (İŞTE ARADIĞIN YER BURASI)
            InventorySubject.getInstance()
                    .notifyObservers("📘 Yeni kitap eklendi: " + title);

            messageLabel.setText("Kitap başarıyla eklendi! 📚");
            titleField.clear();
            authorField.clear();
            categoryField.clear();
            quantityField.clear();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Hata oluştu ❌");
        }
    }

}
