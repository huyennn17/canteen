package canteen.demo;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proteins {
	private int id;
	private String name;
	private String type;
	
	public static List<Proteins> viewList() throws Exception {
		List<Proteins> listProteins = new ArrayList<Proteins>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/canteen_management","root","123456");
		
		try {
			
			System.out.println("Connection created");
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from proteins");
			while(rs.next()) {
				String name = rs.getString("proteins_name");
				int id = rs.getInt("proteins_id");
				String type = rs.getString("proteins_type");
				Proteins proteins = new Proteins(id, name, type);
				
				listProteins.add(proteins);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			con.close();
		}

		return listProteins;
		
	}
	


	public Proteins(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
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


	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	

}
