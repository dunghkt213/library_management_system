package chatbot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ChatBotController {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextArea contextArea;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    @FXML
    private Button playGame;

    @FXML
    private void handleIntroGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/game/introGame.fxml"));
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/bookforstudent.fxml"));
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/manage/infoforstudent.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handletrendingbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/studenttrendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issueforstudent.fxml"));
        Stage stage = (Stage) playGame.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    private void handleSendMessage() {
        String userQuestion = inputField.getText().trim();
        String context = contextArea.getText().trim();

        if (!userQuestion.isEmpty() && !context.isEmpty()) {

            chatArea.appendText("You: " + userQuestion + "\n");

            try {
                String botResponse = sendQuestionToAPI(userQuestion, context);
                chatArea.appendText("Answer: " + botResponse + "\n");
            } catch (Exception e) {
                chatArea.appendText("Bot: Error connecting to the server.\n");
                e.printStackTrace();
            }

            inputField.clear();
        } else {
            chatArea.appendText("Please enter both context and question.\n");
        }
    }

    private String sendQuestionToAPI(String question, String context) throws Exception {
        String apiUrl = "http://127.0.0.1:5000/ask";

        String jsonPayload = String.format("{\"context\": \"%s\", \"question\": \"%s\"}", context, question);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return parseResponse(response.body());
    }

    private String parseResponse(String jsonResponse) {
        try {
            JSONObject responseObject = new JSONObject(jsonResponse);

            String answer = responseObject.getString("answer");

            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response.";
        }
    }
}
