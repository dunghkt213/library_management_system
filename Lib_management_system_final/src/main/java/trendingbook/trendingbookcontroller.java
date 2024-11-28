package trendingbook;
import javafx.application.Platform;

import dao.bookDAO;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.TrendingBooks;
import model.book;
import viewofbook.viewofbookcontroller;

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
    private Hyperlink prelink;
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
    @FXML
    protected void handleGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/game.fxml"));
        Stage stage = (Stage) cardLayout.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("game");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<book> listbook = bookDAO.getInstance().getAll();
        recentlyAddedd = TrendingBooks.getTopTrendingBooks(listbook,Math.min(listbook.size(),5));
        Recommend = new ArrayList<>(books());
        loadBooksAsync(recentlyAddedd, cardLayout, true);
        loadBooksAsync(Recommend, bookContainer, false);
    }

    private void loadBooksAsync(List<book> books, javafx.scene.layout.Region container, boolean isRecentlyAdded) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (isRecentlyAdded) {
                    for (book Book : books) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("card.fxml"));
                        try {
                            HBox cardBox = fxmlLoader.load();
                            cardcontroller cardController = fxmlLoader.getController();
                            cardController.setData(Book);

                            Platform.runLater(() -> {
                                ((HBox) container).getChildren().add(cardBox);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    final int[] column = {0};
                    final int[] row = {1};

                    for (book Book : books) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                        try {
                            VBox bookBox = fxmlLoader.load();
                            BookController bookController = fxmlLoader.getController();
                            bookController.setData(Book);

                            Platform.runLater(() -> {
                                if (column[0] == 5) {
                                    column[0] = 0;
                                    ++row[0];
                                }
                                ((GridPane) container).add(bookBox, column[0]++, row[0]);
                                GridPane.setMargin(bookBox, new Insets(10));
                            });

                            bookBox.setOnMouseClicked(event -> handleBookClick(Book));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    private void handleBookClick(book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewofbook/viewofbook.fxml"));
            Parent root = loader.load();

            viewofbookcontroller controller = loader.getController();
            controller.setBookDetails(selectedBook);

            Stage stage = (Stage) bookContainer.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private List<book> books(){
        List<book> ls = new ArrayList<book>();
        book Book = new book();
/*        Book.setBookID("9780307887917");
        book Book2 = bookDAO.getInstance().getById(Book);
        Book2.printinfo();
        ls.add(Book2);*/

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




