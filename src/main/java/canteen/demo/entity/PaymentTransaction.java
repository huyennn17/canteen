package canteen.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class PaymentTransaction {
    private int id;
    private int studentId;
    private Timestamp createdAt;
    private BigDecimal totalAmount;
    private String status;
    private List<PaymentDetail> details;
    
    public PaymentTransaction() {
        this.details = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public List<PaymentDetail> getDetails() { return details; }
    public void setDetails(List<PaymentDetail> details) { this.details = details; }
}
