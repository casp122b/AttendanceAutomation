/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Student;
import BE.Absence;
import BLL.StudentManager;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jens, Patrick, Casper
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
    public static synchronized StudentModel getInstance()
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
    private StudentModel()
    {
        studentManager = new StudentManager();
        allStudents = FXCollections.observableArrayList();
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
    
    /**
     * Adds name and lastname to the observable list Student.
     * @param name 
     * @param currentClass 
     * @param absence 
     */

    public void addNewStudent(String name, String currentClass, Absence absence)
    {
        Student student = studentManager.createNewStudent(name, currentClass, absence);
        allStudents.add(student);
    }
    /**
     * Saves an arraylist of students into a file.
     * @param file
     * @throws IOException 
     */
    public void SaveStudentsToFile(File file) throws IOException
    {
        studentManager.saveStudents(allStudents, file);
        
    }

    /**
     * Reads from the saved file on the computer. The saved file is specified through FileChooser in TeacherViewController.
     * @param file
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void LoadPersonsFromFile(File file) throws IOException, ClassNotFoundException
    {
        List<Student> newAllStudents = studentManager.loadStudents(file);
        allStudents.setAll(newAllStudents);
        
    }
    
    /**
     * Predefined data of all students in EASV2016A
     */
    public void prefixedStudentList()
    {
        INSTANCE.addNewStudent("Adam", "SCO", new Absence(100,50,0,50,100));
        INSTANCE.addNewStudent("Bo", "SCO", new Absence(50,0,0,0,0));
        INSTANCE.addNewStudent("Casper", "SCO", new Absence(0,100,50,0,0));
        INSTANCE.addNewStudent("Casper R", "SCO", new Absence(0,50,50,50,0));
        INSTANCE.addNewStudent("Emil", "SCO", new Absence(100,50,0,0,0));
        INSTANCE.addNewStudent("Frederik", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Jacob", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Jens", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Jesper", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Joan", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Kenneth", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Kenni", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Lucas", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Mads", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Mathias P", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Mathias R", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Michael", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Mickaei", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Miklas", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Nicolai", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Patrick", "SCO",  new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Rasmus", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Simon B", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Simon H", "SCO", new Absence(0,0,0,0,0));
        INSTANCE.addNewStudent("Stefan O", "SCO", new Absence(100,100,100,100,100));
        INSTANCE.addNewStudent("Stephan R", "SCO", new Absence(100,100,100,100,100));
        INSTANCE.addNewStudent("Stephan F", "SCO", new Absence(100,100,100,100,100));
        INSTANCE.addNewStudent("Thomas", "SCO", new Absence(100,100,100,100,100));
    }
}
