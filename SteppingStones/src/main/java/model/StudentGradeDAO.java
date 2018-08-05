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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    
    public static LinkedHashMap<String, ArrayList<String>> retrieveStudentTuitionGrade(String studentName){
        LinkedHashMap<String, ArrayList<String>> gradeLists = new LinkedHashMap<>();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select subject_name, assessment_type, grade "
                    + "from tuition_grade g, class c, subject s, student stu "
                    + "where g.class_id = c.class_id and s.subject_id = c.subject_id and stu.student_id = g.student_id "
                    + "and student_name = ? and stu.branch_id = ?;");
            stmt.setString(1, studentName);
            stmt.setInt(2, 1); //replace with branchID
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String subjectName = rs.getString("subject_name");
                String assessmentType = rs.getString("assessment_type");
                String grade = rs.getString("grade");
                if(gradeLists.get(subjectName) == null){
                    ArrayList<String> subGrades = new ArrayList<>();
                    subGrades.add(assessmentType + " : " + grade);
                    gradeLists.put(subjectName, subGrades);
                }else{
                    ArrayList<String> subGrades = gradeLists.get(subjectName);
                    subGrades.add(assessmentType + " : " + grade);
                    gradeLists.put(subjectName, subGrades);
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return gradeLists;
    }
    
    public static boolean updateTuitionGrade(int studentID, int classID, String assessmentType, String grade){
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update tuition_grade set grade = ? where student_id = ? and class_id = ? and assessment_type = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, grade);
            stmt.setInt(2, studentID);
            stmt.setInt(3, classID);
            stmt.setString(4, assessmentType);
            stmt.executeUpdate(); 
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
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
