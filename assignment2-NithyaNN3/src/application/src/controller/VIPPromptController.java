package controller;

import java.io.IOException;

import database.CheckRecordExists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Customer;

public class VIPPromptController {

    @FXML
    private Button btnYes;
    
    private Customer customerInfo;
    
    @FXML
    public void initialiseCustomerInfo(Customer customerInfo) {
    	// initialises customer info
    	this.customerInfo = customerInfo;
    }
    
    @FXML
    public void handleYesCase(ActionEvent event) throws IOException {
        // Ask for email ID to upgrade to VIP user
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VIPEmail.fxml"));
        Parent root = loader.load();
        
        VIPEmailController vipemailController = loader.getController();
        vipemailController.initialiseCustomerInfo(customerInfo);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
        Scene scene = new Scene(root);
        stage.setTitle("Enter email");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
       
    }
}
