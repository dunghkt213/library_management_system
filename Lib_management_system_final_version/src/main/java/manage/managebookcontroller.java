package manage;

import dao.bookDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.book;

import java.io.IOException;
import java.util.Objects;

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
            System.out.println("Quantity as integer: " + quantityInt);
        } catch (NumberFormatException e) {
            System.out.println("Quantity is not a valid integer: " + quantity);
        }


        book newBook = new book(id,title, author, publisher, category, language,quantityInt);
        bookDAO.getInstance().insert(newBook);
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

        book newBook = new book(id,title, author, publisher, category, language,quantityInt);
        bookDAO.getInstance().insert(newBook);
    }

}
