package state;

import model.Book;

public class AvaibleState implements BookState {

    @Override
    public void borrow(Book book) {
        System.out.println("📚 Kitap ödünç verildi.");
        book.setState(new BorrowedState());
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("❌ Kitap zaten kütüphanede.");
    }

    @Override
    public void reserve(Book book) {
        System.out.println("📌 Kitap rezerve edildi.");
        book.setState(new ReservedState());
    }
}
