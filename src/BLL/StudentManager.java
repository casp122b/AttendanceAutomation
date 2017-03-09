/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Student;
import DAL.StudentDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jens, Patrick, Casper
 */
public class StudentManager {
    
    //local variable for class StudentDAO.
    private StudentDAO studentDAO;
    
    /**
     * Constructs StudentManager and creates a new StudentDAO object.
     * @throws java.io.IOException
     */
    public StudentManager() throws IOException
    {
        studentDAO = new StudentDAO();
    }
    
    /**
     * Creates annd returns a new Student object.
     * @param name
     * @param attendance
     * @return The new Student object.
     */
    public Student createNewStudent(String name, int attendance)
    {
        return new Student(name, attendance);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }
}
