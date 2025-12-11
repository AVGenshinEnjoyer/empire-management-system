package storage;// storage/DataStorage.java
import models.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    // Все данные хранятся в статических листах (в памяти)
    public static List<Client> clients = new ArrayList<>();
    public static List<Training> trainings = new ArrayList<>();
    public static List<Visit> visits = new ArrayList<>();
    public static List<Sword> swords = new ArrayList<>();
    public static List<SwordSale> sales = new ArrayList<>();
    public static List<InventoryItem> inventory = new ArrayList<>();
    public static List<Employee> employees = new ArrayList<>();
    public static List<AuditLog> auditLogs = new ArrayList<>();

    // Счётчики для ID
    private static int clientIdCounter = 1;
    private static int trainingIdCounter = 1;
    private static int visitIdCounter = 1;
    private static int swordIdCounter = 1;
    private static int saleIdCounter = 1;
    private static int inventoryIdCounter = 1;
    private static int employeeIdCounter = 1;

    // Методы для получения следующего ID
    public static int getNextClientId() { return clientIdCounter++; }
    public static int getNextTrainingId() { return trainingIdCounter++; }
    public static int getNextVisitId() { return visitIdCounter++; }
    public static int getNextSwordId() { return swordIdCounter++; }
    public static int getNextSaleId() { return saleIdCounter++; }
    public static int getNextInventoryId() { return inventoryIdCounter++; }
    public static int getNextEmployeeId() { return employeeIdCounter++; }

    // Метод для инициализации тестовыми данными (опционально)
    public static void initializeTestData() {
        // Можете добавить тестовые данные для разработки
    }
}