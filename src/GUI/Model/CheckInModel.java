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
import BLL.CalendarManager;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class CheckInModel {

    private static CheckInModel INSTANCE;
    private ObservableList<StudentCheckIn> studentCheckIn;
    private final CheckInManager checkInMgr;
    private final CalendarManager calendarMgr;
    private ObservableList<Calendar> calendar;

    /**
     * Constructs a new StudentManager and creates an observable arraylist out
     * of the observable list Student.
     */
    private CheckInModel() throws IOException {
        checkInMgr = new CheckInManager();
        calendarMgr = new CalendarManager();
    }

    /**
     * The method to get a reference to this Singleton:
     *
     * @return
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static synchronized CheckInModel getInstance() throws IOException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new CheckInModel();
        }
        return INSTANCE;
    }

    /**
     * This method returns an observable list of BE class StudentCheckIn.
     * @return 
     */
    public ObservableList<StudentCheckIn> getStudentCheckIn() {
        return studentCheckIn;
    }

    /**
     * This method returns an observable list of BE class Calendar.
     * @return 
     */
    public ObservableList<Calendar> getSchoolDate() {
        return calendar;
    }

    /**
     * Creates an observable list of StudentCheckIn and adds all timeStamps on the specific student by id into CheckInManager.
     * @param id
     * @throws SQLException 
     */
    public void setCheckInListById(int id) throws SQLException {

        studentCheckIn = FXCollections.observableArrayList();
        studentCheckIn.addAll(checkInMgr.getAllCheckInsById(id));
    }

    /**
     * Creates an observable list of Calendar, adds all items of the array into getDays() in CalendarManager.
     * @throws SQLException 
     */
    public void setTest() throws SQLException {
        calendar = FXCollections.observableArrayList();
        calendar.addAll(calendarMgr.getDays());
    }

    /*
    * adds the check ins for students
    */
    public boolean addStudentCheckIn(StudentCheckIn sCheckIn) throws SQLException {
        boolean dateExists = false;
        for (StudentCheckIn sci : studentCheckIn) {
            if (sCheckIn.getDateTime().getDate() == sci.getDateTime().getDate()
                    && sCheckIn.getDateTime().getYear() == sci.getDateTime().getYear()
                    && sCheckIn.getDateTime().getMonth() == sci.getDateTime().getMonth()) {
                dateExists = true;
            }
        }
        if (!dateExists) {
            StudentCheckIn studCheckIn = checkInMgr.add(sCheckIn);
            studentCheckIn.add(studCheckIn);
            return true;
        }
        return false;
    }

    public void deleteCheckIn(StudentCheckIn studCheckIn) throws SQLException {
        checkInMgr.delete(studCheckIn);
        studentCheckIn.remove(studCheckIn);
    }

    public void deleteAttendanceByStudentId(Student s) throws SQLException {
        checkInMgr.deleteByStudentId(s.getId());
        for (int i = 0; i < studentCheckIn.size(); i++) {
            studentCheckIn.remove(i);
        }
    }

    /*
    * calculates the attendance % for a student from the start of the semester 
    * until current date
    */
    public StudentCheckIn calcAttendance(LocalDateTime time, Student s) throws SQLException {
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(time);
        int studentId = s.getId();
        double getArraySize = getStudentCheckIn().size(); //Number of timeStamps on a specific student.
        setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = (daysAway * 100) / schoolDaysUntillNow;

        double isAttendance = absence;
        addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
        StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);
        return studCheckIn;

    }

    /*
    * returns the absence of a specific student
    */
    public double getStudentAbsence() throws SQLException 
    {
        double getArraySize = getStudentCheckIn().size(); //Number of timeStamps on a specific student.
        setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = ((daysAway) * 100) / schoolDaysUntillNow;

        return absence;
    }
    
    /*
    * returns the absence of a specific student
    */
    public StudentCheckIn teacherViewAttendance (LocalDateTime time, Student s) throws SQLException{
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(time);
        int studentId = s.getId();
        double getArraySize = checkInMgr.getAllCheckInsById(s.getId()).size();  //Number of timeStamps on a specific student.
        setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = (daysAway * 100) / schoolDaysUntillNow;
        
        double isAttendance = absence;
            
            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);
        return studCheckIn;
    }
    
    /*
    * formats the absence so that is has the correct amount of decimals
    */
    public String showStudentAbsence() throws SQLException
    {
        String pattern = "#.##";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(getStudentAbsence());
        return output;
    }
}
