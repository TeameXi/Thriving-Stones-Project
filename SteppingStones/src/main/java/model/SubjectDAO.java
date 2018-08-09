/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riana
 */
public class SubjectDAO {
    
    public ArrayList<String> retrieveSubjectsByLevel(String level) {
        ArrayList<String> subjects = new ArrayList<>();
        String sql = "select subject.subject_name from subject join lvl_sub_rel where lvl_sub_rel.level_id = ? and lvl_sub_rel.subject_id = subject.subject_id";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(0, level);
            System.out.println(stmt);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                subjects.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return subjects;
    }
    
    public ArrayList<String> retrieveAllSubjects() {
        ArrayList<String> subjects = new ArrayList<>();
        String sql = "select subject_name from subject order by subject_id";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(stmt);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                subjects.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return subjects;
    }
    
    public static String retrieveSubject(int subject_id) {
        String subject = null;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select subject_name from subject where subject_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subject_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                subject = rs.getString(1);
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveSubject sql");
        }  
        return subject;
    }
    
    public static int retrieveSubjectID(String subjectName) {
        int result = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select subject_id from subject where subject_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subjectName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = rs.getInt("subject_id");
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveSubject sql");
        }  
        return result;
    }
    
    public ArrayList<Subject> retrieveAllSubjectsWithId() {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "select subject_id, subject_name from subject order by subject_id";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(stmt);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Subject subject = new Subject(rs.getInt(1), rs.getString(2));
                subjects.add(subject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return subjects;
    }
    
    public boolean addSubject(int levelID, String subject, int branchID) {
        boolean status = false;
        String sql = "insert ignore into subject(subject_name) values(?)";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,subject);
            
            stmt.executeUpdate();
            
            int subjectID = retrieveSubjectID(subject);
            
            if(subjectID > 0){
                sql = "insert into lvl_sub_rel(level_id, subject_id, branch_id) values(?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, levelID);
                stmt.setInt(2, subjectID);
                stmt.setInt(3, branchID);
                stmt.executeUpdate();
                status = true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return status;
    }
    
    public boolean deleteSubject(int subjectID) {
        boolean status = false;
        String sql = "delete from subject where subject_id = ?";
        
        try (Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subjectID);
            
            int recordsUpdated = stmt.executeUpdate();
            
            if(recordsUpdated > 0) {
                recordsUpdated = 0;
                sql = "delete from lvl_sub_rel where subject_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, subjectID);
                
                recordsUpdated = stmt.executeUpdate();
                
                if(recordsUpdated > 0) {
                    status = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public boolean deleteSubjectLevelRel(int subjectID, int levelID, int branchID){
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from lvl_sub_rel where level_id = ? and subject_id = ? and branch_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, levelID);
            stmt.setInt(2, subjectID);
            stmt.setInt(3, branchID);
            stmt.executeUpdate(); 
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public ArrayList<Subject> retrieveSubjectsByBranch(int branchId) {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "select subject_id, subject_name from subject where subject_id in (select subject_id from lvl_sub_rel where branch_id = ?)";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchId);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                subjects.add(new Subject(rs.getInt(1), rs.getString(2)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }
    
    public boolean updateSubject(int subjectID, String subjectName){
        boolean status = false;
        String sql = "update subject set subject_name = ? where subject_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subjectName);
            stmt.setInt(2, subjectID);
            
            int recordsUpdated = stmt.executeUpdate();
            
            if(recordsUpdated > 0){
                status = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
