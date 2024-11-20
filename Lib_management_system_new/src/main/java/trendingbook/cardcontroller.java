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
import viewofbook.viewofbookcontroller;

import java.io.IOException;

public class cardcontroller {

    @FXML
    private HBox box;
    @FXML
    private Label bookName;
    @FXML
    private Label authorName;
    @FXML
    private ImageView bookImage;

    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    public void setData(book Book) {
        // Thiết lập tên sách và tác giả
        bookName.setText(Book.getBookTitle());
        authorName.setText(Book.getBookAuthor());

        // Thiết lập hình ảnh sách
        String imageUrl = Book.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(getClass().getResourceAsStream(imageUrl));
                if (image.isError()) {
                    System.out.println("Lỗi khi tải hình ảnh: " + imageUrl);
                } else {
                    bookImage.setImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Không thể tải hình ảnh từ đường dẫn: " + imageUrl);
            }
        } else {
            bookImage.setImage(new Image(getClass().getResourceAsStream("/viewofbook/noImage.png")));
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
