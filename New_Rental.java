package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import App.backend.DataManager;
import App.backend.Equipment;
import java.util.UUID;

public class New_Rental extends Application{
	public New_Rental(Stage primaryStage){
		
        BorderPane mainBox = new BorderPane();

      //Start of Navigation Bar
      		BorderPane navigationMainBox = new BorderPane();

      	    HBox navigationBar = new HBox(20);
      	    navigationBar.setPadding(new Insets(10, 20, 10, 20));
      	    navigationBar.setAlignment(Pos.CENTER_LEFT);
      	    navigationMainBox.setStyle("-fx-background-color: white;" + "-fx-border-color: #d9d9d9;" + "-fx-border-width: 0 0 1 0;");

      		Label pageTitle = new Label("EquipRent");
      	    pageTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-text-fill:#0000FF;");

      	    Button homeButton = new Button("Home");
      	    Button rentingButton = new Button("My Renting");
      	    Button cartButton = new Button("My Cart");
      	    Button rentalsButton = new Button("My Rentals");   
      	    rentalsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #2563eb;" + "-fx-font-weight: bold;" + "-fx-border-color: transparent transparent #2563eb transparent;" + "-fx-border-width: 0 0 2 0;");
      	    
      	    Button[] buttons = {rentingButton,cartButton,homeButton};
      	    
      	    for (Button button : buttons) {
      	    	button.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #555555;" + "-fx-font-size: 13px;");
      	    	button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #555555;" + "-fx-font-size: 13px;"));
      	    	button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #eeeeee;" + "-fx-text-fill: #555555;" + "-fx-font-size: 13px;" + "-fx-background-radius: 40;"));
      	    }
         
      	    navigationBar.getChildren().addAll(pageTitle,homeButton,rentingButton,cartButton,rentalsButton);

      	    HBox navigationRightBar = new HBox(20);
      	    navigationRightBar.setAlignment(Pos.CENTER_RIGHT);
      	    Button profileButton = new Button("💀");
      	    TextField searchBar = new TextField();
      	    searchBar.setPromptText("Search");
      	    searchBar.setPrefWidth(200);
      	    profileButton.setStyle("-fx-background-color: #5b6770;" + "-fx-text-fill: white;" + "-fx-background-radius: 20;" + "-fx-min-width: 35;" + "-fx-min-height: 35;" + "-fx-max-width: 35;" + "-fx-max-height: 35;");
      	    
      	    navigationRightBar.getChildren().addAll(searchBar,profileButton);
      	    
      	    navigationMainBox.setLeft(navigationBar);
      	    navigationMainBox.setRight(navigationRightBar);
      	    
      	    mainBox.setTop(navigationMainBox);
      	    //End of Navigation Bar      	    
      	    
      	    //Navigation Buttons
      	    rentingButton.setOnAction((ActionEvent myRentalsPage) ->
              {
             	 primaryStage.close();
             	 Stage myRentingPage = new Stage();
             	 new Active_Renting(myRentingPage);
             });
      	 	homeButton.setOnAction((ActionEvent myRentalsPage) ->
         	{
         		primaryStage.close();
         		Stage myHomePage = new Stage();
         		new Home_Page(myHomePage);
         	});
      	    cartButton.setOnAction((ActionEvent myRentalsPage) ->
              {
              	 primaryStage.close();
              	 Stage myCartPage = new Stage();
                 new My_Cart(myCartPage);
              });


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

        	Button closeRentalPopup = new Button("X");
        	Label titleLabel = new Label("Create New Rental Form");
        	
        	closeRentalPopup.setOnAction(event -> popupRental.hide());
        	
        	FileChooser ownerInputImage = new FileChooser(); 
        	ImageView userImage = new ImageView();

        	userImage.setFitWidth(250);
        	userImage.setFitHeight(250);
        	userImage.setPreserveRatio(true);
        	userImage.setPickOnBounds(true);
        	userImage.setStyle("-fx-cursor: hand; -fx-background-color: #cccccc;");
        	
        	userImage.setOnMouseClicked(event -> {
        		File ownerImage = ownerInputImage.showOpenDialog(primaryStage);
        		 if (ownerImage != null) {
                     Image tempImage = new Image(ownerImage.toURI().toString());
                     userImage.setImage(tempImage);
                 }
        	});
        	
        	userImage.setOnMouseEntered(event -> {
        		userImage.setStyle("-fx-cursor: hand; -fx-background-color: #000000;-fx-border-color: red; -fx-border-width: 4px; -fx-border-style: solid;");
        	});

        	
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
            Label rentalCatergory = new Label("Catergory: \n");
            
            closeButton.setOnAction((ActionEvent closePopup) ->
            {
            	popupRental.hide();
            });
            publishButton.setOnAction((ActionEvent publishRental) ->
            {
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
            		rentalCatergory.setText(rentalCatergory.getText() + "Computing \n");
            	}
            	if(outdoorBox.isSelected())
            	{
            		rentalCatergory.setText(rentalCatergory.getText() + "Outdoor \n");
            	}
            	if(labToolsBox.isSelected())
            	{
            		rentalCatergory.setText(rentalCatergory.getText() + "Lab Tools \n");
            	}
            	if(otherBox.isSelected())
            	{
            		rentalCatergory.setText(rentalCatergory.getText() + "Other");
            	}
            	
            	float rentalTotalCost = Float.parseFloat(costInputField.getText());
            	rentalTotalCost += 395;
            	newRental.setCost(rentalTotalCost);
            	newRental.setName(rentalInputName.getText());
				Equipment equipment = new Equipment(UUID.randomUUID().toString(),newRental.getName(),newRental.getCatergory(),newRental.getCondition(),dailyRate,deposit,"OWNER-001","AVAILABLE");
    			DataManager.addEquipment(equipment);
            	Label rentalName = new Label("Rental Name: " + newRental.getName().trim());
            	ImageView rentalImage = new ImageView(userImage.getImage());
            	rentalImage.setFitWidth(100);
            	rentalImage.setFitHeight(150);
            	Label rentalCondition = new Label("Condition: " + newRental.getCondition());
            	Label rentalCost= new Label("Cost: $" + newRental.getCost());
            	myRentings.getChildren().addAll(rentalName,rentalImage,rentalCondition,rentalCatergory,rentalCost);
            	try {
            	    FileWriter rentalWriter = new FileWriter(newRental.getName().trim() + ".txt", true); // true = append

            	    rentalWriter.write("Name: " + newRental.getName().trim() + "\n");
            	    rentalWriter.write("Condition: " + newRental.getCondition() + "\n");
            	    rentalWriter.write(rentalCatergory.getText());
            	    rentalWriter.write(rentalCost.getText().trim() + "\n");

            	    rentalWriter.close();

            	} catch (IOException e) {
            	    e.printStackTrace();
            	}
            	popupRental.hide();
            });

            VBox popupLayout = new VBox();
            popupLayout.getChildren().addAll(closeRentalPopup,titleLabel,userImage,retnalNameLabel,rentalInputName,conditionLabel,pristineButton,normalButton,heavyButton
            		,catergoryTitle,computingBox,outdoorBox,labToolsBox,otherBox,costInputLabel,costInputField
            		,projectionTitle,dailyrateTitle,rateCost,depositTitle,depositCost,closeButton,publishButton);
            popupLayout.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid; -fx-background-color: white;");
            popupRental.getContent().add(popupLayout);
            popupLayout.setAlignment(Pos.CENTER);
            popupRental.show(primaryStage);
            
        });
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



	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

