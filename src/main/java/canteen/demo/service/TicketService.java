package canteen.demo.service;

import java.sql.Date;
import canteen.demo.database.TicketDatabase;
import canteen.demo.entity.MealTicket;

public class TicketService {
    private TicketDatabase ticketDatabase;
    
    public TicketService(TicketDatabase ticketDatabase) {
        this.ticketDatabase = ticketDatabase;
    }
    
    public void generateTicket(int studentId, int dailyMenuId, String ticketType, Date ticketDate) throws Exception {
        if (ticketDatabase.hasTicketForDay(studentId, ticketDate, ticketType)) {
            throw new Exception("You already have a " + ticketType.toLowerCase() + " ticket for this day");
        }
        
        MealTicket ticket = new MealTicket();
        ticket.setStudentId(studentId);
        ticket.setDailyMenuId(dailyMenuId);
        ticket.setTicketType(ticketType);
        ticket.setTicketDate(ticketDate);
        
        ticketDatabase.createTicket(ticket);
    }
}
