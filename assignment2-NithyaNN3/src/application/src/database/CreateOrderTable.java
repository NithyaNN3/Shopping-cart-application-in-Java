package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import databaseconnection.DatabaseConnection;

public class CreateOrderTable {
	public static void main(String[] args) {
		// creates the order table in db
		final String TABLE_NAME = "orders";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			String sql = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "user_name VARCHAR(20) NOT NULL,"
                    + "day_time VARCHAR(30),"
                    + "amount NUMERIC(4, 2),"
                    + "items VARCHAR(1000),"
                    + "status VARCHAR(20),"
                    + "preptime VARCHAR(20))");

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
