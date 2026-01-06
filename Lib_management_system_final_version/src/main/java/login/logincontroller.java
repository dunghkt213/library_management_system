package login;

import Notification.Notification;
import dao.studentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.student;


import java.io.IOException;

public class logincontroller {
    @FXML
    private TextField studentIDField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorMessageLabel;

    private String errorMessage = "";
    
    @FXML
    protected void handlesignupbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/signup/signup.fxml"));
        Stage stage = (Stage) studentIDField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    @FXML
    protected void handleloginbutton() throws IOException {

        String id = studentIDField.getText();
        String password = passwordField.getText();
        System.out.println(id + " " + password);
        if( id == null || password == null || id.isEmpty() || password.isEmpty() ) {
           errorMessage = "id or password is empty";
        }
        else{
            student checkinStudent = studentDAO.getInstance().getById(new student(id));
            if(checkinStudent == null) {
                errorMessage = "student not found";
            }
            else{
                checkinStudent.print();
                if(checkinStudent.getPassword().equals(password)) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admindashboard/admindashboard.fxml"));
                    Stage stage = (Stage) studentIDField.getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.setTitle("Dashboard");
                    stage.show();
                } else errorMessage += "Password does not match";
            }

        }
        errorMessageLabel.setText(errorMessage);

    }


}
