package views;// views/ClientsViewController.java
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.DoubleStringConverter;
import models.Sword;
import services.ClientService;
import models.Client;

public class ClientsViewController {
    private TableView<Client> clientsTable;
    private TextField nameField, phoneField, emailField;

    public VBox createClientView() {
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(15));
        mainVBox.getStyleClass().add("page"); // чтобы шрифт/цвета были единообразны

        VBox formBox = createClientForm();

        clientsTable = new TableView<>();
        setupClientsTable();
        loadClients();

        mainVBox.getChildren().addAll(formBox, new Separator(), clientsTable);
        return mainVBox;
    }


    private VBox createClientForm() {
        VBox formBox = new VBox(10);
        formBox.getStyleClass().add("card");

        Label title = new Label("📋 Client details:");
        title.getStyleClass().add("section-title");

        nameField = new TextField();
        nameField.setPromptText("Full name");
        nameField.setPrefWidth(300);

        phoneField = new TextField();
        phoneField.setPromptText("Phone number");
        phoneField.setPrefWidth(300);

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(300);

        Button addButton = new Button("Add new client");
        addButton.setPrefWidth(150);
        addButton.getStyleClass().add("primary-button");
        addButton.setOnAction(e -> addNewClient());

        // Элементы в форму
        formBox.getChildren().addAll(
                title,
                nameField,
                phoneField,
                emailField,
                addButton
        );

        return formBox;
    }


    private void setupClientsTable() {
        TableColumn<Client, Void> idCol = new TableColumn<>("ID");
        idCol.setPrefWidth(50);
        idCol.setSortable(false);
        idCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : String.valueOf(getIndex() + 1));
            }
        });


        TableColumn<Client, String> nameCol = new TableColumn<>("Full name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(150);

        TableColumn<Client, String> phoneCol = new TableColumn<>("Phone number");
        phoneCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPhone()));
        phoneCol.setPrefWidth(150);

        TableColumn<Client, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        emailCol.setPrefWidth(150);

        TableColumn<Client, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubscriptionStatus()));
        statusCol.setPrefWidth(100);

        TableColumn<Client, Double> debtCol = new TableColumn<>("Debt");
        debtCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getDebt()).asObject());
        debtCol.setPrefWidth(100);


        // Редактирование данных в таблице, не затрагивая код
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> {
            Client c = e.getRowValue();
            c.setName(e.getNewValue());
            ClientService.updateClient(c);   // сделай метод, если его нет
        });

        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(e -> {
            Client c = e.getRowValue();
            c.setPhone(e.getNewValue());
            ClientService.updateClient(c);
        });

        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(e -> {
            Client c = e.getRowValue();
            c.setEmail(e.getNewValue());
            ClientService.updateClient(c);
        });

        debtCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        debtCol.setOnEditCommit(e -> {
            Client c = e.getRowValue();
            c.setDebt(e.getNewValue());
            ClientService.updateClient(c);
        });





        clientsTable.getColumns().addAll(idCol, nameCol, phoneCol, emailCol, statusCol, debtCol);
        clientsTable.setPrefHeight(400);
        clientsTable.setEditable(true);

    }

    private void loadClients() {
        ObservableList<Client> data = FXCollections.observableArrayList();
        data.addAll(
                new Client("Ramazan", "+7707*******", "Ram****@gmail.com"),
                new Client("Radmir", "+7777*******", "Rad****@mail.ru"),
                new Client("Emil", "+7747*******", "Emil12****@yandex.ru"),
                new Client("Amir", "+7707*******", "AmirS****@gmail.com"),
                new Client("Alexandra", "+7707*******", "SashaTk****@gmail.com"),
                new Client("Victoria", "+7707*******", "4****@iitu.edu.kz"),
                new Client("Anna", "+7707*******", "Ann****@mail.ru"),
                new Client("Roman", "+7707*******", "Roma****@gmail.com"),
                new Client("Dmitriy", "+7701*******", "Dimas15****@yandex.ru"),
                new Client("Ivan", "+7777*******", "Vanek****@mail.ru")
                );

        data.addAll(ClientService.getAllClients());
        clientsTable.setItems(data);
    }

    private void addNewClient() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "Fill in all fields!");
            return;
        }

        Client client = new Client(name, phone, email);
        ClientService.addClient(client);

        nameField.clear();
        phoneField.clear();
        emailField.clear();

        loadClients();
        showAlert("Great", "Client added!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}