package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.Training;
import services.TrainingService;

public class TrainingsViewController {

    private TableView<Training> trainingsTable;
    private TextField trainerField;
    private TextField dayOfWeekField;
    private TextField timeField;
    private TextField maxClientsField;

    public VBox createTrainingsView() {
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(15));

        VBox formBox = createTrainingForm();

        trainingsTable = new TableView<>();
        setupTrainingsTable();
        loadTrainings();

        mainVBox.getChildren().addAll(formBox, new Separator(), trainingsTable);
        return mainVBox;
    }

    private VBox createTrainingForm() {
        VBox formBox = new VBox(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");

        trainerField = new TextField();
        trainerField.setPromptText("Имя тренера");

        dayOfWeekField = new TextField();
        dayOfWeekField.setPromptText("День недели (Пн, Вт, ...)");

        timeField = new TextField();
        timeField.setPromptText("Время (например, 18:30)");

        maxClientsField = new TextField();
        maxClientsField.setPromptText("Максимум клиентов");

        Button addButton = new Button("Добавить тренировку");
        addButton.setOnAction(e -> addTraining());

        formBox.getChildren().addAll(
                new Label("📅 Новая тренировка"),
                trainerField,
                dayOfWeekField,
                timeField,
                maxClientsField,
                addButton
        );

        return formBox;
    }

    private void setupTrainingsTable() {
        TableColumn<Training, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());
        idCol.setPrefWidth(50);

        TableColumn<Training, String> trainerCol = new TableColumn<>("Тренер");
        trainerCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTrainerName()));
        trainerCol.setPrefWidth(150);

        TableColumn<Training, String> dayCol = new TableColumn<>("День");
        dayCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDayOfWeek()));
        dayCol.setPrefWidth(100);

        TableColumn<Training, String> timeCol = new TableColumn<>("Время");
        timeCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTime()));
        timeCol.setPrefWidth(100);

        TableColumn<Training, Integer> maxCol = new TableColumn<>("Макс. клиентов");
        maxCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getMaxClients()).asObject());
        maxCol.setPrefWidth(120);

        trainingsTable.getColumns().addAll(idCol, trainerCol, dayCol, timeCol, maxCol);
        trainingsTable.setPrefHeight(400);
    }

    private void loadTrainings() {
        ObservableList<Training> data = FXCollections.observableArrayList();
        data.addAll(TrainingService.getAllTrainings());
        trainingsTable.setItems(data);
    }

    private void addTraining() {
        String trainer = trainerField.getText();
        String day = dayOfWeekField.getText();
        String time = timeField.getText();
        String maxStr = maxClientsField.getText();

        if (trainer.isEmpty() || day.isEmpty() || time.isEmpty() || maxStr.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля");
            return;
        }

        try {
            int max = Integer.parseInt(maxStr);
            Training training = new Training(trainer, day, time, max);
            TrainingService.addTraining(training);

            trainerField.clear();
            dayOfWeekField.clear();
            timeField.clear();
            maxClientsField.clear();

            loadTrainings();
            showAlert("Успех", "Тренировка добавлена!");
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Максимум клиентов должен быть числом");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
