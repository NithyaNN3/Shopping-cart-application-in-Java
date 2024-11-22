package controller;

import database.UpdateTable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Customer;

public class UpdateProfileController {
	
    @FXML
    private Button btnSubmit;

    @FXML
    private TextField txtnewFirstName;

    @FXML
    private TextField txtnewLastName;

    @FXML
    private PasswordField txtnewPassword;

	private Customer customerInfo;
    
    @FXML
    public void initialiseCustomerInfo(Customer customerInfo){
    	// Initialises the customer information class
    	
        this.customerInfo = customerInfo;
    	
    }
    
    @FXML
    public void updateProfile(ActionEvent event) {
    	// updation of user profile
        String newFirstName = txtnewFirstName.getText();
        String newLastName = txtnewLastName.getText();
        String newPassword = txtnewPassword.getText();	

        // Call method to update profile in the database
        UpdateTable updateTable = new UpdateTable();
        
        customerInfo.setFirst_name(newFirstName);
        customerInfo.setLast_name(newLastName);
        customerInfo.setPassword(newPassword);
        
        System.out.println(customerInfo.getFirst_name());
        
        updateTable.updateCustomerDetails(customerInfo);

    }

}
