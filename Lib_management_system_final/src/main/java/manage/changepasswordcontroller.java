package manage;

import dao.studentDAO;
import database.ImageStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.student;

import java.io.IOException;

public class changepasswordcontroller {

    @FXML
    private TextField currentPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField confirmPassword;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView Studentimage;
    @FXML
    private Button playGame;

    @FXML
    private void handleIntroGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/game/introGame.fxml"));
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize(){
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(), Studentimage);
        ImageStorage.loadStudentImage(student.getInstance().getStudentID(), avatar);
    }
    @FXML
    protected void handleinfo() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void saveChange(){

        if(newPassword.equals(confirmPassword)){
            student newStudent = studentDAO.getInstance().getById(student.getInstance());
            newStudent.setStudentID(student.getInstance().getStudentID());
            newStudent.setName(confirmPassword.getText());
            studentDAO.getInstance().update(newStudent);
        }
        else {
            System.out.println("mat khau xac nhan khong khop");
        }


    }
    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/bookforstudent.fxml"));
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issueforstudent.fxml"));
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/issuedforstudent.fxml"));
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) currentPassword.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}
