module chatbot {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.google.gson;
    requires org.json;

    opens chatbot to javafx.fxml;
    exports chatbot;

}