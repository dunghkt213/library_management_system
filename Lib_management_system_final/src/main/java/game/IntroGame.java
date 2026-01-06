package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class IntroGame {
    @FXML
    private Button playButton;

    @FXML
    private void handleJoinGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/game/game.fxml"));
        Stage stage = (Stage) playButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
