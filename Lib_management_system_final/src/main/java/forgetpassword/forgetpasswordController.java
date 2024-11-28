package forgetpassword;

import dao.studentDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
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
        // Vô hiệu hóa nút Get OTP
        getOtpButton.setDisable(true);

        // Khởi động bộ đếm thời gian
        startCountdown();

        // Tạo một đối tượng Student mới và lấy thông tin email từ cơ sở dữ liệu
        student newstudent = new student();
        newstudent.setStudentID(studentIDField.getText());
        student student = studentDAO.getInstance().getById(newstudent);
        String email = student.getEmail();

        // Tạo OTP ngẫu nhiên
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);

        // Lưu OTP vào đối tượng forgetpassword
        forgetpassword.getinstance().setOtp(String.valueOf(randomNumber));

        // Tạo và thực thi tác vụ gửi email trong một luồng phụ
        Task<Void> sendOtpTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Gửi OTP qua email
                SimpleMailer.sendEmail(email, "OTP Reset Password", "OTP của bạn là: " + randomNumber);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                // Khi gửi email thành công, kích hoạt lại nút Get OTP nếu cần
                showAlert(viewbookcontroller.AlertType.SUCCESS, "Thành công", "OTP đã được gửi đến email của bạn.");
            }

            @Override
            protected void failed() {
                super.failed();
                // Nếu có lỗi trong quá trình gửi email, hiển thị thông báo lỗi
                showAlert(viewbookcontroller.AlertType.ERROR, "Lỗi", "Không thể gửi OTP. Vui lòng thử lại.");
                // Kích hoạt lại nút Get OTP
                getOtpButton.setDisable(false);
            }
        };

        // Bắt đầu tác vụ trong một luồng riêng
        Thread sendOtpThread = new Thread(sendOtpTask);
        sendOtpThread.setDaemon(true);  // Đảm bảo rằng luồng này không gây rối khi ứng dụng đóng
        sendOtpThread.start();
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
            timerText.setText("");
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
