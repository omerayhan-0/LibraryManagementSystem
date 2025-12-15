package controller;

import dao.BorrowDAO; // 1. DAO'yu import et
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Member;
import model.MemberBorrowItem;

public class MemberDetailController {

    // ====== ÜYE BİLGİLERİ ======
    @FXML private Label lblName;
    @FXML private Label lblEmail;
    @FXML private Label lblTc;

    // ====== TABLO ======
    @FXML private TableView<MemberBorrowItem> tableActiveBorrow;
    @FXML private TableColumn<MemberBorrowItem, Integer> colBorrowId;
    @FXML private TableColumn<MemberBorrowItem, String> colTitle;
    @FXML private TableColumn<MemberBorrowItem, String> colBorrowDate;
    @FXML private TableColumn<MemberBorrowItem, String> colDueDate;
    @FXML private TableColumn<MemberBorrowItem, Integer> colLateDays;
    @FXML private TableColumn<MemberBorrowItem, Double> colLateFee;

    private final ObservableList<MemberBorrowItem> borrowList =
            FXCollections.observableArrayList();

    private int memberId;

    // 2. DAO Nesnesini oluştur
    private final BorrowDAO borrowDAO = new BorrowDAO();

    // ======================================================
    // ÜYE SET
    // ======================================================
    public void setMember(Member member) {
        this.memberId = member.getId();

        lblName.setText("Üye: " + member.getName());
        lblEmail.setText("Email: " + member.getEmail());
        lblTc.setText("TC: " + member.getTc());

        setupTable();
        loadActiveBorrow();
    }

    // ======================================================
    // TABLO AYARLARI
    // ======================================================
    private void setupTable() {
        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colLateDays.setCellValueFactory(new PropertyValueFactory<>("lateDays"));
        colLateFee.setCellValueFactory(new PropertyValueFactory<>("lateFee"));

        tableActiveBorrow.setItems(borrowList);
    }

    // ======================================================
    // AKTİF ÖDÜNÇLER (DÜZELTİLEN KISIM)
    // ======================================================
    private void loadActiveBorrow() {
        borrowList.clear();

        // 3. SQL YAZMAK YERİNE DAO ÇAĞIRIYORUZ
        // Bu metot zaten içinde doğru SQL'i barındırıyor ve hata vermez.
        borrowList.addAll(borrowDAO.getActiveBorrows(this.memberId));
    }

    // ======================================================
    // İADE
    // ======================================================
    @FXML
    private void handleReturn() {
        MemberBorrowItem selected =
                tableActiveBorrow.getSelectionModel().getSelectedItem();

        if (selected == null) {
            alert("Lütfen iade edilecek kitabı seçin");
            return;
        }

        // 4. İade işlemi için de DAO kullan
        borrowDAO.returnBook(selected.getBorrowId());

        alert("Kitap başarıyla iade edildi");
        loadActiveBorrow();
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}