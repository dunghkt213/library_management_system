package trendingbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    @FXML
    private GridPane bookContainer;

    private List<book> recentlyAddedd;
    private List<book> Recommend;

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) cardLayout.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        @FXML
        protected void handlemanagestudent() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/manage/managestudent.fxml"));

            // Lấy Stage hiện tại và thay đổi Scene
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        protected void handlemanagebook() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/managebook.fxml"));
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        }


        @FXML
        protected void handlereturn() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/trendingbook.fxml"));
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        }


        @FXML
        protected void handleissuebook() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issuebook.fxml"));
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        }

        @FXML
        protected void handleviewbook() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        }


        @FXML
        protected void handleviewissuedbook() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/viewissuedbook.fxml"));
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        }

        @FXML
        protected void handlelogout() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        }

    @Override
    public void initialize(URL loacation, ResourceBundle resources) {
        recentlyAddedd = new ArrayList<>(recentlyAdded());
        Recommend = new ArrayList<>(books());
        int column = 0;
        int row = 1;
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
        for(book Book : Recommend) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("book.fxml"));
            try {
                VBox bookBox = fxmlLoader.load();
                BookController BookController = fxmlLoader.getController();
                BookController.setData(Book);
                if(column == 5){
                    column = 0;
                    ++ row;
                }
                bookContainer.add(bookBox,column++,row);
                GridPane.setMargin(bookBox, new Insets(10));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<book> recentlyAdded(){
        List<book> ls = new ArrayList<book>();

        book Book = new book();

        Book.setBookTitle("The Pragmatic \n Programmer");
        Book.setBookAuthor("David Thomas");
        Book.setImageUrl("/trendingbook/ThePragmaticProgrammer.jpg");
        ls.add(Book);
        Book = new book();
        Book.setBookTitle("Rich Dad\nPoor Dad");
        Book.setBookAuthor("RoBert T.Kiosaki");
        Book.setImageUrl("/trendingbook/richDadPoorDad.jpg");
        ls.add(Book);


        Book = new book();
        Book.setBookTitle("The\nWARREN BUFFET WAY");
        Book.setBookAuthor("ROBERT G.HAGSTROM");
        Book.setImageUrl("/trendingbook/theWarrenBuffetWay.jpg");
        ls.add(Book);

        Book = new book();
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
        Book.setBookTitle("C#");
        Book.setBookAuthor("Rob Miles");
        Book.setImageUrl("/trendingbook/C.jpg");
        ls.add(Book);
        return ls;

    }

    private List<book> books(){
        List<book> ls = new ArrayList<book>();
        book Book = new book();

        Book.setBookTitle("THE LEAN STARTUP");
        Book.setBookAuthor("ERIC RIES");
        Book.setImageUrl("/trendingbook/theLeanStartUp.jpg");
        ls.add(Book);

        Book = new book();
        Book.setBookTitle("TOOLS OF TITANS");
        Book.setBookAuthor("TIM FERRISS");
        Book.setImageUrl("/trendingbook/theToolOfTitan.jpg");
        ls.add(Book);

        Book = new book();
        Book.setBookTitle("The WARREN \n BUFFET  WAY");
        Book.setBookAuthor("ROBERT G.HAGSTROM");
        Book.setImageUrl("/trendingbook/theWarrenBuffetWay.jpg");
        ls.add(Book);

        Book = new book();
        Book.setBookTitle("Kí Sự Code Dạo");
        Book.setBookAuthor("Phạm Huy Hoàng");
        Book.setImageUrl("/trendingbook/CodeDaoKiSu.jpg");
        ls.add(Book);


        Book = new book();
        Book.setBookTitle("Rich Dad\nPoor Dad");
        Book.setBookAuthor("RoBert T.Kiosaki");
        Book.setImageUrl("/trendingbook/richDadPoorDad.jpg");
        ls.add(Book);

        Book = new book();
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

        Book = new book();
        Book.setBookTitle("C#");
        Book.setBookAuthor("Rob Miles");
        Book.setImageUrl("/trendingbook/C.jpg");
        ls.add(Book);

        Book = new book();
        Book.setBookTitle("The Pragmatic \n Programmer");
        Book.setBookAuthor("David Thomas");
        Book.setImageUrl("/trendingbook/ThePragmaticProgrammer.jpg");
        ls.add(Book);

        Book = new book();
        Book.setBookTitle("Những tấm lòng \n cao cả");
        Book.setBookAuthor("EDMONDO DE AMICIS");
        Book.setImageUrl("/trendingbook/nhungTamLongCaoCa.jpg");
        ls.add(Book);
        return ls;
    }

}


