package canteen.demo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import canteen.demo.entity.Student;

public class StudentDatabase {
	private DataSource dataSource;

	public StudentDatabase(DataSource dataSource) {
		this.dataSource = dataSource;
	}   
	
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<Student>(); 
		Connection con= null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			System.out.println("===========Connection created===========");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from students");
			while(rs.next()) {
				int id = rs.getInt("student_id");
				String email = rs.getString("student_email");
				String password = rs.getString("student_password");
				String firstName = rs.getString("student_firstname");
				String lastName = rs.getString("student_lastname");
				String studentClass = rs.getString("class");
				
				
				Student student = new Student(id, email, password, firstName, lastName, studentClass);
				students.add(student);
			}
			con.close();
			return students;
		} 
		finally {
		}
		
	}
	
	public List<Student> searchStudentsByFirstName(String firstNameParam) throws Exception {
		List<Student> students = new ArrayList<Student>(); 
		Connection con= null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			System.out.println("===========Connection created===========");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from students where first_name like %"+firstNameParam+"%");
			while(rs.next()) {
				int id = rs.getInt("student_id");
				String email = rs.getString("student_email");
				String password = rs.getString("student_password");
				String firstName = rs.getString("student_firstname");
				String lastName = rs.getString("student_lastname");
				String studentClass = rs.getString("class");
				
				
				Student student = new Student(id, email, password, firstName, lastName, studentClass);
				students.add(student);
			}
			con.close();
			return students;
		} 
		finally {
		}
		
	}
	
	public void addStudent(Student student) throws Exception {
		List<Student> listStudents = getStudents();
		if (!listStudents.contains(student)) {
			
			Connection con= null;
			PreparedStatement stmt = null;
				
			try {
				con = dataSource.getConnection();
				System.out.println("===========Connection created===========");
				
				String sql = "INSERT INTO canteen.students(student_email, student_password, student_firstname, student_lastname, class) "
						+ "VALUES (?,?,?,?,?)";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, student.getEmail());
				stmt.setString(2, student.getPassword());
				stmt.setString(3, student.getFirstName());
				stmt.setString(4, student.getLastName());
				stmt.setString(5, student.getStudentClass());
	
				stmt.execute();
				System.out.println("Insert Student Successfully");
				con.close();
			} finally {
				con.close();
			}
		}
		
	}

	public Student getStudentById(int studentId) throws SQLException {
		List<Student> students = new ArrayList<Student>(); 
		Connection con= null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Student student = null;
		try {
			con = dataSource.getConnection();
			System.out.println("===========Connection created===========");
			String sql = "select * from students WHERE student_id = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, studentId);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("student_id");
				String email = rs.getString("student_email");
				String password = rs.getString("student_password");
				String firstName = rs.getString("student_firstname");
				String lastName = rs.getString("student_lastname");
				String studentClass = rs.getString("class");
				
				student = new Student(id, email, password, firstName, lastName, studentClass);
			}
			return student;
			
		} 
		finally {
			con.close();
		}
	}
	public void updateStudent(Student student) throws SQLException {
	    String sql = "UPDATE students SET student_email=?, student_password=?, " +
                    "student_firstname=?, student_lastname=?, class=? " +
                    "WHERE student_id=?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, student.getEmail());
            stmt.setString(2, student.getPassword());
            stmt.setString(3, student.getFirstName());
            stmt.setString(4, student.getLastName());
            stmt.setString(5, student.getStudentClass());
            stmt.setInt(6, student.getStudentId());
            
            stmt.execute();
        }
	}
	public boolean deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id=?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            return stmt.executeUpdate() > 0;
        }
    }
}

