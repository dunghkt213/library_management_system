module com.example.libarary_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.java;

    opens signup to javafx.fxml;
    exports signup;

    opens login to javafx.fxml;
    exports login;

    opens admindashboard to javafx.fxml;
    exports admindashboard;
}