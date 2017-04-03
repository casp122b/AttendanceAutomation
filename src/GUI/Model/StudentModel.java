/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Student;
import BLL.StudentManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class StudentModel {
    
    //local variables for StudentManager and StudentModel.
    private static StudentModel INSTANCE;
    private final StudentManager studentManager;
    
    /**
     * The list of all students currently in view
     */
    private final ObservableList<Student> allStudents;
    
    /**
     * The method to get a reference to this Singleton:
     *
     * @return
     */
    public static synchronized StudentModel getInstance() throws IOException, SQLException
    {
        if (INSTANCE == null)
        {
            INSTANCE = new StudentModel();
        }
        return INSTANCE;
    }
    
    /**
     * Constructs a new StudentManager and creates an observable arraylist out  of the observable list Student.
     */
    private StudentModel() throws IOException, SQLException
    {
        studentManager = new StudentManager();
        allStudents = FXCollections.observableArrayList();
        
        allStudents.addAll(studentManager.getAllStudents());
    }
    
    /**
     * Gets the list of all Students added to the system.
     *
     * @return
     */
    public ObservableList<Student> getAllStudents()
    {
        return allStudents;
    }
    
    public void addStudent(Student s) throws SQLException
    {
        Student stud = studentManager.add(s);
        allStudents.add(stud);
    }
    
    public void deleteStudent(Student s) throws SQLException
    {
        studentManager.delete(s);
        allStudents.remove(s);
    }
}
