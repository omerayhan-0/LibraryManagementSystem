package service;

import dao.BorrowDAO;
import observer.InventorySubject;

import java.time.LocalDate;

public class BorrowService {

    private final BorrowDAO borrowDAO = new BorrowDAO();
    private final InventorySubject inventorySubject =
            InventorySubject.getInstance();

    // ==================================================
    // 📌 ÖDÜNÇ VER
    // ==================================================
    public void borrowBook(int memberId,
                           int bookId,
                           int staffId,
                           LocalDate dueDate,
                           String bookTitle) {

        if (memberId <= 0)
            throw new RuntimeException("Üye bilgisi geçersiz!");

        if (bookId <= 0)
            throw new RuntimeException("Kitap seçilmedi!");

        if (staffId <= 0)
            throw new RuntimeException("Personel oturumu bulunamadı!");

        if (dueDate == null || dueDate.isBefore(LocalDate.now()))
            throw new RuntimeException("Geçerli bir teslim tarihi seçmelisiniz!");

        borrowDAO.insertBorrow(memberId, bookId, staffId, dueDate);

        // 🔔 OBSERVER TETİKLENİR
        InventorySubject.getInstance()
                .bookBorrowed(bookTitle);
    }

    // ==================================================
    // 📌 İADE ET
    // ==================================================
    public void returnBook(int borrowId, String bookTitle) {

        if (borrowId <= 0)
            throw new RuntimeException("Geçersiz ödünç kaydı!");

        borrowDAO.returnBook(borrowId);

        // 🔔 OBSERVER TETİKLENİR
        inventorySubject.bookUpdated(bookTitle);
    }
}
