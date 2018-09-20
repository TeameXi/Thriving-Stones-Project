/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import connection.ConnectionManager;
import entity.Tutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MOH MOH SAN
 */
public class TutorHourlyRateDAO {
    public static Boolean massUpdateTutorsHourlyRate(ArrayList<String> tutorHourlyRateLists) {

            String tutorRate =String.join(",", tutorHourlyRateLists);
            String insertData = "INSERT INTO tutor_hourly_rate(tutor_id,level_id,subject_id,branch_id,hourly_pay) VALUES "+tutorRate;

            try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(insertData)) {
                int num = preparedStatement.executeUpdate();
                if (num != 0) {
                    return true;
                }
 
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return false;
        
    }
    
    public static ArrayList<Tutor> tutorListNotInPayTable(int branch_id,int subject_id,int level_id){
        ArrayList<Tutor> tutorListsWithoutHourlyPay = new ArrayList<>();
        String mysql = "SELECT tutor_id,tutor_fullname FROM tutor WHERE tutor_id NOT in (SELECT tutor_id FROM tutor_hourly_rate WHERE "
                + "level_id = ? AND subject_id = ? AND branch_id = ?) AND branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(mysql)) {
            preparedStatement.setInt(1, level_id);
            preparedStatement.setInt(2, subject_id);
            preparedStatement.setInt(3, branch_id);
            preparedStatement.setInt(4, branch_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String fullname = rs.getString(2);
                Tutor t = new Tutor(id,fullname);
                tutorListsWithoutHourlyPay.add(t);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return tutorListsWithoutHourlyPay;
    }
    
    
    public static Boolean updateTutorPayRate(int tutor_id,int level_id,int subject_id,int branch_id,double pay){
        String update_pay = "UPDATE tutor_hourly_rate SET hourly_pay=? WHERE tutor_id=? AND level_id=? AND subject_id=? AND branch_id=? ";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(update_pay)) {
            preparedStatement.setDouble(1,pay);
            preparedStatement.setInt(2, tutor_id);
            preparedStatement.setInt(3,level_id);
            preparedStatement.setInt(4,subject_id);
            preparedStatement.setInt(5,branch_id);
            
            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static ArrayList<Tutor> tutorListInPayTable(int branch_id,int subject_id,int level_id){
        ArrayList<Tutor> tutorListsWithoutHourlyPay = new ArrayList<>();
        String mysql = "SELECT t.tutor_id,t.tutor_fullname,r.hourly_pay FROM tutor as t, tutor_hourly_rate as r "
                + "WHERE t.tutor_id = r.tutor_id "
                + "AND t.branch_id = ? AND r.subject_id = ? AND r.level_id = ? ORDER BY t.tutor_fullname;";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(mysql)) {
         
            preparedStatement.setInt(1, branch_id);
            preparedStatement.setInt(2, subject_id);
            preparedStatement.setInt(3, level_id);
           
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String fullname = rs.getString(2);
                double pay = rs.getDouble(3);
                Tutor t = new Tutor(id,fullname,pay);
                tutorListsWithoutHourlyPay.add(t);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return tutorListsWithoutHourlyPay;
    }
}
