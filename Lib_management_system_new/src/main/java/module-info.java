module com.example.libarary_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires com.dlsc.formsfx;
    requires mysql.connector.j;
    //requires mysql.connector.java;
    requires com.google.gson;
    requires java.sql;
    requires com.github.benmanes.caffeine;
    requires java.desktop;
    requires java.smartcardio;
    requires java.net.http;
    requires org.json;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.services.gmail.v1.rev110;
    requires com.google.api.client.json.jackson2;
    requires mail;

    exports API;

    opens signup to javafx.fxml;
    exports signup;

    opens login to javafx.fxml;
    exports login;

    opens model to javafx.base;
    exports model;

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
        opens post to javafx.fxml;
    exports post to javafx.fxml;

    opens trendingbook to javafx.fxml;
    exports trendingbook to javafx.fxml;

    opens viewofbook to javafx.fxml;
    exports viewofbook to javafx.fxml;

    opens chatbot to javafx.fxml;
    exports chatbot to javafx.graphics;
}
