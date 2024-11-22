package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Customer;
import model.Orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.FetchOrdersFromTable;
import database.UpdateOrderTable;

public class ViewActiveOrdersController {

    @FXML
    private ListView<String> lstOrders;
    
    @FXML
    private Button btnCollectOrder;
    
	private Customer customerInfo;
	
	@FXML
    public void initialiseCustomerInfo(Customer customerInfo) throws IOException{
    	// Initialises the customer information class
        this.customerInfo = customerInfo;
    }

    public void setOrdersList(List<Orders> ordersList) {
    	// sets orders on display
        ObservableList<String> items = FXCollections.observableArrayList();
        
        for (Orders order : ordersList) {
            items.add(order.getId() + " | " + order.getItemString() + " | $" + order.getAmount() + " | " + order.getStatus());
        }
        lstOrders.setItems(items);
        lstOrders.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE); // Enable multiple selection mode
    }
    
    @FXML
    public void handleCollectOrders(ActionEvent event) {
    	// handles collection of orders
        ObservableList<String> selectedItems = lstOrders.getSelectionModel().getSelectedItems();
        List<Orders> fetchedOrders = new ArrayList<>();
        
        for (String selectedItem : selectedItems) {
            System.out.println("Selected: " + selectedItem);
            // parse id from the string
            String[] parts = selectedItem.split("\\|");

	        // Extract the ID from the first part of the string
	        String idPart = parts[0].trim(); // Trim to remove leading and trailing spaces
	
	        // Extract the ID from the ID part
	        String[] idParts = idPart.split(":");
	        String id = idParts[0].trim(); // Trim to remove leading and trailing spaces

            // Fetch orders by id
	        FetchOrdersFromTable collectedOrders = new FetchOrdersFromTable();
	        List<Orders> ordersById = collectedOrders.fetchOrdersById(Integer.parseInt(id));
	        
	        // Add fetched orders to the accumulated list
	        fetchedOrders.addAll(ordersById);
            
        }
        
        for (Orders order : fetchedOrders) {
        	// Update status to 'collected'
        	UpdateOrderTable statusChange = new UpdateOrderTable();
        	statusChange.updateOrderStatus(customerInfo.getUser_name(), order.getDayTime(), order.getItemString(), "collected");
        }
    }
}
