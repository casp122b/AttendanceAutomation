/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author Jens, Patrick, Casper
 */
public class StudentDAO {
    /**
     * Writes a list of students to the given file.
     * @param allStudents
     * @param file The file to save to.
     * @throws IOException
     */
    public void saveStudentsToFile(List<Student> allStudents, File file) throws IOException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            oos.writeObject(allStudents);
        }
    }

    /**
     * Reads a list of students from the given file.
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public List<Student> loadStudentsFromFile(File file) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
        {
            return (List<Student>) ois.readObject();
        }
    }
}
