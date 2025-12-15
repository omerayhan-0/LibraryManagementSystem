package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.DatabaseConnection;
import observer.InventorySubject;
import service.StaffBookService;

import java.sql.*;

public class BookManageController {

    private StaffBookService staffBookService = new StaffBookService();

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Integer> idCol;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private TableColumn<Book, String> categoryCol;
    @FXML private TableColumn<Book, String> statusCol;
    @FXML private TableColumn<Book, Integer> quantityCol;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        loadBooks();
    }

    @FXML
    public void loadBooks() {
        bookList.clear();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                bookList.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getInt("quantity")
                ));
            }

            bookTable.setItems(bookList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}

            // ❌ conn.close() ASLA OLMAYACAK (Singleton olduğu için)
        }
    }


    @FXML
    private void updateBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Uyarı", "Güncellenecek kitabı seçin!");
            return;
        }

        TextInputDialog titleDialog = new TextInputDialog(selected.getTitle());
        titleDialog.setHeaderText("Yeni kitap adı:");
        titleDialog.setContentText("Kitap Adı:");

        TextInputDialog authorDialog = new TextInputDialog(selected.getAuthor());
        authorDialog.setHeaderText("Yeni yazar adı:");
        authorDialog.setContentText("Yazar:");

        TextInputDialog categoryDialog = new TextInputDialog(selected.getCategory());
        categoryDialog.setHeaderText("Yeni kategori:");
        categoryDialog.setContentText("Kategori:");

        titleDialog.showAndWait().ifPresent(newTitle -> {
            authorDialog.showAndWait().ifPresent(newAuthor -> {
                categoryDialog.showAndWait().ifPresent(newCategory -> {

                    try {
                        Connection conn = DatabaseConnection.getInstance().getConnection();

                        String sql = "UPDATE books SET title=?, author=?, category=? WHERE id=?";
                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setString(1, newTitle);
                        ps.setString(2, newAuthor);
                        ps.setString(3, newCategory);
                        ps.setInt(4, selected.getId());

                        ps.executeUpdate();
                        InventorySubject.getInstance().bookUpdated(newTitle);

                        showAlert("Başarılı", "Kitap bilgileri güncellendi ✅");
                        loadBooks();


                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Hata", "Güncelleme başarısız ❌");
                    }

                });
            });
        });
    }


    // ✅ SİL
    @FXML
    private void deleteBook() {
        Book selected = bookTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Uyarı", "Silmek için kitap seçin!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Silmek istiyor musun?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement("DELETE FROM books WHERE id=?");
                ps.setInt(1, selected.getId());
                ps.executeUpdate();

                InventorySubject.getInstance().bookDeleted(selected.getTitle());

                loadBooks();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            }
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
    @FXML
    private void handleBorrow() {

        Book selectedBook = bookTable
                .getSelectionModel()
                .getSelectedItem();

        if (selectedBook == null) {
            System.out.println("❌ Kitap seçilmedi");
            return;
        }

        staffBookService.borrowBook(selectedBook);
    }
    

}
