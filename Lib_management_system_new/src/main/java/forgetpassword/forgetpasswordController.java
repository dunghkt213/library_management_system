package forgetpassword;

import dao.studentDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import API.SimpleMailer;
import java.io.IOException;
import java.util.Random;

import model.forgetpassword;
import model.student;
import viewbook.viewbookcontroller;

public class forgetpasswordController {
    @FXML
    private Button getOtpButton;

    @FXML
    private Text timerText;

    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField studentIDField;

    @FXML
    private TextField otpField;

    private int seconds = 60;
    private Timeline timeline;

    @FXML
    public void sendOtp() {
        getOtpButton.setDisable(true);
        startCountdown();
        student newstudent = new student();
        newstudent.setStudentID(studentIDField.getText());
        student student = studentDAO.getInstance().getById(newstudent);
        String email = student.getEmail();
        Random random = new Random();


        int randomNumber = 100000 + random.nextInt(900000);
        forgetpassword.getinstance().setOtp(String.valueOf(randomNumber));
        SimpleMailer.sendEmail(email,"OTP Reset Password", "OTP của bạn là: " + String.valueOf(randomNumber));


        startCountdown();
    }
    @FXML
    protected void handleConfirm() throws IOException {

        String Studentid = studentIDField.getText();
        String Password = passwordField.getText();
        String ConfirmPassword = confirmPasswordField.getText();
        String otp = otpField.getText();
        if(otp.equals(forgetpassword.getinstance().getOtp())) {
            if(passwordField.getText().equals(ConfirmPassword)) {
                student newstudent = new student();
                newstudent.setStudentID(Studentid);
                student student = studentDAO.getInstance().getById(newstudent);
                student.setPassword(Password);
                studentDAO.getInstance().update(student);
                showAlert(viewbookcontroller.AlertType.SUCCESS,"Thành công","đổi mật khẩu thành công");
            }else {
                showAlert(viewbookcontroller.AlertType.ERROR,"Lỗi","mật khẩu không trùng khớp");
            }

        }
        else {
            showAlert(viewbookcontroller.AlertType.ERROR,"Lỗi","mã OTP không đúng ");
        }
    }
    private void startCountdown() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateTimer())
        );
        timeline.setCycleCount(30);
        timeline.play();
    }


    private void updateTimer() {
        if (seconds > 0) {
            seconds--;
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            timerText.setText(String.format("%02d:%02d", minutes, remainingSeconds));
        }


        if (seconds == 0) {
            getOtpButton.setDisable(false);
            timerText.setText("00:30");
            seconds = 60;
        }
    }
    @FXML
    protected void handleloginbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) getOtpButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    @FXML
    protected void handlesignupbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/signup/signup.fxml"));
        Stage stage = (Stage) getOtpButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }
    public enum AlertType {
        SUCCESS,
        ERROR
    }

    private void showAlert(viewbookcontroller.AlertType type, String title, String message) {
        Alert alert;
        if (type == viewbookcontroller.AlertType.SUCCESS) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
