package game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    private List<Questions> questions;
    private int currentIndex = 0;
    int scoreOfQuestion = 0;

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
        if (currentIndex < questions.size()) {
            Questions currentQuestion = questions.get(currentIndex);
            question.setText(currentQuestion.getContent());
            String[] choices = currentQuestion.getOptions().getOptions();
            score.setText(String.valueOf(scoreOfQuestion));
            option1.setText(choices[0]);
            option2.setText(choices[1]);
            option3.setText(choices[2]);
            option4.setText(choices[3]);

            resetButtonStyles();
        }
    }

    private void resetButtonStyles() {
        String defaul = "-fx-background-color: lightgray;";
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
            selectedButton.setStyle("-fx-background-color: green");
        } else {
            selectedButton.setStyle("-fx-background-color: red");
        }
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
