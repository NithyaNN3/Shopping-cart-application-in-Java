package controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

import database.UpdateTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

public class SignupController {

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword; 

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtfirstName;

    @FXML
    private TextField txtlastName;

    @FXML
    public void handleRegisterBtnClick(ActionEvent event) throws IOException {
    	// handles registration flow
    	String first_name = txtfirstName.getText();
    	String last_name = txtlastName.getText();
    	String user_name = txtUsername.getText();
    	String password = txtPassword.getText();
    	    	
		UpdateTable updateTable = new UpdateTable();
		boolean updateStatus = updateTable.updateTable(first_name, last_name, user_name, password); 
		
		if (updateStatus) {
			// Navigate to Login.fxml if registration is successful
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	Scene scene = new Scene(root);
	    	stage.setScene(scene);
	    	stage.show();
		} else {
			// Show "User already exists" message if registration fails
			handleUserExists(event);
			txtUsername.clear();
			txtfirstName.clear();
			txtlastName.clear();
			txtPassword.clear();
		}
	    	
    }
    
	@FXML
    private void handleUserExists(ActionEvent event) throws IOException {
		// Opens new stage to notify user that credentials already exist
		try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserAlreadyExists.fxml"));	        
        	Parent root = loader.load();
        	Stage stage = new Stage();
	        Scene scene = new Scene(root);
	        stage.setTitle("Invalid Credentials!");
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
        } catch(Exception e) {
        	e.printStackTrace();
        }
		
		// redirected to user login scene automatically if user exists
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
		
    }
}
