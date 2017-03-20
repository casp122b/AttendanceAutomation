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
 *
 * @author patrick
 */
public class CheckInManager {
    
    
    private CheckInDAO checkInDAO;

    public CheckInManager() throws IOException {
        checkInDAO = new CheckInDAO();
    }
    
    
    
    public List<StudentCheckIn> getAllCheckInsById(int id) throws SQLException {
        return checkInDAO.getByStudentId(id);
    }

    public StudentCheckIn add(StudentCheckIn sCheckIn) throws SQLException {
        return checkInDAO.add(sCheckIn);
    }
    
//    public int getStudentIdFromManager()
//    {
//        return checkInDAO.getStudentId();
//    }
}
