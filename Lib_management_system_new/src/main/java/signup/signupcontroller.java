package signup;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class signupcontroller {
    @FXML
    private TextField usernameField;

    @FXML
    protected void handleloginbutton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.show();
    }
}

