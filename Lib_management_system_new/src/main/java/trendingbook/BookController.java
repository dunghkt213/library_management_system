package trendingbook;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.book;

import java.net.URL;


public class BookController {

    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    public void setData(book Book) {
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


        bookName.setText(Book.getBookTitle());
        authorName.setText(Book.getBookAuthor());

    }


}
