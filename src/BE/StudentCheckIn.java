/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.sql.Timestamp;

/**
 *
 * @author Jens, Patrick, Casper, Kasper
 */
public class StudentCheckIn{
    private int id;
    private Timestamp dateTime;
    private int studentId;
    private double isAttendance;

    public double getIsAttendance() {
        return isAttendance;
    }

    public void setIsAttendance(double isAttendance) {
        this.isAttendance = isAttendance;
    }

    public StudentCheckIn(int id, StudentCheckIn ts) {
        this(id, ts.getDateTime(), ts.getStudentId(), ts.isAttendance);
    }
    
    public StudentCheckIn(Timestamp dateTime, int studentId, double isAttendance){
        this(-1, dateTime, studentId, isAttendance);
    }

    public StudentCheckIn(int id, Timestamp dateTime, int studentId, double isAttendance) {
        this.id = id;
        this.dateTime = dateTime;
        this.studentId = studentId;
        this.isAttendance = isAttendance;
    }
    
    public StudentCheckIn(Timestamp dateTime, double isAttendance)
    {
        this(-1, dateTime, -1, isAttendance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
}
