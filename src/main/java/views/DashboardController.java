package views;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.AnalyticsService;

import java.time.LocalDate;
import java.util.Map;

public class DashboardController {
    private Label totalRevenueLabel;
    private Label swordsRevenueLabel;
    private Label trainingsRevenueLabel;

    public VBox createDashboard() {
        VBox mainVBox = new VBox(15);
        mainVBox.setPadding(new Insets(20));
        mainVBox.getStyleClass().add("page");

        VBox metricsBox = createMetricsBox();
        PieChart revenueChart = createRevenueChart();
        BarChart<String, Number> salesChart = createSalesChart();
        LineChart<String, Number> attendanceChart = createAttendanceChart();

        Label salesLabel = new Label("📊 Sales by sword model:");
        salesLabel.getStyleClass().add("section-title");

        Label attendanceLabel = new Label("📈 Training attendance:");
        attendanceLabel.getStyleClass().add("section-title");

        mainVBox.getChildren().addAll(
                metricsBox,
                new Separator(),
                revenueChart,
                new Separator(),
                salesLabel,
                salesChart,
                attendanceLabel,
                attendanceChart
        );
        return mainVBox;
    }


    private VBox createMetricsBox() {
        VBox metricsBox = new VBox(10);
        metricsBox.getStyleClass().add("card");

        HBox row1 = new HBox(30);

        totalRevenueLabel = createMetricLabel("💰 Total income", "0 ₸");
        swordsRevenueLabel = createMetricLabel("⸸ Swords sales (80%)", "0 ₸");
        trainingsRevenueLabel = createMetricLabel("🎯 Trainings (20%)", "0 ₸");

        row1.getChildren().addAll(totalRevenueLabel, swordsRevenueLabel, trainingsRevenueLabel);
        metricsBox.getChildren().addAll(row1);

        updateMetrics();
        return metricsBox;
    }


    private Label createMetricLabel(String title, String value) {
        Label label = new Label(title + "\n" + value);
        label.getStyleClass().addAll("metric-card", "metric-label");
        return label;
    }


    private void updateMetrics() {
        LocalDate today = LocalDate.now();
        String startDate = today.minusMonths(1).toString();
        String endDate = today.toString();

        double totalRevenue = AnalyticsService.getTotalRevenue(startDate, endDate);
        double swordsRevenue = AnalyticsService.getSwordsRevenue(startDate, endDate);
        double trainingsRevenue = AnalyticsService.getTrainingsRevenue(startDate, endDate);

        totalRevenueLabel.setText(String.format("💰 Total sales: \n%.2f ₸", totalRevenue));
        swordsRevenueLabel.setText(String.format("⸸ Sword sales: (80%%)\n%.2f ₸", swordsRevenue));
        trainingsRevenueLabel.setText(String.format("🎯 Trainings: (20%%)\n%.2f ₸", trainingsRevenue));
    }

    private PieChart createRevenueChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sword sales (80%)", 80),
                new PieChart.Data("Trainings (20%)", 20)
        );

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("📊 Income distribution");
        pieChart.setPrefHeight(300);
        return pieChart;
    }

    // График продажи по моделям мечей
    private BarChart<String, Number> createSalesChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(null); // убрать подпись оси

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Units sold");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sales by sword model");
        barChart.setLegendVisible(false); // убрать легенду

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales"); // имя теперь только для кода

        series.getData().add(new XYChart.Data<>("WaterFall sword", 12));
        series.getData().add(new XYChart.Data<>("SkyBreaker sword", 8));
        series.getData().add(new XYChart.Data<>("TitanFall Sword", 5));

        barChart.getData().add(series);

        // Цвета для столбиков
        String[] colors = {"#E65A6B", "#42C29E", "#F4C342"};

        // Установка стилей ПОСЛЕ того, как график отрисуется. Это ваще ЖОПА
        javafx.application.Platform.runLater(() -> {
            for (int i = 0; i < series.getData().size(); i++) {
                XYChart.Data<String, Number> data = series.getData().get(i);
                if (data.getNode() != null) {
                    String color = colors[i % colors.length];
                    data.getNode().setStyle("-fx-bar-fill: " + color + ";");
                }
            }
        });
        barChart.setPrefHeight(300);
        return barChart;
    }




    // График посещаемость тренировок по дням
    private LineChart<String, Number> createAttendanceChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Attendance marks");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Training's attendance by day");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Attendance");

        Map<String, Integer> attendanceByDay = AnalyticsService.getAttendanceByDay();

        if (attendanceByDay == null || attendanceByDay.isEmpty()) {
            series.getData().add(new XYChart.Data<>("2025-01-12", 5));
            series.getData().add(new XYChart.Data<>("2025-03-12", 8));
            series.getData().add(new XYChart.Data<>("2025-05-12", 8));
            series.getData().add(new XYChart.Data<>("2025-13-12", 10));
            series.getData().add(new XYChart.Data<>("2025-15-12", 7));
        } else {
            attendanceByDay.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry ->
                            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()))
                    );
        }

        lineChart.getData().add(series);
        lineChart.setPrefHeight(300);
        return lineChart;
    }
}
