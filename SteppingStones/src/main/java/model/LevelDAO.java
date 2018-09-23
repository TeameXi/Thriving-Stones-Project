package model;

import connection.ConnectionManager;
import entity.Level;
import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class LevelDAO {
    public ArrayList<String> retrieveAllLevels() {
        ArrayList<String> levels = new ArrayList<>();
        String sql = "select level_name from level";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                levels.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return levels;
    }
    
    public static ArrayList<Level> retrieveAllLevelLists() {
        ArrayList<Level> levels = new ArrayList<>();
        String sql = "select * from level";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                Level lvl = new Level(rs.getInt(1),rs.getString(2));
                levels.add(lvl);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return levels;
    }
    
    public static int retrieveLevelID(String lvl) {
        int result = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select level_id from level where level_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, lvl);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = rs.getInt("level_id");
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveLevelID sql" + ex.getMessage());
        }  
        return result;
    }
    
    public static String retrieveLevel(int lvl_id) {
        String result = "";
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select level_name from level where level_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lvl_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = rs.getString("level_name");
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveLevelID sql");
        }  
        return result;
    }
    
    public static ArrayList<String> retrieveLevelBySubject(int subjectID, int branchID){
        ArrayList<String> level = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select level_name from lvl_sub_rel, level where subject_id = ? and level.level_id = lvl_sub_rel.level_id and branch_id = ? order by level_name;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subjectID);
            stmt.setInt(2, branchID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                level.add(rs.getString("level_name"));
            } 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
        return level;
    }
    
    public static ArrayList<Level> retrieveLevelBySubject1(int subjectID, int branchID){
        ArrayList<Level> levelLists = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select level.level_id,level_name from lvl_sub_rel, level where subject_id = ? and level.level_id = lvl_sub_rel.level_id and branch_id = ? order by level_name;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subjectID);
            stmt.setInt(2, branchID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Level lvl = new Level(rs.getInt("level_id"),rs.getString("level_name"));
                levelLists.add(lvl);
            } 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
        return levelLists;
    }
    public Map<String, Integer> retrieveStudentPerLevel(){
        Map<String, Integer> returnList = new TreeMap<String, Integer>();
        String sql = "SELECT level_name, count(student_id) FROM level inner join student on student.level_id = level.level_id group by level.level_id";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                returnList.put(rs.getString(1),rs.getInt(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return returnList;
    }
    public Map<String, Integer> retrieveStudentPerLevelByBranch(int branchID){
        Map<String, Integer> returnList = new TreeMap<String, Integer>();
        String sql = "SELECT level_name, count(student_id) FROM level inner join student on student.level_id = level.level_id group by level.level_id";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                returnList.put(rs.getString(1),rs.getInt(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return returnList;
    }
    
    public static ArrayList<Subject> retrieveAllSubjectsBelongToLevelAndBranch(int levelID, int branchID){
        ArrayList<Subject> subjectLists = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "SELECT s.subject_id,subject_name, l.cost FROM subject as s, lvl_sub_rel as l WHERE s.subject_id = l.subject_id and branch_id = ? and level_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchID);
            stmt.setInt(2, levelID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Subject s = new Subject(rs.getInt(1), rs.getString(2), rs.getFloat(3));
                subjectLists.add(s);
            } 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
        return subjectLists;
    }
    
    public boolean updateSubjectFees(int branchID, int subjectID, int levelID, float fees){
        String sql = "update lvl_sub_rel set cost = ? where level_id = ? and subject_id = ? and branch_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, fees);
            stmt.setInt(2, levelID);
            stmt.setInt(3, subjectID);
            stmt.setInt(4, branchID);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LevelDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return false;
    }
}
