package database;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import controller.SignupController;
import databaseconnection.DatabaseConnection;
import model.Customer;
import model.Orders;

public class FetchOrdersFromTable {
	private static final String TABLE_NAME = "orders";
	
	public List<Orders> fetchOrders(String user_name) {
		// fetches orders from db
		List<Orders> ordersList = new ArrayList<>();
		
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "SELECT id, day_time, amount, items, status, preptime FROM " + TABLE_NAME + " WHERE user_name = ? "
					+ "ORDER BY day_time DESC";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);

			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
                // Retrieve data from the result set
				int id = result.getInt("id");
                String dayTime = result.getString("day_time");
                double amount = result.getDouble("amount");
                String items = result.getString("items");
                String status = result.getString("status");
                String preptime = result.getString("preptime");

                // Create Orders object and add to the list
                Orders orders = new Orders(id, dayTime, amount, items, status, preptime);
                ordersList.add(orders);
                System.out.println("Orders fetched successfully" + orders.getId() + orders.getDayTime() + orders.getAmount() + orders.getItemString() + orders.getStatus());
            }
			
		} catch (SQLException e) {
			System.out.println("Error fetching orders from: " + TABLE_NAME + ": " + e.getMessage());
		}
		
		return ordersList;
	}
	
	public List<Orders> fetchPreparedOrders(String user_name) {
		// fetches all prepared orders in db
		List<Orders> ordersList = new ArrayList<>();
		
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "SELECT id, day_time, amount, items, status, preptime FROM " + TABLE_NAME + " WHERE user_name = ? AND status = 'placed'"
					+ "ORDER BY day_time";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);

			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
                // Retrieve data from the result set
				int id = result.getInt("id");
                String dayTime = result.getString("day_time");
                double amount = result.getDouble("amount");
                String items = result.getString("items");
                String status = result.getString("status");
                String preptime = result.getString("preptime");

                // Create Orders object and add to the list
                Orders orders = new Orders(id, dayTime, amount, items, status, preptime);
                ordersList.add(orders);
                System.out.println("Orders fetched successfully" + orders.getId() + orders.getDayTime() + orders.getAmount() + orders.getItemString() + orders.getStatus()
                					+ orders.getPreptime());
            }
			
		} catch (SQLException e) {
			System.out.println("Error fetching orders from: " + TABLE_NAME + ": " + e.getMessage());
		}
		
		return ordersList;
	}
	
	public List<Orders> fetchActiveOrders(String user_name) {
		// fetches all active orders in db
		List<Orders> ordersList = new ArrayList<>();
		
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "SELECT id, day_time, amount, items, status, preptime FROM " + TABLE_NAME + " WHERE user_name = ? AND status = 'await for collection'"
					+ "ORDER BY day_time";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);

			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
                // Retrieve data from the result set
				int id = result.getInt("id");
                String dayTime = result.getString("day_time");
                double amount = result.getDouble("amount");
                String items = result.getString("items");
                String status = result.getString("status");
                String preptime = result.getString("preptime");

                // Create Orders object and add to the list
                Orders orders = new Orders(id, dayTime, amount, items, status, preptime);
                ordersList.add(orders);
                System.out.println("Active Orders fetched successfully");
            }
			
		} catch (SQLException e) {
			System.out.println("Error fetching orders from: " + TABLE_NAME + ": " + e.getMessage());
		}
		
		return ordersList;
	}
	
	public List<Orders> fetchOrdersById(int id) {
		// fetches orders by id
		List<Orders> ordersList = new ArrayList<>();
		
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "SELECT id, day_time, amount, items, status, preptime FROM " + TABLE_NAME + " WHERE id = ?"
					+ "ORDER BY day_time";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
                // Retrieve data from the result set
				int ID = result.getInt("id");
                String dayTime = result.getString("day_time");
                double amount = result.getDouble("amount");
                String items = result.getString("items");
                String status = result.getString("status");
                String preptime = result.getString("preptime");

                // Create Orders object and add to the list
                Orders orders = new Orders(ID, dayTime, amount, items, status, preptime);
                ordersList.add(orders);
                System.out.println("Fetched orders to be collected");
            }
			
		} catch (SQLException e) {
			System.out.println("Error fetching orders from: " + TABLE_NAME + ": " + e.getMessage());
		}
		
		return ordersList;
	}
	
	public String fetchExportOrders(int id, String[] headers) {
		// fetches orders to export
	    StringBuilder rowData = new StringBuilder();

	    try (Connection con = DatabaseConnection.getConnection()) {
	        String sql = "SELECT " + String.join(",", headers) + " FROM " + TABLE_NAME + " WHERE id = ? "
	                + "ORDER BY day_time DESC";

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setInt(1, id);

	            try (ResultSet result = pstmt.executeQuery()) {
	                if (result.next()) {
	                    for (String header : headers) {
	                        Object value = result.getObject(header.trim());
	                        rowData.append(value != null ? value.toString() : "").append(",");
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching orders from: " + TABLE_NAME + ": " + e.getMessage());
	    }

	    // Remove trailing comma
	    if (rowData.length() > 0) {
	        rowData.setLength(rowData.length() - 1);
	    }

	    return rowData.toString();
	}
}