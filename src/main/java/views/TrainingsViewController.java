package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.Client;
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
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 10;");

        trainerField = new TextField();
        trainerField.setPromptText("Trainer's name");

        dayOfWeekField = new TextField();
        dayOfWeekField.setPromptText("Day of the week");

        timeField = new TextField();
        timeField.setPromptText("Time");

        maxClientsField = new TextField();
        maxClientsField.setPromptText("Maximum number of clients");

        Button addButton = new Button("Add training");
        addButton.setOnAction(e -> addTraining());

        formBox.getChildren().addAll(
                new Label("📅 New training:"),
                trainerField,
                dayOfWeekField,
                timeField,
                maxClientsField,
                addButton
        );

        return formBox;
    }

    private void setupTrainingsTable() {
        TableColumn<Training, Void> idCol = new TableColumn<>("ID");
        idCol.setPrefWidth(50);
        idCol.setSortable(false);
        idCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : String.valueOf(getIndex() + 1));
            }
        });

        TableColumn<Training, String> trainerCol = new TableColumn<>("Trainer");
        trainerCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTrainerName()));
        trainerCol.setPrefWidth(150);

        TableColumn<Training, String> dayCol = new TableColumn<>("Day");
        dayCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDayOfWeek()));
        dayCol.setPrefWidth(100);

        TableColumn<Training, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTime()));
        timeCol.setPrefWidth(100);

        TableColumn<Training, Integer> maxCol = new TableColumn<>("Max clients");
        maxCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getMaxClients()).asObject());
        maxCol.setPrefWidth(120);

        trainingsTable.getColumns().addAll(idCol, trainerCol, dayCol, timeCol, maxCol);
        trainingsTable.setPrefHeight(400);
    }

    private void loadTrainings() {
        ObservableList<Training> data = FXCollections.observableArrayList();
        data.addAll(
                new Training("Dauletbakova Sofia", "Sunday", "17:00", 13),
                new Training("Arslanov Renat", "Tuesday", "17:30", 18),
                new Training("Alihan Adilzhan", "Thursday", "19:00", 10)

        );
        data.addAll(TrainingService.getAllTrainings());
        trainingsTable.setItems(data);
    }

    private void addTraining() {
        String trainer = trainerField.getText();
        String day = dayOfWeekField.getText();
        String time = timeField.getText();
        String maxStr = maxClientsField.getText();

        if (trainer.isEmpty() || day.isEmpty() || time.isEmpty() || maxStr.isEmpty()) {
            showAlert("Error", "Fill in all fields!");
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
            showAlert("Great", "Training added!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Maximum clients must be a number!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
