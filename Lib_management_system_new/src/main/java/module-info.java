module com.example.libarary_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.libarary_management_system to javafx.fxml;
    exports com.example.libarary_management_system;
}