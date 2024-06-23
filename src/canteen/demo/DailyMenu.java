package canteen.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class DailyMenu {
	private int dailyMenuId;
	private Date MenuDate;
	private int VegetableId;
	private int SnackId;
	public Date getMenuDate() {
		return MenuDate;
	}

	public void setMenuDate(Date menuDate) {
		MenuDate = menuDate;
	}


	private int ProteinId;
	private int appetizerId;
	
	
	public DailyMenu(Date date, int snackId, int appetizerId) {
		this.MenuDate= date;
		this.SnackId = snackId;
		this.appetizerId = appetizerId;
	}

	public DailyMenu() {
		// TODO Auto-generated constructor stub
	}


	public void createMenu(LocalDate date, int snackId, int appetizerId, int vegeId, int proteinId, int carbId, int dessertId) {
		Connection con;
		try {
			con = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/canteen_management","root","123456");
			
			System.out.println("Connection created");
			String sql = "INSERT INTO daily_menu(menu_date, snack_id, vegetable_id, appetizer_id, protein_id, carb_id, dessert_id) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setObject(1, date);
			stmt.setInt(2, snackId);
			stmt.setInt(3, vegeId);
			stmt.setInt(4, appetizerId);
			stmt.setInt(5, proteinId);
			stmt.setInt(6, carbId);
			stmt.setInt(7, dessertId);
			stmt.executeUpdate();
			
			System.out.println("Insert successfully");
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDailyMenuId(int dailyMenuId) {
		this.dailyMenuId = dailyMenuId;
	}
	public int getVegetableId() {
		return VegetableId;
	}
	public void setVegetableId(int vegetableId) {
		VegetableId = vegetableId;
	}
	public int getSnackId() {
		return SnackId;
	}
	public void setSnackId(int snackId) {
		SnackId = snackId;
	}
	public int getProteinId() {
		return ProteinId;
	}
	public void setProteinId(int proteinId) {
		ProteinId = proteinId;
	}


	public int getAppetizerId() {
		return appetizerId;
	}


	public void setAppetizerId(int appetizerId) {
		this.appetizerId = appetizerId;
	}


	public int getDailyMenuId() {
		return dailyMenuId;
	}
	
	

}
