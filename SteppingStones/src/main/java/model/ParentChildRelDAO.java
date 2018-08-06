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
import static model.StudentDAO.retrieveStudentID;

/**
 *
 * @author DEYU
 */
public class ParentChildRelDAO {
    public static void insertParentChildRel(String parentName, String studentName) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            int parent_id = ParentDAO.retrieveParentID(parentName);
            int student_id = retrieveStudentID(studentName);
            String sql = "insert into parent_child_rel(parent_id, child_id) value(?, ? )";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parent_id);
            stmt.setInt(2, student_id);
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
    
}
