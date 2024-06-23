package canteen.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Vegetables {
	private int id;
	private String name;


	public Vegetables(int id2, String name2) {
		// TODO Auto-generated constructor stub
	}


	public List<Vegetables> viewList() throws Exception {
		List<Vegetables> listVegetables = new ArrayList<Vegetables>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/canteen_management","root","123456");
		
		try {
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.   executeQuery("select * from vegetables");
			while(rs.next()) {
				String name = rs.getString("vegetables_name");
				int id = rs.getInt("vegetables_id");
				Vegetables vegetables = new Vegetables(id, name);
				
				listVegetables.add(vegetables);
			}
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.close();
		}

		return listVegetables;
	}
		

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}