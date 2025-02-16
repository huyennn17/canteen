package canteen.demo.database;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import canteen.demo.entity.*;

public class PaymentDatabase {
    private DataSource dataSource;
    
    public PaymentDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<MealTicket> getUnpaidTickets(int studentId) throws SQLException {
        String sql = "SELECT t.*, cp.price " +
                    "FROM meal_tickets t " +
                    "LEFT JOIN config_prices cp ON t.ticket_type = cp.meal_type " +
                    "WHERE t.student_id = ? AND t.paid = false " +
                    "AND cp.effective_datetime = (SELECT MAX(effective_datetime) " +
                                          "FROM config_prices cp2 " +
                                          "WHERE cp2.meal_type = t.ticket_type " +
                                          "AND cp2.effective_datetime <= CURRENT_TIMESTAMP)";
        
        List<MealTicket> tickets = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MealTicket ticket = new MealTicket();
                    ticket.setId(rs.getInt("ticket_id"));
                    ticket.setStudentId(rs.getInt("student_id"));
                    ticket.setDailyMenuId(rs.getInt("daily_menu_id"));
                    ticket.setTicketType(rs.getString("ticket_type"));
                    ticket.setTicketDate(rs.getDate("ticket_date"));
                    ticket.setPaid(rs.getBoolean("paid"));
                    ticket.setPrice(rs.getBigDecimal("price"));
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }
    
    public BigDecimal getCurrentPrice(String mealType) throws SQLException {
        String sql = "SELECT price FROM config_prices WHERE meal_type = ? " +
                    "AND effective_datetime <= CURRENT_TIMESTAMP " +
                    "ORDER BY effective_datetime DESC LIMIT 1";
                    
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mealType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("price");
                }
            }
        }
        throw new SQLException("No price configuration found for " + mealType);
    }
    
    public int createPaymentTransaction(PaymentTransaction transaction) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            
            String sql = "INSERT INTO payment_transactions (student_id, total_amount, status) VALUES (?, ?, ?)";
            int transactionId;
            
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, transaction.getStudentId());
                stmt.setBigDecimal(2, transaction.getTotalAmount());
                stmt.setString(3, transaction.getStatus());
                stmt.executeUpdate();
                
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Failed to create payment transaction");
                    }
                    transactionId = rs.getInt(1);
                }
            }
            
            sql = "INSERT INTO payment_details (payment_transaction_id, ticket_id, amount) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (PaymentDetail detail : transaction.getDetails()) {
                    stmt.setInt(1, transactionId);
                    stmt.setInt(2, detail.getTicketId());
                    stmt.setBigDecimal(3, detail.getAmount());
                    stmt.executeUpdate();
                    
                    markTicketAsPaid(conn, detail.getTicketId());
                }
            }
            
            conn.commit();
            return transactionId;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Error rolling back transaction", ex);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void markTicketAsPaid(Connection conn, int ticketId) throws SQLException {
        String sql = "UPDATE meal_tickets SET paid = true WHERE ticket_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to mark ticket as paid: Ticket not found");
            }
        }
    }

    public MealTicket getTicketById(int ticketId) throws SQLException {
        String sql = "SELECT * FROM meal_tickets WHERE ticket_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MealTicket ticket = new MealTicket();
                    ticket.setId(rs.getInt("ticket_id"));
                    ticket.setStudentId(rs.getInt("student_id"));
                    ticket.setDailyMenuId(rs.getInt("daily_menu_id"));
                    ticket.setTicketType(rs.getString("ticket_type"));
                    ticket.setTicketDate(rs.getDate("ticket_date"));
                    ticket.setPaid(rs.getBoolean("paid"));
                    return ticket;
                }
            }
        }
        return null;
    }

    public void markTicketAsPaid(int ticketId) throws SQLException {
        String sql = "UPDATE meal_tickets SET paid = true WHERE ticket_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ticketId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to mark ticket as paid: Ticket not found");
            }
        }
    }
}
