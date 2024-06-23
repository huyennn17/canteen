package canteen.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Snacks {
	private int id;
	private String name;
	private String ingredients;
	
	public static List<Snacks> viewList() throws Exception {
		List<Snacks> listSnacks = new ArrayList<Snacks>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/canteen_management","root","123456");
		
		try {
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from snacks");
			while(rs.next()) {
				String name = rs.getString("snack_name");
				int id = rs.getInt("snack_id");
				Snacks snacks = new Snacks(id,name);
				
				listSnacks.add(snacks);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			con.close();
		}

		return listSnacks;
		
	}
	
	public Snacks(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
}
	
