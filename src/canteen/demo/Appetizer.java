package canteen.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Appetizer {
	private int appetizerId;
	private String appetizerName;
	

	public static List<Appetizer> viewList() throws Exception {
		List<Appetizer> listAppetizer = new ArrayList<Appetizer>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/canteen_management","root","123456");
		
		try {
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from appetizer");
			while(rs.next()) {
				String name = rs.getString("appetizer_name");
				int id = rs.getInt("appetizer_id");
				Appetizer appetizer = new Appetizer(id,name);
				
				listAppetizer.add(appetizer);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			con.close();
		}

		return listAppetizer;
		
	}
	
	public Appetizer(int appetizerId, String appetizerName) {
		super();
		this.appetizerId = appetizerId;
		this.appetizerName = appetizerName;
	}
	public int getAppetizerId() {
		return appetizerId;
	}
	public void setAppetizerId(int appetizerId) {
		this.appetizerId = appetizerId;
	}
	public String getAppetizerName() {
		return appetizerName;
	}
	public void setAppetizerName(String appetizerName) {
		this.appetizerName = appetizerName;
	}

}
