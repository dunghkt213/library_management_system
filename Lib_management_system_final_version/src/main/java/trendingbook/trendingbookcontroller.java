package trendingbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import model.book;

import javax.smartcardio.Card;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class trendingbookcontroller implements Initializable {

    @FXML
    private HBox cardLayout;
    private List<book> recentlyAddedd;
    @Override
    public void initialize(URL loacation, ResourceBundle resources) {
        recentlyAddedd = new ArrayList<>(recentlyAdded());

        for (int i = 0; i < recentlyAddedd.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("card.fxml"));
            try {
                HBox cardBox = fxmlLoader.load();
                cardcontroller CardController = fxmlLoader.getController();
                CardController.setData(recentlyAddedd.get(i));
                cardLayout.getChildren().add(cardBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<book> recentlyAdded(){
        List<book> ls = new ArrayList<book>();
        book Book = new book();
        Book.setBookTitle("Harry Potter and \n" +
                "the Sorcerer*s Stone");
        Book.setBookAuthor("J. K. Rowling");
        Book.setImageUrl("/trendingbook/Harry-Potter-va-Hon-da-Phu-thuy.jpg");
        if (Book.getImageUrl() != null) {
            Image image = new Image(getClass().getResourceAsStream(Book.getImageUrl()));
            if (image.isError()) {
                System.out.println("Lỗi khi tải hình ảnh: " + Book.getImageUrl());
            } else {
                // Đặt hình ảnh vào ImageView của card
            }
        }

        ls.add(Book);
        Book = new book();
        Book.setBookTitle("Nhà Giả Kim");
        Book.setBookAuthor("Paulo");
        Book.setImageUrl("/trendingbook/nhagiakim.jpg");
        ls.add(Book);
        ls.add(Book);
        ls.add(Book);
        ls.add(Book);
        ls.add(Book);
        return ls;

    }
}
