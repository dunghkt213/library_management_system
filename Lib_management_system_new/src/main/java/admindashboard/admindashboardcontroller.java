package admindashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class admindashboardcontroller {
    @FXML
    private PieChart pieChart;

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
}
