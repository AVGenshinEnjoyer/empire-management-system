package services;// services/TrainingService.java
import models.Training;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class TrainingService {

    public static void addTraining(Training training) {
        training.setId(DataStorage.getNextTrainingId());
        DataStorage.trainings.add(training);
    }

    public static List<Training> getAllTrainings() {
        return new ArrayList<>(DataStorage.trainings);
    }

    public static Training getTrainingById(int id) {
        return DataStorage.trainings.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void updateTraining(Training training) {
        for (int i = 0; i < DataStorage.trainings.size(); i++) {
            if (DataStorage.trainings.get(i).getId() == training.getId()) {
                DataStorage.trainings.set(i, training);
                break;
            }
        }
    }

    public static void deleteTraining(int trainingId) {
        DataStorage.trainings.removeIf(t -> t.getId() == trainingId);
    }
}