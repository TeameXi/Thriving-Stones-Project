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

/**
 *
 * @author DEYU
 */
public class ParentDAO {
    public static void insertParent(String name, String nationality, String company, String designation, int phone, String email, int passwordPhone, int branch_id) {
        String password = String.valueOf(passwordPhone);
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into parent(name, nationality, company, designation, phone, email, password, branch_id)"
                    + " value(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, nationality);
            stmt.setString(3, company);
            stmt.setString(4, designation);
            stmt.setInt(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, password);
            stmt.setInt(8, 1); // replace with branch_id
            stmt.executeUpdate(); 
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static int retrieveParentID(String parentName){
        int result = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select parent_id from parent where name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, parentName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = rs.getInt("parent_id");
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveParentID sql");
        }  
        return result;
    }
    
    public static boolean deleteParent(int parentID){
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from parent where parent_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parentID);
            stmt.executeUpdate(); 
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
}