/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.StudentGrade;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DEYU
 */
public class StudentGradeDAO {
    
    public static void saveSchoolGrade(String studentID, String sub1, StudentGrade stuGrade1, String sub2, StudentGrade stuGrade2, String sub3, StudentGrade stuGrade3){
        Map<String, StudentGrade> Schoolgrades = new HashMap<>();
        Schoolgrades.put(sub1, stuGrade1);
        Schoolgrades.put(sub2, stuGrade2);
        Schoolgrades.put(sub3, stuGrade3);
        String json = new Gson().toJson(Schoolgrades);
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/School.json";
            FirebaseRESTHTTPRequest.put(url, json);
            System.out.println("Save Grades successfully");
        }catch(Exception e){
            System.out.println("Insert Grades Error");
        } 
    }
   
    public static void saveGrades(String studentID, Map<String, Map<String, StudentGrade>> grades){ 
        String json = new Gson().toJson(grades);
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades.json";
            FirebaseRESTHTTPRequest.put(url, json);
            System.out.println("Save Grades successfully");
        }catch(Exception e){
            System.out.println("Insert Grades Error");
        } 
    }
    
    public static void saveCenterGrades(String studentID, String sub, String assessmentType, String grade){
        JsonObject centerGrade = new JsonObject();
        centerGrade.addProperty(assessmentType, grade);
        String json = new Gson().toJson(centerGrade);
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/Center/" + sub  + ".json";
            FirebaseRESTHTTPRequest.patch(url, json);
            System.out.println("Save Grades successfully");
        }catch(Exception e){
            System.out.println("Insert Center Grade Error");
        } 
    }
}