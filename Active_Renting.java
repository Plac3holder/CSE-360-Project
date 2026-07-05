package application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Active_Renting {
	public Active_Renting(Stage primaryStage){
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
	    rentingButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #2563eb;" + "-fx-font-weight: bold;" + "-fx-border-color: transparent transparent #2563eb transparent;" + "-fx-border-width: 0 0 2 0;");
	    Button cartButton = new Button("My Cart");
	    Button rentalsButton = new Button("My Rentals");   
	    
	    Button[] buttons = {homeButton,cartButton,rentalsButton};
	    
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
	    cartButton.setOnAction((ActionEvent myRentalsPage) ->
        {
        	 primaryStage.close();
        	 Stage myCartPage = new Stage();
             new My_Cart(myCartPage);
        });
	    rentalsButton.setOnAction((ActionEvent myRentalsPage) ->
        {
        	 primaryStage.close();
        	 Stage myRentalPage = new Stage();
             new New_Rental(myRentalPage);
        });
	    homeButton.setOnAction((ActionEvent myRentalsPage) ->
        {
        	 primaryStage.close();
        	 Stage myHomePage = new Stage();
             new Home_Page(myHomePage);
        });
	    
	    
	    VBox rentingBox = new VBox();
	    
	    VBox titleBox = new VBox(5);
	    Label reviewLabel = new Label("Active Renting");
	    reviewLabel.setStyle("-fx-font-size:32; -fx-font-weight:bold;");
        Label reviewSubtitle = new Label("Manage the equipment you are currently renting from others.");
        reviewSubtitle.setStyle("-fx-text-fill:gray;");
        titleBox.getChildren().addAll(reviewLabel,reviewSubtitle);
        
        rentingBox.getChildren().add(titleBox);
        mainBox.setCenter(rentingBox);
	    
	    Scene scene = new Scene(mainBox, 1200, 700);
        primaryStage.setTitle("Review Cart");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
