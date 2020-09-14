package controller;

import business.BOFactory;
import business.BOType;
import business.custom.BookBO;
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

import java.io.IOException;
import java.util.List;

public class BooksController {
    private static BookBO bookBO = BOFactory.getInstance().getBO(BOType.BOOK);
    public Button btnBack;
    public TextField txtName;
    public TextField txtISBN;
    public TextField txtAuthor;
    public TableView<BooksTM> tblBooks;
    public Button btnSave;
    public Button btnDelete;
    public AnchorPane root;
    public TextField txtEdition;

    public void initialize() throws Exception {
        //basic initializations
        btnDelete.setDisable(true);

        //mapping columns
        tblBooks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tblBooks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBooks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBooks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("edition"));

        //loading the books from the database
        loadBooks();

        //functionalities when a book from the combo box is selected.
        tblBooks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BooksTM>() {
            @Override
            public void changed(ObservableValue<? extends BooksTM> observable, BooksTM oldValue, BooksTM selectedBook) {
                if (selectedBook == null) {
                    return;
                }

                btnSave.setText("Update");
                btnSave.setDisable(false);
                btnDelete.setDisable(false);

                txtName.setText(selectedBook.getTitle());
                txtISBN.setText(selectedBook.getISBN());
                txtAuthor.setText(selectedBook.getAuthor());
                txtEdition.setText(selectedBook.getEdition());
            }
        });
    }

    @SuppressWarnings("Duplicates")
    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainForm2.fxml"));
        Scene mainscene = new Scene(root);
        Stage mainStage = (Stage) this.root.getScene().getWindow();
        mainStage.setScene(mainscene);
        mainStage.centerOnScreen();
        mainStage.show();
    }

    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) throws Exception {
        String ISBN = txtISBN.getText();
        String name = txtName.getText();
        String author = txtAuthor.getText();
        String edition = txtEdition.getText();

        if (btnSave.getText().equals("Update")) {
            btnSave.setText("Save");
//            BooksTM selectedBook = tblBooks.getSelectionModel().getSelectedItem();
            boolean result = bookBO.updateBook(new BooksTM(ISBN, name, author, edition));
            loadBooks();
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Book updated successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed!", ButtonType.OK).show();
            }

        } else {
            boolean result = bookBO.saveBook(new BooksTM(ISBN, name, author, edition));
            loadBooks();
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Book added successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add book", ButtonType.OK).show();
            }
        }
        btnDelete.setDisable(true);

        txtEdition.clear();
        txtISBN.clear();
        txtName.clear();
        txtAuthor.clear();
    }

    @SuppressWarnings("Duplicates")
    public void btnDelete_OnAction(ActionEvent actionEvent) throws Exception {
        BooksTM selectedBook = tblBooks.getSelectionModel().getSelectedItem();
        boolean result = bookBO.deleteBook(selectedBook.getISBN());
        loadBooks();

        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Book Deleted Successfully!", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete!", ButtonType.OK).show();
        }
        btnSave.setText("Save");
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        txtEdition.clear();
        txtISBN.clear();
        txtName.clear();
        txtAuthor.clear();

    }

    private void loadBooks() throws Exception {
        List<BooksTM> allBooks = bookBO.getAllBooks();
        ObservableList<BooksTM> items = tblBooks.getItems();
        items.clear();
        ObservableList<BooksTM> books = FXCollections.observableArrayList(allBooks);
        tblBooks.setItems(books);
    }
}
