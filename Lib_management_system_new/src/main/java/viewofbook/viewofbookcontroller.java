package viewofbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.book;

import java.io.IOException;

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
    private Label pageCountLabel;

    @FXML
    private Label averageRatingLabel;

    // Phương thức để nhận dữ liệu từ đối tượng book
    public void setBookDetails(book Book) {
        // Thiết lập ảnh bìa sách
        if (Book.getImageUrl() != null && !Book.getImageUrl().isEmpty()) {
            if (Book.getImageUrl().startsWith("http") || Book.getImageUrl().startsWith("https")) {
                // Nếu là URL (HTTP/HTTPS)
                bookImageView.setImage(new Image(Book.getImageUrl()));
            } else {
                // Nếu là đường dẫn cục bộ
                Image image = new Image(getClass().getResourceAsStream(Book.getImageUrl()));
                bookImageView.setImage(image);
            }
        } else {
            bookImageView.setImage(new Image(getClass().getResourceAsStream("/viewofbook/noImage.png")));
        }


        titleLabel.setText(Book.getBookTitle());
        authorLabel.setText("Tác giả: " + Book.getBookAuthor());
        publisherLabel.setText("Nhà xuất bản: " + Book.getBookPublisher());
        categoryLabel.setText("Thể loại: " + Book.getCategoryName());
        languageLabel.setText("Ngôn ngữ: " + Book.getLanguage());
        descriptionLabel.setText(Book.getDescription());
        pageCountLabel.setText("Số trang: " + Book.getPageCount());
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
}
