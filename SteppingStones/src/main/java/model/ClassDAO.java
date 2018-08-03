/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Class;
import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DEYU
 */
public class ClassDAO {
    
    public static ArrayList<Class> getClassByLevel(int level_id){
        String level = LevelDAO.retrieveLevel(level_id);
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and level_id = ? and end_date > curdate() order by subject_id");
            stmt.setInt(1, 1); // replace with branch_id
            stmt.setInt(2, level_id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                Class cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static Class getClassByID(int classID){
        Class cls = null;
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where class_id = ?");
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int levelID = rs.getInt("level_id");
                int subjectID = rs.getInt("subject_id");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                cls = new Class(classID, level, subject, classTime, classDay, mthlyFees, startDate, endDate);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return cls;
    }
    /*
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
    */
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
    
