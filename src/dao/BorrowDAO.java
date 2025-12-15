package dao;

import model.DatabaseConnection;
import model.MemberBorrowItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    // 1️⃣ ÖDÜNÇ VER
    public void insertBorrow(int memberId, int bookId, int staffId, LocalDate dueDate) {
        String sql = """
            INSERT INTO borrow (member_id, book_id, staff_id, borrow_date, return_date, status)
            VALUES (?, ?, ?, ?, ?, 'BORROWED')
        """;
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setInt(2, bookId);
            ps.setInt(3, staffId);
            ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            ps.setDate(5, java.sql.Date.valueOf(dueDate));
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Ödünç verme başarısız!", e);
        }
    }

    // 2️⃣ İADE ET
    public void returnBook(int borrowId) {
        String sql = "UPDATE borrow SET status = 'RETURNED' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, borrowId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("İade işlemi başarısız!", e);
        }
    }

    // 3️⃣ ÜYENİN TÜM GEÇMİŞİ (HEM AKTİF HEM İADE) - HATA BURADAYDI, DÜZELTİLDİ
    public List<MemberBorrowItem> getBorrowsByMember(int memberId) {
        List<MemberBorrowItem> list = new ArrayList<>();

        // DÜZELTME: 'b.return_date AS due_date' eklendi.
        String sql = """
            SELECT 
                b.id,
                b.book_id,
                bk.title,
                b.borrow_date,
                b.return_date AS due_date, 
                b.status
            FROM borrow b
            JOIN books bk ON bk.id = b.book_id
            WHERE b.member_id = ?
            ORDER BY b.borrow_date DESC
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MemberBorrowItem(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("due_date"), // Artık 'due_date' okunabilir
                        null,
                        rs.getString("status"),
                        0,
                        0.0
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // 4️⃣ GECİKMELİ ÖDÜNÇLER
    public List<MemberBorrowItem> getLateBorrows(int memberId) {
        List<MemberBorrowItem> list = new ArrayList<>();
        String sql = """
            SELECT 
                b.id,
                b.book_id,
                bk.title,
                b.borrow_date,
                b.return_date AS due_date,
                DATEDIFF(CURDATE(), b.return_date) AS late_days
            FROM borrow b
            JOIN books bk ON bk.id = b.book_id
            WHERE b.member_id = ?
              AND b.status = 'BORROWED'
              AND CURDATE() > b.return_date
            ORDER BY b.borrow_date DESC
        """;
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int lateDays = rs.getInt("late_days");
                double lateFee = lateDays * 5.0;
                list.add(new MemberBorrowItem(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("due_date"),
                        null,
                        "LATE",
                        lateDays,
                        lateFee
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // 5️⃣ ÜYENİN AKTİF ÖDÜNÇLERİ (Controller için)
    public List<MemberBorrowItem> getActiveBorrows(int memberId) {
        List<MemberBorrowItem> list = new ArrayList<>();
        String sql = """
            SELECT 
                b.id,
                b.book_id,
                bk.title,
                b.borrow_date,
                b.return_date AS due_date,
                b.status,
                IF(CURDATE() > b.return_date, DATEDIFF(CURDATE(), b.return_date), 0) AS late_days
            FROM borrow b
            JOIN books bk ON bk.id = b.book_id
            WHERE b.member_id = ?
              AND b.status = 'BORROWED'
            ORDER BY b.borrow_date DESC
        """;
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int lateDays = rs.getInt("late_days");
                double lateFee = lateDays * 5.0;
                list.add(new MemberBorrowItem(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("borrow_date"),
                        rs.getString("due_date"),
                        null,
                        rs.getString("status"),
                        lateDays,
                        lateFee
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Aktif ödünçler getirilemedi!", e);
        }
        return list;
    }
}