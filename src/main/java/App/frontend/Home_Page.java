package App.frontend;

import App.backend.DataManager;
import App.backend.Equipment;

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

import java.util.ArrayList;
import java.util.HashMap;

public class Home_Page {

    public VBox categoriesBox;

    public Home_Page(Stage primaryStage) {
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

        homeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0284c7; -fx-font-weight: bold; -fx-border-color: transparent transparent #0284c7 transparent; -fx-border-width: 0 0 2 0;");

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

        categoriesBox = new VBox(30);
        categoriesBox.setPadding(new Insets(30, 40, 30, 40));
        categoriesBox.setStyle("-fx-background-color: transparent;");

        loadMarketplaceEquipment();

        ScrollPane scrollCategories = new ScrollPane(categoriesBox);
        scrollCategories.setFitToWidth(true);
        scrollCategories.setStyle("-fx-background-color: transparent; -fx-background: #f9fafb; -fx-border-color: transparent;");
        mainBox.setCenter(scrollCategories);

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

    private void loadMarketplaceEquipment() {
        categoriesBox.getChildren().clear();
        ArrayList<Equipment> allEquip = DataManager.loadEquipment();

        HashMap<String, ArrayList<Rental_Box_Info>> categorizedItems = new HashMap<>();

        for (Equipment eq : allEquip) {
            // Hides items that you own so you don't rent your own stuff!
            if (eq.getStatus().equals("AVAILABLE")) {                String cat = eq.getCategory();
                categorizedItems.putIfAbsent(cat, new ArrayList<>());

                Rental_Box_Info box = new Rental_Box_Info(eq);
                categorizedItems.get(cat).add(box);
            }
        }

        for (String cat : categorizedItems.keySet()) {
            ArrayList<Rental_Box_Info> boxes = categorizedItems.get(cat);
            Rental_Box_Info[] boxArray = boxes.toArray(new Rental_Box_Info[0]);
            categoriesBox.getChildren().add(createCategory(cat, boxArray));
        }

        if (categorizedItems.isEmpty()) {
            Label emptyLabel = new Label("No equipment available right now. Check back later!");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #6b7280;");
            categoriesBox.getChildren().add(emptyLabel);
        }
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
}

class Rental_Box_Info extends VBox {

    // Now completely wired to accept the full Equipment object!
    public Rental_Box_Info(Equipment eq) {
        setSpacing(10);
        setPadding(new Insets(0, 0, 15, 0));
        setPrefWidth(240);
        setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;");

        ImageView rentalImage = new ImageView();
        String imageFile = eq.getImagePath();

        if (imageFile != null && !imageFile.trim().isEmpty()) {
            try {
                if (imageFile.startsWith("file:") || imageFile.startsWith("http")) {
                    rentalImage.setImage(new Image(imageFile));
                } else {
                    String imagePath = java.util.Objects.requireNonNull(getClass().getResource(imageFile)).toExternalForm();
                    rentalImage.setImage(new Image(imagePath));
                }
            } catch (Exception e) {
                System.out.println("Could not load image: " + imageFile);
            }
        }

        rentalImage.setFitWidth(238);
        rentalImage.setFitHeight(140);
        rentalImage.setPreserveRatio(true);

        StackPane imageWrapper = new StackPane(rentalImage);
        imageWrapper.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 4 4 0 0;");
        imageWrapper.setPrefHeight(140);

        Label conditionBadge = new Label(eq.getCondition());
        if (eq.getCondition().equalsIgnoreCase("PRISTINE")) {
            conditionBadge.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 2;");
        } else {
            conditionBadge.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #4b5563; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 2; -fx-border-color: #d1d5db; -fx-border-radius: 2;");
        }

        StackPane topArea = new StackPane(imageWrapper, conditionBadge);
        StackPane.setAlignment(conditionBadge, Pos.TOP_RIGHT);
        StackPane.setMargin(conditionBadge, new Insets(10, 10, 0, 0));

        VBox textContent = new VBox(5);
        textContent.setPadding(new Insets(10, 15, 0, 15));

        Label rentalTitle = new Label(eq.getName());
        rentalTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #111827;");

        Label rentalDesc = new Label("Owner: " + eq.getOwnerId());
        rentalDesc.setWrapText(true);
        rentalDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        rentalDesc.setPrefHeight(40);

        textContent.getChildren().addAll(rentalTitle, rentalDesc);

        Label priceLabel = new Label("$" + String.format("%.2f", eq.getDailyRate()) + "/day");
        priceLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #111827;");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 16; -fx-background-radius: 4; -fx-cursor: hand;");

        // Smarter Add Button Logic
        addButton.setOnAction(e -> {
            boolean alreadyInCart = false;
            for (Equipment cartItem : My_Cart.cart) {
                if (cartItem.getId().equals(eq.getId())) {
                    alreadyInCart = true;
                    break;
                }
            }

            if (!alreadyInCart) {
                My_Cart.cart.add(eq);
                addButton.setText("Added!");
                addButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 16; -fx-background-radius: 4;");
            } else {
                addButton.setText("In Cart");
                addButton.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 16; -fx-background-radius: 4;");
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomRow = new HBox(priceLabel, spacer, addButton);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.setPadding(new Insets(10, 15, 0, 15));

        getChildren().addAll(topArea, textContent, bottomRow);
    }
}