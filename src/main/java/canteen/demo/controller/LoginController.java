package canteen.demo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import javax.sql.DataSource;

import canteen.demo.database.StudentDatabase;
import canteen.demo.service.StudentService;
import canteen.demo.entity.Student;
import canteen.demo.database.AdminDatabase;
import canteen.demo.service.AdminService;
import canteen.demo.entity.Admin;

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Resource(name="jdbc/canteen")
    private DataSource dataSource;
    private StudentService studentService;
    private AdminService adminService;
    
    @Override
    public void init() throws ServletException {
        StudentDatabase studentDatabase = new StudentDatabase(dataSource);
        AdminDatabase adminDatabase = new AdminDatabase(dataSource);
        studentService = new StudentService(studentDatabase);
        adminService = new AdminService(adminDatabase);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("student") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }
        request.getRequestDispatcher("/log-in.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            // Check admin credentials first
            Admin admin = adminService.validateLogin(email, password);
            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                session.setAttribute("userType", "admin");
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                return;
            }
            
            // If not admin, check student credentials
            Student student = studentService.validateLogin(email, password);
            if (student != null) {
                HttpSession session = request.getSession();
                session.setAttribute("student", student);
                session.setAttribute("userType", "student");
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/log-in.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "System error occurred");
            request.getRequestDispatcher("/log-in.jsp").forward(request, response);
        }
    }
}
