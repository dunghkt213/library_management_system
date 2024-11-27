package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizApp {
    public static List<Questions> loadQuestions(String filename) throws IOException {
        List<Questions> questions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String questionText = line.trim();

            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                line = reader.readLine();
            }
            line = reader.readLine();
            String correctAnswer = (line != null) ? line.trim() : "";
            questions.add(new Questions(questionText, new Options(options, correctAnswer)));
        }

        reader.close();
        return questions;
    }
}
