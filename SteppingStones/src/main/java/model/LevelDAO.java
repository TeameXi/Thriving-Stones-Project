/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author HuiXin
 */
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
    
    public ArrayList<Level> retrieveAllLevelLists() {
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
}
