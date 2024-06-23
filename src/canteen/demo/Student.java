package canteen.demo;

import java.util.ArrayList;
import java.util.Scanner;

public class Student {
	private String studentID;
	private String name;
	private String className;
	private String image;
	private String parentID;
	
	public static ArrayList<Student> createStudent(){
		
		Scanner data = new Scanner(System.in);
		System.out.println("Input student name: ");
		String name = data.next();
		
		System.out.println("Input student id: ");
		String studentId = data.next();
		
		System.out.println("Input student's class name: ");
		String className = data.next();
		
		System.out.println("Input student's image: ");
		String image = data.next();
		
		System.out.println("Input parent id: ");
		String parentID = data.next();
		
		
		Student student = new Student(studentId, name, className, image, parentID);
		
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent.add(student);
		
		return listStudent;
		
	}
	
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getParentID() {
		return parentID;
	}
	
	public Student(String studentID, String name, String className, String image, String parentID) {
		this.studentID = studentID;
		this.name = name;
		this.className = className;
		this.image = image;
		this.parentID = parentID;
	}
	
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	
}
