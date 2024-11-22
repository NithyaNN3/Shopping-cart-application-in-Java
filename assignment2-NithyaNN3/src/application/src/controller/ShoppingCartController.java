package controller;

import java.io.IOException;
import java.sql.SQLException;

import database.CheckVIP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.Food;
import model.Kitchen;
import model.ShoppingCart;

public class ShoppingCartController {

    private ShoppingCart shoppingCart;
    private ObservableList<String> cartItems;

    @FXML
    private Button btnBurritoPlus;
    
    @FXML
    private Button btnBurritoMinus;
    
    @FXML
    private Label lblBurritoQuantity;
    
    @FXML
    private Button btnFriesPlus;
    
    @FXML
    private Button btnFriesMinus;
    
    @FXML
    private Label lblFriesQuantity;
    
    @FXML
    private Button btnSodaPlus;
    
    @FXML
    private Button btnSodaMinus;
    
    @FXML
    private Label lblSodaQuantity;
    
    @FXML
    private Label lblMeal;
    
    @FXML
    private Button btnMealPlus;
    
    @FXML
    private Button btnMealMinus;
    
    @FXML
    private Label lblMealQuantity;
    
    @FXML
    private Button btnCheckout;
    
    @FXML
    private Label lblTotal;
    
    @FXML
    private AnchorPane cartPane;
    
    @FXML
    private ListView<String> cartListView;
    
	private Customer customerInfo;
	private String currentTime;
	private Kitchen kitchen;
    
    @FXML
    public void initialiseCustomerInfo(Customer customerInfo, String currentTime) throws SQLException{
    	// Initialises the customer information class
        this.customerInfo = customerInfo;
        this.currentTime= currentTime;
        
        CheckVIP vip = new CheckVIP();
        boolean isVIP = vip.findVIPCustomer(this.customerInfo.getUser_name());
        
        if (isVIP) {
        	setMealControlsVisibility(isVIP);
        }
    }
    
    private void setMealControlsVisibility(boolean isVisible) {
    	//shows meal to vip user
    	if (lblMeal != null && btnMealMinus != null && btnMealPlus != null && lblMealQuantity != null) {
            lblMeal.setVisible(isVisible);
            btnMealMinus.setVisible(isVisible);
            btnMealPlus.setVisible(isVisible);
            lblMealQuantity.setVisible(isVisible);
        }
    }
    
    public void initialiseKitchenInfo(Kitchen kitchen) {
    	//initialising kitchen flow
		this.kitchen = kitchen;
    }
    
	public void initialiseCartInfo(ShoppingCart shoppingCart) {
		// Reinitialises existing information after credits flow
		this.shoppingCart = shoppingCart;
	}

	@FXML
    public void initialize() {
		// initialises rest of the shopping cart
        shoppingCart = new ShoppingCart();
        cartItems = FXCollections.observableArrayList();
        cartListView.setItems(cartItems);

        // On action events are set here instead of the FXML file to enhance readability
        btnBurritoPlus.setOnAction(event -> updateQuantity(lblBurritoQuantity, 1, "Burrito"));
        btnBurritoMinus.setOnAction(event -> updateQuantity(lblBurritoQuantity, -1, "Burrito"));
        btnFriesPlus.setOnAction(event -> updateQuantity(lblFriesQuantity, 1, "Fries"));
        btnFriesMinus.setOnAction(event -> updateQuantity(lblFriesQuantity, -1, "Fries"));
        btnSodaPlus.setOnAction(event -> updateQuantity(lblSodaQuantity, 1, "Soda"));
        btnSodaMinus.setOnAction(event -> updateQuantity(lblSodaQuantity, -1, "Soda"));
   	 	btnMealPlus.setOnAction(event -> updateQuantity(lblMealQuantity, 1, "Meal"));
   	 	btnMealMinus.setOnAction(event -> updateQuantity(lblMealQuantity, -1, "Meal"));
        

        btnCheckout.setOnAction(event -> {
			try {
				handleCheckout(event);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
        
        lblTotal.setText("Total: $" + String.format("%.2f", shoppingCart.getTotalPrice()));
    }

    private void updateQuantity(Label label, int delta, String itemName) {
    	//updates quantity of shopping cart
        int quantity = Integer.parseInt(label.getText());
        quantity += delta;
        if (quantity < 0) quantity = 0;
        label.setText(String.valueOf(quantity));
        
        shoppingCart.updateItem(itemName, quantity); // Update the shopping cart
        updateCartListView(); // Refresh the cart list view
    }

    private void handleCheckout(ActionEvent event) throws SQLException {
    	//handles checkout of cart
        updateCartListView(); // Ensure the list view is up-to-date
        lblTotal.setText("Total: $" + String.format("%.2f", shoppingCart.getTotalPrice()));
        // Proceed to the payment scene
        CheckVIP vip = new CheckVIP();
        
        if (vip.findVIPCustomer(this.customerInfo.getUser_name())) {
        	loadCreditsScene(event);
        } else {
        	loadPaymentScene(event);
        }
    }
    
    private void loadCreditsScene(ActionEvent event) {
    	//handles vip credit flow
    	try {
        	// Loads the scene for credit redemption flow
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VIPRedeemCredits.fxml"));
            Parent creditRoot = loader.load();
            
            VIPRedeemCreditsController cart = loader.getController();
            cart.initialiseCustomerInfo(customerInfo, shoppingCart, currentTime, kitchen);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene creditScene = new Scene(creditRoot);
            
            currentStage.setScene(creditScene);
            currentStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadPaymentScene(ActionEvent event) {
    	// handles payment flow
        try {
        	// Loads the scene for payment flow
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Payment.fxml"));
            Parent payRoot = loader.load();
            
            PaymentController pay = loader.getController();
            pay.initialiseInfo(customerInfo, shoppingCart, currentTime, kitchen);
            
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene payScene = new Scene(payRoot);
            
            currentStage.setScene(payScene);
            currentStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCartListView() {
    	// updates cart view 
        cartItems.clear();
        for (Food item : shoppingCart.getItems()) {
            cartItems.add(item.getName() + " - " + item.getQuantity() + " @ $" + String.format("%.2f", item.getPrice()) + " each");
        }
        
        // Update the total price label
        lblTotal.setText("Total: $" + String.format("%.2f", shoppingCart.getTotalPrice()));
    }
}
