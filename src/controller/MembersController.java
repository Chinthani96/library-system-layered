package controller;

import business.BOFactory;
import business.BOType;
import business.custom.MemberBO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MembersTM;

import java.io.IOException;
import java.util.List;

public class MembersController {
    private static MemberBO memberBO = BOFactory.getInstance().getBO(BOType.MEMBER);
    public Button btnAdd;
    public Button btnBack;
    public TextField txtMemberId;
    public TextField txtMemberName;
    public TextField txtAddress;
    public TableView<MembersTM> tblMembers;
    public Button btnDelete;
    public Button btnSave;
    public AnchorPane root;

    public void initialize() throws Exception {
        //Basic Initializations
        btnAdd.requestFocus();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        //Mapping columns
        tblMembers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblMembers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberName"));
        tblMembers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadMembers();

        tblMembers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MembersTM>() {
            @Override
            public void changed(ObservableValue<? extends MembersTM> observable, MembersTM oldValue, MembersTM selectedMember) {
                btnSave.setText("Update");
                btnDelete.setDisable(false);
                btnSave.setDisable(false);

                if (selectedMember == null) {
                    return;
                }
                txtMemberId.setText(selectedMember.getMemberId());
                txtMemberName.setText(selectedMember.getMemberName());
                txtAddress.setText(selectedMember.getAddress());
            }
        });

    }

    public void btnAdd_OnAction(ActionEvent actionEvent) throws Exception {
        btnSave.setDisable(false);
        btnAdd.setDisable(true);
        txtMemberName.requestFocus();
        String memberId = memberBO.generateNewMemberId();
        txtMemberId.setText(memberId);
    }

    @SuppressWarnings("Duplicates")
    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainForm2.fxml"));
        Scene mainscene = new Scene(root);
        Stage mainStage = (Stage) this.root.getScene().getWindow();
        mainStage.setScene(mainscene);
        mainStage.centerOnScreen();
//        mainStage.show();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) throws Exception {
        String memberId = txtMemberId.getText();
        boolean result = memberBO.deleteMember(memberId);
        if (result) {
            System.out.println("Member deleted successfully");
            loadMembers();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete from databases", ButtonType.OK).show();
        }
        btnSave.setText("Save");
        btnAdd.requestFocus();
        btnAdd.setDisable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        txtMemberId.clear();
        txtMemberName.clear();
        txtAddress.clear();
    }

    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) throws Exception {
        String memberId = txtMemberId.getText();
        String name = txtMemberName.getText();
        String address = txtAddress.getText();

        if (btnSave.getText().equals("Update")) {
            btnSave.setText("Save");
            boolean result = memberBO.updateMember(new MembersTM(memberId, name, address));
            if (result) {
                loadMembers();
                System.out.println("Added to database successfully");
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add member", ButtonType.OK).show();
            }
        } else {
            boolean result = memberBO.saveMember(new MembersTM(memberId, name, address));
            if (result) {
                loadMembers();
                System.out.println("Updated to database successfully");
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update member", ButtonType.OK).show();
            }
        }
        txtMemberId.clear();
        txtMemberName.clear();
        txtAddress.clear();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnAdd.setDisable(false);
        btnAdd.requestFocus();

    }

    private void loadMembers() throws Exception {
        ObservableList<MembersTM> members = tblMembers.getItems();
        members.clear();
        List<MembersTM> allMembers = memberBO.getAllMembers();
        ObservableList<MembersTM> membersTMS = FXCollections.observableArrayList(allMembers);
        tblMembers.setItems(membersTMS);
    }
}
