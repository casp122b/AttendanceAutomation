/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Calendar;
import DAL.CalendarDAO;
import DAL.CheckInDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Casper
 */
public class CalendarManager {
    
    private CalendarDAO calendarDAO;

    public CalendarManager() throws IOException {
        calendarDAO = new CalendarDAO();
    }
    
    public List<Calendar> getDays() throws SQLException
    {
        return calendarDAO.getDays();
    }
    
}
