package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Home_Navigation extends Application{
	public void start (Stage primaryStage){
		new Home_Page(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
