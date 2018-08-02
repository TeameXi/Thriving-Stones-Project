/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Class;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import connection.ConnectionManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
public class ClassDAO {

    public static void saveClasses(String level, String subject, String classTime, String classDay, double mthlyFees, String startDate){
        Class cls = new Class(level, subject, classTime, classDay, mthlyFees, startDate);
        String json = new Gson().toJson(cls);
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/classes.json";         
            FirebaseRESTHTTPRequest.post(url, json);
            System.out.println("Save class successfully");
        }catch(Exception e){
            System.out.println("Insert Class Error");
        }
    }
    
    public static Map<String, Class> getClassByLevel(String level){
        Map<String, Class> classes = new HashMap<>();
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/classes.json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                Set<String> keys = result.keySet();
                for(String key: keys){
                    Class cls = new Gson().fromJson(result.getJSONObject(key).toString(), Class.class);
                    if(cls.getLevel().equals(level)){
                        classes.put(key, cls);
                    }
                } 
            } 
        }catch(Exception e){
            System.out.println("Retrieve Class Error");
        } 
        return classes;
    }
    
    public static Class getClassByID(String classID){
        Class cls = null;
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/classes/" + classID + ".json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                cls = new Gson().fromJson(result.toString(), Class.class);
                cls.setClassID(classID);
            }
        }catch(Exception e){
            System.out.println("Retrieve Class Error");
        } 
        return cls;
    }
    
    public static ArrayList<Class> listAllClasses(){
        ArrayList<Class> classes = new ArrayList();
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/classes/.json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);       
            if (result != null) {
                Set<String> keys = result.keySet();
                for(String key: keys){
                    Class cls = new Gson().fromJson(result.getJSONObject(key).toString(), Class.class);
                    cls.setClassID(key);
                    classes.add(cls);
                } 
            } 
        }catch(Exception e){
            System.out.println("List all classes Error");
        } 
        return classes;
    }
    
    public static ArrayList<String> getAllClassesNames() {
        ArrayList<String> classes = new ArrayList<>();

        try {
            String url = "https://team-exi-thriving-stones.firebaseio.com/classes/.json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                Set<String> keys = result.keySet();
                for (String key : keys) {
                    classes.add(key);
                }
            }
        } catch (Exception e) {
            System.out.println("Retrieve Classes Error");
        }
        return classes;
    }
    
    public static String getClassLevel(String classID) {
        
        Gson gson = new GsonBuilder().create();

        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/classes/" + classID + ".json";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            Class classes = gson.fromJson(jsonString, Class.class);
            reader.close();
            return classes.getLevel();
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean updateClass(String level, String subject, String timing) {
        String sql = "update class set timing = ? where level_id = ? and subject_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,timing);
            stmt.setInt(2, Integer.parseInt(level));
            stmt.setInt(3, Integer.parseInt(subject));
            System.out.println(stmt);
            
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
