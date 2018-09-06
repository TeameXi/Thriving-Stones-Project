/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Lesson;
import entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 *
 * @author Zang Yu
 */
public class StudentAttendanceDAO {
   
    public static ArrayList<Student> retrieveAllStudentListsByLesson(int lessonId) {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "select student_id from student_attendance where lesson_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonId);           
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                int studentid = rs.getInt("student_id");
                Student student = StudentDAO.retrieveStudentbyID(studentid);
                students.add(student);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return students;
    }
}
