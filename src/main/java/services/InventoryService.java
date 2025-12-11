package services;// services/InventoryService.java
import models.InventoryItem;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    public static void addMaterial(InventoryItem item) {
        item.setId(DataStorage.inventory.size() + 1);
        DataStorage.inventory.add(item);
    }

    public static List<InventoryItem> getAllMaterials() {
        return new ArrayList<>(DataStorage.inventory);
    }

    public static void updateMaterialQuantity(int id, int newQuantity) {
        InventoryItem item = DataStorage.inventory.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setQuantity(newQuantity);
        }
    }

    public static void deleteMaterial(int id) {
        DataStorage.inventory.removeIf(i -> i.getId() == id);
    }
}