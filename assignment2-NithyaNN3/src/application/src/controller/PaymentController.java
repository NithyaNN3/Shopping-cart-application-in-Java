package controller;

import model.CreditCard;
import model.Customer;
import model.Kitchen;
import model.Orders;
import model.ShoppingCart;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.CheckRecordExists;
import database.UpdateOrderTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PaymentController {

    @FXML
    private Button btnPayNow;

    @FXML
    private AnchorPane orderPane;
    
    @FXML
    private Label lblAmount;

    @FXML
    private TextField txtCVV;

    @FXML
    private TextField txtExpiryDate;

    @FXML
    private TextField txtccNum;
    
    @FXML
    private Label lblMessage;
    
	private Customer customerInfo;

	private ShoppingCart shoppingCart;
	
	private String currentTime;

	private Kitchen kitchen;
	
    @FXML
    public void initialiseInfo(Customer customerInfo, ShoppingCart shoppingCart, String currentTime, Kitchen kitchen){
    	// Initialises the information for the class
        this.customerInfo = customerInfo;
        this.shoppingCart = shoppingCart;
        this.currentTime = currentTime;
        this.kitchen = kitchen;
        lblAmount.setText(String.valueOf(shoppingCart.getTotalPrice()));
    }
    
    @FXML
    public void handleCreditCardNum(ActionEvent event) throws IOException {
    	// handles payment flow and credit card validations
    	CreditCard creditCard = new CreditCard(txtccNum.getText(), txtExpiryDate.getText(), txtCVV.getText());

    	if (creditCard.isValidNo()) {
            lblMessage.setText("Payment successful!");
            // order placement flow
            // get current day and time in formatted version
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDateTime = currentDateTime.format(formatter);
            
            // time of order placement 
            System.out.println("Current date and time when order is placed: " + formattedDateTime);
            
            // updating order details in database
            UpdateOrderTable orderUpdate = new UpdateOrderTable();
            orderUpdate.updateOrderTable(this.customerInfo.getUser_name(), lblAmount.getText(), formattedDateTime + " " + currentTime, 
            		this.shoppingCart.getItemString(), "placed", getPrepTime());

        } else {
            lblMessage.setText("Invalid credit card information. Please try again.");
            clearFields();
        }
    	
    }
    
    @FXML
    private void clearFields() {
    	// clears fields
        txtccNum.clear();
        txtExpiryDate.clear();
        txtCVV.clear();
    }
    
    public String getPrepTime() {
    	//fetches prep time calculation
    	String prepTime = String.valueOf(this.kitchen.calculatePreparationTime(this.shoppingCart.getItemString())) + " mins";
    	System.out.println(prepTime);
		return prepTime;
    }

}
