package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import database.FetchOrdersFromTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;
import model.Orders;

public class ExportOrdersController {
	
	@FXML
	private TextField txtFields;
	
	@FXML
	private TextField txtDestination;
	
	@FXML
	private TextField txtfileName;
	
	@FXML
	private Button btnExport;
	
	@FXML
	private Label lblMsg;

	private Customer customerInfo;

	private List<Orders> selectedOrders;

	@FXML
	public void initialise(List<Orders> selectedOrders, Customer customerInfo) {
		//initialise customer info
		this.customerInfo = customerInfo;
		this.selectedOrders = selectedOrders;
	}
	
	@FXML
	private void handleExportOrders(ActionEvent event) {
		// exporting orders to csv flow
		String destinationDirectory = txtDestination.getText();
		String fileName = txtfileName.getText();
		
		exportOrdersToCSV(this.selectedOrders, destinationDirectory, fileName);
	}
	
	private void exportOrdersToCSV(List<Orders> ordersList, String destinationDirectory, String fileName) {
		// exporting flow
	    Path filePath = Paths.get(destinationDirectory, fileName + ".csv");
	    String[] headers = txtFields.getText().split(",");
	    FetchOrdersFromTable exports = new FetchOrdersFromTable();
	    
	    try (FileWriter writer = new FileWriter(filePath.toFile())) {
	        // Write CSV header
	        writer.append(String.join(",", headers)).append("\n");
	        
	        // Write order data
	        for (Orders order : ordersList) {
	            int id = order.getId();
	            String value = exports.fetchExportOrders(id, headers);

	            writer.append(value);
	            writer.append("\n");
	        }
	        lblMsg.setText("Data has been exported");
	        System.out.println("Data has been exported to " + filePath.toAbsolutePath());
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        lblMsg.setText("An error occurred");
	        System.out.println("An error occurred while writing to the file.");
	    }
	}

}
