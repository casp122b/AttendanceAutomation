/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.Date;

/**
 *
 * @author Casper
 */
public class StudentCheckIn{
    private int id;
    private String dateTime;
    private int studentId;

    public StudentCheckIn(int id, StudentCheckIn ts) {
        this(id, ts.getDateTime(), ts.getStudentId());
    }
    
    public StudentCheckIn(String dateTime, int studentId){
        this(-1, dateTime, studentId);
    }

    public StudentCheckIn(int id, String dateTime, int studentId) {
        this.id = id;
        this.dateTime = dateTime;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
}
