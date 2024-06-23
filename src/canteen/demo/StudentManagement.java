package canteen.demo;

import java.util.ArrayList;

public class StudentManagement {
	public static void main(String[] args) {
		ArrayList<Student> listStudent = Student.createStudent();
		for (Student student : listStudent) {
			System.out.println("Student Name: " + student.getName());
		}
		
	}
}
