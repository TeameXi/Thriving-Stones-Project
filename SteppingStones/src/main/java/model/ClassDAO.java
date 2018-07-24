/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Class;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
            System.out.println("Insert Student Error");
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
            System.out.println("Retrieve Student by ID Error");
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
            System.out.println("Retrieve Student by ID Error");
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
}
