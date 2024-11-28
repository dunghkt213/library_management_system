package manage;

import dao.loanDAO;
import dao.studentDAO;
import database.JDBCUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.loan;
import model.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class managestudentcontroller {
    @FXML
    private TextField addstudentBirth;

    @FXML
    private TextField addstudentID;

    @FXML
    private TextField addstudentMail;

    @FXML
    private TextField addstudentMajor;

    @FXML
    private TextField addstudentName;

    @FXML
    private TextField addstudentNumber;

    @FXML
    private TableView<student> studentTableView;

    @FXML
    private TableColumn<student, String> colStudentID;

    @FXML
    private TableColumn<student, String> colStudentName;

    @FXML
    private TableColumn<student, String> colStudentBirthday;

    @FXML
    private TableColumn<student, String> colStudentEmail;

    @FXML
    private TableColumn<student, String> colStudentNumber;

    @FXML
    private TableColumn<student, String> colstudentpassword;
    @FXML
    private TextField addPassword;
    private ObservableList<student> observableStudents;
    private ObservableList<student> observableSearchStudentResults;
    static final Logger LOGGER = Logger.getLogger(managestudentcontroller.class.getName());

    public void initialize() {
        setupTableColumns();
        setupEditableColumns();
        loadStudentData();
    }

    private void setupTableColumns() {
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStudentBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colStudentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStudentNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colstudentpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void setupEditableColumns() {
        studentTableView.setEditable(true);

        colStudentName.setCellFactory(TextFieldTableCell.forTableColumn());
        colStudentBirthday.setCellFactory(TextFieldTableCell.forTableColumn());
        colStudentEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colStudentNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        colstudentpassword.setCellFactory(TextFieldTableCell.forTableColumn());
        colStudentName.setOnEditCommit(event -> {
            student selectedStudent = event.getRowValue();
            selectedStudent.setName(event.getNewValue());
        });

        colStudentBirthday.setOnEditCommit(event -> {
            student selectedStudent = event.getRowValue();
            selectedStudent.setBirthday(event.getNewValue());
        });

        colStudentEmail.setOnEditCommit(event -> {
            student selectedStudent = event.getRowValue();
            selectedStudent.setEmail(event.getNewValue());
        });

        colStudentNumber.setOnEditCommit(event -> {
            student selectedStudent = event.getRowValue();
            selectedStudent.setPhoneNumber(event.getNewValue());
        });

        colstudentpassword.setOnEditCommit(event -> {
            student selectedStudent = event.getRowValue();
            selectedStudent.setPassword(event.getNewValue());
        });

    }

    private ArrayList<student> getAllStudent() {
        ArrayList<student> students = studentDAO.getInstance().getAll();
        ArrayList<student> res = new ArrayList<>();
        for (student student : students) {
            String status = studentDAO.getInstance().getStatusbyId(student);
            if (status.equals("student")) {
                res.add(student);
            }
        }
        return res;
    }

    private void loadStudentData() {
        try {
            ArrayList<student> students = getAllStudent();
            observableStudents = FXCollections.observableArrayList(students);
            studentTableView.setItems(observableStudents);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading student data", e);
            showAlert("Error loading student data. Please try again.");
        }
    }

    @FXML
    protected void handleDeleteStudent() {
        student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                loan newloan = new loan(selectedStudent.getStudentID(), null);
                int result2 = loanDAO.getInstance().delete(newloan);
                if (result2 > 0) System.out.println("Xóa thành công danh sách mượn của " + selectedStudent.getStudentID());
                int result = studentDAO.getInstance().delete(selectedStudent);
                if (result == 0) {
                    showAlert("Error occurred while deleting the student: " + selectedStudent.getStudentID());
                } else {
                    observableStudents.removeIf(student -> student.getStudentID().equals(selectedStudent.getStudentID()));
                    if (observableSearchStudentResults != null) {
                        observableSearchStudentResults.removeIf(student -> student.getStudentID().equals(selectedStudent.getStudentID()));
                    }
                    showAlert("Student deleted successfully: " + selectedStudent.getStudentID());
                }
                loadStudentData();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error deleting student", e);
                showAlert("Exception occurred while deleting the student: " + selectedStudent.getStudentID());
            }
        } else {
            showAlert("Please select a student to delete.");
        }
    }

    @FXML
    protected void handleFindStudent() {
        String id = addstudentID.getText();
        String name = addstudentName.getText();
        String phoneNumber = addstudentNumber.getText();
        String password= addPassword.getText();
        student searchCriteria = new student();
        searchCriteria.setStudentID(id);
        searchCriteria.setName(name);
        searchCriteria.setPhoneNumber(phoneNumber);
        searchCriteria.setPassword(password);

        ArrayList<student> studentList = studentDAO.getInstance().getByCondition(searchCriteria);

        observableSearchStudentResults = FXCollections.observableArrayList(studentList);
        studentTableView.setItems(observableSearchStudentResults);
        //clearInputFields();
    }

    @FXML
    protected void handleClearFindStudent() {
        studentTableView.setItems(observableStudents);
    }

    @FXML
    protected void handleAddStudent() {
        if (validateInputFields()) {
            try {
                String id = addstudentID.getText();
                String name = addstudentName.getText();
                String birthday = addstudentBirth.getText();
                String email = addstudentMail.getText();
                String phoneNumber = addstudentNumber.getText();
                String major = addstudentMajor.getText();
                String password = addPassword.getText();

                student newStudent = new student(id, name, email, phoneNumber, birthday, major, password);
                int result = studentDAO.getInstance().insert(newStudent);

                if (result == 0) {
                    showAlert("Error occurred while adding the student.");
                } else {
                    observableStudents.add(newStudent);
                    showAlert("Student added successfully: " + newStudent.getStudentID());
                    clearInputFields();
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input for quantity. Please enter a number.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error adding student", e);
                showAlert("Error occurred while adding the student.");
            }
        }
    }

    private boolean validateInputFields() {
        if (addstudentID.getText().isEmpty() ||
                addstudentBirth.getText().isEmpty() ||
                addstudentMail.getText().isEmpty() ||
                addstudentMajor.getText().isEmpty() ||
                addstudentNumber.getText().isEmpty() ||
                addstudentName.getText().isEmpty() ||
                addPassword.getText().isEmpty()) {
            showAlert("All fields must be filled out.");
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        addstudentID.clear();
        addstudentBirth.clear();
        addstudentNumber.clear();
        addstudentMajor.clear();
        addstudentName.clear();
        addstudentMail.clear();
        addPassword.clear();
    }

    @FXML
    protected void handleUpdateStudent() {
        try {
            for (student updatedStudent : observableStudents) {
                studentDAO.getInstance().update(updatedStudent);
            }
            showAlert("All changes have been updated successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating students", e);
            showAlert("Error occurred while updating students.");
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
    protected void handleadmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admindashboard/admindashboard.fxml")));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/manage/managestudent.fxml")));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/managebook.fxml"));
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/trendingbook.fxml"));
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issuebook.fxml"));
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/viewissuedbook.fxml"));
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/trendingbook/trendingbook.fxml")));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) addstudentID.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}