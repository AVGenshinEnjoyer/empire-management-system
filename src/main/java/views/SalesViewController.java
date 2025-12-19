package views;// views/SalesViewController.java
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.DoubleStringConverter;
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

    private void loadSwords(){
        ObservableList<Sword> data = FXCollections.observableArrayList();

        data.addAll(
                new Sword("Waterfall Sword", "/images/waterfall.png", 5995),
                new Sword("Guardian’s Grace Sword", "/images/Guardian’s Grace.png", 5995),
                new Sword("Skybreaker Sword", "/images/Skybreaker.png", 5995),
                new Sword("Fernspire ", "/images/Fernspire.png", 5995),
                new Sword("Diablo Sword", "/images/Diablo.png", 7995),
                new Sword("Titanfall Sword", "/images/Titanfall.png", 6995));

        data.addAll(SwordService.getAllSwords());
        swordsTable.setItems(data);
    }



    private VBox createCatalogForm() {
        VBox formBox = new VBox(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");

        swordModelField = new TextField();
        swordModelField.setPromptText("Sword model");
        swordModelField.setPrefWidth(300);

        swordPriceField = new TextField();
        swordPriceField.setPromptText("Price");
        swordPriceField.setPrefWidth(300);

        Button addButton = new Button("Add new sword");
        addButton.setPrefWidth(200);
        addButton.setOnAction(e -> addNewSword());

        formBox.getChildren().addAll(
                new Label("New sword:"),
                swordModelField, swordPriceField, addButton
        );
        return formBox;
    }

    private void setupSwordsTable() {
        // ID - прибавление по одному
        TableColumn<Sword, Void> idCol = new TableColumn<>("ID");
        idCol.setPrefWidth(50);
        idCol.setSortable(false);
        idCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : String.valueOf(getIndex() + 1));
            }
        });


        TableColumn<Sword, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModel()));
        modelCol.setPrefWidth(150);

        TableColumn<Sword, Double> priceCol = new TableColumn<>("₸Price");
        priceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        priceCol.setPrefWidth(100);

        TableColumn<Sword, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStockStatus()));
        statusCol.setPrefWidth(120);




        // Меняем наличие мечей(In Stock, Out Of Stock)
        swordsTable.setEditable(true);

        statusCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList("In stock", "Out of stock")));


        // Возможность менять значения в таблице, не трогая код
        statusCol.setOnEditCommit(e -> {
            Sword s = e.getRowValue();
            s.setStockStatus(e.getNewValue());
            SwordService.updateSword(s);
        });

        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceCol.setOnEditCommit(e -> {
            Sword s = e.getRowValue();
            s.setPrice(e.getNewValue());
            SwordService.updateSword(s);
        });


        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(e -> {
            Sword s = e.getRowValue();
            s.setModel(e.getNewValue());
            SwordService.updateSword(s);        
        });




        swordsTable.getColumns().addAll(idCol, modelCol, priceCol, statusCol);
        swordsTable.setPrefHeight(300);
    }


    private void addNewSword() {
        String model = swordModelField.getText();
        String priceStr = swordPriceField.getText();

        if (model.isEmpty() || priceStr.isEmpty()) {
            showAlert("Error", "Fill in all fields!");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            Sword sword = new Sword(model, "standard", price);
            SwordService.addSword(sword);

            swordModelField.clear();
            swordPriceField.clear();

            loadSwords();
            showAlert("Great", "Sword added!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be a number!");
        }
    }

    private VBox createSaleForm() {
        VBox formBox = new VBox(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");

        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("Cash", "Card");
        paymentMethodCombo.setPrefWidth(200);

        Button saleButton = new Button("Confirm sale");
        saleButton.setPrefWidth(200);
        saleButton.setOnAction(e -> recordSale());

        formBox.getChildren().addAll(
                new Label("💰 Sale summary:"),
                new Label("Choose payment method:"),
                paymentMethodCombo, saleButton
        );
        return formBox;
    }

    private void recordSale() {
        Sword selectedSword = swordsTable.getSelectionModel().getSelectedItem();
        String paymentMethod = paymentMethodCombo.getValue();

        if (selectedSword == null || paymentMethod == null) {
            showAlert("Error", "Choose sword and payment method!");
            return;
        }

        SwordSale sale = new SwordSale(selectedSword.getId(), 1, selectedSword.getPrice(), paymentMethod);
        SalesService.recordSale(sale);

        showAlert("Great", "Sale recorded!");
        loadSwords();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}