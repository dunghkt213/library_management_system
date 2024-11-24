package issuebook;

import dao.loanDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.loan;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class issuebookforstudentcontroller {

    @FXML
    private TableView<loan> viewTable;

    public void initialize() {

    }


    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbookcontroller.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handlemanagerbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/bookforstudent.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handlemanagerstudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
