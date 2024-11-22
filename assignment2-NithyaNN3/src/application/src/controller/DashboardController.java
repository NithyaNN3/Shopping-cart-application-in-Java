package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import database.CheckRecordExists;
import database.FetchOrdersFromTable;
import database.UpdateOrderTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Customer;
import model.Kitchen;
import model.Orders;

public class DashboardController {
	
    @FXML
    private Label lblfirstName;

    @FXML
    private Label lbllastName;
    
    @FXML
    private TextField txtTime;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private Button btnEditProfile;

    @FXML
    private Button btnExportOrders;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnViewOrders;
    
    @FXML
    private Button btnEnter;
    
    @FXML
    private AnchorPane contentArea;

	private Customer customerInfo;
	
	private ViewActiveOrdersController viewActiveOrdersController;

	private Kitchen kitchen;
	
	public void setViewActiveOrdersController(ViewActiveOrdersController controller) {
        this.viewActiveOrdersController = controller;
    }
    
    @FXML
    public void initialiseCustomerInfo(Customer customerInfo) throws IOException{
    	// Initialises the customer information class
    	
        lblfirstName.setText(customerInfo.getFirst_name());
        lbllastName.setText(customerInfo.getLast_name());
        
        this.customerInfo = customerInfo;
        handleDashboardOrders();
    	
    }
    
    @FXML
    public void initialiseKitchenInfo(Kitchen kitchen) {
    	// initialises kitchen stocks
    	this.kitchen = kitchen;
    }
    
    @FXML
    public void handleDashboardOrders() throws IOException {
    	// shows all orders awaiting collection
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewActiveOrders.fxml"));
        Parent viewRoot = loader.load();

        // Fetch orders
        FetchOrdersFromTable orders = new FetchOrdersFromTable();
        List<Orders> ordersList = orders.fetchActiveOrders(customerInfo.getUser_name());

        // Get the controller
        ViewActiveOrdersController controller = loader.getController();

        // Set the ViewActiveOrdersController instance in DashboardController
        setViewActiveOrdersController(controller);

        // Pass the list of orders
        controller.setOrdersList(ordersList);
        controller.initialiseCustomerInfo(customerInfo);

        // Load the new content into the AnchorPane
        contentArea.getChildren().clear();
        contentArea.getChildren().add(viewRoot);
    }
    
    @FXML
    public void handleEditProfile(ActionEvent event) throws IOException {
    	// Takes user to edit profile flow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateProfile.fxml"));
        Parent root = loader.load();
        
        UpdateProfileController editProfile = loader.getController();
        editProfile.initialiseCustomerInfo(customerInfo);

        Stage editProfileStage = new Stage();
        Scene editProfileScene = new Scene(root);
        editProfileStage.setTitle("Edit Profile");

        editProfileStage.setScene(editProfileScene); // Show the edit profile stage and wait for it to be closed
        editProfileStage.show();
        
    }
    
    @FXML
    public void handleShoppingCart(ActionEvent event) throws IOException, SQLException {
    	// handles shopping cart flow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShoppingCart.fxml"));
        Parent cartRoot = loader.load();
        
        ShoppingCartController cart = loader.getController();
        cart.initialiseCustomerInfo(customerInfo, txtTime.getText());
        cart.initialiseKitchenInfo(kitchen);

        // Create a new stage
        Stage cartStage = new Stage();
        Scene scene = new Scene(cartRoot);
        cartStage.setScene(scene);
        
        // Show the stage
        cartStage.show();
    }
    
    @FXML
    public void handleViewOrders(ActionEvent event) throws IOException {
    	// handles the view orders flow
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewOrders.fxml"));
        Parent viewRoot = loader.load();
        
        FetchOrdersFromTable orders = new FetchOrdersFromTable();
        List<Orders> ordersList = orders.fetchOrders(customerInfo.getUser_name());
        
        // Get the controller and pass the list of orders
        ViewOrdersController controller = loader.getController();
        controller.initialiseCustomerInfo(customerInfo);
        controller.setOrdersList(ordersList);

        // Create a new stage
        Stage viewStage = new Stage();
        Scene scene = new Scene(viewRoot);
        viewStage.setScene(scene);
        
        // Show the stage
        viewStage.show();
    }
    
    @FXML
    public void handleOrderStatuses(ActionEvent event) throws IOException {
    	//updates order statuses if needed
    	
    	String enteredTime = txtTime.getText();
    	System.out.println("Current time: " + enteredTime);
    	UpdateOrderTable statusUpdate = new UpdateOrderTable();
    	
    	// Fetch each order and parse for the time of placement if the order status = placed
    	FetchOrdersFromTable orders = new FetchOrdersFromTable();
    	List<Orders> orderList = orders.fetchPreparedOrders(customerInfo.getUser_name());
    	
    	// Fetch order time details from the database
    	for (Orders order : orderList) {
	    	// Use time entered by user to check if it is greater than order placement + prep time
    		String collectionTime = addTime(order.getDayTime(), order.getPreptime());
    		System.out.println("Collection: " + collectionTime);
    				
	    	if (isTimeExceeded(enteredTime, collectionTime)) {
	    		// Update order status in the table
	    		statusUpdate.updateOrderStatus(customerInfo.getUser_name(), order.getDayTime(), order.getItemString(), "await for collection");
	    	} else {
	    		System.out.println("Customer has to wait");
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PleaseWait.fxml"));
	            Parent viewRoot = loader.load();
	            
	            PleaseWaitController wait = loader.getController();
	            wait.handleCollectionTime(collectionTime);
	            
	            // Create a new stage
	            Stage viewStage = new Stage();
	            Scene scene = new Scene(viewRoot);
	            viewStage.setScene(scene);
	            
	            // Show the stage
	            viewStage.show();
	    	}
    	}
    	handleDashboardOrders();
    }
    
    public String addTime(String time1, String time2) {
    	// Extract the time part from time1
    	
        String[] parts = time1.split(" ");
        System.out.println(parts);
        String timePart = parts[1];
        
    	// Parse minutes from prepTime
        String prepTime = time2.split(" ")[0];
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(timePart, formatter);
        LocalTime totalTime = localTime.plusMinutes(Integer.parseInt(prepTime));

        return totalTime.format(formatter);
    }
    
    public boolean isTimeExceeded(String totalTime, String collectionTime) {
    	// checks if time is exceeded
    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        // Parse the entered time and collection time to LocalTime objects
        LocalTime totalLocalTime = LocalTime.parse(totalTime, timeFormatter);
        LocalTime collectionLocalTime = LocalTime.parse(collectionTime, timeFormatter);
        
        // Compare the times
        return !totalLocalTime.isBefore(collectionLocalTime);
    }    

     
    @FXML 
    public void handleLogoutAction(ActionEvent event) throws IOException {
    	// logout feature
    	
    	// Get the source node, stage of the event
        Node node = (Node) event.getSource();
        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.close();
        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        
        LoginController nextLogin = new LoginController();
        nextLogin.initialiseKitchenInfo(kitchen); // Pass the kitchen object

        // Create a new stage
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Show the stage
        stage.show();
    	
    }

}
