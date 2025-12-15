package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Book;
import model.StaffSession;
import service.BookService;
import service.BorrowService;

import java.time.LocalDate;

public class BorrowController {

    // ===== FXML BİLEŞENLERİ =====
    @FXML
    private ComboBox<Book> bookCombo;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private Label memberLabel;

    @FXML
    private Label messageLabel;

    // ===== DIŞARIDAN GELEN ÜYE BİLGİSİ =====
    private int userId;
    private String username;

    // ===== SERVICELER =====
    private final BorrowService borrowService = new BorrowService();
    private final BookService bookService = new BookService();

    // ===== FXML YÜKLENDİĞİNDE =====
    @FXML
    public void initialize() {
        loadAvailableBooks();
    }

    // ===== ÜYE LİSTESİNDEN GELEN VERİ =====
    public void setUserData(int userId, String username) {
        this.userId = userId;
        this.username = username;
        memberLabel.setText("Üye: " + username);
    }

    // ===== KİTAPLARI COMBOBOX'A YÜKLE =====
    private void loadAvailableBooks() {
        bookCombo.getItems().setAll(
                bookService.getAvailableBooks()
        );
    }

    // ===== ÖDÜNÇ VER BUTONU =====
    @FXML
    private void handleBorrow() {

        Book selectedBook = bookCombo.getValue();
        LocalDate dueDate = dueDatePicker.getValue();

        if (selectedBook == null || dueDate == null) {
            messageLabel.setText("Kitap ve teslim tarihi seçmelisiniz!");
            return;
        }

        int staffId = StaffSession.getStaffId(); // 🔥 DÜZELTİLEN SATIR

        try {
            borrowService.borrowBook(
                    userId,
                    selectedBook.getId(),
                    staffId,
                    dueDate,
                    selectedBook.getTitle() // 🔥 EKLENDİ
            );

            messageLabel.setText("Ödünç verme başarılı ✅");

        } catch (RuntimeException e) {
            messageLabel.setText(e.getMessage());
        }
    }

}
