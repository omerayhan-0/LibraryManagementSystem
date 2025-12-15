package state;

import model.Book;

public class ReservedState implements BookState {

    @Override
    public void borrow(Book book) {
        System.out.println("❌ Kitap rezerve edilmiş.");
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("❌ Rezerve kitap iade edilemez.");
    }

    @Override
    public void reserve(Book book) {
        System.out.println("❌ Kitap zaten rezerve.");
    }
}
