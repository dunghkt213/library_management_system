package issuebook;

import dao.loanDAO;
import database.ImageStorage;
import database.JDBCUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.loan;
import model.student;
import model.bookloan;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class issuebookforstudentcontroller {
    @FXML
    private TableView<bookloan> viewTable;

    @FXML
    private TableColumn<bookloan, String> colID;
    @FXML
    private TableColumn<bookloan, String> colBookTitle;
    @FXML
    private TableColumn<bookloan, String> colAuthor;
    @FXML
    private TableColumn<bookloan, String> colStatus;
    @FXML
    private TableColumn<bookloan, String> colCategory;
    @FXML
    private TableColumn<bookloan, String> colIssuedDate;
    @FXML
    private TableColumn<bookloan, String> colDueDate;
    @FXML
    private ImageView avatar;
    private ObservableList<bookloan> observableBookLoan = FXCollections.observableArrayList();
    private static final Logger LOGGER = Logger.getLogger(issuebookforstudentcontroller.class.getName());

    public ArrayList<bookloan> getAllBookLoan() throws SQLException {
        ArrayList<bookloan> list = new ArrayList<>();
        String query = "SELECT books.bookID, books.bookTitle, books.bookAuthor, loans.status, " +
                "books.categoryName, loans.loanDate, loans.dueDate " +
                "FROM books LEFT JOIN loans ON books.bookID = loans.bookID " +
                "WHERE loans.studentID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getInstance().getStudentID());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                list.add(new bookloan(
                        resultSet.getString("bookID"),
                        resultSet.getString("bookTitle"),
                        resultSet.getString("bookAuthor"),
                        resultSet.getString("status"),
                        resultSet.getString("categoryName"),
                        resultSet.getString("loanDate"),
                        resultSet.getString("dueDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void initialize() {
        setupTableColumns();
        loadBookData();
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(), avatar);
    }

    private void setupTableColumns() {
        colID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }

    private void loadBookData() {
        try {
            ArrayList<bookloan> bookloans = getAllBookLoan();
            Collections.sort(bookloans, new Comparator<bookloan>() {
                @Override
                public int compare(bookloan o1, bookloan o2) {
                    int priority1 = getStatusPriority(o1.getStatus());
                    int priority2 = getStatusPriority(o2.getStatus());
                    return Integer.compare(priority1, priority2);
                }
                private int getStatusPriority(String status) {
                    switch (status) {
                        case "Active":
                            return 1;
                        case "Returned":
                            return 2;
                        case "OverDue":
                            return 3;
                        default:
                            return 4;
                    }
                }
            });
            observableBookLoan = FXCollections.observableArrayList(bookloans);
            viewTable.setItems(observableBookLoan);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading book data", e);
            showAlert("Error loading book data. Please try again.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handlereturnbook() {
        bookloan selectedBook = viewTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            try {
                LocalDate loanDate = LocalDate.parse(selectedBook.getLoanDate());
                LocalDate dueDate = LocalDate.parse(selectedBook.getDueDate());
                LocalDate currentDate = LocalDate.now();
                student currentStudent = student.getInstance();

                if (!currentDate.isBefore(loanDate) && !currentDate.isAfter(dueDate)) {
                    selectedBook.setStatus("Returned");
                    showAlert("Sách đã được trả đúng hạn.");
                } else if (currentDate.isAfter(dueDate)) {
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentDate);
                    long fine = overdueDays * 5000; // Tính tiền phạt 5k/ngày
                    selectedBook.setStatus("OverDue");
                    showAlert("Bạn đã trả sách muộn " + overdueDays + " ngày. Số tiền phạt của bạn là: " + fine + " VND");
                }

                loan newLoan = new loan(selectedBook.getStatus(), selectedBook.getDueDate(), selectedBook.getLoanDate(), currentStudent.getStudentID(), selectedBook.getBookID());
                int result = loanDAO.getInstance().update(newLoan);
                loadBookData();
                System.out.println("Có " + result + " thay đổi trả sách");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing return book", e);
                showAlert("Error processing book return. Please try again.");
            }
        }
    }

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/bookforstudent.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issueforstudent.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/issuedforstudent.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) viewTable.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}
