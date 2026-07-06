package App.frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Create_New_Rental extends Application{
    public void start(Stage primaryStage){

        BorderPane mainBox = new BorderPane();

        HBox navigationBar = new HBox(20);
        navigationBar.setPadding(new Insets(10, 20, 10, 20));
        navigationBar.setAlignment(Pos.CENTER_LEFT);

        Label pageTitle = new Label("EquipRent");
        pageTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-text-fill:#0000FF;");

        Label homeLabel = new Label("Home");
        Label rentingLabel = new Label("My Renting");
        Label cartLabel = new Label("My Cart");
        Label rentalsLabel = new Label("My Rentals");
        Button profileButton = new Button("Account");

        navigationBar.getChildren().addAll(pageTitle,homeLabel,rentingLabel,cartLabel,rentalsLabel,profileButton);

        mainBox.setTop(navigationBar);


        VBox myRentings = new VBox(15);
        myRentings.setPadding(new Insets(20));
        Label myRentingsTitle = new Label("My Rentals Directory");
        myRentingsTitle.setStyle("-fx-font-size:28px; -fx-font-weight:bold;");
        Label myRentingsSubtitle = new Label("Manage your equipment listings and active rentals.");
        myRentingsSubtitle.setStyle("-fx-text-fill:gray;");
        myRentings.getChildren().addAll(myRentingsTitle,myRentingsSubtitle);
        mainBox.setCenter(myRentings);

        Button createRentalButton = new Button("+Create New Rental");



        createRentalButton.setStyle("-fx-background-color:#0000FF;-fx-background-radius:10;-fx-padding:12 18;-fx-text-fill:#FFFFFF;");

        StackPane buttonInfo = new StackPane(mainBox);
        StackPane.setAlignment(createRentalButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(createRentalButton,new Insets(0, 30, 30, 0));

        buttonInfo.getChildren().add(createRentalButton);

        Scene scene = new Scene(buttonInfo, 1200, 700);

        primaryStage.setTitle("EquipRent");
        primaryStage.setScene(scene);
        primaryStage.show();

        createRentalButton.setOnAction((ActionEvent openPopUp) -> {

            rentalItem newRental = new rentalItem();
            Popup popupRental = new Popup();

            // 3. Create content for the pop-up
            Label titleLabel = new Label("Create New Rental Form");
//        	ImageView imageView = new ImageView();
//
//        	imageView.setFitWidth(200);
//        	imageView.setPreserveRatio(true);

            Label retnalNameLabel = new Label("ITEM NAME");

            TextField rentalInputName = new TextField();
            rentalInputName.setPromptText("e.g., Digital Camera");
            rentalInputName.setPrefHeight(35);

            Label conditionLabel = new Label("Condition");
            ToggleGroup conditionGroup = new ToggleGroup();
            ToggleButton pristineButton = new ToggleButton("Pristine");
            ToggleButton normalButton = new ToggleButton("Normal");
            ToggleButton heavyButton = new ToggleButton("Heavy");

            pristineButton.setToggleGroup(conditionGroup);
            normalButton.setToggleGroup(conditionGroup);
            heavyButton.setToggleGroup(conditionGroup);

            Label catergoryTitle = new Label("Target Catergory");
            CheckBox computingBox = new CheckBox("Computing");
            CheckBox outdoorBox = new CheckBox("Outdoor");
            CheckBox labToolsBox = new CheckBox("Lab Tools");
            CheckBox otherBox = new CheckBox("Other");

            Label costInputLabel = new Label("ESTIMATED COST:");
            TextField costInputField = new TextField();
            costInputField.setPromptText("$0.00");
            costInputField.setPrefHeight(35);

            Label projectionTitle = new Label("SYSTEM PROJECTIONS");
            Label dailyrateTitle =  new Label("Generated Daily Rental Rate");
            Label rateCost = new Label("$45.00");
            Label depositTitle =  new Label("Required Security Deposit");
            Label depositCost = new Label("$350.00");

            Button closeButton = new Button("Close");
            Button publishButton = new Button("+ Publish Renting");
            publishButton.setStyle("-fx-background-color:#0000FF;-fx-background-radius:10;-fx-padding:12 18;-fx-text-fill:#FFFFFF;");
            closeButton.setOnAction((ActionEvent closePopup) ->
            {
                popupRental.hide();
            });
            publishButton.setOnAction((ActionEvent publishRental) ->
            {
                newRental.setName(rentalInputName.getText());
                if(pristineButton.isSelected())
                {
                    newRental.setCondition("Pristine");
                }
                if(normalButton.isSelected())
                {
                    newRental.setCondition("Normal");
                }
                if(heavyButton.isSelected())
                {
                    newRental.setCondition("Heavy");
                }

                if(computingBox.isSelected())
                {
                    newRental.setCatergory("Computing");
                }
                if(outdoorBox.isSelected())
                {
                    newRental.setCatergory("OutDoor");
                }
                if(labToolsBox.isSelected())
                {
                    newRental.setCatergory("Lab Tools");
                }
                if(otherBox.isSelected())
                {
                    newRental.setCatergory("Other");
                }

                float rentalTotalCost = Float.parseFloat(costInputField.getText());
                rentalTotalCost += 395;
                newRental.setCost(rentalTotalCost);
                Label rentalName = new Label("Rental Name: " + newRental.getName());
                Label rentalCondition = new Label("Condition: " + newRental.getCondition());
                Label rentalCatergory = new Label("Catergory: " + newRental.getCatergory());
                Label rentalCost= new Label("Cost: $" + newRental.getCost());
                myRentings.getChildren().addAll(rentalName, rentalCondition,rentalCatergory,rentalCost);
                popupRental.hide();
            });

            VBox popupLayout = new VBox();
            popupLayout.getChildren().addAll(titleLabel,retnalNameLabel,rentalInputName,conditionLabel,pristineButton,normalButton,heavyButton
                    ,catergoryTitle,computingBox,outdoorBox,labToolsBox,otherBox,costInputLabel,costInputField
                    ,projectionTitle,dailyrateTitle,rateCost,depositTitle,depositCost,closeButton,publishButton);
            popupLayout.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid; -fx-background-color: white;");
            popupRental.getContent().add(popupLayout);
            popupLayout.setAlignment(Pos.CENTER);
            popupRental.show(primaryStage);

        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    class rentalItem{
        public String rentalName;
        public String rentalCondition;
        public String rentalCatergory;
        public float rentalCost;


        public String getName()
        {
            return rentalName;
        }

        public String getCondition()
        {
            return rentalCondition;
        }

        public String getCatergory()
        {
            return rentalCatergory;
        }

        public float getCost()
        {
            return rentalCost;
        }

        public void setName(String name)
        {
            this.rentalName = name;
        }

        public void setCondition(String condition)
        {
            this.rentalCondition = condition;
        }

        public void setCatergory(String catergory)
        {
            this.rentalCatergory = catergory;
        }

        public void setCost(float cost)
        {
            this.rentalCost = cost;
        }

    }
}

