package canteen.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class StudentTicket {
    private int id;
    private int studentId;
    private String studentName;
    private int dailyMenuId;
    private String ticketType; // "LUNCH" or "SNACK"
    private Date ticketDate;
    private BigDecimal price;
    private boolean paid;
    
    
	public StudentTicket(int id, int studentId, String studentName, int dailyMenuId, String ticketType, Date ticketDate,
			BigDecimal price, boolean paid) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.studentName = studentName;
		this.dailyMenuId = dailyMenuId;
		this.ticketType = ticketType;
		this.ticketDate = ticketDate;
		this.price = price;
		this.paid = paid;
	}
	
	public StudentTicket() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getDailyMenuId() {
		return dailyMenuId;
	}
	public void setDailyMenuId(int dailyMenuId) {
		this.dailyMenuId = dailyMenuId;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public Date getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
    
	 
}