package services;// services/SalesService.java
import models.SwordSale;
import models.Sword;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class SalesService {

    public static void recordSale(SwordSale sale) {
        sale.setId(DataStorage.getNextSaleId());
        DataStorage.sales.add(sale);

        // Обновляем статус меча
        Sword sword = DataStorage.swords.stream()
                .filter(s -> s.getId() == sale.getSwordId())
                .findFirst()
                .orElse(null);

        if (sword != null) {
            sword.setStockStatus("sold");
        }
    }

    public static List<SwordSale> getAllSales() {
        return new ArrayList<>(DataStorage.sales);
    }

    public static double getTotalSales(String startDate, String endDate) {
        return DataStorage.sales.stream()
                .filter(s -> s.getSaleDate().compareTo(startDate) >= 0 && s.getSaleDate().compareTo(endDate) <= 0)
                .mapToDouble(SwordSale::getAmount)
                .sum();
    }

    public static List<SwordSale> getSalesByDate(String date) {
        return DataStorage.sales.stream()
                .filter(s -> s.getSaleDate().equals(date))
                .toList();
    }
}