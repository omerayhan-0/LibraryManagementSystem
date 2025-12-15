package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookSearchController {

    @FXML private TextField searchField;
    @FXML private TableView<Book> tableBooks;

    @FXML private TableColumn<Book, Integer> colId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, String> colCategory;
    @FXML private TableColumn<Book, Integer> colQuantity;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadAllBooks();

        // Arama çubuğu dinleme
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooks(newValue);
        });
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    // Tüm kitapları yükle
    private void loadAllBooks() {
        bookList.clear();

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM books";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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

            tableBooks.setItems(bookList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Arama fonksiyonu
    private void searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            tableBooks.setItems(bookList);
            return;
        }

        ObservableList<Book> filtered = FXCollections.observableArrayList();

        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                filtered.add(book);
            }
        }

        tableBooks.setItems(filtered);
    }


}