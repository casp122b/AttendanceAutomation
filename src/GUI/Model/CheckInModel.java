/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.StudentCheckIn;
import BLL.CheckInManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BE.Calendar;
import BE.Student;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Jens, Patrick, Casper, Kasper
 */
public class CheckInModel {
    
    private static CheckInModel INSTANCE;
    private ObservableList<StudentCheckIn> studentCheckIn;
    private final CheckInManager checkInMgr;
    private ObservableList<Calendar> calendar;

    /**
     * Constructs a new StudentManager and creates an observable arraylist out  of the observable list Student.
     */
    private CheckInModel() throws IOException
    {
        checkInMgr = new CheckInManager();
    }
    
    /**
     * The method to get a reference to this Singleton:
     *
     * @return
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static synchronized CheckInModel getInstance() throws IOException, SQLException
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CheckInModel();
        }
        return INSTANCE;
    }
    
    public ObservableList<StudentCheckIn> getStudentCheckIn() {
        return studentCheckIn;
    }
    
    public ObservableList<Calendar> getSchoolDate()
    {
        return calendar;
    }

    public void setStudentCheckIn(ObservableList<StudentCheckIn> studentCheckIn) {
        this.studentCheckIn = studentCheckIn;
    }
    
    public void setCheckInListById(int id) throws SQLException{
                
        studentCheckIn = FXCollections.observableArrayList();
        studentCheckIn.addAll(checkInMgr.getAllCheckInsById(id));
    }
    
    public void setTest() throws SQLException
    {
        calendar = FXCollections.observableArrayList();
        calendar.addAll(checkInMgr.getDays());
    }

    public boolean addStudentCheckIn(StudentCheckIn sCheckIn) throws SQLException {
        boolean dateExists = false;
        for (StudentCheckIn sci : studentCheckIn) {
            
            if(sCheckIn.getDateTime().getDate() == sci.getDateTime().getDate() 
                    && sCheckIn.getDateTime().getYear() == sci.getDateTime().getYear() 
                    && sCheckIn.getDateTime().getMonth() == sci.getDateTime().getMonth())
            {
                dateExists = true;
            }
                
            
        }
        if(!dateExists)
        {
        StudentCheckIn studCheckIn = checkInMgr.add(sCheckIn);
        studentCheckIn.add(studCheckIn);
        return true;
        }
        return false;
    }
    
    public void getStudentCheckInOnly() throws SQLException
    {
        studentCheckIn = FXCollections.observableArrayList();
        studentCheckIn.addAll(checkInMgr.getStudentCheckIn());
    }
    
//    public int getStudentIdFromModel()
//    {
//        return checkInMgr.getStudentIdFromManager();
//    }

    public void deleteCheckIn(StudentCheckIn studCheckIn) throws SQLException {
        checkInMgr.delete(studCheckIn);
        studentCheckIn.remove(studCheckIn);
    }
    
    public void deleteAttendanceByStudentId(Student s) throws SQLException
    {
        checkInMgr.deleteByStudentId(s.getId());
        for (int i = 0; i < studentCheckIn.size(); i++)
        {
            studentCheckIn.remove(i);
        }
    }
    public StudentCheckIn calcAttendance(LocalDateTime time, Student s) throws SQLException{
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(time);
        int studentId = s.getId();
        double getArraySize = getStudentCheckIn().size(); //Number of timeStamps on a specific student.
        setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = ((daysAway - 1) * 100) / schoolDaysUntillNow;
        
        double isAttendance = absence;
            addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);
        return studCheckIn;
        
    }
    
    public double getStudentAbsence() throws SQLException
    {
        double getArraySize = getStudentCheckIn().size(); //Number of timeStamps on a specific student.
        setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = ((daysAway) * 100) / schoolDaysUntillNow;
        
        return absence;
    }
}
