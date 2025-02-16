package canteen.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import canteen.demo.database.StudentDatabase;
import canteen.demo.entity.Student;

public class StudentController extends HttpServlet {
	
	private StudentDatabase studentDatabase;
	
	@Resource(name="jdbc/canteen")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDatabase = new StudentDatabase(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private static final long serialVersionUID = 1L;
       
    public StudentController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String command = request.getParameter("command");
			if (command == null) {
				command = "LIST";
			}
			
			switch (command) {
				case "VIEW":
					viewStudent(request, response);
					break;
				case "DELETE":
					deleteStudent(request, response);
					break;
				case "LIST":
				default:
					listStudents(request, response);
					break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String command = request.getParameter("command");
			
			switch (command) {
				case "ADD":
					addStudent(request, response);
					break;
				case "UPDATE":
					updateStudent(request, response);
					break;
				default:
					listStudents(request, response);
					break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = studentDatabase.getStudents();
		request.setAttribute("student_list", students);
		request.getRequestDispatcher("/view_students.jsp").forward(request, response);
	}

	private void viewStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getParameter("studentId");
		Student student = studentDatabase.getStudentById(Integer.parseInt(studentId));
		request.setAttribute("student", student);
		request.getRequestDispatcher("/admin/student-details.jsp").forward(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String studentClass = request.getParameter("studentClass");

		Student student = new Student(studentId, email, password, firstName, lastName, studentClass);
		
		if (password == null || password.trim().isEmpty()) {
			Student existingStudent = studentDatabase.getStudentById(studentId);
			student.setPassword(existingStudent.getPassword());
		}

		studentDatabase.updateStudent(student);
		response.sendRedirect(request.getContextPath() + "/students?command=LIST");
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		studentDatabase.deleteStudent(studentId);
		response.sendRedirect(request.getContextPath() + "/students?command=LIST");
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String studentClass = request.getParameter("studentClass");
		
		Student student = new Student(email, password, firstName, lastName, studentClass);
		studentDatabase.addStudent(student);
		response.sendRedirect(request.getContextPath() + "/students?command=LIST");
	}
}
