package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Customer;
import model.Orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.UpdateOrderTable;

public class ViewOrdersController {

    @FXML
    private ListView<String> lstOrders;

    @FXML
    private Button btnExportOrders;
    
    @FXML
    private Button btnCancel;

    private List<Orders> orders;

    private Customer customerInfo;

    @FXML
    public void initialiseCustomerInfo(Customer customerInfo) throws IOException {
        // Initialises the customer information class
        this.customerInfo = customerInfo;
    }

    @FXML
    public void setOrdersList(List<Orders> ordersList) {
        // Sets the List view to display the orders
        this.orders = ordersList;

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Orders order : ordersList) {
            String orderInfo = "Day & Time: " + order.getDayTime() + " | Amount: $" + order.getAmount() + " | Status: " + order.getStatus() + " | Items: " + order.getItemString();
            items.add(orderInfo);
        }
        lstOrders.setItems(items);
        lstOrders.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE); // Enable multiple selection mode
    }

    @FXML
    public void handleExportOrders(ActionEvent event) throws IOException {
        // Get the selected indices
        ObservableList<Integer> selectedIndices = lstOrders.getSelectionModel().getSelectedIndices();

        // Create a list to store selected orders
        List<Orders> selectedOrders = new ArrayList<>();
        for (Integer index : selectedIndices) {
            selectedOrders.add(orders.get(index)); // Fetch the selected orders from the orders list
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportOrders.fxml"));
        Parent root = loader.load();

        ExportOrdersController export = loader.getController();
        export.initialise(selectedOrders, customerInfo); // Pass the selected orders to the ExportOrdersController

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Export Orders");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @FXML
    public void handleCancelOrders(ActionEvent event) {
    	// Cancels order on click of the Cancel button
    	// Get the selected indices
        ObservableList<Integer> selectedIndices = lstOrders.getSelectionModel().getSelectedIndices();

        // Create a list to store selected orders
        List<Orders> selectedOrders = new ArrayList<>();
        for (Integer index : selectedIndices) {
            selectedOrders.add(orders.get(index)); // Fetch the selected orders from the orders list
        }
        
        // finds orders that can be cancelled and updates their statuses
		UpdateOrderTable cancelled = new UpdateOrderTable();
        for (Orders order : selectedOrders) {
        	if (order.getStatus().equals("await for collection") || order.getStatus().equals("placed")) {
        		cancelled.updateOrderStatus(customerInfo.getUser_name(), order.getDayTime(), order.getItemString(), "cancelled");
        	}
        }
    }
}
