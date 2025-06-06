package canteen.demo.entity;

public abstract class MenuItem {
    protected int id;
    protected String name;
    
    public MenuItem() {}
    
    public MenuItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
