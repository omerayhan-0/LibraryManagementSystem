package service;

import dao.BookDAO;
import model.Book;
import observer.InventorySubject;
import observer.NotificationObserver;

import java.util.List;

public class BookService {

    private final BookDAO bookDAO = new BookDAO();
    private final InventorySubject inventorySubject =
            InventorySubject.getInstance();

    public BookService() {
        inventorySubject.addObserver(new NotificationObserver("Personel"));
        inventorySubject.addObserver(new NotificationObserver("Üyeler"));
    }

    // 🔴 BU METOT YOKSA HATA ALIRSIN
    public List<Book> getAvailableBooks() {
        return bookDAO.getAvailableBooks();
    }

    public void addBook(Book book) {
        bookDAO.addBook(book);
        inventorySubject.bookUpdated(book.getTitle());
    }
}
