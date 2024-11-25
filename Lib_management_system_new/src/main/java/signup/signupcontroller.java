package signup;

import dao.accountDAO;
import dao.studentDAO;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.account;
import model.student;

import java.io.IOException;

public class signupcontroller {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField studentIDField;
    @FXML
    protected void handleloginbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.show();
    }
    @FXML
    protected void handleSignUpButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String studentID = studentIDField.getText();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("Contact: " + contact);
        System.out.println("studentID: " + studentID);
        student student = new student(studentID, username, password, email, contact,"");
        studentDAO.getInstance().insert(student);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

