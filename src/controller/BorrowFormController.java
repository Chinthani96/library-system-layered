package controller;

import business.BOFactory;
import business.BOType;
import business.custom.BookBO;
import business.custom.BorrowBO;
import business.custom.MemberBO;
import db.DBConnection;
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
import util.BooksTM;
import util.BorrowDetails;
import util.BorrowTM;
import util.MembersTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowFormController {
    private static BorrowBO borrowBO = BOFactory.getInstance().getBO(BOType.BORROW);
    private static MemberBO memberBO = BOFactory.getInstance().getBO(BOType.MEMBER);
    private static BookBO bookBO = BOFactory.getInstance().getBO(BOType.BOOK);
    public TextField txtBorrowId;
    public TextField txtDate;
    public ComboBox<MembersTM> cmbMemberIds;
    public TextField txtName;
    public TextField txtTitle;
    public ComboBox<BooksTM> cmbISBNs;
    public TableView<BorrowTM> tblBorrowDetails;
    public Button btnAdd;
    public Button btnSave;
    public Button btnBack;
    public AnchorPane root;
    public TextField txtAuthor;

    public void initialize() throws Exception {
        //basic initializations
        btnSave.setDisable(true);

        //mapping columns
        tblBorrowDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        tblBorrowDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblBorrowDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBorrowDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("author"));

        loadAllMembers();
        loadAllBooks();

        LocalDate today = LocalDate.now();
        txtDate.setText(String.valueOf(today));

        cmbMemberIds.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MembersTM>() {
            @Override
            public void changed(ObservableValue<? extends MembersTM> observable, MembersTM oldValue, MembersTM selectedMember) {
                if (selectedMember == null) {
                    return;
                }
                try {
                    incrementId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                txtName.setText(selectedMember.getMemberName());
            }
        });

        cmbISBNs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BooksTM>() {
            @Override
            public void changed(ObservableValue<? extends BooksTM> observable, BooksTM oldValue, BooksTM selectedBook) {
                if (selectedBook == null) {
                    return;
                }
                txtTitle.setText(selectedBook.getTitle());
                txtAuthor.setText(selectedBook.getAuthor());
            }
        });

        tblBorrowDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BorrowTM>() {
            @Override
            public void changed(ObservableValue<? extends BorrowTM> observable, BorrowTM oldValue, BorrowTM selectedDetail) {
                if (selectedDetail == null) {
                    return;
                }
                btnAdd.setText("Update");
            }
        });
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) throws Exception {
        incrementId();
        btnSave.setDisable(false);
        ObservableList<BorrowTM> borrowDetails = tblBorrowDetails.getItems();
        BooksTM selectedBook = cmbISBNs.getSelectionModel().getSelectedItem();
        String borrowId = txtBorrowId.getText();
        String isbn = selectedBook.getISBN();
        String title = selectedBook.getTitle();
        String author = selectedBook.getAuthor();

        if (btnSave.getText().equals("Update")) {
            int selectedIndex = tblBorrowDetails.getSelectionModel().getSelectedIndex();
            borrowDetails.set(selectedIndex, new BorrowTM(borrowId, isbn, title, author));
        } else {
            borrowDetails.add(new BorrowTM(borrowId, isbn, title, author));
        }
    }

    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) throws Exception {
        ObservableList<BorrowTM> BorrowDetails = tblBorrowDetails.getItems();
        ObservableList<BooksTM> books = cmbISBNs.getItems();
        for (BorrowTM borrowDetail : BorrowDetails) {
            String borrowId = borrowDetail.getBorrowId();
            String isbn = borrowDetail.getIsbn();
            String memberId = cmbMemberIds.getSelectionModel().getSelectedItem().getMemberId();
            String date = txtDate.getText();

            boolean result = borrowBO.saveBorrowDetail(borrowId,memberId,isbn, date);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Entry added Successfully.", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error adding entry to the database", ButtonType.OK).show();
            }
        }
    }

    private void loadAllMembers() throws Exception {
        ObservableList<MembersTM> members = cmbMemberIds.getItems();
        members.clear();
        List<MembersTM> allMembers = memberBO.getAllMembers();
        ObservableList<MembersTM> membersTMS = FXCollections.observableArrayList(allMembers);
        cmbMemberIds.setItems(membersTMS);
    }

    @SuppressWarnings("Duplicates")
    private void loadAllBooks() throws Exception {
        ObservableList<BooksTM> books = cmbISBNs.getItems();
        books.clear();
        List<BooksTM> allBooks = bookBO.getAllBooks();
        ObservableList<BooksTM> booksTMS = FXCollections.observableArrayList(allBooks);
        cmbISBNs.setItems(booksTMS);
    }

    private void incrementId() throws Exception {
        String newID = borrowBO.generateNewBorrowId();
        txtBorrowId.setText(newID);
    }

    @SuppressWarnings("Duplicates")
    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainForm2.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage) this.root.getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.centerOnScreen();
    }
}


