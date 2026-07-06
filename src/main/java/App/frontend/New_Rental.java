package App.frontend;

import App.backend.DataManager;
import App.backend.Equipment;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class New_Rental {

    private VBox myRentingsList;

    public New_Rental(Stage primaryStage) {
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

        rentalsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0284c7; -fx-font-weight: bold; -fx-border-color: transparent transparent #0284c7 transparent; -fx-border-width: 0 0 2 0;");

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
        homeButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new Home_Page(new Stage());
        });
        cartButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new My_Cart(new Stage());
        });

        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30, 40, 30, 40));

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(5);
        Label myRentingsTitle = new Label("My Rentals Directory");
        myRentingsTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 2 0 0;");
        Label myRentingsSubtitle = new Label("Manage your equipment listings and active rentals.");
        myRentingsSubtitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(myRentingsTitle, myRentingsSubtitle);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button createRentalButton = new Button("+ Create New Rental");
        createRentalButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;");

        headerBox.getChildren().addAll(titleBox, headerSpacer, createRentalButton);

        myRentingsList = new VBox(15);
        myRentingsList.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 20;");

        loadUserEquipment();

        contentBox.getChildren().addAll(headerBox, myRentingsList);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f9fafb; -fx-border-color: transparent;");

        mainBox.setCenter(scrollPane);

        Scene scene = new Scene(mainBox, 1200, 700);
        primaryStage.setTitle("EquipRent - My Rentals");
        primaryStage.setScene(scene);
        primaryStage.show();

        createRentalButton.setOnAction((ActionEvent openPopUp) -> {
            Popup popupRental = new Popup();

            VBox popupLayout = new VBox(15);
            popupLayout.setPadding(new Insets(30));
            popupLayout.setPrefWidth(500);
            popupLayout.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");

            HBox popHeader = new HBox();
            popHeader.setAlignment(Pos.CENTER_LEFT);
            Label titleLabel = new Label("Create New Rental Form");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            Region popSpacer = new Region();
            HBox.setHgrow(popSpacer, Priority.ALWAYS);
            Button closeRentalPopup = new Button("✕");
            closeRentalPopup.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-cursor: hand;");
            closeRentalPopup.setOnAction(event -> popupRental.hide());
            popHeader.getChildren().addAll(titleLabel, popSpacer, closeRentalPopup);

            FileChooser ownerInputImage = new FileChooser();
            ImageView userImage = new ImageView();
            userImage.setFitWidth(150);
            userImage.setFitHeight(150);
            userImage.setPreserveRatio(true);

            final String[] selectedImagePath = {""};

            StackPane imageUploadArea = new StackPane();
            imageUploadArea.setPrefHeight(150);
            imageUploadArea.setStyle("-fx-background-color: #f3f4f6; -fx-border-color: #d1d5db; -fx-border-style: dashed; -fx-border-radius: 6; -fx-cursor: hand;");
            Label uploadLabel = new Label("Click to Add Image");
            uploadLabel.setStyle("-fx-text-fill: #6b7280;");
            imageUploadArea.getChildren().addAll(uploadLabel, userImage);

            imageUploadArea.setOnMouseClicked(event -> {
                File ownerImage = ownerInputImage.showOpenDialog(primaryStage);
                if (ownerImage != null) {
                    selectedImagePath[0] = ownerImage.toURI().toString();
                    userImage.setImage(new Image(selectedImagePath[0]));
                    uploadLabel.setVisible(false);
                }
            });

            VBox formFields = new VBox(10);

            Label retnalNameLabel = new Label("ITEM NAME");
            retnalNameLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            TextField rentalInputName = new TextField();
            rentalInputName.setPromptText("e.g., Digital Camera");
            rentalInputName.setStyle("-fx-padding: 8; -fx-border-color: #d1d5db; -fx-border-radius: 4;");

            Label conditionLabel = new Label("CONDITION");
            conditionLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            ToggleGroup conditionGroup = new ToggleGroup();
            ToggleButton pristineButton = new ToggleButton("PRISTINE");
            ToggleButton normalButton = new ToggleButton("NORMAL WEAR");
            ToggleButton heavyButton = new ToggleButton("HEAVY WEAR");
            pristineButton.setToggleGroup(conditionGroup);
            normalButton.setToggleGroup(conditionGroup);
            heavyButton.setToggleGroup(conditionGroup);
            HBox conditionBox = new HBox(10, pristineButton, normalButton, heavyButton);

            Label catergoryTitle = new Label("TARGET CATEGORIES");
            catergoryTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

            // --- ADDED AUDIO/VISUAL GEAR HERE ---
            CheckBox computingBox = new CheckBox("Computing");
            CheckBox avBox = new CheckBox("Audio/Visual Gear");
            CheckBox outdoorBox = new CheckBox("Outdoor");
            CheckBox labToolsBox = new CheckBox("Lab Tools");
            CheckBox otherBox = new CheckBox("Other");
            VBox categoryBox = new VBox(5, computingBox, avBox, outdoorBox, labToolsBox, otherBox);

            Label costInputLabel = new Label("ESTIMATED RETAIL VALUE ($):");
            costInputLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            TextField costInputField = new TextField();
            costInputField.setPromptText("e.g. 500.00");
            costInputField.setStyle("-fx-padding: 8; -fx-border-color: #d1d5db; -fx-border-radius: 4;");

            formFields.getChildren().addAll(retnalNameLabel, rentalInputName, conditionLabel, conditionBox, catergoryTitle, categoryBox, costInputLabel, costInputField);

            VBox projectionsBox = new VBox(5);
            projectionsBox.setPadding(new Insets(15));
            projectionsBox.setStyle("-fx-background-color: #f3f4f6; -fx-background-radius: 6;");
            Label projectionTitle = new Label("SYSTEM PROJECTIONS");
            projectionTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            Label dailyrateTitle =  new Label("Generated Daily Rental Rate: $0.00");
            Label depositTitle =  new Label("Required Security Deposit: $0.00");
            dailyrateTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #0284c7;");
            depositTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
            projectionsBox.getChildren().addAll(projectionTitle, dailyrateTitle, depositTitle);

            costInputField.textProperty().addListener((obs, oldText, newText) -> {
                try {
                    double value = Double.parseDouble(newText);
                    dailyrateTitle.setText("Generated Daily Rental Rate: $" + String.format("%.2f", value * 0.05));
                    depositTitle.setText("Required Security Deposit: $" + String.format("%.2f", value * 0.40));
                } catch (Exception ex) {
                    dailyrateTitle.setText("Generated Daily Rental Rate: $0.00");
                    depositTitle.setText("Required Security Deposit: $0.00");
                }
            });

            Label errorLabel = new Label("");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px; -fx-font-weight: bold;");

            HBox footerBtns = new HBox(10);
            footerBtns.setAlignment(Pos.CENTER_RIGHT);
            Button closeButton = new Button("Cancel");
            closeButton.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-padding: 8 15; -fx-cursor: hand;");
            Button publishButton = new Button("Publish Listing");
            publishButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 8 15; -fx-cursor: hand;");
            footerBtns.getChildren().addAll(errorLabel, closeButton, publishButton);

            closeButton.setOnAction(e -> popupRental.hide());

            publishButton.setOnAction((ActionEvent publishRental) -> {
                if (rentalInputName.getText().trim().isEmpty() || costInputField.getText().trim().isEmpty()) {
                    errorLabel.setText("Item Name and Value are required!");
                    return;
                }

                double retailValue = 0;
                try {
                    retailValue = Double.parseDouble(costInputField.getText().trim());
                } catch (Exception ex) {
                    errorLabel.setText("Value must be a valid number!");
                    return;
                }

                String condition = "NORMAL WEAR";
                if(pristineButton.isSelected()) condition = "PRISTINE";
                if(heavyButton.isSelected()) condition = "HEAVY WEAR";

                String category = "";
                if(computingBox.isSelected()) category += "Computing ";
                if(avBox.isSelected()) category += "Audio/Visual Gear ";
                if(outdoorBox.isSelected()) category += "Outdoor ";
                if(labToolsBox.isSelected()) category += "Lab Tools ";
                if(otherBox.isSelected()) category += "Other";
                if(category.isEmpty()) category = "General";

                String equipmentId = UUID.randomUUID().toString().substring(0, 8);
                String name = rentalInputName.getText().trim();
                double dailyRate = retailValue * 0.05;
                double deposit = retailValue * 0.40;
                String ownerId = "USR-0001";
                String status = "AVAILABLE";

                Equipment newEq = new Equipment(equipmentId, name, category.trim(), condition, dailyRate, deposit, ownerId, status, selectedImagePath[0]);

                DataManager.addEquipment(newEq);
                loadUserEquipment();
                popupRental.hide();
            });

            popupLayout.getChildren().addAll(popHeader, imageUploadArea, formFields, projectionsBox, footerBtns);
            popupRental.getContent().add(popupLayout);

            popupRental.centerOnScreen();
            popupRental.show(primaryStage);
        });
    }

    private void loadUserEquipment() {
        myRentingsList.getChildren().clear();

        ArrayList<Equipment> allEquip = DataManager.loadEquipment();
        boolean hasItems = false;

        for (Equipment eq : allEquip) {
            if (eq.getOwnerId().equals("USR-0001")) {
                hasItems = true;

                HBox entry = new HBox(15);
                entry.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-padding: 10;");
                entry.setAlignment(Pos.CENTER_LEFT);

                Node imageNode;
                if (eq.getImagePath() != null && !eq.getImagePath().isEmpty()) {
                    ImageView imgView = new ImageView();
                    try {
                        if (eq.getImagePath().startsWith("file:") || eq.getImagePath().startsWith("http")) {
                            imgView.setImage(new Image(eq.getImagePath()));
                        } else {
                            imgView.setImage(new Image(getClass().getResource(eq.getImagePath()).toExternalForm()));
                        }
                        imgView.setFitWidth(60);
                        imgView.setFitHeight(60);
                        imgView.setPreserveRatio(true);
                    } catch (Exception ex) {}
                    imageNode = imgView;
                } else {
                    Region placeholder = new Region();
                    placeholder.setPrefSize(60, 60);
                    placeholder.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 4;");
                    imageNode = placeholder;
                }

                VBox entryText = new VBox(3);
                Label entryName = new Label(eq.getName());
                entryName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                Label entryCats = new Label(eq.getCategory());
                entryCats.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
                entryText.getChildren().addAll(entryName, entryCats);

                Region entrySpacer = new Region();
                HBox.setHgrow(entrySpacer, Priority.ALWAYS);

                VBox entryDetails = new VBox(5);
                entryDetails.setAlignment(Pos.CENTER_RIGHT);
                Label entryCond = new Label(eq.getCondition());
                entryCond.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 12px;");
                Label entryCost = new Label("$" + String.format("%.2f", eq.getDailyRate()) + "/day");
                entryCost.setStyle("-fx-font-weight: bold;");

                Button deleteButton = new Button("Delete");
                deleteButton.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 4; -fx-cursor: hand;");
                deleteButton.setOnAction(e -> {
                    DataManager.deleteEquipment(eq.getId());
                    loadUserEquipment();
                });

                entryDetails.getChildren().addAll(entryCond, entryCost, deleteButton);

                entry.getChildren().addAll(imageNode, entryText, entrySpacer, entryDetails);
                myRentingsList.getChildren().add(entry);
            }
        }

        if (!hasItems) {
            Label emptyLabel = new Label("You have no equipment listed yet.");
            emptyLabel.setStyle("-fx-text-fill: #9ca3af; -fx-font-style: italic;");
            myRentingsList.getChildren().add(emptyLabel);
        }
    }
}