/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.StudentCheckIn;
import DAL.CheckInDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class CheckInManager {
    
    
    private CheckInDAO checkInDAO;

    public CheckInManager() throws IOException {
        checkInDAO = new CheckInDAO();
    }
    
    /**
     * ArrayList of BE class StudentCheckIn.
     * @param id
     * @return
     * @throws SQLException 
     */
    public List<StudentCheckIn> getAllCheckInsById(int id) throws SQLException {
        return checkInDAO.getByStudentId(id);
    }
    
    /**
     * Method for calling add() in checkInDAO.
     * @param sCheckIn
     * @return
     * @throws SQLException 
     */
    public StudentCheckIn add(StudentCheckIn sCheckIn) throws SQLException {
        return checkInDAO.add(sCheckIn);
    }
    
    /**
     * Method for calling getAllCheckIns() in checkInDAO.
     * @return
     * @throws SQLException 
     */
    public List<StudentCheckIn> getStudentCheckIn() throws SQLException
    {
        return checkInDAO.getAllCheckIns();
    }

    /**
     * Method for calling delete() in checkInDAO.
     * @param studCheckIn
     * @throws SQLException 
     */
    public void delete(StudentCheckIn studCheckIn) throws SQLException {
        checkInDAO.delete(studCheckIn);
    }
    
    /**
     * Method for calling deleteByStudentId() in checkInDAO.
     * @param id
     * @throws SQLException 
     */
    public void deleteByStudentId(int id) throws SQLException
    {
        checkInDAO.deleteByStudentId(id);
    }
}
