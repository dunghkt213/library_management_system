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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.book;
import dao.bookDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class viewbookcontroller {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<book> resultsListView;
    @FXML
    private TextFlow bookDetailsTextFlow;
    @FXML
    private Button searchButton, nextButton, prevButton, downloadButton;
    @FXML
    private ChoiceBox<String> searchOptionChoiceBox;

    private int currentPage = 0;
    private final int pageSize = 6;
    private final int maxBooksLimit = 12; // Giới hạn số sách tối đa
    private final BookController bookController = new BookController();
    private ArrayList<book> allFetchedBooks = new ArrayList<>();

    @FXML
    private void initialize() {
        searchButton.setOnAction(this::handleSearchAction);
        nextButton.setOnAction(this::handleNextAction);
        prevButton.setOnAction(this::handlePrevAction);
        downloadButton.setOnAction(this::handleDownloadAction);
        resultsListView.setCellFactory(param -> createBookListCell());
        resultsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showBookDetails(newValue));
        searchOptionChoiceBox.setValue("Title");
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        currentPage = 0;
        allFetchedBooks.clear();
        searchBooks(searchOptionChoiceBox.getValue(), searchField.getText(), currentPage, pageSize);
    }

    private void searchBooks(String searchOption, String keyword, int pageNumber, int pageSize) {
        if (keyword.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Lỗi", "Vui lòng nhập từ khóa tìm kiếm.");
            return;
        }

        long startTime = System.currentTimeMillis();
        CompletableFuture<ArrayList<book>> futureBooks = bookController.searchBooksOnlineOrCache(searchOption, keyword, pageNumber, pageSize);
        futureBooks.thenAccept(books -> javafx.application.Platform.runLater(() -> {
            if (books == null || books.isEmpty()) {
                showAlert(AlertType.ERROR, "Không có kết quả", "Không tìm thấy sách với từ khóa đã nhập.");
            } else {
                allFetchedBooks.addAll(books);
                loadBooksFromCache();
                // Pre-fetching next page
                prefetchNextPages(searchOption, keyword, pageNumber, pageSize);
            }
            System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + " ms");
        })).exceptionally(ex -> {
            javafx.application.Platform.runLater(() -> showAlert(AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi khi tìm kiếm."));
            return null;
        });
    }

    private void prefetchNextPages(String searchOption, String keyword, int pageNumber, int pageSize) {
        if (allFetchedBooks.size() < maxBooksLimit) {
            bookController.searchBooksOnlineOrCache(searchOption, keyword, pageNumber + 1, pageSize);
        }
    }

    private void loadBooksFromCache() {
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, allFetchedBooks.size());
        ObservableList<book> observableBooks = FXCollections.observableArrayList(allFetchedBooks.subList(start, end));
        resultsListView.setItems(observableBooks);
    }

    private void showBookDetails(book selectedBook) {
        if (selectedBook == null) {
            return;
        }
        bookDetailsTextFlow.getChildren().clear();

        String bookDetails = String.format("• ISBN: %s\n\n• Title: %s\n\n• Authors: %s\n\n• Publisher: %s\n\n• Edition: %s\n\n• Language: %s\n\n• Category Name: %s\n\n• Description: %s\n\n• Page Count: %d\n\n",
                selectedBook.getBookID(), selectedBook.getBookTitle(), selectedBook.getBookAuthor(), selectedBook.getBookPublisher(), selectedBook.getEdition(), selectedBook.getLanguage(),
                selectedBook.getCategoryName(), selectedBook.getDescription(), selectedBook.getPageCount());

        String previewLinkText = "• Preview URL: ";
        Hyperlink previewLink = new Hyperlink(selectedBook.getPreviewLink());

        previewLink.setOnAction(event -> openLinkInBrowser(selectedBook.getPreviewLink()));

        bookDetailsTextFlow.getChildren().addAll(new Text(bookDetails), new Text(previewLinkText), previewLink);
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

    @FXML
    private void handleNextAction(ActionEvent event) {
        if ((currentPage + 1) * pageSize < maxBooksLimit && (currentPage + 1) * pageSize < allFetchedBooks.size()) {
            currentPage++;
            loadBooksFromCache();
        } else if ((currentPage + 1) * pageSize < maxBooksLimit) {
            currentPage++;
            searchBooks(searchOptionChoiceBox.getValue(), searchField.getText(), currentPage, pageSize);
        } else {
            showAlert(AlertType.SUCCESS, "Giới hạn", "Đã đạt đến giới hạn của 12 cuốn sách.");
        }
    }

    @FXML
    private void handlePrevAction(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            loadBooksFromCache();
        }
    }

    private void openLinkInBrowser(String url) {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDownloadAction(ActionEvent event) {
        book selectedBook = resultsListView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert(AlertType.ERROR, "Lỗi", "Bạn phải chọn một cuốn sách trước khi tải xuống.");
            return;
        }

        if (selectedBook.getImageUrl() == null || selectedBook.getImageUrl().isEmpty() || selectedBook.getDescription() == null || selectedBook.getDescription().isEmpty()) {
            showAlert(AlertType.ERROR, "Lỗi", "Dữ liệu của sách không đầy đủ (URL hoặc mô tả bị thiếu).");
            return;
        }

        if ("ISBN Not Available".equals(selectedBook.getBookID())) {
            selectedBook.setBookID(GoogleBooksService.generateUniqueBookID(selectedBook));
        }

        selectedBook.setQuantity(20);

        int result = bookDAO.getInstance().insert(selectedBook);

        if (result > 0) {
            showAlert(AlertType.SUCCESS, "Thành công", "Sách đã được lưu vào cơ sở dữ liệu.");
        } else {
            showAlert(AlertType.ERROR, "Lỗi", "Không thể lưu sách vào cơ sở dữ liệu.");
        }
    }

    @FXML
    private void handleAdmin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admindashboard/admindashboard.fxml")));
        Stage stage = (Stage) searchField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public enum AlertType {
        SUCCESS,
        ERROR
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert;
        if (type == AlertType.SUCCESS) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}