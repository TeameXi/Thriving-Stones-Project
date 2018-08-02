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
    public static ArrayList<Subject> listAllSubjects(){
       Gson gson = new GsonBuilder().create();
        ArrayList<Subject> tutors = new ArrayList<>();

        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/subjects.json");
            URLConnection connection = userURL.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            JsonElement jelement = new JsonParser().parse(jsonString);
            JsonObject jobject = jelement.getAsJsonObject();
            Set entries = jobject.keySet();
            Iterator iter = entries.iterator();
            while (iter.hasNext()) {
                String user = (String) iter.next();
                JsonElement userDataString = jobject.get(user);
                Subject tutor = new Subject(gson.fromJson(userDataString, String.class));
                tutor.setSubjectId(user);
                tutors.add(tutor);
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutors;
    }
    
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
}
