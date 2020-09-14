package controller;

import business.BOFactory;
import business.BOType;
import business.custom.ReturnBO;
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
import util.MembersTM;
import util.ReturnTM;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReturnFormController {
    public AnchorPane root;
    public ComboBox<MembersTM> cmbMembers;
    public TextField txtMemberName;
    public TextField txtReturnDate;
    public TableView<ReturnTM> tblReturn;
    public Button btnReturn;
    public Button btnBack;
    public Button btnSelectAll;
    public ArrayList<ReturnTM> returningBooks = new ArrayList<>();
    public ArrayList<String> memberIds = new ArrayList<>();
    public LocalDate today;
    public TextField txtFee;

    private static ReturnBO returnBO = BOFactory.getInstance().getBO(BOType.RETURN);

    @SuppressWarnings("Duplicates")
    public void initialize() throws Exception {
        //basic initializations
        today = LocalDate.now();
        txtReturnDate.setText(String.valueOf(today));
        loadMembers();
        loadReturnTable("");

        //map columns
        tblReturn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        tblReturn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblReturn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("title"));

        //when the combo box is selected
        cmbMembers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MembersTM>() {
            @Override
            public void changed(ObservableValue<? extends MembersTM> observable, MembersTM oldValue, MembersTM selectedMember) {
                if (selectedMember==null) {
                    return;
                }
                txtMemberName.setText(selectedMember.getMemberName());
                int i;

                ObservableList<ReturnTM> tableItems = tblReturn.getItems();
                tableItems.clear();

                for(ReturnTM book:returningBooks){

                    i = returningBooks.indexOf(book);

                    if(memberIds.get(i).equals(selectedMember.getMemberId())){
                        tableItems.add(book);
                        System.out.println("loaded to the table");
                    }
                }
            }
        });
    }
    public void btnReturn_OnAction(ActionEvent actionEvent) throws Exception {
        ObservableList<ReturnTM> returnBooks = tblReturn.getItems();
        ReturnTM selectedBook = tblReturn.getSelectionModel().getSelectedItem();

        String borrowId = selectedBook.getBorrowId();
        String date = txtReturnDate.getText();

        try {
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Return` VALUES(?,?)");
            pst.setObject(1,borrowId);
            pst.setObject(2,date);
            int affectedRows = pst.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.INFORMATION,"Book has been returned successfully",ButtonType.OK).show();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Return Unsuccessful",ButtonType.OK);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadMembers();
        calculateFee();
        returnBooks.clear();
        txtMemberName.clear();
        cmbMembers.getSelectionModel().clearSelection();
    }
    @SuppressWarnings("Duplicates")
    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainForm2.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage)this.root.getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.centerOnScreen();
    }
    public void btnSelectAll_OnAction(ActionEvent actionEvent) {
        tblReturn.getSelectionModel().selectAll();
        System.out.println("All is selected");
    }
    @SuppressWarnings("Duplicates")
    public void loadMembers() throws Exception {
        ObservableList<MembersTM> members = cmbMembers.getItems();
        members.clear();
        List<MembersTM> allMembersWithBorrowedAndNotReturnedBooks = returnBO.getAllMembersWithBorrowedAndNotReturnedBooks();
        ObservableList<MembersTM> membersTMS = FXCollections.observableArrayList(allMembersWithBorrowedAndNotReturnedBooks);
        cmbMembers.setItems(membersTMS);
    }
    @SuppressWarnings("Duplicates")
    public void loadReturnTable(String id) throws Exception {
//        try {
//            Statement stm = DBConnection.getInstance().getConnection().createStatement();
//            ResultSet rst = stm.executeQuery("SELECT B.isbn,B.title,BO.borrow_id,BO.m_id FROM Book B" +
//                    " INNER JOIN Borrow BO on B.isbn = BO.isbn where BO.borrow_id not in (select borrow_id from `return`)\n");

            List<ReturnTM> allReturnableBooksOFMember = returnBO.getAllReturnableBooksOFMember(id);
            List<String> allIds = returnBO.getAllIds();
            for (ReturnTM item : allReturnableBooksOFMember) {
                returningBooks.add(new ReturnTM(item.getIsbn(),item.getTitle(),item.getBorrowId()));
            }
            for (String item : allIds) {
                memberIds.add(item);
            }
//
//            while(rst.next()){
//                String isbn = rst.getString(1);
//                String title = rst.getString(2);
//                String borrowId = rst.getString(3);
//
//                String memberId = rst.getString(4);
//
//                memberIds.add(memberId);
//                returningBooks.add(new ReturnTM(borrowId,isbn,title));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void calculateFee(){
        ReturnTM selectedBook = tblReturn.getSelectionModel().getSelectedItem();
        String borrowId = selectedBook.getBorrowId();
        String borrowDate="";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT B.date FROM Borrow B WHERE B.borrow_id='"+borrowId+"'");

            while(rst.next()){
                borrowDate= rst.getString(1);
            }
            String returnDate = txtReturnDate.getText();

            Date date1 = new SimpleDateFormat("dd-mm-yyyy").parse(borrowDate);
            Date date2 = new SimpleDateFormat("dd-mm-yyyy").parse(returnDate);

            int days = (int) (date2.getDate() - date1.getTime());

            if(days>9){
                double fee=0;
                int difference = days-14;
                fee = difference*10;
                txtFee.setText(String.valueOf(fee));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
