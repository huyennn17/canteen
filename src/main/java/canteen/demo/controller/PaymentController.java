package canteen.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.List;

import canteen.demo.service.PaymentService;
import canteen.demo.entity.*;
import canteen.demo.database.PaymentDatabase;

public class PaymentController extends HttpServlet {
    @Resource(name="jdbc/canteen")
    private DataSource dataSource;
    private PaymentService paymentService;
    
    @Override
    public void init() throws ServletException {
        paymentService = new PaymentService(new PaymentDatabase(dataSource));
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String command = request.getParameter("command");
        if (command == null) command = "VIEW";
        
        try {
            switch (command) {
                case "VIEW":
                    viewUnpaidTickets(request, response);
                    break;
                case "VIEW-PAID-TICKET":
                	viewPaidTickets(request, response);
                    break;
                default:
                    viewUnpaidTickets(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void viewPaidTickets(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        
        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<MealTicket> paidTickets = paymentService.getPaidTickets(student.getStudentId());
        request.setAttribute("paidTickets", paidTickets);
        request.getRequestDispatcher("/paidticket.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String command = request.getParameter("command");
        
        try {
            if ("PROCESS".equals(command)) {
                processPayment(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e.getMessage());
        }
    }
    
    private void viewUnpaidTickets(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        
        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        List<MealTicket> unpaidTickets = paymentService.getUnpaidTickets(student.getStudentId());
        request.setAttribute("unpaidTickets", unpaidTickets);
        request.getRequestDispatcher("/payment.jsp").forward(request, response);
    }
    
    private void processPayment(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        
        if (student == null) {
            setPaymentMessage(session, "You must be logged in to make payments", "danger");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String[] ticketIds = request.getParameterValues("ticketIds");
        if (ticketIds == null || ticketIds.length == 0) {
            setPaymentMessage(session, "No tickets selected for payment", "warning");
            response.sendRedirect(request.getContextPath() + "/payment?command=VIEW");
            return;
        }
        
        try {
            int[] selectedTickets = new int[ticketIds.length];
            for (int i = 0; i < ticketIds.length; i++) {
                selectedTickets[i] = Integer.parseInt(ticketIds[i]);
            }
            
            PaymentTransaction transaction = paymentService.processPayment(student.getStudentId(), selectedTickets);
            
            if ("COMPLETED".equals(transaction.getStatus())) {
                BigDecimal amount = transaction.getTotalAmount();
                setPaymentMessage(session, 
                    String.format("Payment of $%.2f processed successfully!", amount), 
                    "success");
            } else {
                setPaymentMessage(session, "Payment processing failed", "danger");
            }
            
        } catch (Exception e) {
            setPaymentMessage(session, "Error: " + e.getMessage(), "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/payment?command=VIEW");
    }
    
    private void setPaymentMessage(HttpSession session, String message, String status) {
        session.setAttribute("PAYMENT_MESSAGE", message);
        session.setAttribute("PAYMENT_STATUS", status);
    }
    
    private void handleError(HttpServletRequest request, HttpServletResponse response, String error) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("NOTIFICATION_MESSAGE", error);
        session.setAttribute("NOTIFICATION_TYPE", "danger");
        response.sendRedirect(request.getContextPath() + "/payment?command=VIEW");
    }
}
