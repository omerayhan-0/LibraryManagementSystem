package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DatabaseConnection;
import model.StaffBorrowItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StaffBorrowListController {

    @FXML private TableView<StaffBorrowItem> borrowTable;

    @FXML private TableColumn<StaffBorrowItem, Integer> colBorrowId;
    @FXML private TableColumn<StaffBorrowItem, String> colMember;
    @FXML private TableColumn<StaffBorrowItem, String> colBook;
    @FXML private TableColumn<StaffBorrowItem, String> colBorrowDate;
    @FXML private TableColumn<StaffBorrowItem, String> colReturnDate;
    @FXML private TableColumn<StaffBorrowItem, String> colStatus;

    @FXML
    public void initialize() {
        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMember.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBook.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadBorrowList();
    }

    private void loadBorrowList() {
        borrowTable.getItems().clear();

        String sql =
                "SELECT br.id, u.username AS memberName, b.title AS bookTitle, " +
                        "br.borrow_date, br.return_date, br.status " +
                        "FROM borrow br " +
                        "JOIN users u ON br.member_id = u.id " +
                        "JOIN books b ON br.book_id = b.id " +
                        "ORDER BY br.id DESC";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                borrowTable.getItems().add(new StaffBorrowItem(
                        rs.getInt("id"),
                        rs.getString("memberName"),
                        rs.getString("bookTitle"),
                        rs.getString("borrow_date"),
                        rs.getString("return_date"),
                        rs.getString("status")
                ));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
