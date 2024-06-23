package com.connect.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfiguration {

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/canteen_management","root","123456");
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from snacks");
			while(rs.next()) {
				System.out.println(rs.getString("snack_name"));
				con.close();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		

	}

}
