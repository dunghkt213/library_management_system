package manage;

import dao.bookDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class managebookcontroller {

    @FXML
    private TextField addBookTitle;
    @FXML
    private TextField addBookID;
    @FXML
    private TextField addBookAuthor;
    @FXML
    private TextField addBookPublisher;
    @FXML
    private TextField addBookCategoryName;
    @FXML
    private TextField addBookLanguage;
    @FXML
    private TextField addBookQuantity;

    @FXML
    private TableView<book> bookTableView;

    @FXML
    private TableColumn<book, String> colBookID;
    @FXML
    private TableColumn<book, String> colBookTitle;
    @FXML
    private TableColumn<book, String> colBookAuthor;
    @FXML
    private TableColumn<book, Integer> colBookQuantity;
    @FXML
    private TableColumn<book, String> colBookAvailability;
    @FXML
    private TableColumn<book, Integer> colBookCountOfBorrow;

    private ObservableList<book> observableBooks;
    private ObservableList<book> observableSearchResults;
    private static final Logger LOGGER = Logger.getLogger(managebookcontroller.class.getName());

    public void initialize() {
        setupTableColumns();
        setupEditableColumns();
        loadBookData();
    }

    private void setupTableColumns() {
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        colBookQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colBookAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colBookCountOfBorrow.setCellValueFactory(new PropertyValueFactory<>("countOfBorrow"));
    }

    private void setupEditableColumns() {
        bookTableView.setEditable(true);
        colBookTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colBookAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        colBookQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        colBookTitle.setOnEditCommit(event -> {
            book selectedBook = event.getRowValue();
            selectedBook.setBookTitle(event.getNewValue());
        });

        colBookAuthor.setOnEditCommit(event -> {
            book selectedBook = event.getRowValue();
            selectedBook.setBookAuthor(event.getNewValue());
        });

        colBookQuantity.setOnEditCommit(event -> {
            book selectedBook = event.getRowValue();
            selectedBook.setQuantity(event.getNewValue());
        });
    }

    private void loadBookData() {
        try {
            ArrayList<book> books = bookDAO.getInstance().getAll();
            observableBooks = FXCollections.observableArrayList(books);
            bookTableView.setItems(observableBooks);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading book data", e);
            showAlert("Error loading book data. Please try again.");
        }
    }

    @FXML
    protected void handleDeleteBook() {
        book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                int result = bookDAO.getInstance().delete(selectedBook);
                if (result == 0) {
                    showAlert("Error occurred while deleting the book: " + selectedBook.getBookID());
                } else {
                    // Xóa sách khỏi observableBooks và observableSearchResults
                    observableBooks.removeIf(book -> book.getBookID().equals(selectedBook.getBookID()));
                    if (observableSearchResults != null) {
                        observableSearchResults.removeIf(book -> book.getBookID().equals(selectedBook.getBookID()));
                    }
                    showAlert("Book deleted successfully: " + selectedBook.getBookID());
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error deleting book", e);
                showAlert("Exception occurred while deleting the book: " + selectedBook.getBookID());
            }
        } else {
            showAlert("Please select a book to delete.");
        }
    }

    @FXML
    protected void handleFindBook() {
        String id = addBookID.getText();
        String title = addBookTitle.getText();
        String author = addBookAuthor.getText();

        book searchCriteria = new book();
        searchCriteria.setBookID(id);
        searchCriteria.setBookTitle(title);
        searchCriteria.setBookAuthor(author);

        ArrayList<book> bookList = bookDAO.getInstance().getByCondition(searchCriteria);

        observableSearchResults = FXCollections.observableArrayList(bookList);
        bookTableView.setItems(observableSearchResults);
        //clearInputFields();
    }

    @FXML
    protected void handleClearFindBook() {
        bookTableView.setItems(observableBooks);
    }

    @FXML
    protected void handleAddBook() {
        if (validateInputFields()) {
            try {
                String id = addBookID.getText();
                String title = addBookTitle.getText();
                String author = addBookAuthor.getText();
                String publisher = addBookPublisher.getText();
                String category = addBookCategoryName.getText();
                String language = addBookLanguage.getText();
                int quantity = Integer.parseInt(addBookQuantity.getText());

                book newBook = new book(id, 0, "Available", 0, language, "", author, title, publisher, quantity, category, "", "", 0);
                int result = bookDAO.getInstance().insert(newBook);

                if (result == 0) {
                    showAlert("Error occurred while adding the book.");
                } else {
                    observableBooks.add(newBook);
                    showAlert("Book added successfully: " + newBook.getBookID());
                    clearInputFields();
                }

            } catch (NumberFormatException e) {
                showAlert("Invalid input for quantity. Please enter a number.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error adding book", e);
                showAlert("Error occurred while adding the book.");
            }
        }
    }

    private boolean validateInputFields() {
        if (addBookID.getText().isEmpty() ||
                addBookTitle.getText().isEmpty() ||
                addBookAuthor.getText().isEmpty() ||
                addBookPublisher.getText().isEmpty() ||
                addBookCategoryName.getText().isEmpty() ||
                addBookLanguage.getText().isEmpty() ||
                addBookQuantity.getText().isEmpty()) {
            showAlert("All fields must be filled out.");
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        addBookID.clear();
        addBookTitle.clear();
        addBookAuthor.clear();
        addBookPublisher.clear();
        addBookCategoryName.clear();
        addBookLanguage.clear();
        addBookQuantity.clear();
    }

    @FXML
    protected void handleUpdateBook() {
        try {
            for (book updatedBook : observableBooks) {
                bookDAO.getInstance().update(updatedBook);
            }
            showAlert("All changes have been updated successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating books", e);
            showAlert("Error occurred while updating books.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigation methods (unchanged)
    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagestudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/managestudent.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/managebook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/trendingbook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issuebook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/viewissuedbook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

}