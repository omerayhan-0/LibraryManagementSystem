package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.MemberSession;
import model.StaffSession;
import service.BorrowService;

import java.time.LocalDate;

public class BorrowConfirmController {

    @FXML private Label bookTitleLabel;
    @FXML private DatePicker dueDatePicker;

    private int bookId;
    private String bookTitle;

    private final BorrowService borrowService = new BorrowService();

    // 🔹 Önceki ekrandan kitap bilgisi gelir
    public void setBook(int bookId, String title) {
        this.bookId = bookId;
        this.bookTitle = title;
        bookTitleLabel.setText(title);
    }

    @FXML
    private void confirmBorrow() {

        LocalDate dueDate = dueDatePicker.getValue();

        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            System.out.println("Geçerli bir iade tarihi seçin!");
            return;
        }

        int userId = MemberSession.getMemberId();   // ✅ SESSION
        int staffId = StaffSession.getStaffId();    // ✅ SESSION

        borrowService.borrowBook(
                userId,
                bookId,
                staffId,
                dueDate,
                bookTitle
        );

        closeWindow();
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) dueDatePicker.getScene().getWindow();
        stage.close();
    }
}
