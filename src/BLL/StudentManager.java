/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Absence;
import BE.Student;
import DAL.StudentDAO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
     */
    public StudentManager()
    {
        studentDAO = new StudentDAO();
    }
    
    /**
     * Creates annd returns a new Student object.
     * @param name
     * @param currentClass
     * @param absence
     * @return The new Student object.
     */
    public Student createNewStudent(String name, String currentClass, Absence absence)
    {
        return new Student(name, currentClass, null);
    }
    /**
     * Saves an arraylist of the class object Student in a file.
     * @param allStudents
     * @param file
     * @throws IOException 
     */
    public void saveStudents(List<Student> allStudents, File file) throws IOException
    {
        //Because the observerable list is not serializable we have to convert it to a normal arraylist.
        ArrayList<Student> serializableList = new ArrayList<>(allStudents);
        studentDAO.saveStudentsToFile(serializableList, file);
    }

    /**
     * Loads an arraylist of the class object Student.
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public List<Student> loadStudents(File file) throws IOException, ClassNotFoundException
    {
       return studentDAO.loadStudentsFromFile(file);
    }
}
