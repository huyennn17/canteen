package canteen.demo.database;

import java.sql.*;
import javax.sql.DataSource;
import canteen.demo.entity.MealTicket;

public class TicketDatabase {
    private DataSource dataSource;
    
    public TicketDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void createTicket(MealTicket ticket) throws SQLException {
        String sql = "INSERT INTO meal_tickets (student_id, daily_menu_id, ticket_type, ticket_date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getStudentId());
            stmt.setInt(2, ticket.getDailyMenuId());
            stmt.setString(3, ticket.getTicketType());
            stmt.setDate(4, ticket.getTicketDate());
            stmt.executeUpdate();
        }
    }
    
    public boolean hasTicketForDay(int studentId, Date ticketDate, String ticketType) throws SQLException {
        String sql = "SELECT COUNT(*) FROM meal_tickets WHERE student_id = ? AND ticket_date = ? AND ticket_type = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setDate(2, ticketDate);
            stmt.setString(3, ticketType);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
