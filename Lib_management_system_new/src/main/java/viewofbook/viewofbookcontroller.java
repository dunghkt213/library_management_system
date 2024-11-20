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
    private Label averageRatingLabel;

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
        authorLabel.setText("Tác giả: " + Book.getBookAuthor());
        publisherLabel.setText("Nhà xuất bản: " + Book.getBookPublisher());
        categoryLabel.setText("Thể loại: " + Book.getCategoryName());
        languageLabel.setText("Ngôn ngữ: " + Book.getLanguage());
        descriptionLabel.setText(Book.getDescription());
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
