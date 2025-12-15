package controller;

import dao.BorrowDAO; // 1. DAO'yu import et
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MemberBorrowItem;
import model.MemberSession;
import observer.InventorySubject;

public class MemberBorrowListController {

    @FXML private TableView<MemberBorrowItem> tableBorrowed;
    @FXML private TableColumn<MemberBorrowItem, String> colTitle;
    @FXML private TableColumn<MemberBorrowItem, String> colBorrowDate;
    @FXML private TableColumn<MemberBorrowItem, String> colReturnDate;
    @FXML private TableColumn<MemberBorrowItem, String> colStatus;

    private final ObservableList<MemberBorrowItem> borrowList = FXCollections.observableArrayList();

    // DAO nesnesini burada oluşturuyoruz
    private final BorrowDAO borrowDAO = new BorrowDAO();

    @FXML
    private void initialize() {
        // Tablo Sütun Eşleştirmeleri
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        // Modelde 'dueDate' (Teslim Tarihi) alanını kullanıyoruz
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableBorrowed.setItems(borrowList);

        // Verileri yükle
        loadBorrowedBooks();
    }

    private void loadBorrowedBooks() {
        // Listeyi temizle
        borrowList.clear();

        // Oturum açmış üyenin ID'sini al
        int memberId = MemberSession.getMemberId();

        // ==========================================================
        // 🔥 BAKIN KOD NE KADAR KISALDI 🔥
        // SQL yazmak yerine sadece DAO'ya "Getir" diyoruz.
        // Hata yönetimi ve SQL sorgusu artık BorrowDAO içinde.
        // ==========================================================
        borrowList.addAll(borrowDAO.getBorrowsByMember(memberId));
    }

    @FXML
    private void returnBook() {
        MemberBorrowItem selected = tableBorrowed.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Lütfen iade edilecek kitabı seçin!");
            return;
        }

        // İade işlemi için de DAO kullanıyoruz
        borrowDAO.returnBook(selected.getBorrowId());
        InventorySubject.getInstance().bookReturned(selected.getTitle());
        System.out.println("Kitap iade edildi.");

        // Listeyi yenile ki tablo güncellensin
        loadBorrowedBooks();
    }
}