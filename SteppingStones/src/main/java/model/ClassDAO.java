/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Class;
import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author DEYU
 */
public class ClassDAO {
    
    public static ArrayList<Class> getClassesToEnrolled(int level_id, int student_id){
        String level = LevelDAO.retrieveLevel(level_id);
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and level_id = ? and end_date > curdate() and "
                    + "class_id not in (select class_id from class_student_rel where student_id = ?) order by subject_id");
            stmt.setInt(1, 1); // replace with branch_id
            stmt.setInt(2, level_id);
            stmt.setInt(3, student_id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                Class cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static Class getClassByID(int classID){
        Class cls = null;
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where class_id = ?");
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int levelID = rs.getInt("level_id");
                int subjectID = rs.getInt("subject_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return cls;
    }
    
    public static ArrayList<Class> getStudentEnrolledClass(int student_id){
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class c, class_student_rel cs where c.class_id = cs.class_id and end_date > curdate() and student_id = ?");
            stmt.setInt(1, student_id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int levelID = rs.getInt("level_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static ArrayList<Class> listAllClasses(int branchID){
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and end_date > curdate()");
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int levelID = rs.getInt("level_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return classList;
    }
    
    public static ArrayList<Class> listAllClassesByTutorID(int tutorID, int branchID){
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and tutor_id = ? and end_date > curdate()");
            stmt.setInt(1, branchID);
            stmt.setInt(2, tutorID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int levelID = rs.getInt("level_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return classList;
    }

    public int insertClass(int level, int subject, int term, int hasReminderForFees, int branch, String classTime, String classDay, double mthlyFees, String startDate, String endDate) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "INSERT into CLASS (level_id, subject_id, term, fees, has_reminder_for_fees, timing, class_day, start_date, end_date, branch_id)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, level);
            stmt.setInt(2, subject);
            stmt.setInt(3, term);
            stmt.setDouble(4, mthlyFees);
            stmt.setInt(5, hasReminderForFees);
            stmt.setString(6, classTime);
            stmt.setString(7, classDay);
            stmt.setString(8, startDate);
            stmt.setString(9, endDate);
            stmt.setInt(10, branch);
            stmt.executeUpdate(); 
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public boolean updateClass(String level, String subject, String timing) {
        String sql = "update class set timing = ? where level_id = ? and subject_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,timing);
            stmt.setInt(2, Integer.parseInt(level));
            stmt.setInt(3, Integer.parseInt(subject));
            System.out.println(stmt);
            
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
}
    
