package canteen.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class MealTicket {
    private int id;
    private int studentId;
    private int dailyMenuId;
    private String ticketType; // "LUNCH" or "SNACK"
    private Date ticketDate;
    private BigDecimal price;
    private boolean paid;

    public MealTicket() {}

    public MealTicket(int id, int studentId, int dailyMenuId, String ticketType, Date ticketDate, BigDecimal price, boolean paid) {
        this.id = id;
        this.studentId = studentId;
        this.dailyMenuId = dailyMenuId;
        this.ticketType = ticketType;
        this.ticketDate = ticketDate;
        this.price = price;
        this.paid = paid;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getDailyMenuId() { return dailyMenuId; }
    public void setDailyMenuId(int dailyMenuId) { this.dailyMenuId = dailyMenuId; }
    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }
    public Date getTicketDate() { return ticketDate; }
    public void setTicketDate(Date ticketDate) { this.ticketDate = ticketDate; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}