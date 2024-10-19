module org.example.nvb_library_oop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires java.sql;
    requires mysql.connector.j;

    opens Library to javafx.fxml;

    exports Library;
}