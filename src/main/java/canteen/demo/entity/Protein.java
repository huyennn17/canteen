package canteen.demo.entity;

public class Protein extends MenuItem {
    private String type;
    
    public Protein(int id, String name, String type) { 
        super(id, name);
        this.type = type;
    }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
