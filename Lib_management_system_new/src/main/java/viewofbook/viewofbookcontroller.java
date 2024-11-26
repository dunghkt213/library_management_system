package viewofbook;

import dao.bookDAO;
import dao.loanDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.book;
import model.loan;
import model.student;
import viewbook.viewbookcontroller;

import java.io.IOException;
import java.time.LocalDate;

public class viewofbookcontroller {

    @FXML
    private ImageView bookImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button borrowButton;
    @FXML
    private Label averageRatingLabel;

    @FXML
    private Hyperlink prelink;

    @FXML
    private Label bookID;

    @FXML
    public void setBookDetails(book Book) {
        if (Book.getImageUrl() != null && !Book.getImageUrl().isEmpty()) {
            if (Book.getImageUrl().startsWith("http") || Book.getImageUrl().startsWith("https")) {
                bookImageView.setImage(new Image(Book.getImageUrl()));
            } else {
                Image image = new Image(getClass().getResourceAsStream(Book.getImageUrl()));
                bookImageView.setImage(image);
            }
        } else {
            bookImageView.setImage(new Image(getClass().getResourceAsStream("/viewofbook/noImage.png")));
        }


        titleLabel.setText(Book.getBookTitle());
        bookID.setText("BookID: " + Book.getBookID());
        authorLabel.setText("Tác giả: " + Book.getBookAuthor());
        publisherLabel.setText("Nhà xuất bản: " + Book.getBookPublisher());
        categoryLabel.setText("Thể loại: " + Book.getCategoryName());
        languageLabel.setText("Ngôn ngữ: " + Book.getLanguage());

        if (Book.getPreviewLink() != null && !Book.getPreviewLink().isEmpty()) {
            prelink.setText(Book.getPreviewLink());
            prelink.setOnAction(event -> openLinkInBrowser(Book.getPreviewLink()));
        } else {
            prelink.setText("Không có liên kết xem trước");
            prelink.setDisable(true);
        }
        borrowButton.setOnAction(event -> {
            try {
                handleBorrow(Book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        descriptionLabel.setText(Book.getDescription());
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
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) bookImageView.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    protected void handleBorrow(book selectedBook) throws IOException {
        student currentStudent = student.getInstance();
        LocalDate loanDate1 = LocalDate.now(); // Ngày hiện tại
        LocalDate dueDate1 = loanDate1.plusDays(50); // Ngày trả là 50 ngày sau

        loan newLoan = new loan("Active", dueDate1.toString(), loanDate1.toString(), currentStudent.getStudentID(), selectedBook.getBookID());

        int result = 0;
        loan objloan = loanDAO.getInstance().getById(newLoan);
        if (objloan != null) {
            result = -1;
        } else {
            result = loanDAO.getInstance().insert(newLoan);
        }

        if (result > 0) {
                book book = bookDAO.getInstance().getById(selectedBook);
                book.setCountOfBorrow(book.getCountOfBorrow() + 1);
                book.setQuantity(book.getQuantity() - 1);
                bookDAO.getInstance().update(book);
                showAlert(viewbookcontroller.AlertType.SUCCESS, "Thành công", "đã mượn thành công.");
        } else {
            showAlert(viewbookcontroller.AlertType.ERROR, "Lỗi", "Bạn không thể mượn cuốn sách này.");
        }
    }
    public enum AlertType {
        SUCCESS,
        ERROR
    }

    private void showAlert(viewbookcontroller.AlertType type, String title, String message) {
        Alert alert;
        if (type == viewbookcontroller.AlertType.SUCCESS) {
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
