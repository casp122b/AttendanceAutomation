/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Calendar;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper
 */
public class CalendarDAO {
    
    private final ConnectionManager cm;

    public CalendarDAO() throws IOException 
    {
        cm = new ConnectionManager();
    }
    
    public Calendar getOneSchoolDay(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Timestamp schoolDate = rs.getTimestamp("schoolDate");

        return new Calendar(id, schoolDate);
    }
    
    public List<Calendar> getDays() throws SQLException {

        List<Calendar> allSchoolDays = new ArrayList<>();
        String sql = "SELECT * FROM SchoolDays WHERE schoolDate > '2017-01-02' and schoolDate < GETDATE();";

        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allSchoolDays.add(getOneSchoolDay(rs));
            }
            return allSchoolDays;
        }
    }
}
