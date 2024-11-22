package database;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import controller.SignupController;
import databaseconnection.DatabaseConnection;
import model.Customer;

public class UpdateOrderTable {
	private static final String TABLE_NAME = "orders";

	public boolean updateOrderTable(String username, String amount, String daytime, String itemString, String status, String preptime) {
		// updates order table with new data
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO " + TABLE_NAME + " (user_name, day_time, amount, items, status, preptime) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, daytime);
			pstmt.setString(3, amount);
			pstmt.setString(4, itemString);
			pstmt.setString(5, status);
			pstmt.setString(6, preptime);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Update table " + TABLE_NAME + " executed successfully");
				pstmt.close();
				return true;
			} else {
				System.out.println("No rows updated in table " + TABLE_NAME);
				pstmt.close();
				return false;
			}

		} catch (SQLException e) {
			System.out.println("Error updating table " + TABLE_NAME + ": " + e.getMessage());
			return false;
		}
	}
	
	public void updateOrderStatus(String username, String daytime, String itemString, String status) {
		// updates status of order
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "UPDATE " + TABLE_NAME + " SET status = ? WHERE user_name = ? AND day_time = ? AND items = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setString(2, username);
			pstmt.setString(3, daytime);
			pstmt.setString(4, itemString);

			int result = pstmt.executeUpdate();
			System.out.println("Rows affected: "+ result);
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
	}
}