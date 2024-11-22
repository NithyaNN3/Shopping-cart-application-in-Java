package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseconnection.DatabaseConnection;

public class CheckVIP {
	
	private static final String TABLE_NAME = "customer";
	
	public boolean findVIPCustomer(String user_name) throws SQLException {
		// Checks if the customer is VIP or not
		try(Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_name = ? AND email_id IS NOT NULL";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_name);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                // VIP customer found
                String firstName = rs.getString("first_name");
                String emailID = rs.getString("email_id");
                System.out.println("Customer fetched is VIP: " + firstName + " " + emailID);
                return true;
            } else {
                // No VIP customer found
                return false;
            }
		} catch (SQLException e) {
			System.out.println("Error fetching customer from " + TABLE_NAME + ": " + e.getMessage());
			return false;
		}
	}
}
