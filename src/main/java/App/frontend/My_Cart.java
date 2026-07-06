package App.frontend;

import App.backend.DataManager;
import App.backend.Equipment;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class My_Cart {

    public static ArrayList<Equipment> cart = new ArrayList<>();

    public VBox itemsBox;
    public VBox summaryBox;

    // Trackers for dynamic math
    private double baseDailyTotal = 0.0;
    private double baseDepositTotal = 0.0;

    public My_Cart(Stage primaryStage) {
        BorderPane mainBox = new BorderPane();
        mainBox.setStyle("-fx-background-color: #f9fafb;");

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

        cartButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0284c7; -fx-font-weight: bold; -fx-border-color: transparent transparent #0284c7 transparent; -fx-border-width: 0 0 2 0;");

        navigationLeftBar.getChildren().addAll(pageTitle, homeButton, rentingButton, cartButton, rentalsButton);

        HBox navigationRightBar = new HBox(15);
        navigationRightBar.setAlignment(Pos.CENTER_RIGHT);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(250);
        searchBar.setStyle("-fx-background-color: #f3f4f6; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 5 10;");

        Button profileButton = new Button("💀");
        profileButton.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius: 20; -fx-min-width: 40; -fx-min-height: 40; -fx-max-width: 40; -fx-max-height: 40; -fx-font-size: 16px; -fx-padding: 0; -fx-alignment: center;");

        profileButton.setOnAction(e -> {
            primaryStage.close();
            new Admin_Dashboard(new Stage());
        });

        navigationRightBar.getChildren().addAll(searchBar, profileButton);
        navigationMainBox.setLeft(navigationLeftBar);
        navigationMainBox.setRight(navigationRightBar);
        mainBox.setTop(navigationMainBox);

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

        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30, 40, 30, 40));

        Label reviewLabel = new Label("Review Cart");
        reviewLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 2 0 0;");
        Label reviewSubtitle = new Label("Verify your equipment selection and duration before finalizing.");
        reviewSubtitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        VBox titleBox = new VBox(5, reviewLabel, reviewSubtitle);

        HBox cartLayout = new HBox(30);

        itemsBox = new VBox(15);
        itemsBox.setPrefWidth(700);

        for (Equipment eq : cart) {
            baseDailyTotal += eq.getDailyRate();
            baseDepositTotal += eq.getDeposit();
            itemsBox.getChildren().add(createCartItem(eq));
        }

        if (cart.isEmpty()) {
            Label emptyLabel = new Label("Your cart is completely empty.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #6b7280; -fx-font-style: italic;");
            itemsBox.getChildren().add(emptyLabel);
        }

        summaryBox = new VBox(15);
        summaryBox.setPrefWidth(350);
        summaryBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 20;");

        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // --- ADDED NUMBER OF DAYS INPUT ---
        VBox daysBox = new VBox(5);
        Label daysLabel = new Label("Rental Duration (Days):");
        daysLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #4b5563;");
        TextField daysInput = new TextField("1");
        daysInput.setStyle("-fx-padding: 8; -fx-border-color: #d1d5db; -fx-border-radius: 4;");
        daysBox.getChildren().addAll(daysLabel, daysInput);

        VBox dynamicTotalsBox = new VBox(10); // We will update this dynamically

        Button finalizeButton = new Button("Finalize Purchase");
        finalizeButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 12 20; -fx-background-radius: 6; -fx-cursor: hand;");
        finalizeButton.setPrefWidth(Double.MAX_VALUE);

        if (cart.isEmpty()) finalizeButton.setDisable(true);

        // Method to recalculate the totals
        Runnable updateMath = () -> {
            dynamicTotalsBox.getChildren().clear();
            int days = 1;
            try {
                days = Integer.parseInt(daysInput.getText().trim());
                if (days < 1) days = 1;
            } catch (Exception ignored) {}

            double rentalFee = baseDailyTotal * days;
            double serviceFee = rentalFee * 0.10;
            double tax = rentalFee * 0.08;
            double finalTotal = rentalFee + baseDepositTotal + serviceFee + tax;

            dynamicTotalsBox.getChildren().addAll(
                    createSummaryRow("Rental Fee (" + days + " days)", "$" + String.format("%.2f", rentalFee), false),
                    createSummaryRow("Refundable Deposit", "$" + String.format("%.2f", baseDepositTotal), false),
                    createSummaryRow("Service Fee", "$" + String.format("%.2f", serviceFee), false),
                    createSummaryRow("Estimated Taxes", "$" + String.format("%.2f", tax), false),
                    new Label("--------------------------------------------------"),
                    createSummaryRow("Total Cost", "$" + String.format("%.2f", finalTotal), true)
            );
        };

        // Recalculate anytime the user types a new number
        daysInput.textProperty().addListener((obs, oldVal, newVal) -> updateMath.run());
        updateMath.run(); // Initial calculation

        finalizeButton.setOnAction(e -> {
            int finalDays = 1;
            try { finalDays = Integer.parseInt(daysInput.getText().trim()); } catch (Exception ignored) {}

            for (Equipment eq : cart) {
                DataManager.checkoutEquipment(eq.getId(), "USR-0001", finalDays + " Days");
            }
            cart.clear();

            primaryStage.close();
            new Active_Renting(new Stage());
        });

        Label disclaimer = new Label("You won't be charged until the owner confirms.");
        disclaimer.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        disclaimer.setAlignment(Pos.CENTER);
        disclaimer.setMaxWidth(Double.MAX_VALUE);

        summaryBox.getChildren().addAll(summaryTitle, daysBox, dynamicTotalsBox, finalizeButton, disclaimer);

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

    public HBox createCartItem(Equipment eq) {
        HBox itemBox = new HBox(15);
        itemBox.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        itemBox.setAlignment(Pos.CENTER_LEFT);

        Node imageNode;
        if (eq.getImagePath() != null && !eq.getImagePath().trim().isEmpty()) {
            ImageView imgView = new ImageView();
            try {
                if (eq.getImagePath().startsWith("file:") || eq.getImagePath().startsWith("http")) {
                    imgView.setImage(new Image(eq.getImagePath()));
                } else {
                    imgView.setImage(new Image(getClass().getResource(eq.getImagePath()).toExternalForm()));
                }
                imgView.setFitWidth(80);
                imgView.setFitHeight(80);
                imgView.setPreserveRatio(true);
            } catch (Exception ex) {}
            imageNode = imgView;
        } else {
            Region placeholder = new Region();
            placeholder.setPrefSize(80, 80);
            placeholder.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 4;");
            imageNode = placeholder;
        }

        VBox textInfo = new VBox(5);
        Label titleLabel = new Label(eq.getName());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 2 0 0;");
        Label subLabel = new Label("Deposit: $" + String.format("%.2f", eq.getDeposit()) + " | " + eq.getCondition());
        subLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");
        textInfo.getChildren().addAll(titleLabel, subLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox priceInfo = new VBox();
        priceInfo.setAlignment(Pos.CENTER_RIGHT);
        Label priceLabel = new Label("$" + String.format("%.2f", eq.getDailyRate()));
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label perDayLabel = new Label("/day");
        perDayLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");

        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-font-size: 11px; -fx-cursor: hand; -fx-padding: 5 0 0 0;");
        removeBtn.setOnAction(e -> {
            cart.removeIf(cartItem -> cartItem.getId().equals(eq.getId()));
            Stage currentStage = (Stage) removeBtn.getScene().getWindow();
            currentStage.close();
            new My_Cart(new Stage());
        });

        priceInfo.getChildren().addAll(priceLabel, perDayLabel, removeBtn);

        itemBox.getChildren().addAll(imageNode, textInfo, spacer, priceInfo);
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