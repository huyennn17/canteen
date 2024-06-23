package canteen.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dessert {
	private int id;
	private String name;
	
	
	public static List<Dessert> viewList() throws Exception {
		List<Dessert> listDessert = new ArrayList<Dessert>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/canteen_management","root","123456");
		
		try {
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from dessert");
			while(rs.next()) {
				String name = rs.getString("dessert_name");
				int id = rs.getInt("dessert_id");
				Dessert dessert = new Dessert(id, name);
				
				listDessert.add(dessert);
			}
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.close();
		}

		return listDessert;
		
	}

	public Dessert(int id, String name) {
		this.id = id;
		this.name = name;
	}
	

	public Dessert() {
		super();
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

