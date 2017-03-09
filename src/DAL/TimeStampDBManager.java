/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.TimeStamp;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper
 */
public class TimeStampDBManager
{

    private final ConnectionManager cm;

    public TimeStampDBManager() throws IOException
    {
        cm = new ConnectionManager();
    }

    public TimeStamp add(TimeStamp ts) throws SQLException
    {
        String sql = "INSERT INTO TimeStamp(dateTime, studentId) VALUES(?, ?)";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, ts.getDateTime());
            ps.setInt(2, ts.getStudentId());

            ps.executeUpdate();
            ResultSet generatedKey = ps.getGeneratedKeys();
            generatedKey.next();
            int id = generatedKey.getInt(1);
            return new TimeStamp(id, ts);
        }
    }

    public void update(TimeStamp ts) throws SQLException
    {
        String sql = "UPDATE TimeStamp "
                + "SET dateTime = ?, "
                + "    studentId = ?, "
                + "WHERE id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ts.getDateTime());
            ps.setInt(2, ts.getStudentId());
            ps.setInt(3, ts.getId());

            ps.executeUpdate();
        }
    }

    public void delete(TimeStamp ts) throws SQLException
    {
        String sql = "DELETE FROM TimeStamp where id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ts.getId());

            ps.executeUpdate();
        }
    }

    public List<TimeStamp> getAll() throws SQLException
    {
        List<TimeStamp> allTimeStamps = new ArrayList<>();

        String sql = "SELECT * FROM TimeStamp";
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                allTimeStamps.add(getOneTimeStamp(rs));
            }
            return allTimeStamps;
        }
    }

    public TimeStamp getById(int id) throws SQLException
    {
        String sql = "SELECT * FROM TimeStamp WHERE id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneTimeStamp(rs);
            }
            else
            {
                return null;
            }
        }
    }

    private TimeStamp getOneTimeStamp(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String dateTime = rs.getString("dateTime");
        int studentId = rs.getInt("studentId");
        
        return new TimeStamp(id, dateTime, studentId);
    }
}
