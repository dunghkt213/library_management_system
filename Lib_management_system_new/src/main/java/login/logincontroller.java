package login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class logincontroller {
    @FXML
    private TextField usernameField;

    @FXML
    protected void handlesignupbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/signup/signup.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    @FXML
    protected void handleloginbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admindashboard/admindashboard.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}
