package models;
import java.time.LocalDate;

public class SwordSale {
    private int id;
    private int swordId;
    private int clientId;
    private String saleDate;
    private double amount;
    private String paymentMethod; // "cash", "card"

    public SwordSale(int swordId, int clientId, double amount, String paymentMethod) {
        this.swordId = swordId;
        this.clientId = clientId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.saleDate = LocalDate.now().toString();
    }

    // Салам
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public int getSwordId(){ return swordId; }
    public int getClientId(){ return clientId; }
    public String getSaleDate(){ return saleDate; }
    public double getAmount(){ return amount; }
    public String getPaymentMethod(){ return paymentMethod; }
}