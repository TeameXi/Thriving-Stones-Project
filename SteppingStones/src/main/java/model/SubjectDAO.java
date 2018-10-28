package model;

import connection.ConnectionManager;
import entity.Lvl_Sub_Rel;
import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public static boolean addSubject(int levelID, String subject, int branchID,double monthlyCourseFees) {
        String sql = "insert ignore into subject(subject_name) values(?)";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,subject);
            
            stmt.executeUpdate();
            
            int subjectID = retrieveSubjectID(subject);
            
            if(subjectID > 0){
                sql = "insert into lvl_sub_rel(level_id, subject_id, branch_id,cost) values(?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, levelID);
                stmt.setInt(2, subjectID);
                stmt.setInt(3, branchID);
                stmt.setDouble(4, monthlyCourseFees);
                int num = stmt.executeUpdate();
                if(num > 0){
                    return true;
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static void addSubjectForCombineClass(List<String> subjectLists) {
        try (Connection conn = ConnectionManager.getConnection()) {
            String combineSubjectLists = String.join(",", subjectLists);
            String insert_combine_subject = "insert ignore into lvl_sub_rel values " + combineSubjectLists;
            PreparedStatement insertStatement = conn.prepareStatement(insert_combine_subject);
            insertStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean deleteSubject(int subjectID) {
        boolean status = false;
        String sql = "delete from subject where subject_id = ?";
        
        try (Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subjectID);
            
            int recordsUpdated = stmt.executeUpdate();
            
            if(recordsUpdated > 0) {
                sql = "delete from lvl_sub_rel where subject_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, subjectID);
                stmt.executeUpdate();
                
                
                sql = "update class set subject_id=0 where subject_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, subjectID);
                recordsUpdated = stmt.executeUpdate();
                
           
                return true;
                
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
    
    public static ArrayList<Subject> retrieveSubjectsByBranch(int branchId) {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "select subject_id, subject_name from subject where subject_id in (select distinct(subject_id) from lvl_sub_rel where branch_id = ?)";
        
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
    
    
    public static ArrayList<Lvl_Sub_Rel> retrieveLevelAndSubjectForHourlyRates(int branchId){
        ArrayList<Lvl_Sub_Rel> lvlWithSubLists = new ArrayList<>();
         String sql = "SELECT rel.level_id,rel.subject_id,lvl.level_name,sub.subject_name " +
            "FROM lvl_sub_rel as rel,level as lvl,subject as sub WHERE rel.level_id = lvl.level_id AND " +
            "rel.subject_id = sub.subject_id AND rel.branch_id = ? ORDER by level_name,subject_name";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchId);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                lvlWithSubLists.add(new Lvl_Sub_Rel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lvlWithSubLists;
    }
    
    public static ArrayList<Lvl_Sub_Rel> retrieveLevelAndSubjectForHourlyRatesForCombineClass(int branchId){
        ArrayList<Lvl_Sub_Rel> lvlWithSubLists = new ArrayList<>();

        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "SELECT s.subject_name,l.subject_id,l.additonal_level_id FROM lvl_sub_rel as l,subject as s "
                    + "WHERE l.subject_id = s.subject_id AND l.branch_id=? AND combined_class = 1 ORDER BY s.subject_name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lvlWithSubLists.add(new Lvl_Sub_Rel(rs.getString(1), rs.getInt(2), rs.getString(3)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lvlWithSubLists;
    }
    
    public ArrayList<Subject> retrieveSubjectsByLevel(int levelID){
        ArrayList<Subject> subjects = new ArrayList<>();
        
        String sql = "select * from subject where subject_id in (select subject_id from lvl_sub_rel where level_id = ?)";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, levelID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Subject subject = new Subject(rs.getInt(1), rs.getString(2));
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return subjects;
    }
    
    public double retrieveSubjectFees(int subjectID, int levelID, int branchID){
        double fees = 0;
        String sql = "select cost from lvl_sub_rel where subject_id = ? and level_id = ? and branch_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subjectID);
            stmt.setInt(2, levelID);
            stmt.setInt(3, branchID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                fees = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fees;
    }
}
