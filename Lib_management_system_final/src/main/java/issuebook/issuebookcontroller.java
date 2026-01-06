package issuebook;

import dao.loanDAO;
import database.JDBCUtil;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class issuebookcontroller {
    @FXML
    private TableColumn<loan, String> DueDate;
    @FXML
    private TableColumn<loan, String> loanID;
    @FXML
    private TextField bookID;

    @FXML
    private TableColumn<loan, String> returnDate;

    @FXML
    private TableColumn<loan, String> columnBookID;

    @FXML
    private TableColumn<loan, String> status;

    @FXML
    private TableColumn<loan, String> studenID;

    @FXML
    private TextField studentID;
    @FXML
    private TextField LoanID;

    @FXML
    private TableView<loan> viewTable;

    public void initialize() {
        viewTable.setEditable(true);
        loanID.setCellValueFactory(new PropertyValueFactory<>("loansID"));
        columnBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        studenID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        loanID.setCellFactory(TextFieldTableCell.forTableColumn());
        columnBookID.setCellFactory(TextFieldTableCell.forTableColumn());
        returnDate.setCellFactory(TextFieldTableCell.forTableColumn());
        studenID.setCellFactory(TextFieldTableCell.forTableColumn());
        DueDate.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setCellFactory(TextFieldTableCell.forTableColumn());

        loanID.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setLoansID(e.getNewValue()));
        columnBookID.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setBookID(e.getNewValue()));
        returnDate.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setReturnDate(e.getNewValue()));
        studenID.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setStudentID(e.getNewValue()));
        DueDate.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setDueDate(e.getNewValue()));
        status.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setStatus(e.getNewValue()));
        viewTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        handleFind();
    }

    @FXML
    protected void handleadmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admindashboard/admindashboard.fxml")));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleFind() {
        String id = studentID.getText();
        String bookid = bookID.getText();
        String loanid = LoanID.getText();
        loan loan = new loan(loanid, id, bookid);

        ArrayList<loan> loans = loanDAO.getInstance().getByCondition(loan);
        ObservableList<loan> observableLoans = FXCollections.observableArrayList(loans);
        viewTable.setItems(observableLoans);
    }
    @FXML
    protected void handleAdd() {
        String id = studentID.getText();
        String bookid = bookID.getText();
        String loanid = LoanID.getText();
        LocalDate currentDate = LocalDate.now();
        String date = currentDate.toString();
        LocalDate dateAfter6Months = currentDate.plusMonths(6);
        String Duedate = dateAfter6Months.toString();
        loan loan = new loan("Active",Duedate, date, id, bookid);
        loanDAO.getInstance().insert(loan);
        handleFind();
    }
    @FXML
    protected void handleupdate() {
        loan selectedLoan = viewTable.getSelectionModel().getSelectedItem();

        if (selectedLoan != null) {
            loanDAO.getInstance().update(selectedLoan);

            System.out.println("Loan updated successfully!");
        } else {
            System.out.println("No loan selected to update.");
        }
    }

    public int deletebyID(loan loan) {
        int result = 0;
        String sql = "DELETE FROM loans WHERE loansID = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loan.getLoansID());
            result = pstmt.executeUpdate();
            System.out.println("Câu lệnh đã được thực thi thành công. Có " + result + " dòng đã bị xóa.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @FXML
    protected void handleDelete() {
        loan selectedLoan = viewTable.getSelectionModel().getSelectedItem();

        if (selectedLoan != null) {
            viewTable.getItems().remove(selectedLoan);
            deletebyID(selectedLoan);

            System.out.println("Loan deleted successfully!");
        } else {
            System.out.println("No loan selected to delete.");
        }
    }


    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/managestudent.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/managebook.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/trendingbook.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issuebook.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/viewissuedbook.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) studentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}