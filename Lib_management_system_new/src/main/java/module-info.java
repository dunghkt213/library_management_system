module com.example.libarary_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires mysql.connector.java;
    requires com.google.gson;
    requires java.sql;

    exports API;

    opens signup to javafx.fxml;
    exports signup;

    opens login to javafx.fxml;
    exports login;

    opens admindashboard to javafx.fxml;
    exports admindashboard;

    opens manage to javafx.fxml;
    exports manage to javafx.fxml;

    opens issuebook to javafx.fxml;
    exports issuebook to javafx.fxml;

    opens returnbook to javafx.fxml;
    exports returnbook to javafx.fxml;

    opens viewbook to javafx.fxml;
    exports viewbook to javafx.fxml;

    opens viewissuedbook to javafx.fxml;
    exports viewissuedbook to javafx.fxml;
}