package database;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import databaseconnection.DatabaseConnection;
import model.Customer;

public class CheckRecordExists {
	private static final String TABLE_NAME = "customer";
	
	public boolean findCustomer(String user_name, String password) throws SQLException {
		// finds customer in db

		try(Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT first_name, last_name FROM " + TABLE_NAME + " WHERE user_name = ? AND password = ?";
			
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_name);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			System.out.println("Customer fetched: " + firstName + lastName);
			if (firstName != null) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			System.out.println("Error fetching customer from " + TABLE_NAME + ": " + e.getMessage());
			return false;
		}
	}
	
	public Customer returnCustomer(String user_name) throws SQLException{
		// returns customer details
		try(Connection conn = DatabaseConnection.getConnection()) {
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_name = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_name);
            
            ResultSet rs = pstmt.executeQuery();
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String password = rs.getString("password");
			String emailid = rs.getString("email_id");
			String credits = rs.getString("credits");
			System.out.println("Customer fetched: " + firstName + lastName);
			
			//Initialise Customer data model class
			Customer cust = new Customer();
			cust.setFirst_name(firstName);
		    cust.setLast_name(lastName);
		    cust.setUser_name(user_name);
		    cust.setPassword(password);
		    if ((emailid) != null && (emailid) != "") {
		    	cust.setEmailID(emailid);
		    }
		    
		    cust.setCredits(credits);
			
			return cust;
		} catch (SQLException e) {
			System.out.println("Error fetching customer from " + TABLE_NAME + ": " + e.getMessage());
			return null;
		}
	}
}
