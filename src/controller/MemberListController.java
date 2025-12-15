package controller;

import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.Member;

public class MemberListController {

    @FXML private TableView<Member> tableMembers;
    @FXML private TableColumn<Member, Integer> colId;
    @FXML private TableColumn<Member, String> colUsername;
    @FXML private TableColumn<Member, String> colEmail;
    @FXML private TableColumn<Member, String> colTc;
    @FXML private TextField txtSearch;

    private final UserDAO userDAO = new UserDAO();
    private final ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("name")); // ✅
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTc.setCellValueFactory(new PropertyValueFactory<>("tc"));

        loadAllMembers();
    }

    private void loadAllMembers() {
        memberList.setAll(userDAO.getAllMembers());
        tableMembers.setItems(memberList);
    }

    @FXML
    private void handleSearch() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadAllMembers();
        } else {
            memberList.setAll(userDAO.searchMembers(keyword));
        }
    }

    @FXML
    private void handleBorrow() {
        Member selected = tableMembers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lütfen bir üye seçin!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BorrowView.fxml"));
            Parent root = loader.load();

            BorrowController controller = loader.getController();
            controller.setUserData(selected.getId(), selected.getName()); // ✅

            Stage stage = new Stage();
            stage.setTitle("Ödünç Ver - " + selected.getName());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTableClick(javafx.scene.input.MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Member selected = tableMembers.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openMemberDetail(selected);
            }
        }
    }

    private void openMemberDetail(Member member) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/MemberDetailView.fxml")
            );
            Parent root = loader.load();

            MemberDetailController controller = loader.getController();
            controller.setMember(member);

            Stage stage = new Stage();
            stage.setTitle("Üye Detayı - " + member.getName()); // ✅
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
