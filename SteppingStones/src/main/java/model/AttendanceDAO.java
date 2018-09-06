/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Xin
 */
public class AttendanceDAO {
    public boolean updateStudentAttendance(int studentID, int lessonID, int classID, int tutorID){
        String sql = "insert into student_attendance(lesson_id, student_id, student_attended, tutor_marked) values(?, ?, ?, ?)";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);
            stmt.setInt(2, studentID);
            stmt.setBoolean(3, true);
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
}
