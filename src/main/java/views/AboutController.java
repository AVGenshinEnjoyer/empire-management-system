package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {

    // Ссылки на соцсети/проект
    private static final String INSTAGRAM_URL = "https://www.instagram.com/imperia_sumraka/";
    private static final String GITHUB_URL = "https://github.com/AVGenshinEnjoyer/empire-management-system";

    // Создаём экран "About Us" в стиле баннера с профилем
    public VBox createAboutView() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30, 40, 30, 40));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("page");


        // Баннер сверху
        ImageView bannerView = new ImageView();
        try {
            Image banner = new Image(getClass().getResourceAsStream("/Banner.jpg"));
            bannerView.setImage(banner);
        } catch (Exception ignored) {
        }
        bannerView.setPreserveRatio(true);
        bannerView.setFitWidth(800); // ширина

        // Хэштег под баннером
        Label hashtag = new Label("@Imperia_Sumraka");
        hashtag.getStyleClass().add("about-hashtag");

        // Текст о проекте
        Label line1 = new Label("Learn more about us through socials");

        line1.setWrapText(true);

        VBox textBox = new VBox(5, line1);
        textBox.setAlignment(Pos.CENTER);

        // Иконки соцсетей с ссылками
        HBox socialBox = new HBox(20);
        socialBox.setAlignment(Pos.CENTER);
        socialBox.setPadding(new Insets(20, 0, 0, 0));

        Hyperlink instagramLink = createIconLink("/Instagram.png", INSTAGRAM_URL);
        Hyperlink githubLink = createIconLink("/github.png", GITHUB_URL);

        socialBox.getChildren().addAll(instagramLink, githubLink);

        root.getChildren().addAll(bannerView, hashtag, textBox, socialBox);
        return root;
    }

    // Ссылка‑иконка
    private Hyperlink createIconLink(String iconPath, String url) {
        Hyperlink link = new Hyperlink();
        link.getStyleClass().add("about-icon-link");

        ImageView iconView = new ImageView();
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            iconView.setImage(icon);
        } catch (Exception ignored) {
        }
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        iconView.setPreserveRatio(true);

        link.setGraphic(iconView);
        link.setOnAction(e -> openInBrowser(url));
        return link;
    }

    // URL в браузере
    private void openInBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
