package canteen.demo.entity;

import java.math.BigDecimal;

public class PaymentDetail {
    private int id;
    private int paymentTransactionId;
    private int ticketId;
    private BigDecimal amount;
    
    public PaymentDetail() {}
    
    public PaymentDetail(int paymentTransactionId, int ticketId, BigDecimal amount) {
        this.paymentTransactionId = paymentTransactionId;
        this.ticketId = ticketId;
        this.amount = amount;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(int paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }
    
    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
