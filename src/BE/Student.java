/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jens, Patrick, Casper
 */
public class Student implements Serializable {
    private int id;
    private String name;
    private int attendance = 0;
    private ArrayList<StudentCheckIn> stamps;

    /**
     * Constructor for the Student class
     * @param name 
     * @param attendance 
     */
    public Student (String name, int attendance){
        this(-1, name, attendance);
    }

    public Student(int id, Student s) {
        this(id, s.getName(), s.getAttendance());
    }
    
    public Student(int id, String name, int attendance){
        this.id = id;
        this.attendance = attendance;
        this.name = name;
        this.stamps = new ArrayList<StudentCheckIn>();
    }

    /**
     * Gets the value of name
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns name, currentClass and attendance as strings
     * @return 
     */
    @Override
    public String toString() {
        return name + attendance;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

}
