package game;

public class Options extends Content {
    String[] options;
    String correctAnswer;

    public Options(String[] options, String correctAnswer) {
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    String getContent() {
        return String.join(" ", options);
    }

    public boolean checkAnswer(String selectedAnswer) {
        return correctAnswer.equals(selectedAnswer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }
}