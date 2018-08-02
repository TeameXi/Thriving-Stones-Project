/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author HuiXin
 */
public class LessonDAO {
    public void updateLesson(String tutorID, String level, String subject) {
        System.out.println("HALPPPPPPPPP");
        String lesson = "";
        String sql = "select lesson_id from lesson where class_id in (select class_id from class where level_id = ? and subject_id = ?)";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,level);
            stmt.setString(2,subject);
            System.out.println(stmt);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                lesson = rs.getString(1);
            }
            
            sql = "update lesson set tutor_id = ? where lesson_id = ?";
            System.out.println(sql + " CORRECT");
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tutorID);
            stmt.setString(2, lesson);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
