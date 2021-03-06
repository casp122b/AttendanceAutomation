/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Calendar;
import BE.StudentCheckIn;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class CheckInDAO
{

    private final ConnectionManager cm;

    public CheckInDAO() throws IOException
    {
        cm = new ConnectionManager();
    }

    /**
     * Adds a student object to database table StudentCheckIn containing timeStamp, studentId and absence percentage.
     * @param ts
     * @return
     * @throws SQLException 
     */
    public StudentCheckIn add(StudentCheckIn ts) throws SQLException
    {
        String sql = "INSERT INTO StudentCheckIn(studentCheckIn, studentId, isAttendance) VALUES(?, ?, ?)";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, ts.getDateTime());
            ps.setInt(2, ts.getStudentId());
            ps.setDouble(3, ts.getIsAttendance());

            ps.executeUpdate();
            ResultSet generatedKey = ps.getGeneratedKeys();
            generatedKey.next();
            int id = generatedKey.getInt(1);
            return new StudentCheckIn(id, ts);   
        }
    }

    /**
     * Updates database table StudentCheckIn.
     * @param ts
     * @throws SQLException 
     */
    public void update(StudentCheckIn ts) throws SQLException
    {
        String sql = "UPDATE StudentCheckIn "
                + "SET dateTime = ?, "
                + "    studentId = ?, "
                + "WHERE id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, ts.getDateTime());
            ps.setInt(2, ts.getStudentId());
            ps.setInt(3, ts.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Deletes a timeStamp from StudentCheckIn.
     * @param ts
     * @throws SQLException 
     */
    public void delete(StudentCheckIn ts) throws SQLException
    {
        String sql = "DELETE FROM StudentCheckIn where id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ts.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Populates an ArrayList of BE class StudentCheckIn with timeStamps.
     * @return
     * @throws SQLException 
     */
    public List<StudentCheckIn> getAll() throws SQLException
    {
        List<StudentCheckIn> allTimeStamps = new ArrayList<>();

        String sql = "SELECT * FROM StudentCheckIn";
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                allTimeStamps.add(getOneCheckIn(rs));
            }
            return allTimeStamps;
        }
    }

    /**
     * Method for selecting a specific student by id. The data is gathered from database table StudentCheckIn.
     * @param id
     * @return
     * @throws SQLException 
     */
    public StudentCheckIn getById(int id) throws SQLException
    {
        String sql = "SELECT * FROM StudentCheckIn WHERE id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneCheckIn(rs);
            }
            else
            {
                return null;
            }
        }
    }
    
    /**
     * Populates a new ArrayList of StudentCheckIn with timeStamps for a specific student gathered from database table StudentCheckIn.
     * @param id
     * @return
     * @throws SQLException 
     */
    public List<StudentCheckIn> getByStudentId(int id) throws SQLException{
        
      List<StudentCheckIn> allTimeStamps = new ArrayList<>();
      String sql = "SELECT * FROM StudentCheckIn WHERE studentId = ?";
      try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                allTimeStamps.add(getOneCheckIn(rs));
            }
            return allTimeStamps;
        }
      
    }
    
    /**
     * Reflects the attributes for 1 student in the database.
     * @param rs
     * @return
     * @throws SQLException 
     */
    public StudentCheckIn getOneCheckIn(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        Timestamp dateTime = rs.getTimestamp("studentCheckIn");
        int studentId = rs.getInt("studentId");
        double isAttendance = rs.getDouble("isAttendance");
        
        return new StudentCheckIn(id, dateTime, studentId, isAttendance);
    }

    /**
     * Method for returning all timeStamps on a student.
     * @return
     * @throws SQLException 
     */
    public List<StudentCheckIn> getAllCheckIns() throws SQLException {
        List<StudentCheckIn> allCheckIns = new ArrayList<>();

        String sql = "SELECT studentCheckIn FROM StudentCheckIn";
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                allCheckIns.add(getOneCheckIn(rs));
            }
            return allCheckIns;
        }
    }

    /**
     * Method for deleting a selected timeStamps for a specific student.
     * @param id
     * @throws SQLException 
     */
    public void deleteByStudentId(int id) throws SQLException
    {
        String sql = "DELETE FROM StudentCheckIn where studentId = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
}
