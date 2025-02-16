package canteen.demo.entity;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class ConfigPrice {
    private int id;
    private String mealType;
    private BigDecimal price;
    private Timestamp effectiveDateTime;
    
    public ConfigPrice() {}
    
    public ConfigPrice(String mealType, BigDecimal price, Timestamp effectiveDateTime) {
        this.mealType = mealType;
        this.price = price;
        this.effectiveDateTime = effectiveDateTime;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Timestamp getEffectiveDateTime() { return effectiveDateTime; }
    public void setEffectiveDateTime(Timestamp effectiveDateTime) { this.effectiveDateTime = effectiveDateTime; }
}
