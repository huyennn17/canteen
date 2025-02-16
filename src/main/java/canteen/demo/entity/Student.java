package canteen.demo.entity;


public class Student {
	private int studentId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String studentClass;
	
	
	public Student() {
		
	}
	
	
	public Student(String email, String password, String firstName, String lastName,
			String studentClass) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentClass = studentClass;
	}
	
	public Student(int studentId, String email, String password, String firstName, String lastName,
			String studentClass) {
		this.studentId = studentId;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentClass = studentClass;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	
	

}
