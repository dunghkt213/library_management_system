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

    opens issuebook to javafx.fxml;
    exports issuebook;

    opens admindashboard to javafx.fxml;
    exports admindashboard;

    opens viewbook to javafx.fxml;
    exports viewbook to javafx.fxml;

    opens viewissuedbook to javafx.fxml;
    exports viewissuedbook;

    opens manage to javafx.fxml;
    exports manage to javafx.fxml;

    opens returnbook to javafx.fxml;
    exports returnbook to javafx.fxml;
}