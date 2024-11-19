package trendingbook;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.book;


public class BookController {

    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    public void setData(book Book){
        Image image = new Image(getClass().getResourceAsStream(Book.getImageUrl()));
        bookImage.setImage(image);
        bookName.setText(Book.getBookTitle());
        authorName.setText(Book.getBookAuthor());

    }

}
