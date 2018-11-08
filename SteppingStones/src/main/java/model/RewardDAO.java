/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Reward;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RewardDAO {
    public static ArrayList<Reward> listAllRewardsByStudent(int student_id) {
        ArrayList<Reward> rewardList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from reward where student_id = ? order by reward_id desc");
            stmt.setInt(1, student_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int reward_id = rs.getInt("reward_id");
                int studentId = rs.getInt("student_id");
                int tutor_id = rs.getInt("tutor_id");
                String description = rs.getString("description");
                int amount = rs.getInt("amount");
                Date date = rs.getDate("timestamp");
                Reward reward = new Reward(reward_id, studentId, tutor_id, description, amount, date);
                rewardList.add(reward);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return rewardList;
    }
    
    public static int insertReward(int student_id, int tutor_id, String description, int amount) {

        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into reward(student_id, tutor_id, description, amount)"
                    + " value(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, student_id);
            stmt.setInt(2, tutor_id);
            stmt.setString(3, description);
            stmt.setInt(4, amount);
            stmt.executeUpdate();
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (Exception e) {
            System.out.println("error in insertReward method" + e.getMessage());
        }
        return 0;
    }
    public static int countStudentPoint(int student_id) {
        
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select sum(amount) from reward where student_id = ?");
            stmt.setInt(1, student_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return 0;
    }
}
