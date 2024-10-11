package Library;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProjectController {
    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TableView<LibraryBook> tableViewBooks;
    @FXML
    private TableColumn<LibraryBook, Integer> columnId;

    @FXML
    private TableColumn<LibraryBook, String> columnTitle;

    @FXML
    private TableColumn<LibraryBook, String> columnAuthor;

    public void initialize() {
        columnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        columnAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
    }

    public void handleAddBook(ActionEvent event) {
        String title = txtBookTitle.getText();
        String author = txtAuthor.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Find Book");
        alert.setContentText("Are you looking for the book " + title + " by author " + author + "?");
        alert.show();
    }

    public void handleViewBooks() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            tableViewBooks.getItems().clear();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                tableViewBooks.getItems().add(new LibraryBook(id, title, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}