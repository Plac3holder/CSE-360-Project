package application;

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

public class Home_Page extends Application{
	public Home_Page(Stage primaryStage){
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
	    homeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #2563eb;" + "-fx-font-weight: bold;" + "-fx-border-color: transparent transparent #2563eb transparent;" + "-fx-border-width: 0 0 2 0;");
	    Button rentingButton = new Button("My Renting");
	    Button cartButton = new Button("My Cart");
	    Button rentalsButton = new Button("My Rentals");   
	    
	    Button[] buttons = {rentingButton,cartButton,rentalsButton};
	    
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
	    
	    
	    //Categories Layout
	    VBox categoriesBox = new VBox(20);
	    categoriesBox.setPadding(new Insets(20));
	    
	    //Code to create new boxes (change when database is one)
	    categoriesBox.getChildren().add(createCategory("Computing", 
	    		new Rental_Box_Info("test", "test", "$45 per day", "Pristine", null),
	            new Rental_Box_Info("test", "test", "$30/day", "Normal", null)));
	    categoriesBox.getChildren().add(createCategory("Outdoor", 
	    		new Rental_Box_Info("test", "test", "$45 per day", "Pristine", null),
	            new Rental_Box_Info("test", "test", "$30/day", "Normal", null)));
	    categoriesBox.getChildren().add(createCategory("Lab Tools",
	    		new Rental_Box_Info("test", "test", "$45 per day", "Pristine", null),
	            new Rental_Box_Info("test", "test", "$30/day", "Normal", null)));
	    categoriesBox.getChildren().add(createCategory("Others", 
	    		new Rental_Box_Info("test", "test", "$45 per day", "Pristine", null),
	            new Rental_Box_Info("test", "test", "$30/day", "Normal", null)));
	    
	    ScrollPane scrollCatergories = new ScrollPane(categoriesBox);
	    scrollCatergories.setFitToWidth(true);
	    
	    mainBox.setCenter(scrollCatergories);
	    //End Of Catergories Layout
	    
	    //Navigation Buttons
	    rentingButton.setOnAction((ActionEvent myRentalsPage) ->
        {
       	 	primaryStage.close();
       	 	Stage myRentingPage = new Stage();
            new Active_Renting(myRentingPage);
       });
	    rentalsButton.setOnAction((ActionEvent myRentalsPage) ->
        {
        	 primaryStage.close();
        	 Stage myRentalPage = new Stage();
             new New_Rental(myRentalPage);
        });
	    cartButton.setOnAction((ActionEvent myRentalsPage) ->
        {
        	 primaryStage.close();
        	 Stage myCartPage = new Stage();
             new My_Cart(myCartPage);
        });
	    
	    
	    //Scene
	    Scene scene = new Scene(mainBox, 1200, 700);	    
	    primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
	    
	}
		
	public VBox createCategory(String catergoryTitle,Rental_Box_Info... boxInfos){

	    Label catergoryLabel = new Label(catergoryTitle);
	    catergoryLabel.setStyle( "-fx-font-size:18;"+"-fx-font-weight:bold;");

	    BorderPane catergoryBox = new BorderPane();
	    
	    FlowPane rentalInfoBox = new FlowPane();
	    rentalInfoBox.setHgap(15);
	    rentalInfoBox.setVgap(15);

	    for(Rental_Box_Info boxInfo : boxInfos){
	    	rentalInfoBox.getChildren().add(boxInfo);
	    }

	    catergoryBox.setLeft(catergoryLabel);

	    VBox categoryInfo = new VBox(15);

	    categoryInfo.getChildren().addAll(catergoryBox, rentalInfoBox);

	    return categoryInfo;

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}

class Rental_Box_Info extends VBox{
	public Rental_Box_Info(String title, String description, String price,String condition, String imageFile) {
		setSpacing(8);
		setPrefWidth(170);

		setStyle("-fx-background-color:white;" + "-fx-border-color:#d9d9d9;");
		
		ImageView rentalImage = new ImageView(new Image("file:" + imageFile));

		rentalImage.setFitWidth(170);
        rentalImage.setFitHeight(120);
        
		Label rentalCondition = new Label(condition);
		if(condition.equals("Pristine")){
			rentalCondition.setStyle("-fx-background-color:#1d4ed8;" + "-fx-text-fill:white;" + "-fx-font-size:9;" + "-fx-padding:2 5;");
        }
		else{
        	rentalCondition.setStyle("-fx-background-color:#dddddd;" + "-fx-font-size:9;" + "-fx-padding:2 5;");
        }
		
		StackPane rentalImagePane = new StackPane(rentalImage,rentalCondition);
        StackPane.setAlignment(rentalCondition,Pos.TOP_RIGHT);
        
		Label rentalTitle = new Label(title);
		rentalTitle.setStyle("-fx-font-weight:bold;");
        Label rentalDesc = new Label(description);
        rentalDesc.setWrapText(true);
        rentalDesc.setStyle("-fx-font-size:11;");
        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-weight:bold;");
        Button addButton = new Button("+ Add");
        addButton.setStyle("-fx-background-color:#2563eb;" + "-fx-text-fill:white;");
        Region whitespace = new Region();
        HBox.setHgrow(whitespace, Priority.ALWAYS); 
        
        HBox rentalInfo = new HBox(10, priceLabel,whitespace,addButton);
        
        getChildren().addAll(rentalImagePane,rentalTitle,rentalDesc,rentalInfo);    
        
        
	}
}
