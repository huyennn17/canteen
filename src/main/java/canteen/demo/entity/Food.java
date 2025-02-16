package canteen.demo.entity;

public class Food {
	private int food_id;
	private String name;
	private String type;
	
	
	
	public Food(int food_id, String name, String type) {
		this.food_id = food_id;
		this.name = name;
		this.type = type;
	}
	
	public int getFood_id() {
		return food_id;
	}
	public void setFood_id(int food_id) {
		this.food_id = food_id;
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

