module com.example.test_javafx_library {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.test_javafx_library to javafx.fxml;
    opens Library to javafx.fxml;

    exports com.example.test_javafx_library;
    exports Library;
}