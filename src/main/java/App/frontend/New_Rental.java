package App.frontend;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.io.FileWriter;
import java.io.IOException;

public class New_Rental {
    public New_Rental(Stage primaryStage) {
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
        homeButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new Home_Page(new Stage());
        });
        cartButton.setOnAction((ActionEvent e) -> {
            primaryStage.close();
            new My_Cart(new Stage());
        });

        // --- Main Content Area ---
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

        VBox myRentingsList = new VBox(15);
        myRentingsList.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 20;");

        contentBox.getChildren().addAll(headerBox, myRentingsList);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f9fafb; -fx-border-color: transparent;");

        mainBox.setCenter(scrollPane);

        Scene scene = new Scene(mainBox, 1200, 700);
        primaryStage.setTitle("EquipRent - My Rentals");
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- Popup Logic ---
        createRentalButton.setOnAction((ActionEvent openPopUp) -> {
            rentalItem newRental = new rentalItem();
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

            StackPane imageUploadArea = new StackPane();
            imageUploadArea.setPrefHeight(150);
            imageUploadArea.setStyle("-fx-background-color: #f3f4f6; -fx-border-color: #d1d5db; -fx-border-style: dashed; -fx-border-radius: 6; -fx-cursor: hand;");
            Label uploadLabel = new Label("Click to Add Image");
            uploadLabel.setStyle("-fx-text-fill: #6b7280;");
            imageUploadArea.getChildren().addAll(uploadLabel, userImage);

            imageUploadArea.setOnMouseClicked(event -> {
                File ownerImage = ownerInputImage.showOpenDialog(primaryStage);
                if (ownerImage != null) {
                    Image tempImage = new Image(ownerImage.toURI().toString());
                    userImage.setImage(tempImage);
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
            ToggleButton pristineButton = new ToggleButton("Pristine");
            ToggleButton normalButton = new ToggleButton("Normal Wear");
            ToggleButton heavyButton = new ToggleButton("Heavy Wear");
            pristineButton.setToggleGroup(conditionGroup);
            normalButton.setToggleGroup(conditionGroup);
            heavyButton.setToggleGroup(conditionGroup);
            HBox conditionBox = new HBox(10, pristineButton, normalButton, heavyButton);

            Label catergoryTitle = new Label("TARGET CATEGORIES");
            catergoryTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            CheckBox computingBox = new CheckBox("Computing");
            CheckBox outdoorBox = new CheckBox("Outdoor");
            CheckBox labToolsBox = new CheckBox("Lab Tools");
            CheckBox otherBox = new CheckBox("Other");
            VBox categoryBox = new VBox(5, computingBox, outdoorBox, labToolsBox, otherBox);

            Label costInputLabel = new Label("ESTIMATED RETAIL VALUE ($):");
            costInputLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            TextField costInputField = new TextField();
            costInputField.setPromptText("$0.00");
            costInputField.setStyle("-fx-padding: 8; -fx-border-color: #d1d5db; -fx-border-radius: 4;");

            formFields.getChildren().addAll(retnalNameLabel, rentalInputName, conditionLabel, conditionBox, catergoryTitle, categoryBox, costInputLabel, costInputField);

            VBox projectionsBox = new VBox(5);
            projectionsBox.setPadding(new Insets(15));
            projectionsBox.setStyle("-fx-background-color: #f3f4f6; -fx-background-radius: 6;");
            Label projectionTitle = new Label("SYSTEM PROJECTIONS");
            projectionTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");
            Label dailyrateTitle =  new Label("Generated Daily Rental Rate: $45.00");
            Label depositTitle =  new Label("Required Security Deposit: $350.00");
            dailyrateTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #0284c7;");
            depositTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
            projectionsBox.getChildren().addAll(projectionTitle, dailyrateTitle, depositTitle);

            HBox footerBtns = new HBox(10);
            footerBtns.setAlignment(Pos.CENTER_RIGHT);
            Button closeButton = new Button("Cancel");
            closeButton.setStyle("-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 4; -fx-padding: 8 15; -fx-cursor: hand;");
            Button publishButton = new Button("Publish Listing");
            publishButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 8 15; -fx-cursor: hand;");
            footerBtns.getChildren().addAll(closeButton, publishButton);

            Label rentalCatergory = new Label("Category: ");

            closeButton.setOnAction(e -> popupRental.hide());

            publishButton.setOnAction((ActionEvent publishRental) -> {
                if(pristineButton.isSelected()) { newRental.setCondition("Pristine"); }
                if(normalButton.isSelected()) { newRental.setCondition("Normal"); }
                if(heavyButton.isSelected()) { newRental.setCondition("Heavy"); }

                if(computingBox.isSelected()) { rentalCatergory.setText(rentalCatergory.getText() + "Computing "); }
                if(outdoorBox.isSelected()) { rentalCatergory.setText(rentalCatergory.getText() + "Outdoor "); }
                if(labToolsBox.isSelected()) { rentalCatergory.setText(rentalCatergory.getText() + "Lab Tools "); }
                if(otherBox.isSelected()) { rentalCatergory.setText(rentalCatergory.getText() + "Other"); }

                float rentalTotalCost = 0;
                try {
                    rentalTotalCost = Float.parseFloat(costInputField.getText());
                } catch (NumberFormatException ex) {
                    rentalTotalCost = 0;
                }
                rentalTotalCost += 395;
                newRental.setCost(rentalTotalCost);
                newRental.setName(rentalInputName.getText());

                HBox newEntry = new HBox(15);
                newEntry.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-padding: 10;");
                newEntry.setAlignment(Pos.CENTER_LEFT);

                ImageView entryImage = new ImageView(userImage.getImage());
                entryImage.setFitWidth(60);
                entryImage.setFitHeight(60);

                VBox entryText = new VBox(3);
                Label entryName = new Label(newRental.getName().trim());
                entryName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                Label entryCats = new Label(rentalCatergory.getText());
                entryCats.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
                entryText.getChildren().addAll(entryName, entryCats);

                Region entrySpacer = new Region();
                HBox.setHgrow(entrySpacer, Priority.ALWAYS);

                VBox entryDetails = new VBox(3);
                entryDetails.setAlignment(Pos.CENTER_RIGHT);
                Label entryCond = new Label(newRental.getCondition());
                entryCond.setStyle("-fx-text-fill: #4b5563;");
                Label entryCost = new Label("$" + newRental.getCost() + "/day");
                entryCost.setStyle("-fx-font-weight: bold;");
                entryDetails.getChildren().addAll(entryCond, entryCost);

                newEntry.getChildren().addAll(entryImage, entryText, entrySpacer, entryDetails);
                myRentingsList.getChildren().add(newEntry);

                try {
                    FileWriter rentalWriter = new FileWriter(newRental.getName().trim() + ".txt", true);
                    rentalWriter.write("Name: " + newRental.getName().trim() + "\n");
                    rentalWriter.write("Condition: " + newRental.getCondition() + "\n");
                    rentalWriter.write(rentalCatergory.getText() + "\n");
                    rentalWriter.write(newRental.getCost() + "\n");
                    rentalWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                popupRental.hide();
            });

            popupLayout.getChildren().addAll(popHeader, imageUploadArea, formFields, projectionsBox, footerBtns);
            popupRental.getContent().add(popupLayout);

            popupRental.centerOnScreen();
            popupRental.show(primaryStage);
        });
    }

    class rentalItem {
        public String rentalName;
        public String rentalCondition;
        public String rentalCatergory;
        public float rentalCost;

        public String getName() { return rentalName; }
        public String getCondition() { return rentalCondition; }
        public String getCatergory() { return rentalCatergory; }
        public float getCost() { return rentalCost; }

        public void setName(String name) { this.rentalName = name; }
        public void setCondition(String condition) { this.rentalCondition = condition; }
        public void setCatergory(String catergory) { this.rentalCatergory = catergory; }
        public void setCost(float cost) { this.rentalCost = cost; }
    }
}