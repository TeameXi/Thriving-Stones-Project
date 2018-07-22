/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import entity.Student;
import java.util.ArrayList;
import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
public class StudentDAO {

    public static void insertStudent(String studentID, String studentName, int age, String gender, String lvl, String address, String phone, double reqAmt, double outstandingAmt){        
        Student stu = new Student(studentName, age, gender, lvl, address, phone, reqAmt, outstandingAmt); 
        String json = new Gson().toJson(stu);
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + ".json";
            FirebaseRESTHTTPRequest.put(url, json);
            System.out.println("Save student successfully");
        }catch(Exception e){
            System.out.println("Insert Student Error");
        } 
              
    }
    
    public static Student retrieveStudentbyID(String studentID){
        Student stu = null;
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + ".json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                stu = new Gson().fromJson(result.toString(), Student.class); 
            } 
        }catch(Exception e){
            System.out.println("Retrieve Student by ID Error");
        } 
        return stu;
    }
   
    public static boolean deleteStudentbyID(String studentID){
        boolean deletedStatus = false;
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + ".json";
            FirebaseRESTHTTPRequest.delete(url);
            deletedStatus = true;
        }catch(Exception e){
            System.out.println("Delete Error");
        } 
        return deletedStatus;
    }
   
    public static ArrayList<Student> listAllStudents(){
        ArrayList<Student> student = new ArrayList();
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/.json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);       
            if (result != null) {
                Set<String> keys = result.keySet();
                for(String key: keys){
                    Student stu = new Gson().fromJson(result.getJSONObject(key).toString(), Student.class);
                    student.add(stu);
                    stu.setStudentID(key);
                } 
            } 
        }catch(Exception e){
            System.out.println("List all students Error");
        } 
        return student;
    }
    
    public static boolean updateStudent(String studentID, String json){
        boolean updatedStatus = false;
        try{
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + ".json";
            FirebaseRESTHTTPRequest.patch(url, json);
            System.out.println("Student Update successfully");
            updatedStatus = true;
        }catch(Exception e){
            System.out.println("Database error");
        }
        return updatedStatus;
    }
}
