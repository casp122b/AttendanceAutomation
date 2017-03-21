/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

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
 *
 * @author Casper
 */
public class CheckInDAO
{

    private final ConnectionManager cm;
//    private int studentId;

    public CheckInDAO() throws IOException
    {
        cm = new ConnectionManager();
    }

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
    
    public StudentCheckIn getOneCheckIn(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        Timestamp dateTime = rs.getTimestamp("studentCheckIn");
        int studentId = rs.getInt("studentId");
        double isAttendance = rs.getDouble("isAttendance");
        
        return new StudentCheckIn(id, dateTime, studentId, isAttendance);
    }

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
    
//    public int getStudentId()
//    {
//        return studentId;
//    }
}
