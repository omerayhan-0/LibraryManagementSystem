package state;

import model.Book;

public class BorrowedState implements BookState {

    @Override
    public void borrow(Book book) {
        System.out.println("❌ Kitap zaten ödünçte.");
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("✅ Kitap iade edildi.");
        book.setState(new AvaibleState());
    }

    @Override
    public void reserve(Book book) {
        System.out.println("❌ Ödünçteki kitap rezerve edilemez.");
    }
}
