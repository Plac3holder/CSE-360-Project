package App.frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Home_Page extends Application {
    public Home_Page(Stage primaryStage) {
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

        // --- Categories Layout ---
        VBox categoriesBox = new VBox(30);
        categoriesBox.setPadding(new Insets(30, 40, 30, 40));
        categoriesBox.setStyle("-fx-background-color: transparent;");

        categoriesBox.getChildren().add(createCategory("Computing Devices",
                new Rental_Box_Info("MacBook Pro M2 16\"", "High-performance workstation for video editing...", "$45/day", "PRISTINE", "/App/images/macbook.png"),
                new Rental_Box_Info("Dell XPS 15", "Solid all-around Windows laptop for general office use...", "$30/day", "NORMAL WEAR", "/App/images/dell.png")));

        categoriesBox.getChildren().add(createCategory("Audio/Visual Gear",
                new Rental_Box_Info("Sony A7S III Kit", "Includes 24-70mm GM lens, 3 batteries...", "$85/day", "PRISTINE", "/App/images/camera.png"),
                new Rental_Box_Info("DJI Mavic 3 Pro", "Fly More Combo included. Perfect for cinematic...", "$60/day", "NORMAL WEAR", "/App/images/drone.png")));

        ScrollPane scrollCategories = new ScrollPane(categoriesBox);
        scrollCategories.setFitToWidth(true);
        scrollCategories.setStyle("-fx-background-color: transparent; -fx-background: #f9fafb; -fx-border-color: transparent;");
        mainBox.setCenter(scrollCategories);
        // --- End Of Categories Layout ---

        // --- Navigation Button Actions ---
        rentingButton.setOnAction((ActionEvent event) -> {
            primaryStage.close();
            new Active_Renting(new Stage());
        });
        rentalsButton.setOnAction((ActionEvent event) -> {
            primaryStage.close();
            new New_Rental(new Stage());
        });
        cartButton.setOnAction((ActionEvent event) -> {
            primaryStage.close();
            new My_Cart(new Stage());
        });

        Scene scene = new Scene(mainBox, 1200, 700);
        primaryStage.setTitle("EquipRent - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox createCategory(String categoryTitle, Rental_Box_Info... boxInfos) {
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label categoryLabel = new Label(categoryTitle);
        categoryLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label viewAllLabel = new Label("View All");
        viewAllLabel.setStyle("-fx-text-fill: #0284c7; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand;");

        headerBox.getChildren().addAll(categoryLabel, spacer, viewAllLabel);

        FlowPane rentalInfoBox = new FlowPane();
        rentalInfoBox.setHgap(20);
        rentalInfoBox.setVgap(20);
        for (Rental_Box_Info boxInfo : boxInfos) {
            rentalInfoBox.getChildren().add(boxInfo);
        }

        VBox categoryContainer = new VBox(15);
        categoryContainer.getChildren().addAll(headerBox, rentalInfoBox);
        return categoryContainer;
    }

    @Override
    public void start(Stage arg0) {}
}

class Rental_Box_Info extends VBox {
    public Rental_Box_Info(String title, String description, String price, String condition, String imageFile) {
        setSpacing(10);
        setPadding(new Insets(0, 0, 15, 0));
        setPrefWidth(240);
        setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;");

        ImageView rentalImage = new ImageView();
        try {
            String imagePath = getClass().getResource(imageFile).toExternalForm();
            rentalImage.setImage(new Image(imagePath));
        } catch (Exception e) {
            System.out.println("Could not find image: " + imageFile);
        }
        rentalImage.setFitWidth(238);
        rentalImage.setFitHeight(140);

        StackPane imageWrapper = new StackPane(rentalImage);
        imageWrapper.setStyle("-fx-background-color: #374151; -fx-background-radius: 4 4 0 0;");
        imageWrapper.setPrefHeight(140);

        Label conditionBadge = new Label(condition);
        if (condition.equalsIgnoreCase("PRISTINE")) {
            conditionBadge.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 2;");
        } else {
            conditionBadge.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #4b5563; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 2; -fx-border-color: #d1d5db; -fx-border-radius: 2;");
        }

        StackPane topArea = new StackPane(imageWrapper, conditionBadge);
        StackPane.setAlignment(conditionBadge, Pos.TOP_RIGHT);
        StackPane.setMargin(conditionBadge, new Insets(10, 10, 0, 0));

        VBox textContent = new VBox(5);
        textContent.setPadding(new Insets(10, 15, 0, 15));

        Label rentalTitle = new Label(title);
        rentalTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #111827;");

        Label rentalDesc = new Label(description);
        rentalDesc.setWrapText(true);
        rentalDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        rentalDesc.setPrefHeight(40);

        textContent.getChildren().addAll(rentalTitle, rentalDesc);

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #111827;");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 16; -fx-background-radius: 4; -fx-cursor: hand;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomRow = new HBox(priceLabel, spacer, addButton);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.setPadding(new Insets(10, 15, 0, 15));

        getChildren().addAll(topArea, textContent, bottomRow);
    }
}