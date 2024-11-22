package controller;

import java.sql.SQLException;

import database.UpdateTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Kitchen;
import model.ShoppingCart;

public class VIPCreditPromptController {

    @FXML
    private Button btnRedeem;

    @FXML
    private Label lblCreditsRemaining;

    @FXML
    private TextField txtCreditsToRedeem;
    
    private Customer customerInfo;
	
    private ShoppingCart shoppingCart;
    
    private String currentTime;

	private Kitchen kitchen;
	
    @FXML
    public void initialiseInfo(Customer customerInfo, ShoppingCart shoppingCart, String currentTime, Kitchen kitchen){
    	// Initialises the customer information class
        this.customerInfo = customerInfo;	
        this.shoppingCart = shoppingCart;
        this.currentTime = currentTime;
        this.kitchen = kitchen;
        lblCreditsRemaining.setText(customerInfo.getCredits());
    }

    @FXML
    void handleCreditRedemption(ActionEvent event) throws SQLException {
    	//handles credit redemption
    	String creditsToRedeem = txtCreditsToRedeem.getText();

    	if (isDouble(creditsToRedeem)) {
    		String newCredits = String.valueOf(Double.parseDouble(this.customerInfo.getCredits()) - Double.parseDouble(creditsToRedeem));
    		
    		// Set new total price of all the items
    		shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - (Double.parseDouble(creditsToRedeem))/100);
    		
    		// Setting credits in model
    		double newCreditsAfterPay = Double.parseDouble(newCredits) + shoppingCart.getTotalPrice();
    		this.customerInfo.setCredits(String.valueOf(newCreditsAfterPay));
    		
    		// Update user credits in db
    		UpdateTable newcreds = new UpdateTable();
    		newcreds.updateVIPCredits(this.customerInfo.getUser_name(), String.valueOf(newCreditsAfterPay));
    	}
    	
    	// load payment scene 
    	ShoppingCartController payment = new ShoppingCartController();
    	payment.initialiseCartInfo(shoppingCart);
    	payment.initialiseKitchenInfo(kitchen);
    	payment.initialiseCustomerInfo(customerInfo, currentTime);
    	payment.loadPaymentScene(event);
    }
    
    public boolean isDouble(String creditsToRedeem) {
    	// checks if double
    	try {
            Double.parseDouble(creditsToRedeem);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
