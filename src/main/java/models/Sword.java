package models;

// models/Sword.java
public class Sword {
    private int id;
    private String model;
    private String type;
    private double price;
    private String stockStatus; // "available", "out_of_stock", "production"

    public Sword(String model, String type, double price) {
        this.model = model;
        this.type = type;
        this.price = price;
        this.stockStatus = "available";
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String status) { this.stockStatus = status; }
}