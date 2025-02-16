package canteen.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import canteen.demo.database.PaymentDatabase;
import canteen.demo.entity.*;

public class PaymentService {
    private PaymentDatabase paymentDatabase;
    
    public PaymentService(PaymentDatabase paymentDatabase) {
        this.paymentDatabase = paymentDatabase;
    }
    
    public List<MealTicket> getUnpaidTickets(int studentId) throws Exception {
        List<MealTicket> tickets = paymentDatabase.getUnpaidTickets(studentId);
        
        for (MealTicket ticket : tickets) {
            BigDecimal currentPrice = paymentDatabase.getCurrentPrice(ticket.getTicketType());
        }
        
        return tickets;
    }
    
    public PaymentTransaction processPayment(int studentId, int[] ticketIds) throws Exception {
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PaymentDetail> details = new ArrayList<>();
        
        for (int ticketId : ticketIds) {
            MealTicket ticket = paymentDatabase.getTicketById(ticketId);
            if (ticket == null || ticket.getStudentId() != studentId) {
                throw new Exception("Invalid ticket selection");
            }
            
            BigDecimal price = paymentDatabase.getCurrentPrice(ticket.getTicketType());
            totalAmount = totalAmount.add(price);
            
            PaymentDetail detail = new PaymentDetail();
            detail.setTicketId(ticketId);
            detail.setAmount(price);
            details.add(detail);
        }
        
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setStudentId(studentId);
        transaction.setTotalAmount(totalAmount);
        transaction.setStatus("PENDING");
        transaction.setDetails(details);
        
        boolean paymentSuccessful = processPaymentWithGateway(transaction);
        
        if (paymentSuccessful) {
            transaction.setStatus("COMPLETED");
            int transactionId = paymentDatabase.createPaymentTransaction(transaction);
            transaction.setId(transactionId);
        } else {
            transaction.setStatus("FAILED");
            throw new Exception("Payment processing failed");
        }
        
        return transaction;
    }
    
    private boolean processPaymentWithGateway(PaymentTransaction transaction) {
        return true;
    }
}
