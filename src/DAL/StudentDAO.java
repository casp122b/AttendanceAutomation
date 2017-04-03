/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class StudentDAO
{
    
    private final ConnectionManager cm;

    public StudentDAO() throws IOException {
        cm = new ConnectionManager();
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> allStudents = new ArrayList<>();

        String sql = "SELECT * FROM Student";
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                allStudents.add(getOneStudent(rs));
              
            }
            return allStudents;
        }
    }
    
    public Student add(Student s) throws SQLException
    {
        String sql = "INSERT INTO Student(name) VALUES(?)";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getName());

            ps.executeUpdate();
            ResultSet generatedKey = ps.getGeneratedKeys();
            generatedKey.next();
            int id = generatedKey.getInt(1);
            return new Student(id, s);
        }
    }
    
    public void update(Student s) throws SQLException
    {
        String sql = "UPDATE Student "
                + "SET name = ?, "
                + "WHERE id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setInt(3, s.getId());

            ps.executeUpdate();
        }
    }

    public void delete(Student s) throws SQLException
    {
        String sql = "DELETE FROM Student where id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, s.getId());

            ps.executeUpdate();
        }
    }

//    public Student getById(int id) throws SQLException
//    {
//        String sql = "SELECT * FROM Student WHERE id = ?";
//        try (Connection con = cm.getConnection())
//        {
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, id);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next())
//            {
//                return getOneStudent(rs);
//            }
//            else
//            {
//                return null;
//            }
//        }
//    }

    private Student getOneStudent(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        
        return new Student(id, name);
    }

}

