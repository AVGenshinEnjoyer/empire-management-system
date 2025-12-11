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
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardController {
    private Label totalRevenueLabel;
    private Label swordsRevenueLabel;
    private Label trainingsRevenueLabel;

    public VBox createDashboard() {
        VBox mainVBox = new VBox(15);
        mainVBox.setPadding(new Insets(20));
        mainVBox.setStyle("-fx-font-size: 14;");

        VBox metricsBox = createMetricsBox();
        PieChart revenueChart = createRevenueChart();
        BarChart<String, Number> salesChart = createSalesChart();
        LineChart<String, Number> attendanceChart = createAttendanceChart();

        mainVBox.getChildren().addAll(
                metricsBox,
                new Separator(),
                revenueChart,
                new Separator(),
                new Label("📊 Продажи по моделям:"),
                salesChart,
                new Label("📈 Посещаемость тренировок по дням:"),
                attendanceChart
        );
        return mainVBox;
    }

    private VBox createMetricsBox() {
        VBox metricsBox = new VBox(10);
        metricsBox.setStyle("-fx-border-color: #3498db; -fx-padding: 15; -fx-border-radius: 5; -fx-background-color: #ecf0f1;");

        HBox row1 = new HBox(30);

        totalRevenueLabel = createMetricLabel("💰 Общий доход", "0 ₸");
        swordsRevenueLabel = createMetricLabel("⸸ Продажи мечей (80%)", "0 ₸");
        trainingsRevenueLabel = createMetricLabel("🎯 Тренировки (20%)", "0 ₸");

        row1.getChildren().addAll(totalRevenueLabel, swordsRevenueLabel, trainingsRevenueLabel);
        metricsBox.getChildren().addAll(row1);

        updateMetrics();
        return metricsBox;
    }

    private Label createMetricLabel(String title, String value) {
        Label label = new Label(title + "\n" + value);
        label.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-padding: 10;");
        return label;
    }

    private void updateMetrics() {
        LocalDate today = LocalDate.now();
        String startDate = today.minusMonths(1).toString();
        String endDate = today.toString();

        double totalRevenue = AnalyticsService.getTotalRevenue(startDate, endDate);
        double swordsRevenue = AnalyticsService.getSwordsRevenue(startDate, endDate);
        double trainingsRevenue = AnalyticsService.getTrainingsRevenue(startDate, endDate);

        totalRevenueLabel.setText(String.format("💰 Общий доход\n%.2f ₸", totalRevenue));
        swordsRevenueLabel.setText(String.format("⸸ Продажи мечей (80%%)\n%.2f ₸", swordsRevenue));
        trainingsRevenueLabel.setText(String.format("🎯 Тренировки (20%%)\n%.2f ₸", trainingsRevenue));
    }

    private PieChart createRevenueChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Продажи мечей (80%)", 80),
                new PieChart.Data("Тренировки (20%)", 20)
        );

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("📊 Распределение доходов");
        pieChart.setPrefHeight(300);
        return pieChart;
    }

    // График: продажи по моделям мечей
    private BarChart<String, Number> createSalesChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Модель меча");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Количество продаж");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Продажи по моделям мечей");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Продажи");

        Map<String, Integer> salesByModel = AnalyticsService.getSalesByModel();

        // Если данных ещё нет, подставляем примерные значения
        if (salesByModel == null || salesByModel.isEmpty()) {
            Map<String, Integer> demo = new LinkedHashMap<>();
            demo.put("ABS Katana", 12);
            demo.put("3D-printed Longsword", 8);
            demo.put("Training Sword", 5);

            for (Map.Entry<String, Integer> entry : demo.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        } else {
            for (Map.Entry<String, Integer> entry : salesByModel.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        }

        barChart.getData().add(series);
        barChart.setPrefHeight(300);
        return barChart;
    }

    // График: посещаемость тренировок по дням
    private LineChart<String, Number> createAttendanceChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Дата");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Количество посещений");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Посещаемость тренировок по дням");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Посещения");

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
