package manage;

import dao.bookDAO;
import dao.loanDAO;
import database.ImageStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.book;
import model.loan;
import model.student;
import viewbook.viewbookcontroller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class managebookforstudentcontroller {

    @FXML
    private TextField addBookTitle;
    @FXML
    private TextField addBookID;
    @FXML
    private TextField addBookAuthor;

    @FXML
    private TableView<book> bookTableView;

    @FXML
    private TableColumn<book, String> colBookID;
    @FXML
    private TableColumn<book, String> colBookTitle;
    @FXML
    private TableColumn<book, String> colBookAuthor;
    @FXML
    private TableColumn<book, String> colBookPublisher;
    @FXML
    private TableColumn<book, String> colBookCategory;
    @FXML
    private ImageView ImageView;
    @FXML
    private Button playGame;

    @FXML
    private void handleIntroGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/game/introGame.fxml"));
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private ObservableList<book> observableBooks;
    private ObservableList<book> observableSearchResults;
    private static final Logger LOGGER = Logger.getLogger(managebookcontroller.class.getName());

    public void initialize() {
        setupTableColumns();
        loadBookData();
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(),ImageView);
    }

    private void setupTableColumns() {
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        colBookPublisher.setCellValueFactory(new PropertyValueFactory<>("bookPublisher"));
        colBookCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
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
    protected void handleBorrowBook() {
        book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("No book selected");
            return;
        }

        if (selectedBook.getQuantity() < 1) {
            showAlert("Hiện nay thư viện đã hết sách này!");
            return;
        }

        LocalDate loanDate1 = LocalDate.now(); // Ngày hiện tại
        LocalDate dueDate1 = loanDate1.plusDays(50); // Ngày trả là 50 ngày sau

        student currentStudent = student.getInstance();

        loan newLoan = new loan("Active", dueDate1.toString(), loanDate1.toString(), currentStudent.getStudentID(), selectedBook.getBookID());
        int result = 0;
        loan objloan = loanDAO.getInstance().getById(newLoan);

        if (objloan != null && objloan.getStudentID().equals(student.getInstance().getStudentID())
            && objloan.getStatus().equals("Returned")) {

            objloan.setStatus("Active");
            objloan.setLoanDate(loanDate1.toString());
            objloan.setDueDate(dueDate1.toString());

            selectedBook.setQuantity(selectedBook.getQuantity() - 1);
            selectedBook.setCountOfBorrow(selectedBook.getCountOfBorrow() + 1);
            result = loanDAO.getInstance().update(objloan);
            bookDAO.getInstance().update(selectedBook);

        } else if (objloan != null && objloan.getStudentID().equals(student.getInstance().getStudentID())
                && objloan.getStatus().equals("Overdue")) {

            objloan.setStatus("Active");
            objloan.setLoanDate(loanDate1.toString());
            objloan.setDueDate(dueDate1.toString());

            selectedBook.setQuantity(selectedBook.getQuantity() - 1);
            selectedBook.setCountOfBorrow(selectedBook.getCountOfBorrow() + 1);
            result = loanDAO.getInstance().update(objloan);
            bookDAO.getInstance().update(selectedBook);

        }
        else if (objloan != null && objloan.getStudentID().equals(student.getInstance().getStudentID())
                && objloan.getStatus().equals("Active")) {
            result = -1;
        } else {
            result = loanDAO.getInstance().insert(newLoan);
            selectedBook.setQuantity(selectedBook.getQuantity() - 1);
            selectedBook.setCountOfBorrow(selectedBook.getCountOfBorrow() + 1);
            bookDAO.getInstance().update(selectedBook);
        }

        if (result > 0) {
            showAlert("Sách đã được mượn thành công.");
        } else {
            showAlert("Lỗi: Bạn không thể mượn cuốn sách này.");
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

    private boolean validateInputFields() {
        if (addBookID.getText().isEmpty() ||
                addBookTitle.getText().isEmpty() ||
                addBookAuthor.getText().isEmpty()) {
            showAlert("All fields must be filled out.");
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        addBookID.clear();
        addBookTitle.clear();
        addBookAuthor.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/bookforstudent.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));
        Stage stage = (Stage) addBookTitle.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issueforstudent.fxml"));
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
    protected void handleAI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chatbot/chatBot.fxml"));
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
