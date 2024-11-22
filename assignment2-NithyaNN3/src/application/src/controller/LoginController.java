package controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;
import model.Kitchen;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;

import database.CheckRecordExists;
import database.CheckVIP;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;
    
    private Kitchen kitchen;
    
    @FXML
    void SignUpAction(ActionEvent event) throws IOException {
    	// sign up flow
    	Parent root = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();

    }
    
    public void updateKitchenInfo(Kitchen updatedKitchen) {
    	// receives updated kitchen object
        this.kitchen = updatedKitchen;
    }
    
    void initialiseKitchenInfo(Kitchen kitchen) {
    	//initialises kitchen data
        if (kitchen != null) {
            this.kitchen = kitchen; // Use the existing kitchen object
        }
    }
    
    @FXML 
    void LoginAction(ActionEvent event) throws IOException, SQLException {
    	// handles login action and flow relating to customer type
    	String user_name = txtUsername.getText();
    	String password = txtPassword.getText();
    	
    	CheckRecordExists customer = new CheckRecordExists();
    	
    	if (customer.findCustomer(user_name, password)) {
    		Customer customerInfo = customer.returnCustomer(user_name);
    		
    		// Pass user credentials to DashboardController
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
    		Parent root = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.initialiseCustomerInfo(customerInfo);
            dashboardController.initialiseKitchenInfo(this.kitchen);
            
        	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        	Scene scene = new Scene(root);
        	stage.setScene(scene);
        	stage.show();
        	
        	// check if customer is a VIP then show the VIP prompt
        	CheckVIP vip = new CheckVIP();
        	
        	if (!vip.findVIPCustomer(user_name)) {
	        	FXMLLoader VIPloader = new FXMLLoader(getClass().getResource("/view/VIPPrompt.fxml"));	        
	        	Parent root2 = VIPloader.load();
	        	
	        	VIPPromptController vippromptController = VIPloader.getController();
	        	vippromptController.initialiseCustomerInfo(customerInfo);
	            
	        	Stage stage2 = new Stage();
		        Scene scene2 = new Scene(root2);
		        stage2.setTitle("VIP Access");
		        stage2.setScene(scene2);
		        stage2.setResizable(false);
		        stage2.show();
        	}
        	
    	} else {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("No customer with these credentials! Please sign up if you're a new customer");
        	alert.showAndWait(); // Show the alert
    	}
    	
    }
}
