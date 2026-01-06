package Notification;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Notification extends StackPane {

    public Notification(String message) {
        Label label = new Label(message);
        label.setTextFill(Color.WHITE); // Màu chữ trắng
        label.setFont(new Font("Arial", 16));
        label.setStyle("-fx-background-color: #FF6347; -fx-padding: 10px 20px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 2, 2);");

        // Thêm Label vào StackPane
        this.getChildren().add(label);

        // Cài đặt hiệu ứng fade-in và fade-out
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(2)); // Hiển thị trong 2 giây trước khi ẩn đi

        fadeIn.setOnFinished(event -> fadeOut.play());
        fadeIn.play();
    }
}
