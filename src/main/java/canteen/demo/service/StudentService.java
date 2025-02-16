package canteen.demo.service;

import canteen.demo.database.StudentDatabase;
import canteen.demo.entity.Student;
import java.util.List;

public class StudentService {
    private StudentDatabase studentDatabase;
    
    public StudentService(StudentDatabase studentDatabase) {
        this.studentDatabase = studentDatabase;
    }
    
    public Student validateLogin(String email, String password) throws Exception {
        List<Student> students = studentDatabase.getStudents();
        for(Student student : students) {
            if(student.getEmail().equals(email) && student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() throws Exception {
        return studentDatabase.getStudents();
    }
    
    public void createStudent(Student student) throws Exception {
        studentDatabase.addStudent(student);
    }
    
    public Student getStudentById(int id) throws Exception {
        return studentDatabase.getStudentById(id);
    }
    
    public void updateStudent(Student student) throws Exception {
        studentDatabase.updateStudent(student);
    }
    
    public boolean deleteStudent(int id) throws Exception {
        return studentDatabase.deleteStudent(id);
    }
}
