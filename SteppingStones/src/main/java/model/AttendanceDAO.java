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
        String sqlr = "select student_attended from student_attendance where "
                +"student_id = ? and lesson_id = ?";
        try(Connection conn = ConnectionManager.getConnection()){
            boolean exist = false;
            PreparedStatement stmt = conn.prepareStatement(sqlr);
            stmt.setInt(1, studentID);
            stmt.setInt(2, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                exist = true;
            }
            if(!exist){
                String sqli = "insert into student_attendance(lesson_id, student_id, student_attended, tutor_marked) values(?, ?, ?, ?)";
                PreparedStatement stmti = conn.prepareStatement(sqli);
                stmti.setInt(1, lessonID);
                stmti.setInt(2, studentID);
                stmti.setBoolean(3, attended);
                stmti.setInt(4, tutorID);

                int rows = stmti.executeUpdate();

                if(rows > 0) {
                    return true;
                }
            }else{
                String sqlu = "update student_attendance set student_attended = ? where lesson_id = ? and student_id = ?";
                PreparedStatement stmtu = conn.prepareStatement(sqlu);
                stmtu.setBoolean(1, attended);
                stmtu.setInt(2, lessonID);
                stmtu.setInt(3, studentID);
                int num = stmtu.executeUpdate();
                if (num != 0) {
                    return true;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public double retrieveNumberOfStudentAttendances(int studentID, int classID){
        String sql = "select student_attended from student_attendance where "
                +"student_id = ? and lesson_id in (select lesson_id from lesson where class_id = ?)";
        
        double attended = 0;
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                boolean studentAttended = rs.getBoolean(1);
                
                if(studentAttended){
                    attended += 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attended;
    }
    
    public boolean retrieveStudentAttendances(int studentID, int lessonID){
        String sql = "select student_attended from student_attendance where "
                +"student_id = ? and lesson_id = ?";
        
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
    public boolean retrieveStudentAttendanceAbsent(int studentID, int lessonID){
        String sql = "select student_attended from student_attendance where "
                +"student_id = ? and lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int retrieveNumberOfStudentsAttended(int lessonID){
        String sql = "select count(distinct student_id) from student_attendance where lesson_id = ?";
        int numStudents = 0;
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                numStudents = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numStudents;
    }
}
