package model;

import connection.ConnectionManager;
import entity.Lesson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonDAO {
    public void updateLesson(String tutorID, String level, String subject) {
        System.out.println("HALPPPPPPPPP");
        String lesson = "";
        String sql = "select lesson_id from lesson where class_id in (select class_id from class where level_id = ? and subject_id = ?)";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,level);
            stmt.setString(2,subject);
            System.out.println(stmt);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                lesson = rs.getString(1);
            }
            
            sql = "update lesson set tutor_id = ? where lesson_id = ?";
            System.out.println(sql + " CORRECT");
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tutorID);
            stmt.setString(2, lesson);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static boolean updateTutor(int tutorId,int classId,Timestamp lessonDateTime){
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update lesson set tutor_id = ? where class_id = ? and lesson_date_time = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorId);
            stmt.setInt(2, classId);
            stmt.setTimestamp(3, lessonDateTime);

            stmt.executeUpdate(); 
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean createLesson(int classid, int tutorid, Timestamp lessonDateTime) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "INSERT into LESSON (class_id, lesson_date_time, tutor_id, tutor_attended)"
                + "VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            stmt.setTimestamp(2, lessonDateTime);
            stmt.setInt(3, tutorid);
            stmt.setInt(4, 0);
            stmt.executeUpdate(); 
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static ArrayList<Lesson> retrieveAllLessonLists(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, lesson_date_time from lesson where class_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                Lesson lesson = new Lesson(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return lessons;
    }
    public static ArrayList<Lesson> retrieveAllLessonListsByTutor(int classid, int tutorid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, lesson_date_time from lesson where class_id = ? and tutor_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            stmt.setInt(2, tutorid);
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                Lesson lesson = new Lesson(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return lessons;
    }
    
    public static ArrayList<Lesson> retrieveLessonsByTutor(int tutorid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, lesson_date_time from lesson where tutor_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorid);            
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                Lesson lesson = new Lesson(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return lessons;
    }
    public ArrayList<Lesson> retrieveAllLessonListsDateRange(Timestamp startDate, Timestamp endDate) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, lesson_date_time from lesson where lesson_date_time between ? and ?";
        
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, startDate);
            stmt.setTimestamp(2, endDate);
            
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                Lesson lesson = new Lesson(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return lessons;
    }
    
     public static Lesson getLessonByID(int lessonID){
        Lesson les = null;
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from lesson where lesson_id = ?");
            stmt.setInt(1, lessonID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int lessonid = rs.getInt("lesson_id");
                int classid = rs.getInt("class_id");
                int tutorid = rs.getInt("tutor_id");
                int tutorAttended = rs.getInt("tutor_attended");
                Timestamp lessonDateTime = rs.getTimestamp("lesson_date_time");
               
                les = new Lesson(lessonid, classid, tutorid, tutorAttended, lessonDateTime);
            }
        }catch(Exception  e){
            System.out.println(e);
        }       
        return les;
    }
     
    public static boolean updateTutorAttendance(int lessonid, int attended) {        
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update lesson set tutor_attended = ? where lesson_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attended);
            stmt.setInt(2, lessonid);          

            stmt.executeUpdate(); 
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean retrieveAttendanceForLesson(int lessonID){
        String sql = "select tutor_attended from lesson where lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,lessonID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
