package manage;

import dao.studentDAO;
import database.ImageStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.student;

import java.io.File;
import java.io.IOException;

public class infoforstudentcontroller {

    @FXML
    private TextField name;
    @FXML
    private TextField major;
    @FXML
    private TextField dateOfBirth;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private Label studentName;
    @FXML
    private ImageView Studentimage;
    @FXML
    private ImageView avatar;
    @FXML
    public void initialize(){
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(), Studentimage);
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(), avatar);
        student newStudent = studentDAO.getInstance().getById(student.getInstance());
        name.setText(newStudent.getName());
        major.setText(newStudent.getMajor());
        phone.setText(newStudent.getPhoneNumber());
        email.setText(newStudent.getEmail());
        dateOfBirth.setText(newStudent.getBirthday());
    }
    @FXML
    protected void saveChange(){

        student newStudent = studentDAO.getInstance().getById(student.getInstance());
        newStudent.setStudentID(student.getInstance().getStudentID());
        newStudent.setName(name.getText());
        newStudent.setMajor(major.getText());
        newStudent.setEmail(email.getText());
        newStudent.setPhoneNumber(phone.getText());
        newStudent.setBirthday(dateOfBirth.getText());
        studentDAO.getInstance().update(newStudent);

    }
    @FXML
    protected void handleChangePassword() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/changepassword.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/bookforstudent.fxml"));
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issueforstudent.fxml"));
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/issuedforstudent.fxml"));
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) name.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleEditPicture() {
        ImageStorage.saveStudentImage(student.getInstance().getStudentID());
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(),Studentimage);
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(), avatar);
    }


}
