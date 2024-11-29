package viewofbook;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dao.commentDAO;
import dao.bookDAO;
import dao.loanDAO;
import dao.studentDAO;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.book;
import model.comment;
import model.loan;
import model.student;
import org.controlsfx.control.Rating;
import trendingbook.BookController;
import trendingbook.cardcontroller;
import viewbook.viewbookcontroller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private VBox Box;
    @FXML
    private Hyperlink prelink;

    @FXML
    private Label bookID;
    @FXML
    private TextField commentField;
    @FXML
    private Button postButton;
    @FXML
    private Rating rating;
    @FXML
    private Rating bookRating;
    /*@FXML
    private ImageView qrCodeImageView;*/

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
        if (bookRating != null) {
            bookRating.setRating((double) Book.getAverageRating());
        } else {
            bookRating.setRating(5);
        }
        if (Book.getPreviewLink() != null && !Book.getPreviewLink().isEmpty()) {
            prelink.setText(Book.getPreviewLink());
            prelink.setOnAction(event -> openLinkInBrowser(Book.getPreviewLink()));

            //Create QR for previewLink
            /*try {
                qrCodeImageView.setImage(generateQRCodeImage(Book.getPreviewLink()));
            } catch (Exception e) {
                e.printStackTrace();
                qrCodeImageView.setImage(new Image(getClass().getResourceAsStream("/viewofbook/noQR.png")));
            }*/

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
        postButton.setOnAction(event -> {
            try {
                handlepostcomment(Book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        descriptionLabel.setText(Book.getDescription());
        comment tempcomment = new comment();

        tempcomment.setBookID(Book.getBookID());
        ArrayList<comment> listcommnet = commentDAO.getInstance().getByCondition(tempcomment);
        loadBooksAsync(listcommnet);

    }

    /*private Image generateQRCodeImage(String content) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 150, 150);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
    }*/

    private void loadBooksAsync(List<comment> comments) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (comment comment : comments) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comment.fxml"));
                    try {
                        VBox cardcomment = fxmlLoader.load();
                        commentcontroller commentcontroller =  fxmlLoader.getController();
                        commentcontroller.setData(comment);

                        Platform.runLater(() -> {
                            ((VBox) Box).getChildren().add(cardcomment);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        new Thread(task).start();
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

        Parent root = null;
        if("student".equals(studentDAO.getInstance().getStatusbyId(student.getInstance())))
        {
             root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));
        } else {
             root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));
        }
        Stage stage = (Stage) bookImageView.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    protected void handleBorrow(book selectedBook) throws IOException {
        student currentStudent = student.getInstance();
        LocalDate loanDate1 = LocalDate.now();
        LocalDate dueDate1 = loanDate1.plusDays(50);

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

    protected void handlepostcomment(book book) throws IOException {
           String commenttext = commentField.getText();
           if (commenttext.isEmpty()) {
               showAlert(viewbookcontroller.AlertType.ERROR,"Lỗi","không được để trống nội dung. ");
               return;
           }
        comment newComment = new comment();
        newComment.setBookID(book.getBookID());

        System.out.println(student.getInstance().getStudentID() + "STUDENTID");
        System.out.println("hello");

        newComment.setStudentID(Integer.parseInt(student.getInstance().getStudentID()));
        double rate = rating.getRating();

        book.setTotalRating(book.getTotalRating() + rate);
        book.setCountOfRating(book.getCountOfRating()+1);
        book.setAverageRating((float) (book.getTotalRating()/book.getCountOfRating()));
        System.out.println((float) book.getAverageRating() + (float)rate/(book.getCountOfRating()+1));
        bookDAO.getInstance().update(book);
        if (bookRating != null) {
            bookRating.setRating((double) book.getAverageRating());
        } else {
            bookRating.setRating(5);
        }
        newComment.setRating(rate);
        newComment.setComment(commenttext);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        newComment.setCreatedAt(date);
        int result = commentDAO.getInstance().insert(newComment);
        if (result < 0) {
            showAlert(viewbookcontroller.AlertType.ERROR,"Lỗi","không thể đăng bình luận ");
        }
        else {
            showAlert(viewbookcontroller.AlertType.SUCCESS, "Thành công", "đã đăng bình luận ");
            commentField.setText("");
        }
        comment tempcomment = new comment();
        tempcomment.setCreatedAt(date);
        System.out.println(tempcomment.getCreatedAt());
        ArrayList<comment> listcommnet = commentDAO.getInstance().getByCondition(tempcomment);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comment.fxml"));
        try {
            VBox cardcomment = fxmlLoader.load();
            commentcontroller commentcontroller =  fxmlLoader.getController();
            commentcontroller.setData(listcommnet.get(0));

            Platform.runLater(() -> {
                ((VBox) Box).getChildren().add(cardcomment);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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
