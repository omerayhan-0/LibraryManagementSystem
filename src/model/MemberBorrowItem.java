package model;

public class MemberBorrowItem {

    private int borrowId;
    private int bookId;
    private String title;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private String status;
    private int lateDays;
    private double lateFee;

    public MemberBorrowItem(
            int borrowId,
            int bookId,
            String title,
            String borrowDate,
            String dueDate,
            String returnDate,
            String status,
            int lateDays,
            double lateFee
    ) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.title = title;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.lateDays = lateDays;
        this.lateFee = lateFee;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    public int getLateDays() {
        return lateDays;
    }

    public double getLateFee() {
        return lateFee;
    }
}
