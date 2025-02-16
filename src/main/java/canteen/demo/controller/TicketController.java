package canteen.demo.controller;

import java.io.IOException;
import java.sql.Date;  
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import canteen.demo.database.TicketDatabase;
import canteen.demo.service.TicketService;
import canteen.demo.entity.Student;

public class TicketController extends HttpServlet {
    @Resource(name="jdbc/canteen")
    private DataSource dataSource;
    private TicketService ticketService;
    
    @Override
    public void init() throws ServletException {
        ticketService = new TicketService(new TicketDatabase(dataSource));
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            
            if ("GENERATE".equals(command)) {
                generateTicket(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e.getMessage());
        }
    }
    
    private void generateTicket(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        
        if (student == null) {
            throw new Exception("You must be logged in to generate tickets");
        }
        
        int studentId = student.getStudentId();
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        String ticketType = request.getParameter("ticketType");
        Date ticketDate = Date.valueOf(request.getParameter("menuDate"));
        
        ticketService.generateTicket(studentId, menuId, ticketType, ticketDate);
        
        session.setAttribute("NOTIFICATION_MESSAGE", "Ticket generated successfully!");
        session.setAttribute("NOTIFICATION_TYPE", "success");
        
        response.sendRedirect(request.getContextPath() + "/menu?command=VIEW");
    }
    
    private void handleError(HttpServletRequest request, HttpServletResponse response, String error) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("NOTIFICATION_MESSAGE", error);
        session.setAttribute("NOTIFICATION_TYPE", "danger");
        response.sendRedirect(request.getContextPath() + "/menu?command=VIEW");
    }
}
