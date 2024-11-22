package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.Kitchen;
import model.ShoppingCart;

public class VIPRedeemCreditsController {

    @FXML
    private AnchorPane VIPCreditPane;

    @FXML
    private Button btnYes;
    
    @FXML
    private Button btnNo;
    
	private Customer customerInfo;
	
	private ShoppingCart shoppingCart;
	
	private String currentTime;

	private Kitchen kitchen;
	
    @FXML
    public void initialiseCustomerInfo(Customer customerInfo, ShoppingCart shoppingCart, String currentTime, Kitchen kitchen){
    	// Initialises the customer information class
        this.customerInfo = customerInfo;	
        this.shoppingCart = shoppingCart;
        this.currentTime = currentTime;
        this.kitchen = kitchen;
        
    }

    @FXML
    void handleYesCase(ActionEvent event) throws IOException {
    	// uses credits to modify the final price
    	
    	// Get the current stage from the event's source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Load the scene for VIP redemption flow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VIPCreditsPrompt.fxml"));
        Parent root = loader.load();
        
        VIPCreditPromptController redeem = loader.getController();
        redeem.initialiseInfo(customerInfo, shoppingCart, currentTime, kitchen);

        // Set the new scene to the current stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    void handleNoCase(ActionEvent event) throws SQLException {
    	// if no credits are redeemed flow
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
    	
    	// load payment scene 
    	ShoppingCartController payment = new ShoppingCartController();
    	payment.initialiseCartInfo(shoppingCart);
    	payment.initialiseKitchenInfo(kitchen);
    	payment.initialiseCustomerInfo(customerInfo, currentTime);
    	payment.loadPaymentScene(event);
    }

}
