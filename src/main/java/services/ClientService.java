package services;// services/ClientService.java
import models.Client;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public static void addClient(Client client) {
        client.setId(DataStorage.getNextClientId());
        DataStorage.clients.add(client);
    }

    public static List<Client> getAllClients() {
        return new ArrayList<>(DataStorage.clients);
    }

    public static Client getClientById(int id) {
        return DataStorage.clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void updateClient(Client client) {
        for (int i = 0; i < DataStorage.clients.size(); i++) {
            if (DataStorage.clients.get(i).getId() == client.getId()) {
                DataStorage.clients.set(i, client);
                break;
            }
        }
    }

    public static void deleteClient(int clientId) {
        DataStorage.clients.removeIf(c -> c.getId() == clientId);
        // Удалим также все визиты этого клиента
        DataStorage.visits.removeIf(v -> v.getClientId() == clientId);
    }
}