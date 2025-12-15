import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import views.*;
import views.AboutController;

public class Main extends Application {

    // Данные пользователя справа сверху
    private static final String USER_NAME = "Renat Arslanov";
    private static final String USER_ROLE = "Owner";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Imperia Sumraka software");

        // Корневой layout: слева меню, сверху header, по центру содержимое
        BorderPane root = new BorderPane();

        // Область, куда будем подменять экраны (Dashboard, Clients и т.д.)
        StackPane contentPane = new StackPane();
        contentPane.setPadding(new Insets(15));

        // Контроллеры экранов
        DashboardController dashboardController = new DashboardController();
        ClientsViewController clientsViewController = new ClientsViewController();
        TrainingsViewController trainingsViewController = new TrainingsViewController();
        SalesViewController salesViewController = new SalesViewController();
        AboutController aboutController = new AboutController();

        // Экран по умолчанию – Dashboard
        contentPane.getChildren().setAll(dashboardController.createDashboard());

        // Боковое меню слева
        VBox sidebar = createSidebar(
                contentPane,
                dashboardController,
                clientsViewController,
                trainingsViewController,
                salesViewController,
                aboutController
        );

        // Верхняя панель с именем пользователя и аватаркой
        HBox header = createHeader();

        root.setLeft(sidebar);
        root.setTop(header);
        root.setCenter(contentPane);

        Scene scene = new Scene(root, 1400, 900);
        // Общий CSS
        scene.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Создание бокового меню
    private VBox createSidebar(StackPane contentPane,
                               DashboardController dashboardController,
                               ClientsViewController clientsViewController,
                               TrainingsViewController trainingsViewController,
                               SalesViewController salesViewController,
                               AboutController aboutController) {

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(220);
        sidebar.getStyleClass().add("sidebar");

        Label logoLabel = new Label("Imperia Sumraka");
        logoLabel.getStyleClass().add("sidebar-logo");

        SideBarButton dashboardBtn = new SideBarButton("Dashboard");
        SideBarButton clientsBtn = new SideBarButton("Clients");
        SideBarButton trainingsBtn = new SideBarButton("Trainings");
        SideBarButton salesBtn = new SideBarButton("Sales");
        SideBarButton aboutBtn = new SideBarButton("About Us");

        // Список всех кнопок для сброса стилей
        SideBarButton[] buttons = {dashboardBtn, clientsBtn, trainingsBtn, salesBtn, aboutBtn};

        // Функция, которая делает одну кнопку активной
        Runnable resetButtons = () -> {
            for (SideBarButton b : buttons) {
                b.setActive(false);
            }
        };

        // Обработчики кликов
        dashboardBtn.setOnMouseClicked(e -> {
            contentPane.getChildren().setAll(dashboardController.createDashboard());
            resetButtons.run();
            dashboardBtn.setActive(true);
        });

        clientsBtn.setOnMouseClicked(e -> {
            contentPane.getChildren().setAll(clientsViewController.createClientView());
            resetButtons.run();
            clientsBtn.setActive(true);
        });

        trainingsBtn.setOnMouseClicked(e -> {
            contentPane.getChildren().setAll(trainingsViewController.createTrainingsView());
            resetButtons.run();
            trainingsBtn.setActive(true);
        });

        salesBtn.setOnMouseClicked(e -> {
            contentPane.getChildren().setAll(salesViewController.createSalesView());
            resetButtons.run();
            salesBtn.setActive(true);
        });

        aboutBtn.setOnMouseClicked(e -> {
            contentPane.getChildren().setAll(aboutController.createAboutView());
            resetButtons.run();
            aboutBtn.setActive(true);
        });

        // По умолчанию активен Dashboard
        dashboardBtn.setActive(true);

        sidebar.getChildren().addAll(
                logoLabel,
                new Separator(),
                dashboardBtn,
                clientsBtn,
                trainingsBtn,
                salesBtn,
                new Separator(),
                aboutBtn
        );

        return sidebar;
    }


    // Header с логином и аватаркой
    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.getStyleClass().add("top-header");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Аватарка
        ImageView avatarView = new ImageView();
        try {
            Image avatar = new Image(getClass().getResourceAsStream("/ImperiaAvatar.jpg"));
            avatarView.setImage(avatar);
        } catch (Exception ex){};
        avatarView.setFitWidth(36);
        avatarView.setFitHeight(36);
        avatarView.setPreserveRatio(true);
        avatarView.getStyleClass().add("user-avatar-placeholder");

        VBox userBox = new VBox(2);
        userBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(USER_NAME);
        nameLabel.getStyleClass().add("user-name-label");

        Label roleLabel = new Label(USER_ROLE);
        roleLabel.getStyleClass().add("user-role-label");

        userBox.getChildren().addAll(nameLabel, roleLabel);

        header.getChildren().addAll(spacer, avatarView, userBox);
        return header;
    }
}

