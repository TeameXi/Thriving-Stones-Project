/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Xin
 */
public class AttendanceDAO {
    public boolean updateStudentAttendance(int studentID, int lessonID, int classID, int tutorID, boolean attended){
        String sql = "insert into student_attendance(lesson_id, student_id, student_attended, tutor_marked) values(?, ?, ?, ?)";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);
            stmt.setInt(2, studentID);
            stmt.setBoolean(3, attended);
            stmt.setInt(4, tutorID);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean retrieveStudentAttendance(int studentID, int lessonID){
        String sql = "select student_attended from student_attendance where student_id = ? and lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
