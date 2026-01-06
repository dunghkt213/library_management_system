package trendingbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.book;
import org.controlsfx.control.Rating;
import viewofbook.viewofbookcontroller;

import java.io.IOException;
import java.net.URL;

public class cardcontroller {

    @FXML
    private HBox box;
    @FXML
    private Label bookName;
    @FXML
    private Label authorName;
    @FXML
    private ImageView bookImage;
    @FXML
    private Rating  bookRating;
    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    public void setData(book Book) {
        // Thiết lập tên sách và tác giả
        bookName.setText(Book.getBookTitle());
        authorName.setText(Book.getBookAuthor());

        if (bookRating != null) {
            bookRating.setRating((double) Book.getAverageRating());
        } else {
            bookRating.setRating(5);
        }
        // Thiết lập hình ảnh sách
        if (Book.getImageUrl().startsWith("http")) {
            Image image = new Image(Book.getImageUrl());
            bookImage.setImage(image);
        } else {
            // Đường dẫn cục bộ trong tài nguyên
            URL imageUrl = getClass().getResource(Book.getImageUrl());
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toString());
                bookImage.setImage(image);
            } else {
                // Hình ảnh mặc định nếu không tìm thấy
                bookImage.setImage(new Image("default_image.jpg"));
            }
        }

        // Thiết lập màu nền ngẫu nhiên cho card
        box.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)]
                + "; -fx-background-radius:15;"
                + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0),10,0,0,10);");

        // Thêm sự kiện click vào card
        box.setOnMouseClicked(event -> {
            try {
                // Tải fxml của viewbook
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewofbook/viewofbook.fxml"));
                Parent root = loader.load();

                // Lấy controller của viewbook
                viewofbookcontroller controller = loader.getController();

                // Chuyển thông tin sách vào controller của viewbook
                controller.setBookDetails(Book);

                // Lấy Stage hiện tại và thay đổi Scene
                Stage stage = (Stage) box.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
