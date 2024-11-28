package game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gameController {
    @FXML
    private Label question;

    @FXML
    private Button option1;

    @FXML
    private Button option2;

    @FXML
    private Button option3;

    @FXML
    private Button option4;

    @FXML
    private Label score;

    @FXML
    private ImageView image;

    @FXML
    private ImageView getScore;

    @FXML
    private ImageView Timer;

    private List<Questions> questions;
    private int currentIndex = 0;
    static int scoreOfQuestion = 0;
    private Timeline questionTimer;

    public void initialize() {
        try {
            questions = QuizApp.loadQuestions("./src/main/resources/game/questions.txt");
            loadCurrentQuestion();
            score.setText("0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCurrentQuestion() {
        Timer.setImage(null);
        if (currentIndex < questions.size()) {
            Questions currentQuestion = questions.get(currentIndex);
            question.setText(currentQuestion.getContent());
            String[] choices = currentQuestion.getOptions().getOptions();
            option1.setText(choices[0]);
            option2.setText(choices[1]);
            option3.setText(choices[2]);
            option4.setText(choices[3]);

            resetButtonStyles();
            option1.setDisable(false);
            option2.setDisable(false);
            option3.setDisable(false);
            option4.setDisable(false);

            startQuestionTimer();

            Image time = new Image(getClass().getResource("/game/time10s.gif").toExternalForm());
            Timer.setImage(time);
            PauseTransition pause = new PauseTransition(Duration.seconds(100));
            pause.setOnFinished(event -> Timer.setImage(null));
            pause.play();
        }
        else {
            question.setText("Game Over! Your score: " + scoreOfQuestion);
            Image newImage = new Image(getClass().getResource("/game/ani.gif").toExternalForm());
            image.setImage(newImage);
            score.setText("");
            option1.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);
        }
    }

    private void startQuestionTimer() {
        questionTimer = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> {
                    moveToNextQuestion();
                })
        );
        questionTimer.setCycleCount(1);
        questionTimer.play();
    }

    private void moveToNextQuestion() {
        Timer.setImage(null);

        currentIndex++;
        if (currentIndex < questions.size()) {
            loadCurrentQuestion();
        } else {
            question.setText("Game Over! Your score: " + scoreOfQuestion);
            Image newImage = new Image(getClass().getResource("/game/ani.gif").toExternalForm());
            image.setImage(newImage);
            score.setText("");
            option1.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);
        }
    }

    private void resetButtonStyles() {
        String defaul = "-fx-background-color: lightgray";
        option1.setStyle(defaul);
        option2.setStyle(defaul);
        option3.setStyle(defaul);
        option4.setStyle(defaul);
    }

    private void checkAnswer(String selectedAnswer, Button selectedButton) {
        Questions currentQuestion = questions.get(currentIndex);
        String correctAnswer = currentQuestion.getOptions().getCorrectAnswer();

        if (selectedAnswer.equals(correctAnswer)) {
            scoreOfQuestion++;
            score.setText(String.valueOf(scoreOfQuestion));
            showImageCorrectAnswer();
            selectedButton.setStyle("-fx-background-color: green");
        } else {
            selectedButton.setStyle("-fx-background-color: red");
        }

        selectedButton.setDisable(true);
        option1.setDisable(true);
        option2.setDisable(true);
        option3.setDisable(true);
        option4.setDisable(true);

        questionTimer.stop();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            javafx.application.Platform.runLater(() -> {
                currentIndex++;
                loadCurrentQuestion();
            });
        }).start();
    }

    private void showImageCorrectAnswer() {
        Image correctImage = new Image(getClass().getResource("/game/getScore.gif").toExternalForm());
        getScore.setImage(correctImage);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> getScore.setImage(null));
        pause.play();
    }

    @FXML
    private void handleOption1() {
        checkAnswer(option1.getText(), option1);
    }

    @FXML
    private void handleOption2() {
        checkAnswer(option2.getText(), option2);
    }

    @FXML
    private void handleOption3() {
        checkAnswer(option3.getText(), option3);
    }

    @FXML
    private void handleOption4() {
        checkAnswer(option4.getText(), option4);
    }
}