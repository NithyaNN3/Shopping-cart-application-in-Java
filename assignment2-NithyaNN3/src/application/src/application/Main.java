package application;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception{

		// Login scene initialised and entry point for program is set
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));	        
        	Parent root = loader.load();
	        Scene scene = new Scene(root);
	        stage.setTitle("Login Page");
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
