package views;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

// Боковое меню
public class SideBarButton extends HBox {

    private final Label label;

    public SideBarButton(String text) {
        label = new Label(text);
        label.getStyleClass().add("sidebar-button-label");

        this.getChildren().add(label);
        this.getStyleClass().add("sidebar-button");
        this.setPadding(new Insets(8, 12, 8, 12));
        this.setCursor(Cursor.HAND);
    }

    // Подсветка кнопки синим цветом слева
    public void setActive(boolean active) {
        this.getStyleClass().remove("sidebar-button-active");
        if (active) {
            this.getStyleClass().add("sidebar-button-active");
        }
    }
}
