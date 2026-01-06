package trendingbook;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.book;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

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
    private GridPane bookContainer;


    private String [] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    private Color color;

    public void setData(book Book) {
        bookName.setText(Book.getBookTitle());
        authorName.setText(Book.getBookAuthor());

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
            System.out.println("Đường dẫn hình ảnh không hợp lệ: " + imageUrl);
        }

        box.setStyle("-fx-background-color: #" +  colors[(int) (Math.random() * colors.length)]
                + "; -fx-background-radius:15;"
                + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0),10,0,0,10);");
    }
}
