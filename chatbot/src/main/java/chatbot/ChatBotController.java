package chatbot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONObject;

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
    private void handleSendMessage() {
        String userQuestion = inputField.getText().trim();
        String context = contextArea.getText().trim();

        if (!userQuestion.isEmpty() && !context.isEmpty()) {

            chatArea.appendText("You: " + userQuestion + "\n");

            try {
                String botResponse = sendQuestionToAPI(userQuestion, context);
                chatArea.appendText("Bot: " + botResponse + "\n");
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
