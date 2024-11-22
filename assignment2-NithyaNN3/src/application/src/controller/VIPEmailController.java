package controller;

import java.io.IOException;
import java.sql.SQLException;

import controller.LoginController;
import database.UpdateTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

public class VIPEmailController {

    @FXML
    private TextField txtEmail;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private Button btnSubmit;
    
    private Customer customerInfo;
    
    public void initialiseCustomerInfo(Customer customerInfo) {
    	// Initialising customer information here
        this.customerInfo = customerInfo;
    }

    @FXML
    public void handleEmailSubmit(ActionEvent event) throws IOException, SQLException {
    	// Handling email entered by user
    	String email = txtEmail.getText();
    	System.out.println(email);
    	try {
    	    // Fetch email address entered by user and set it in the customer model
    	    if (!customerInfo.setEmailID(email)) {
    	        // Handle the case where setting the email failed
    	    	throw new IllegalArgumentException("Please enter a valid email address.");
    	    }

    	    // Update the email address in the database schema
            UpdateTable addEmail = new UpdateTable();
   	        if (addEmail.updateVIPEmail(customerInfo.getUser_name(), customerInfo.getEmailID())) {
   	            // Indicate to the user that they have been upgraded to VIP
   	            lblMessage.setText("Please logout and login again to access VIP benefits!");
   	        } else {
   	          	throw new IllegalArgumentException("Failed to update email in the database.");
   	        }
    	} catch (IllegalArgumentException e) {
    		// Clear text field
            txtEmail.clear();
   	        lblMessage.setText(e.getMessage());
   	    }
    }
}
