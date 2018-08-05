/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import connection.ConnectionManager;
import entity.Subject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
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
        String result = "";
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select subject_name from subject where subject_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, subject_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = rs.getString("subject_name");
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
}
