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
 * @author Jens, Patrick, Casper, Kasper
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
        return new Student(name);
    }

    /**
     * Method for calling getAllStudents() in studentDAO.
     * @return
     * @throws SQLException 
     */
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }
    
    /**
     * Method for calling add() in studentDAO.
     * @param s
     * @return
     * @throws SQLException 
     */
    public Student add(Student s) throws SQLException
    {
        return studentDAO.add(s);
    }
    
    /**
     * Method for calling delete() in studentDAO.
     * @param s
     * @throws SQLException 
     */
    public void delete(Student s) throws SQLException
    {
        studentDAO.delete(s);
    }
}
