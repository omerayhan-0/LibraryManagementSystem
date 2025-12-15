package dao;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StaffDAO {

    // Metoda 'fullName' parametresi eklendi
    public void addStaff(String username, String email, String password, String fullName) {

        // SQL Güncellendi: full_name ve role eklendi
        // 'role' kısmına varsayılan olarak 'personel' yazıyoruz.
        String sql = "INSERT INTO staff (username, email, password, full_name, role) VALUES (?, ?, ?, ?, 'personel')";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, fullName); // Ad Soyad eklendi

            ps.executeUpdate();

        } catch (Exception e) {
            // Gerçek hatayı konsola yazdıralım ki görelim
            e.printStackTrace();
            throw new RuntimeException("Kayıt hatası: " + e.getMessage());
        }
    }
}