package services;

import storage.DataStorage;
import models.SwordSale;
import models.Visit;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsService{

    // Общий доход за период
    public static double getTotalRevenue(String startDate, String endDate) {
        return DataStorage.sales.stream()
                .filter(s -> s.getSaleDate().compareTo(startDate) >= 0 && s.getSaleDate().compareTo(endDate) <= 0)
                .mapToDouble(SwordSale::getAmount)
                .sum();
    }

    // Доход от продаж мечей
    public static double getSwordsRevenue(String startDate, String endDate) {
        return getTotalRevenue(startDate, endDate) * 0.80;
    }

    // Доход от тренировок
    public static double getTrainingsRevenue(String startDate, String endDate) {
        return getTotalRevenue(startDate, endDate) * 0.20;
    }

    // Количество продаж по моделям
    public static Map<String, Integer> getSalesByModel() {
        Map<String, Integer> salesMap = new HashMap<>();

        for (SwordSale sale : DataStorage.sales) {
            int swordId = sale.getSwordId();
            String model = DataStorage.swords.stream()
                    .filter(s -> s.getId() == swordId)
                    .map(s -> s.getModel())
                    .findFirst()
                    .orElse("Unknown");

            salesMap.put(model, salesMap.getOrDefault(model, 0) + 1);
        }
        return salesMap;
    }

    // Посещаемость по дням
    public static Map<String, Integer> getAttendanceByDay() {
        Map<String, Integer> attendanceMap = new HashMap<>();

        for (Visit visit : DataStorage.visits) {
            if ("present".equals(visit.getStatus())) {
                String date = visit.getVisitDate();
                attendanceMap.put(date, attendanceMap.getOrDefault(date, 0) + 1);
            }
        }
        return attendanceMap;
    }

    // Общая статистика
    public static int getTotalClients() {
        return DataStorage.clients.size();
    }

    public static int getTotalSales() {
        return DataStorage.sales.size();
    }

    public static int getTotalTrainings() {
        return DataStorage.trainings.size();
    }
}