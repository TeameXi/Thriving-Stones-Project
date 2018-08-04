/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import connection.ConnectionManager;
import entity.StudentGrade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
public class StudentGradeDAO {
    
    public static boolean saveTuitionGrades(int studentID, int classID, String assessmentType, String grade) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into tuition_grade(student_id, class_id, assessment_type, grade)"
                    + " value(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            stmt.setString(3, assessmentType);
            stmt.setString(4, grade);
            stmt.executeUpdate(); 
            conn.commit();
            status = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }
    
    public static boolean deleteStudentTuitionGrade(int studentID){
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from tuition_grade where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.executeUpdate(); 
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public static void saveSchoolGrade(String studentID, String sub1, StudentGrade stuGrade1, String sub2, StudentGrade stuGrade2, String sub3, StudentGrade stuGrade3) {
        Map<String, StudentGrade> Schoolgrades = new HashMap<>();
        Schoolgrades.put(sub1, stuGrade1);
        Schoolgrades.put(sub2, stuGrade2);
        Schoolgrades.put(sub3, stuGrade3);
        String json = new Gson().toJson(Schoolgrades);
        try {
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/School.json";
            FirebaseRESTHTTPRequest.put(url, json);
            System.out.println("Save Grades successfully");
        } catch (Exception e) {
            System.out.println("Insert Grades Error");
        }
    }

    public static void saveGrades(String studentID, Map<String, Map<String, StudentGrade>> grades) {
        String json = new Gson().toJson(grades);
        try {
            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades.json";
            FirebaseRESTHTTPRequest.put(url, json);
            System.out.println("Save Grades successfully");
        } catch (Exception e) {
            System.out.println("Insert Grades Error");
        }
    }

    

    public static ArrayList<String> createGrade(String studentID, String location, String subject, String examination, String grade) {
        ArrayList<String> errorGrade = new ArrayList<String>();
        String tempGrade = "";
        try {

            String url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/" + location + "/" + subject + "/" + examination + ".json";
            JSONObject result = FirebaseRESTHTTPRequest.get(url);
            if (result != null) {
                tempGrade = new Gson().fromJson(result.toString(), String.class);
            }
            if (!tempGrade.equals("")) {
                url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/" + location + ".json";

                FirebaseRESTHTTPRequest.put(url, subject);
                url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/" + location + "/" + subject + ".json";

                FirebaseRESTHTTPRequest.put(url, examination);
                url = "https://team-exi-thriving-stones.firebaseio.com/students/" + studentID + "/grades/" + location + "/" + subject + "/" + examination + ".json";

                FirebaseRESTHTTPRequest.put(url, grade);
                System.out.println("Grade Update successfully");
                return errorGrade;
            } else {
                errorGrade.add("Grade already exists");
                return errorGrade;
            }

        } catch (Exception e) {
            System.out.println("Database error");
        }
        return errorGrade;
    }
}
