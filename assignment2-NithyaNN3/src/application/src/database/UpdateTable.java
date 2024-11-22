package database;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import controller.SignupController;
import databaseconnection.DatabaseConnection;
import model.Customer;

public class UpdateTable {
	private static final String TABLE_NAME = "customer";

	public boolean updateTable(String firstName, String lastName, String userName, String password) {
		// updates table
		try (Connection con = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO " + TABLE_NAME + " (first_name, last_name, user_name, password) "
					+ "VALUES (?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, userName);
			pstmt.setString(4, password);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Update table " + TABLE_NAME + " executed successfully");
				System.out.println(result + " row(s) affected");
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

	public boolean updateVIPEmail(String user_name, String emailID) throws SQLException {
		// updates vip email
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "UPDATE " + TABLE_NAME + " SET email_id = ? WHERE user_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emailID);
			pstmt.setString(2, user_name);

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Customer email updated successfully. Customer upgraded to VIP.");
				pstmt.close();
				return true;
			} else {
				System.out.println("Failed to update customer email.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error updating customer email: " + e.getMessage());
			return false;
		}
	}

	public void updateCustomerDetails(Customer customerInfo) {
		// updates customer details
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "UPDATE " + TABLE_NAME
					+ " SET first_name = ?, last_name = ?, password = ? WHERE user_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customerInfo.getFirst_name());
			pstmt.setString(2, customerInfo.getLast_name());
			pstmt.setString(3, customerInfo.getPassword());
			pstmt.setString(4, customerInfo.getUser_name());

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Customer details updated successfully.");
				pstmt.close();
			} else {
				System.out.println("Failed to update customer details.");
			}
		} catch (SQLException e) {
			System.out.println("Error updating customer details: " + e.getMessage());
		}
	}

	public void updateVIPCredits(String user_name, String new_creds) throws SQLException {
		// updates vip credits 
		try (Connection conn = DatabaseConnection.getConnection()) {
			String sql = "UPDATE " + TABLE_NAME + " SET credits = ? WHERE user_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new_creds);
			pstmt.setString(2, user_name);

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Customer credits updated successfully.");
			} else {
				System.out.println("Failed to update customer credits.");
			}
		} catch (SQLException e) {
			System.out.println("Error updating customer credits: " + e.getMessage());
		}
	}
}
