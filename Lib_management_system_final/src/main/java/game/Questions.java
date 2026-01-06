package game;

public class Questions extends Content {
    String question;
    Options options;

    public Questions(String question, Options options) {
        this.options = options;
        this.question = question;
    }

    @Override
    String getContent() {
        return question;
    }

    Options getOptions() {
        return options;
    }
}
