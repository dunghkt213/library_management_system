package viewbook;

import API.GoogleBooksService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class viewbookcontroller {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<book> resultsListView;

    @FXML
    private TextArea bookDetailsTextArea;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {
        resultsListView.setCellFactory(param -> createBookListCell());
        resultsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showBookDetails(newValue));
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        String keyword = searchField.getText();
        searchBooks(keyword);
    }

    private void searchBooks(String keyword) {
        if (keyword.isEmpty()) {
            resultsListView.getItems().clear();
            return;
        }

        try {
            resultsListView.getItems().clear();
            ArrayList<book> booksOnline = GoogleBooksService.searchBooks(keyword);
            ObservableList<book> observableBooks = FXCollections.observableArrayList(booksOnline);
            resultsListView.setItems(observableBooks);

            if (observableBooks.isEmpty()) {
                showAlert("Không có kết quả", "Không tìm thấy sách với từ khóa đã nhập.");
            }
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi tìm kiếm.");
        }
    }

    private void showBookDetails(book selectedBook) {
        if (selectedBook == null) {
            return;
        }

        String bookDetails = "• Title: " + selectedBook.getBookTitle() + "\n\n";
        bookDetails += "• Authors: " + selectedBook.getBookAuthor() + "\n\n";
        bookDetails += "• Publisher: " + selectedBook.getBookPublisher() + "\n\n";
        bookDetails += "• Edition: " + selectedBook.getEdition() + "\n\n";
        bookDetails += "• Language: " + selectedBook.getLanguage() + "\n\n";
        bookDetails += "• Category Name: " + selectedBook.getCategoryName() + "\n\n";

        bookDetails += "• Description: " + selectedBook.getDescription() + "\n\n";
        bookDetails += "• Page Count: " + selectedBook.getPageCount() + "\n\n";
        bookDetails += "• Average Rating: " + selectedBook.getAverageRating() + "\n\n";
        bookDetails += "• Maturity Rating: " + selectedBook.getMaturityRating() + "\n";

        //bookDetails += "Số lượng: " + selectedBook.getQuantity() + "\n";
        //bookDetails += "Hàng trong kho: " + selectedBook.getRemainingBooks() + "\n";
        //bookDetails += "Trạng thái: " + selectedBook.getAvailability() + "\n";
        //bookDetails += "Mã thể loại: " + selectedBook.getCategoryID() + "\n";

        bookDetailsTextArea.setText(bookDetails);
    }

    private ListCell<book> createBookListCell() {
        return new ListCell<book>() {
            @Override
            protected void updateItem(book item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);

                    // Kiểm tra nếu có URL ảnh
                    if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                        imageView.setImage(new Image(item.getImageUrl()));
                    }

                    Label bookTitle = new Label(item.getBookTitle());
                    Label bookAuthor = new Label(item.getBookAuthor());
                    hbox.getChildren().addAll(imageView, bookTitle, bookAuthor);
                    setGraphic(hbox);
                }
            }
        };
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handleadmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admindashboard/admindashboard.fxml")));

        Stage stage = (Stage) searchField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
