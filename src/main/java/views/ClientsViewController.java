package views;// views/ClientsViewController.java
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.ClientService;
import models.Client;

public class ClientsViewController {
    private TableView<Client> clientsTable;
    private TextField nameField, phoneField, emailField;

    public VBox createClientView() {
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(15));

        // Форма для добавления клиента
        VBox formBox = createClientForm();

        // Таблица клиентов
        clientsTable = new TableView<>();
        setupClientsTable();
        loadClients();

        mainVBox.getChildren().addAll(formBox, new Separator(), clientsTable);
        return mainVBox;
    }

    private VBox createClientForm() {
        VBox formBox = new VBox(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");

        nameField = new TextField();
        nameField.setPromptText("ФИО");
        nameField.setPrefWidth(300);

        phoneField = new TextField();
        phoneField.setPromptText("Телефон");
        phoneField.setPrefWidth(300);

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(300);

        Button addButton = new Button("Добавить клиента");
        addButton.setPrefWidth(150);
        addButton.setStyle("-fx-font-size: 12;");
        addButton.setOnAction(e -> addNewClient());

        formBox.getChildren().addAll(
                new Label("📋 Регистрация клиента"),
                nameField, phoneField, emailField, addButton
        );
        return formBox;
    }

    private void setupClientsTable() {
        TableColumn<Client, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        idCol.setPrefWidth(50);

        TableColumn<Client, String> nameCol = new TableColumn<>("ФИО");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(150);

        TableColumn<Client, String> phoneCol = new TableColumn<>("Телефон");
        phoneCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPhone()));
        phoneCol.setPrefWidth(150);

        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        emailCol.setPrefWidth(150);

        TableColumn<Client, String> statusCol = new TableColumn<>("Статус");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubscriptionStatus()));
        statusCol.setPrefWidth(100);

        TableColumn<Client, Double> debtCol = new TableColumn<>("Задолженность");
        debtCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getDebt()).asObject());
        debtCol.setPrefWidth(100);

        clientsTable.getColumns().addAll(idCol, nameCol, phoneCol, emailCol, statusCol, debtCol);
        clientsTable.setPrefHeight(400);
    }

    private void loadClients() {
        ObservableList<Client> data = FXCollections.observableArrayList();
        data.addAll(ClientService.getAllClients());
        clientsTable.setItems(data);
    }

    private void addNewClient() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            showAlert("Ошибка", "Заполните ФИО и телефон!");
            return;
        }

        Client client = new Client(name, phone, email);
        ClientService.addClient(client);

        nameField.clear();
        phoneField.clear();
        emailField.clear();

        loadClients();
        showAlert("Успех", "Клиент добавлен!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}