package models;

public class InventoryItem {
    private int id;
    private String materialName;
    private int quantity;
    private String unit;

    public InventoryItem(String materialName, int quantity, String unit) {
        this.materialName = materialName;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Снова геттеры и сеттеры
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getMaterialName(){ return materialName; }
    public int getQuantity(){ return quantity; }
    public void setQuantity(int quantity){ this.quantity = quantity; }
    public String getUnit(){ return unit; }
}