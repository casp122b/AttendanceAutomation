/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.io.Serializable;

/**
 *
 * @author Jens, Patrick, Casper
 */
public class Student implements Serializable {
    private String name;
    private String currentClass;
    private Boolean attendance = false;
    private Absence absences;

    /**
     * Constructor for the Student class
     * @param name
     * @param currentClass
     * @param absence 
     */
    public Student (String name, String currentClass, Absence absence){
        this.name = name;
        this.currentClass = currentClass;
        this.absences = absence;
    }
    
    /**
     * Get the value of attendance
     *
     * @return the value of attendance
     */
    public Boolean isAttendance() {
        return attendance;
    }
    /**
     * Set the value of attendance
     *
     * @param attendance new value of attendance
     */
    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
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
     * gets the currentClass
     * @return 
     */
    public String getCurrentClass() {
        return currentClass;
    }

    /**
     * sets currentClass
     * @param currentClass 
     */
    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    /**
     * returns name, currentClass and attendance as strings
     * @return 
     */
    @Override
    public String toString() {
        return name + currentClass + attendance;
    }

    /**
     * @return the absences
     */
    public Absence getAbsences() {
        return absences;
    }
}
