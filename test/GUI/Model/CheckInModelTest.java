/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Calendar;
import BE.Student;
import BE.StudentCheckIn;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author patrick
 */
public class CheckInModelTest {
    
    public CheckInModelTest() {
    }

    /**
     * Test of getInstance method, of class CheckInModel.
     */
    @Test
    public void testGetInstance() throws Exception {
        System.out.println("getInstance");
        CheckInModel expResult = null;
        CheckInModel result = CheckInModel.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
  
    }

    /**
     * Test of getStudentCheckIn method, of class CheckInModel.
     */
    @Test
    public void testGetStudentCheckIn() {
        System.out.println("getStudentCheckIn");
        CheckInModel instance = null;
        ObservableList<StudentCheckIn> expResult = null;
        ObservableList<StudentCheckIn> result = instance.getStudentCheckIn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     
    }   

    /**
     * Test of getSchoolDate method, of class CheckInModel.
     */
    @Test
    public void testGetSchoolDate() {
        System.out.println("getSchoolDate");
        CheckInModel instance = null;
        ObservableList<Calendar> expResult = null;
        ObservableList<Calendar> result = instance.getSchoolDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of setCheckInListById method, of class CheckInModel.
     */
    @Test
    public void testSetCheckInListById() throws Exception {
        System.out.println("setCheckInListById");
        int id = 0;
        CheckInModel instance = null;
        instance.setCheckInListById(id);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of setTest method, of class CheckInModel.
     */
    @Test
    public void testSetTest() throws Exception {
        System.out.println("setTest");
        CheckInModel instance = null;
        instance.setTest();
        // TODO review the generated test code and remove the default call to fail.
     
    }

    /**
     * Test of addStudentCheckIn method, of class CheckInModel.
     */
    @Test
    public void testAddStudentCheckIn() throws Exception {
        System.out.println("addStudentCheckIn");
        StudentCheckIn sCheckIn = null;
        CheckInModel instance = null;
        boolean expResult = false;
        boolean result = instance.addStudentCheckIn(sCheckIn);
        assertEquals(expResult, result);
      
    }

    /**
     * Test of deleteCheckIn method, of class CheckInModel.
     */
    @Test
    public void testDeleteCheckIn() throws Exception {
        System.out.println("deleteCheckIn");
        StudentCheckIn studCheckIn = null;
        CheckInModel instance = null;
        instance.deleteCheckIn(studCheckIn);
     
    }

    /**
     * Test of deleteAttendanceByStudentId method, of class CheckInModel.
     */
    @Test
    public void testDeleteAttendanceByStudentId() throws Exception {
        System.out.println("deleteAttendanceByStudentId");
        Student s = null;
        CheckInModel instance = null;
        instance.deleteAttendanceByStudentId(s);
       
    }

    /**
     * Test of calcAttendance method, of class CheckInModel.
     */
    @Test
    public void testCalcAttendance() throws Exception {
        System.out.println("calcAttendance");
        LocalDateTime time = null;
        Student s = null;
        CheckInModel instance = null;
        StudentCheckIn expResult = null;
        StudentCheckIn result = instance.calcAttendance(time, s);
        assertEquals(expResult, result);
 
    }

    /**
     * Test of getStudentAbsence method, of class CheckInModel.
     */
    @Test
    public void testGetStudentAbsence() throws Exception {
        System.out.println("getStudentAbsence");
        CheckInModel instance = null;
        double expResult = 0.0;
        double result = instance.getStudentAbsence();
        assertEquals(expResult, result, 0.0);
 
    }

    /**
     * Test of teacherViewAttendance method, of class CheckInModel.
     */
    @Test
    public void testTeacherViewAttendance() throws Exception {
        System.out.println("teacherViewAttendance");
        LocalDateTime time = null;
        Student s = null;
        CheckInModel instance = null;
        StudentCheckIn expResult = null;
        StudentCheckIn result = instance.teacherViewAttendance(time, s);
        assertEquals(expResult, result);
   
    }

    /**
     * Test of showStudentAbsence method, of class CheckInModel.
     */
    @Test
    public void testShowStudentAbsence() throws Exception {
        System.out.println("showStudentAbsence");
        CheckInModel instance = null;
        String expResult = "";
        String result = instance.showStudentAbsence();
        assertEquals(expResult, result);

    }
    
}
