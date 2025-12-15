package service;

import model.Book;

public class StaffBookService {

    public void borrowBook(Book book) {
        book.borrow();
    }

    public void returnBook(Book book) {
        book.returnBook();
    }

    public void reserveBook(Book book) {
        book.reserve();
    }
}
