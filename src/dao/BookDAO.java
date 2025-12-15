package dao;

import model.Book;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAvailableBooks() {

        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE quantity > 0";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public boolean isAvailable(int bookId) {
        String sql = "SELECT quantity FROM books WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity") > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void decreaseQuantity(int bookId) {
        String sql = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increaseQuantity(int bookId) {
        String sql = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addBook(Book book) {

        String sql = """
        INSERT INTO books (title, author, category, quantity,status,quantity) VALUES (?,?,?,?,?,?)
    """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.setString(5, book.getStatus());
            ps.setInt(6, book.getQuantity());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
