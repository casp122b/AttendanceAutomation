/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Student;
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
     * @param absences 
     * @param timeStamp 
     */

    public void addNewStudent(String name, String currentClass, int absences, Date timeStamp)
    {
        Student student = studentManager.createNewStudent(name, currentClass, absences, timeStamp);
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
     * Reads from the saved file on the computer. The saved file is specified through FileChooser in MainViewController.
     * @param file
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void LoadPersonsFromFile(File file) throws IOException, ClassNotFoundException
    {
        List<Student> newAllStudents = studentManager.loadStudents(file);
        allStudents.setAll(newAllStudents);
        
    }
    
    public void prefixedStudentList()
    {
        INSTANCE.addNewStudent("Casper", "SCO", 50, null);
        INSTANCE.addNewStudent("IB", "SCO", 12, null);
        INSTANCE.addNewStudent("Bent", "SCO", 20, null);
        
    }
}
