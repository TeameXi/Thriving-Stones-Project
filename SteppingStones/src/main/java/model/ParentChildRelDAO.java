package model;

import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParentChildRelDAO {
    public static void insertParentChildRel(int parentPhone, int studentID, int branchID) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            int parentID = ParentDAO.retrieveParentID(parentPhone);
            String sql = "insert into parent_child_rel(parent_id, child_id, branch_id) value(?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parentID);
            stmt.setInt(2, studentID);
            stmt.setInt(3, branchID);
            stmt.executeUpdate(); 
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void insertParentChildRelForUpload(int parentPhone, int studentID, int branchID, String relationship) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            int parentID = ParentDAO.retrieveParentID(parentPhone);
            String sql = "insert into parent_child_rel(parent_id, child_id, branch_id, relationship) value(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parentID);
            stmt.setInt(2, studentID);
            stmt.setInt(3, branchID);
            stmt.setString(4, relationship);
            stmt.executeUpdate(); 
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static int getParentID(int studentID){
        int parentID = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select parent_id from parent_child_rel where child_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                parentID = rs.getInt("parent_id");
            } 
        } catch (SQLException ex) {
            System.out.println("error in getParentID sql");
        }  
        return parentID;
    }
    
    public static boolean deleteParentChildRel(int studentID){
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from parent_child_rel where child_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.executeUpdate(); 
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public static int getNumOfChild(int parentID){
        int numOfChild = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select count(distinct child_id) as count from parent_child_rel group by parent_id having parent_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parentID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                numOfChild = rs.getInt("count");
            } 
        } catch (SQLException ex) {
            System.out.println("error in getParentID sql");
        }  
        return numOfChild;
    }
    
}
