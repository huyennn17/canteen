package canteen.demo.entity;

import java.sql.Date;

public class DailyMenu {
    private int dailyMenuId;
    private Date menuDate;
    private Snack snack;
    private Appetizer appetizer;
    private Vegetable vegetable;
    private Protein protein;
    private Carbohydrate carb;
    private Dessert dessert;
    
    public DailyMenu() {}
    
    public DailyMenu(Date menuDate) {
        this.dailyMenuId = 0;
        this.menuDate = menuDate;
        this.snack = null;
        this.appetizer = null;
        this.vegetable = null;
        this.protein = null;
        this.carb = null;
        this.dessert = null;
    }
    
    public DailyMenu(Date menuDate, Snack snack, Appetizer appetizer, 
            Vegetable vegetable, Protein protein, Carbohydrate carb, Dessert dessert) {
        this.menuDate = menuDate;
        this.snack = snack;
        this.appetizer = appetizer;
        this.vegetable = vegetable;
        this.protein = protein;
        this.carb = carb;
        this.dessert = dessert;
    }
    
    public int getDailyMenuId() { return dailyMenuId; }
    public void setDailyMenuId(int dailyMenuId) { this.dailyMenuId = dailyMenuId; }
    
    public Date getMenuDate() { return menuDate; }
    public void setMenuDate(Date menuDate) { this.menuDate = menuDate; }
    
    public Snack getSnack() { return snack; }
    public void setSnack(Snack snack) { this.snack = snack; }
    
    public Appetizer getAppetizer() { return appetizer; }
    public void setAppetizer(Appetizer appetizer) { this.appetizer = appetizer; }
    
    public Vegetable getVegetable() { return vegetable; }
    public void setVegetable(Vegetable vegetable) { this.vegetable = vegetable; }
    
    public Protein getProtein() { return protein; }
    public void setProtein(Protein protein) { this.protein = protein; }
    
    public Carbohydrate getCarb() { return carb; }
    public void setCarb(Carbohydrate carb) { this.carb = carb; }
    
    public Dessert getDessert() { return dessert; }
    public void setDessert(Dessert dessert) { this.dessert = dessert; }
}
