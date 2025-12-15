package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileController {

    @FXML private TextField txtName, txtEmail, txtPhone,txtTc;
    @FXML private PasswordField txtPassword;
    @FXML private Label labelStatus;

    private int userId;

    // Girişte çağrılır
    public void setUserData(int userId, String username, String email, String tc) {
        this.userId = userId;

        txtName.setText(username);
        txtEmail.setText(email);
        txtTc.setText(tc);

        loadUserData();   // ✅ BU SATIRI EKLE
    }


    private void loadUserData() {

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM users WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("username"));
                txtEmail.setText(rs.getString("email"));
                txtPhone.setText(rs.getString("tc"));
                txtPassword.setText(rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            labelStatus.setText("Veriler yüklenemedi!");
        }
    }

    @FXML
    public void handleUpdate() {

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "UPDATE users SET full_name=?, email=?, phone=?, password=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, txtPassword.getText());
            ps.setInt(5, userId);

            ps.executeUpdate();

            labelStatus.setText("✅ Bilgiler güncellendi!");

        } catch (Exception e) {
            e.printStackTrace();
            labelStatus.setText("❌ Güncelleme başarısız!");
        }
    }
}
