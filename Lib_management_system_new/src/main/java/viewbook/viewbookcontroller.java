package viewbook;

import API.GoogleBooksService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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

    private final BookController bookController;

    public viewbookcontroller() {
        this.bookController = new BookController();
    }

    @FXML
    private void initialize() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchBooks(newValue));

        resultsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showBookDetails(newValue.getBookTitle()));

        resultsListView.setCellFactory(param -> createBookListCell());
    }

    @FXML
    private void handleKeyReleased(KeyEvent event) {
        String keyword = searchField.getText();
        searchBooks(keyword);
    }

    @FXML
    private void searchBooks(String keyword) {
        if (keyword.isEmpty()) {
            resultsListView.getItems().clear();
            return;
        }

        try {
            resultsListView.getItems().clear();

            ArrayList<book> booksOnline = GoogleBooksService.searchBooks(keyword);

            resultsListView.getItems().addAll(booksOnline);

            if (resultsListView.getItems().isEmpty()) {
                showAlert("Không có kết quả", "Không tìm thấy sách với từ khóa đã nhập.");
            }
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi tìm kiếm.");
        }
    }

    private void showBookDetails(String bookTitle) {
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            return;
        }

        try {
            ArrayList<book> booksOnline = GoogleBooksService.searchBooks(bookTitle);

            if (!booksOnline.isEmpty()) {
                book selectedBook = booksOnline.get(0);

                String bookDetails = "Tiêu đề: " + selectedBook.getBookTitle() + "\n";
                bookDetails += "Tác giả: " + selectedBook.getBookAuthor() + "\n";
                bookDetails += "Nhà xuất bản: " + selectedBook.getBookPublisher() + "\n";
                bookDetails += "Phiên bản: " + selectedBook.getEdition() + "\n";
                bookDetails += "Ngôn ngữ: " + selectedBook.getLanguage() + "\n";
                bookDetails += "Thể loại: " + selectedBook.getCategoryName() + "\n";
                bookDetails += "Số lượng: " + selectedBook.getQuantity() + "\n";
                bookDetails += "Hàng trong kho: " + selectedBook.getRemainingBooks() + "\n";
                bookDetails += "Trạng thái: " + selectedBook.getAvailability() + "\n";
                bookDetails += "Mã thể loại: " + selectedBook.getCategoryID() + "\n";

                bookDetailsTextArea.setText(bookDetails);
            } else {
                showAlert("Không có kết quả", "Không tìm thấy sách với từ khóa đã nhập.");
            }
        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi khi hiển thị chi tiết sách.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private ListCell<book> createBookListCell() {
        return new ListCell<book>() {
            @Override
            protected void updateItem(book item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    HBox hbox = new HBox(10);
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);

                    // Kiểm tra nếu có URL ảnh
                    if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                        Image image = new Image(item.getImageUrl());
                        imageView.setImage(image);
                    }

                    Label bookTitle = new Label(item.getBookTitle());
                    hbox.getChildren().addAll(imageView, bookTitle);
                    setGraphic(hbox);
                }
            }
        };
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
