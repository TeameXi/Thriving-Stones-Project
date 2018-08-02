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
import entity.Tutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorDAO {

    public void addTutor(String tutorID, Tutor tutor) {
        Gson gson = new GsonBuilder().create();

        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + tutorID + ".json";
            String userData = gson.toJson(tutor);
            
            FirebaseRESTHTTPRequest.put(urlString, userData);
            
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tutor retrieveSpecificTutor(String id) {
        Gson gson = new GsonBuilder().create();

        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + id + ".json";
            URL url = new URL(urlString);
            System.out.println(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            System.out.println(jsonString);
            Tutor tutor = gson.fromJson(jsonString, Tutor.class);
            tutor.setID(id);
            reader.close();
            System.out.println(connection.getResponseCode());
            return tutor;
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Tutor> retrieveAllTutors() {
        Gson gson = new GsonBuilder().create();
        ArrayList<Tutor> tutors = new ArrayList<>();

        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/tutors.json");
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
                Tutor tutor = gson.fromJson(userDataString, Tutor.class);
                tutor.setID(user);
                tutors.add(tutor);
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutors;
    }

    public boolean updateTutor(String tutorID, Map<String, Object> updates) {
        Gson gson = new GsonBuilder().create();

        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + tutorID + "/.json";
            String userData = gson.toJson(updates);
            
            FirebaseRESTHTTPRequest.patch(urlString, userData);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean removeTutor(String tutorID) {
        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + tutorID + ".json";
            FirebaseRESTHTTPRequest.delete(urlString);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<String> retrieveTutorList() {
        ArrayList<String> tutors = new ArrayList<>();
        String sql = "select tutor_fullname from tutor order by tutor_id";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                tutors.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return tutors;
    }
    
    public Tutor retrieveTutorByEmail(String email){
        Tutor tutor = null;
        String sql = "select * from tutor where email = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                tutor = new Tutor(rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5),rs.getString(6)
                        ,rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)
                        ,rs.getInt(11),rs.getString(12));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return tutor;
    }
}
