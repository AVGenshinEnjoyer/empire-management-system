package services;// services/SwordService.java
import models.Sword;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class SwordService {

    public static void addSword(Sword sword) {
        sword.setId(DataStorage.getNextSwordId());
        DataStorage.swords.add(sword);
    }

    public static List<Sword> getAllSwords() {
        return new ArrayList<>(DataStorage.swords);
    }

    public static Sword getSwordById(int id) {
        return DataStorage.swords.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void updateSword(Sword sword) {
        for (int i = 0; i < DataStorage.swords.size(); i++) {
            if (DataStorage.swords.get(i).getId() == sword.getId()) {
                DataStorage.swords.set(i, sword);
                break;
            }
        }
    }

    public static void deleteSword(int swordId) {
        DataStorage.swords.removeIf(s -> s.getId() == swordId);
    }
}