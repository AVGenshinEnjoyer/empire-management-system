package services;// services/VisitService.java
import models.Visit;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class VisitService {

    public static void recordVisit(int clientId, String date, String status) {
        Visit visit = new Visit(clientId, date, status);
        visit.setId(DataStorage.visits.size() + 1);
        DataStorage.visits.add(visit);
    }

    public static List<Visit> getVisitsByClientId(int clientId) {
        List<Visit> clientVisits = new ArrayList<>();
        for (Visit visit : DataStorage.visits) {
            if (visit.getClientId() == clientId) {
                clientVisits.add(visit);
            }
        }
        return clientVisits;
    }

    public static int getAttendanceCount(int clientId) {
        return (int) DataStorage.visits.stream()
                .filter(v -> v.getClientId() == clientId && "present".equals(v.getStatus()))
                .count();
    }

    public static List<Visit> getAllVisits() {
        return new ArrayList<>(DataStorage.visits);
    }
}