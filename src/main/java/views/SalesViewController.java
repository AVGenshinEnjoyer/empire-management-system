package views;// views/SalesViewController.java
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.SwordService;
import services.SalesService;
import models.Sword;
import models.SwordSale;

public class SalesViewController {
    private TableView<Sword> swordsTable;
    private ComboBox<String> paymentMethodCombo;
    private TextField swordModelField;
    private TextField swordPriceField;

    public VBox createSalesView() {
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(15));

        // Форма для добавления меча
        VBox catalogForm = createCatalogForm();

        // Таблица мечей
        swordsTable = new TableView<>();
        setupSwordsTable();
        loadSwords();

        // Форма для продажи
        VBox saleForm = createSaleForm();

        mainVBox.getChildren().addAll(catalogForm, new Separator(), swordsTable, new Separator(), saleForm);
        return mainVBox;
    }

    private VBox createCatalogForm() {
        VBox formBox = new VBox(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");

        swordModelField = new TextField();
        swordModelField.setPromptText("Модель меча");
        swordModelField.setPrefWidth(300);

        swordPriceField = new TextField();
        swordPriceField.setPromptText("Цена");
        swordPriceField.setPrefWidth(300);

        Button addButton = new Button("Добавить меч в каталог");
        addButton.setPrefWidth(200);
        addButton.setOnAction(e -> addNewSword());

        formBox.getChildren().addAll(
                new Label("Новый меч в каталог"),
                swordModelField, swordPriceField, addButton
        );
        return formBox;
    }

    private void setupSwordsTable() {
        TableColumn<Sword, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        idCol.setPrefWidth(50);

        TableColumn<Sword, String> modelCol = new TableColumn<>("Модель");
        modelCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModel()));
        modelCol.setPrefWidth(150);

        TableColumn<Sword, Double> priceCol = new TableColumn<>("Цена");
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        priceCol.setPrefWidth(100);

        TableColumn<Sword, String> statusCol = new TableColumn<>("Статус");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStockStatus()));
        statusCol.setPrefWidth(120);

        swordsTable.getColumns().addAll(idCol, modelCol, priceCol, statusCol);
        swordsTable.setPrefHeight(300);
    }

    private void loadSwords() {
        ObservableList<Sword> data = FXCollections.observableArrayList();
        data.addAll(SwordService.getAllSwords());
        swordsTable.setItems(data);
    }

    private void addNewSword() {
        String model = swordModelField.getText();
        String priceStr = swordPriceField.getText();

        if (model.isEmpty() || priceStr.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            Sword sword = new Sword(model, "standard", price);
            SwordService.addSword(sword);

            swordModelField.clear();
            swordPriceField.clear();

            loadSwords();
            showAlert("Успех", "Меч добавлен!");
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Цена должна быть числом");
        }
    }

    private VBox createSaleForm() {
        VBox formBox = new VBox(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");

        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("Наличные", "Карта");
        paymentMethodCombo.setPrefWidth(200);

        Button saleButton = new Button("Зафиксировать продажу");
        saleButton.setPrefWidth(200);
        saleButton.setOnAction(e -> recordSale());

        formBox.getChildren().addAll(
                new Label("💰 Фиксация продажи"),
                new Label("Выберите метод оплаты:"),
                paymentMethodCombo, saleButton
        );
        return formBox;
    }

    private void recordSale() {
        Sword selectedSword = swordsTable.getSelectionModel().getSelectedItem();
        String paymentMethod = paymentMethodCombo.getValue();

        if (selectedSword == null || paymentMethod == null) {
            showAlert("Ошибка", "Выберите меч и метод оплаты");
            return;
        }

        SwordSale sale = new SwordSale(selectedSword.getId(), 1, selectedSword.getPrice(), paymentMethod);
        SalesService.recordSale(sale);

        showAlert("Успех", "Продажа зафиксирована!");
        loadSwords();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}