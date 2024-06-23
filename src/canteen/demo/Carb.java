package canteen.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Carb {
	private int id;
	private String name;
	
	public static List<Carb> viewList() throws Exception {
		List<Carb> listCarb = new ArrayList<Carb>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/canteen_management","root","123456");
		
		try {
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from carbs");
			while(rs.next()) {
				String name = rs.getString("carb_name");
				int id = rs.getInt("carb_id");
				Carb carb = new Carb(id,name);
				
				listCarb.add(carb);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			con.close();
		}

		return listCarb;
		
	}

	public Carb(int id, String name) {
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
	
}