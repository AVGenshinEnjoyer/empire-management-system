package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AboutController {

    // Создаём простой экран "About Us"
    public VBox createAboutView() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.TOP_LEFT);

        // Заголовок секции
        Label title = new Label("About Empire of Dusk");
        title.getStyleClass().add("section-title");

        // Основной текст
        Label text1 = new Label(
                "Empire of Dusk is a small ERP-like demo application for managing a fencing club."
        );
        Label text2 = new Label(
                "The system demonstrates client management, training schedules, sword sales, " +
                        "simple inventory, and analytics dashboards."
        );
        Label text3 = new Label(
                "The project is built with Java 17, JavaFX, and in-memory storage (no database) " +
                        "for educational purposes."
        );

        box.getChildren().addAll(title, text1, text2, text3);
        return box;
    }
}
