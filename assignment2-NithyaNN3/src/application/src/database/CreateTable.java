package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import databaseconnection.DatabaseConnection;

public class CreateTable {
	public static void main(String[] args) {
		// creates customer table in db
		final String TABLE_NAME = "customer";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME 
										+ "(user_name VARCHAR(20) NOT NULL,"
										+ "first_name VARCHAR(20) NOT NULL,"
										+ "last_name VARCHAR(20) NOT NULL,"
										+ "password VARCHAR(20) NOT NULL,"
										+ "email_id VARCHAR(100),"
										+ "credits NUMERIC (5, 2),"
										+ "PRIMARY KEY (user_name))");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
