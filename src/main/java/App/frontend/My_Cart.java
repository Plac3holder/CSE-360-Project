package App.frontend;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class My_Cart {

    public VBox itemsBox;
    public VBox summaryBox;

    public My_Cart(Stage primaryStage) {
        BorderPane mainBox = new BorderPane();
        mainBox.setStyle("-fx-background-color: #f9fafb;");

        // --- Start of Navigation Bar ---
        BorderPane navigationMainBox = new BorderPane();
        navigationMainBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        navigationMainBox.setPadding(new Insets(10, 20, 10, 20));

        HBox navigationLeftBar = new HBox(20);
        navigationLeftBar.setAlignment(Pos.CENTER_LEFT);

        Label pageTitle = new Label("EquipRent");
        pageTitle.setStyle("-fx-font-size:22px; -fx-font-weight:bold; -fx-text-fill:#0ea5e9; -fx-padding: 0 2 0 0;");

        Button homeButton = new Button("Home");
        Button rentingButton = new Button("My Renting");
        Button cartButton = new Button("My Cart");
        Button rentalsButton = new Button("My Rentals");

        Button[] buttons = {homeButton, rentingButton, cartButton, rentalsButton};
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-font-weight: bold;");
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-font-weight: bold;"));
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #111827; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 5;"));
        }

        navigationLeftBar.getChildren().addAll(pageTitle, homeButton, rentingButton, cartButton, rentalsButton);

        HBox navigationRightBar = new HBox(15);
        navigationRightBar.setAlignment(Pos.CENTER_RIGHT);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(250);
        searchBar.setStyle("-fx-background-color: #f3f4f6; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 5 10;");

        Button profileButton = new Button("💀");
        profileButton.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius: 20; -fx-min-width: 40; -fx-min-height: 40; -fx-max-width: 40; -fx-max-height: 40; -fx-font-size: 16px; -fx-padding: 0; -fx-alignment: center;");

        navigationRightBar.getChildren().addAll(searchBar, profileButton);

        navigationMainBox.setLeft(navigationLeftBar);
        navigationMainBox.setRight(navigationRightBar);
        mainBox.setTop(navigationMainBox);
        // --- End of Navigation Bar ---

        // --- Navigation Button Actions ---
        rentingButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new Active_Renting(new Stage());
        });
        rentalsButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new New_Rental(new Stage());
        });
        homeButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new Home_Page(new Stage());
        });

        // --- Main Content Area (Review Cart) ---
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30, 40, 30, 40));

        Label reviewLabel = new Label("Review Cart");
        reviewLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 2 0 0;");
        Label reviewSubtitle = new Label("Verify your equipment selection before finalizing the reservation.");
        reviewSubtitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        VBox titleBox = new VBox(5, reviewLabel, reviewSubtitle);

        HBox cartLayout = new HBox(30);

        itemsBox = new VBox(15);
        itemsBox.setPrefWidth(700);

        summaryBox = new VBox(15);
        summaryBox.setPrefWidth(350);
        summaryBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 20;");

        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        summaryBox.getChildren().addAll(
                summaryTitle,
                createSummaryRow("Equipment Subtotal (0 items)", "$0.00", false),
                createSummaryRow("Service Fee", "$0.00", false),
                createSummaryRow("Estimated Taxes", "$0.00", false),
                new Label("--------------------------------------------------"),
                createSummaryRow("Total Cost", "$0.00", true)
        );

        Button finalizeButton = new Button("Finalize Purchase");
        finalizeButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20; -fx-background-radius: 6; -fx-cursor: hand;");
        finalizeButton.setPrefWidth(Double.MAX_VALUE);

        Label disclaimer = new Label("You won't be charged until the owner confirms.");
        disclaimer.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        disclaimer.setAlignment(Pos.CENTER);
        disclaimer.setMaxWidth(Double.MAX_VALUE);

        summaryBox.getChildren().addAll(finalizeButton, disclaimer);

        cartLayout.getChildren().addAll(itemsBox, summaryBox);
        contentBox.getChildren().addAll(titleBox, cartLayout);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f9fafb; -fx-border-color: transparent;");

        mainBox.setCenter(scrollPane);

        Scene scene = new Scene(mainBox, 1200, 700);
        primaryStage.setTitle("EquipRent - Review Cart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox createCartItem(String title, String subtitle, String price) {
        HBox itemBox = new HBox(15);
        itemBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        itemBox.setAlignment(Pos.CENTER_LEFT);

        Region imagePlaceholder = new Region();
        imagePlaceholder.setPrefSize(80, 80);
        imagePlaceholder.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 4;");

        VBox textInfo = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 2 0 0;");
        Label subLabel = new Label(subtitle);
        subLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");
        textInfo.getChildren().addAll(titleLabel, subLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox priceInfo = new VBox();
        priceInfo.setAlignment(Pos.CENTER_RIGHT);
        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label perDayLabel = new Label("/day");
        perDayLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
        priceInfo.getChildren().addAll(priceLabel, perDayLabel);

        itemBox.getChildren().addAll(imagePlaceholder, textInfo, spacer, priceInfo);
        return itemBox;
    }

    public HBox createSummaryRow(String label, String value, boolean isTotal) {
        HBox row = new HBox();
        Label left = new Label(label);
        Label right = new Label(value);

        if (isTotal) {
            left.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            right.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        } else {
            left.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px;");
            right.setStyle("-fx-font-size: 14px;");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(left, spacer, right);
        return row;
    }
}