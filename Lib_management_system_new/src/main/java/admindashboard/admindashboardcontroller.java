package admindashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class admindashboardcontroller {
    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Apple", 30),
                new PieChart.Data("Orange", 20),
                new PieChart.Data("Banana", 25),
                new PieChart.Data("Grapes", 15),
                new PieChart.Data("Others", 10)
        );

        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(false);
    }

    @FXML
    protected void handlemanagestudent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/trendingbook/trendingbook.fxml"));

        // Lấy Stage hiện tại và thay đổi Scene
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handlemanagebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/manage/managebook.fxml"));
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handlereturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/returnbook/returnbook.fxml"));
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleissuebook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuebook/issuebook.fxml"));
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handleviewbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewbook/viewbook.fxml"));
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }


    @FXML
    protected void handleviewissuedbook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewissuedbook/viewissuedbook.fxml"));
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void handlelogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
        Stage stage = (Stage) pieChart.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}
