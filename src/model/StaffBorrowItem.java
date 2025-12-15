package model;

public class StaffBorrowItem {

    private int id;
    private String memberName;
    private String bookTitle;
    private String borrowDate;
    private String returnDate;
    private String status;

    public StaffBorrowItem(int id, String memberName, String bookTitle, String borrowDate, String returnDate, String status) {
        this.id = id;
        this.memberName = memberName;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getMemberName() { return memberName; }
    public String getBookTitle() { return bookTitle; }
    public String getBorrowDate() { return borrowDate; }
    public String getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
}
