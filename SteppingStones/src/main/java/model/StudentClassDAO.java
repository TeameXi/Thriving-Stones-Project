/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
import entity.Student;

/**
 *
 * @author DEYU
 */
public class StudentClassDAO {
    
     public static void saveClassWithStudent(String classKey, String studentID, String studentName){
        Map<String, String> studentClass = new HashMap<>();
        studentClass.put(studentID, studentName);
        String json = new Gson().toJson(studentClass);
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/StudentClass/" + classKey + ".json";
            FirebaseRESTHTTPRequest.patch(url, json);
            System.out.println("Patched successfully");
        }catch(Exception e){
            System.out.println("Database error");
        }
     }
     
     public static ArrayList<String> retrieveStudentClassesID(String studentID){
        final ArrayList<String> classIDs = new ArrayList<>();
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/StudentClass.json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                Set<String> keys = result.keySet();
                for(String key: keys){
                    Map<String, String> stuClass = new HashMap<>();
                    stuClass = new Gson().fromJson(result.getJSONObject(key).toString(), stuClass.getClass());
                    if(stuClass.keySet().contains(studentID)){
                        classIDs.add(key);
                    } 
                } 
            } 
        }catch(Exception e){
            System.out.println("Retrieve Student by ID Error");
        } 
        return classIDs;
    }
    
    public static void deleteStudentClassbyID(String studentID){
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/StudentClass.json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                Set<String> keys = result.keySet();
                for(String key: keys){
                    Map<String, String> stuClass = new HashMap<>();
                    stuClass = new Gson().fromJson(result.getJSONObject(key).toString(), stuClass.getClass());
                    if(stuClass.keySet().contains(studentID)){
                        try{
                            String deleteUrl = "https://team-exi-thriving-stones.firebaseio.com/StudentClass/" + key + "/" + studentID + ".json";
                            FirebaseRESTHTTPRequest.delete(deleteUrl);
                        }catch(Exception e){
                            System.out.println("Delete Error");
                        } 
                    } 
                } 
            } 
        }catch(Exception e){
            System.out.println("Retrieve Student by ID Error");
        } 
    }
    
    public static ArrayList<entity.Student> getStudentsByClassID(String classID) {
        
        ArrayList<entity.Student> students = new ArrayList<>();
        /*
        try {
            String url = "https://team-exi-thriving-stones.firebaseio.com/StudentClass/" + classID + ".json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                Set<String> keys = result.keySet();
                for (String key : keys) {
                    Student stu = StudentDAO.retrieveStudentbyID(key);
                    students.add(stu);
                }
            }
        } catch (Exception e) {
            System.out.println("List all students Error");
        }
        */
        return students;
    }
}
