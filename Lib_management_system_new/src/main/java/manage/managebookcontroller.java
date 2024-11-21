package manage;

import dao.bookDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.book;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class managebookcontroller {
    @FXML
    private TextField textField;
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

    public void initialize(URL url, ResourceBundle rb) {
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colBookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        colBookQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colBookAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colBookCountOfBorrow.setCellValueFactory(new PropertyValueFactory<>("countOfBorrow"));

        loadBookData();
    }

    private void loadBookData() {
        ObservableList<book> bookList = FXCollections.observableArrayList(bookDAO.getInstance().getAll());
        bookTableView.setItems(bookList);
    }

    @FXML
    protected void handleadmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admindashboard/admindashboard.fxml")));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) textField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleAddBook() {
        String title = addBookTitle.getText();
        String id = addBookID.getText();
        String author = addBookAuthor.getText();
        String publisher = addBookPublisher.getText();
        String category = addBookCategoryName.getText();
        String language = addBookLanguage.getText();
        String quantity = addBookQuantity.getText();
        int quantityInt = 0;

        try {
            quantityInt = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            System.out.println("Quantity is not a valid integer: " + quantity);
            return; // Exit if quantity is not valid
        }

        book newBook = new book(id,title, author, publisher, category, language, quantityInt);
        int result = bookDAO.getInstance().insert(newBook);

        // Refresh TableView after adding a new book
        if (result > 0) {
            loadBookData();
        }
    }

    @FXML
    protected void handleUpdateBook() {
        String title = addBookTitle.getText();
        String id = addBookID.getText();
        String author = addBookAuthor.getText();
        String publisher = addBookPublisher.getText();
        String category = addBookCategoryName.getText();
        String language = addBookLanguage.getText();
        String quantity = addBookQuantity.getText();
        int quantityInt = 0;
        try {
            quantityInt = Integer.parseInt(quantity);
            System.out.println("Quantity as integer: " + quantityInt);
        } catch (NumberFormatException e) {
            System.out.println("Quantity is not a valid integer: " + quantity);
        }

        System.out.println("Username: " + title);
        System.out.println("Password: " + author);
        System.out.println("Email: " + publisher);
        System.out.println("Contact: " + category);
        System.out.println("studentID: " + language);
        System.out.println("studentID: " + quantity);

        book newBook = new book(id, title, author, publisher, category, language, quantityInt);
        bookDAO.getInstance().update(newBook);

        loadBookData();
    }

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
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
