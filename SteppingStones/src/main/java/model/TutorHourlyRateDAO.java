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
import java.util.Arrays;

/**
 *
 * @author MOH MOH SAN
 */
public class TutorHourlyRateDAO {
    public static Boolean massUpdateTutorsHourlyRate(ArrayList<String> tutorHourlyRateLists) {

            String tutorRate =String.join(",", tutorHourlyRateLists);
            String insertData = "INSERT INTO tutor_hourly_rate(tutor_id,level_id,subject_id,branch_id,hourly_pay,pay_type) VALUES "+tutorRate;

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
                + "level_id = ? AND subject_id = ? AND branch_id = ? AND combined_class=0) AND branch_id = ?";
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
        String update_pay = "UPDATE tutor_hourly_rate SET hourly_pay=? WHERE tutor_id=? AND level_id=? AND subject_id=? AND branch_id=? AND combined_class=0 ";
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
    
    public static Boolean deleteTutorPayRate(int tutor_id,int level_id,int subject_id,int branch_id){
        String update_pay = "DELETE FROM tutor_hourly_rate WHERE tutor_id=? AND level_id=? AND subject_id=? AND branch_id=? AND combined_class=0 ";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(update_pay)) {
            preparedStatement.setInt(1, tutor_id);
            preparedStatement.setInt(2,level_id);
            preparedStatement.setInt(3,subject_id);
            preparedStatement.setInt(4,branch_id);
            
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
        String mysql = "SELECT t.tutor_id,t.tutor_fullname,r.hourly_pay,r.pay_type, r.additonal_level_id, r.level_id FROM tutor as t, tutor_hourly_rate as r "
                + "WHERE t.tutor_id = r.tutor_id "
                + "AND t.branch_id = ? AND r.subject_id = ? ORDER BY t.tutor_fullname;";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(mysql)) {
         
            preparedStatement.setInt(1, branch_id);
            preparedStatement.setInt(2, subject_id);
           
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String fullname = rs.getString(2);
                double pay = rs.getDouble(3);
                int payType = rs.getInt(4);
                ArrayList<String> additional = new ArrayList<>(Arrays.asList(rs.getString("additonal_level_id").split(",")));
                
                if(additional.size() > 1){
                    if(additional.contains(level_id + "")){
                        Tutor t = new Tutor(id,fullname,pay,payType);
                        tutorListsWithoutHourlyPay.add(t);
                    }
                }else{
                    int levelID = rs.getInt("level_id");
                    if(levelID == level_id){
                        Tutor t = new Tutor(id,fullname,pay,payType);
                        tutorListsWithoutHourlyPay.add(t);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for(Tutor t: tutorListsWithoutHourlyPay){
            System.out.println(t.getName() + " CORRECT");
        }
        return tutorListsWithoutHourlyPay;
    }
    
    // Combined Level
    public static ArrayList<Tutor> tutorListNotInPayTableForCombine(int branch_id,int subject_id,String level_ids){
        ArrayList<Tutor> tutorListsWithoutHourlyPay = new ArrayList<>();
        String mysql = "SELECT tutor_id,tutor_fullname FROM tutor WHERE tutor_id NOT in (SELECT tutor_id FROM tutor_hourly_rate WHERE "
                + "additonal_level_id = ? AND subject_id = ? AND branch_id = ? AND combined_class=1) AND branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(mysql)) {
            preparedStatement.setString(1, level_ids);
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
    
    public static ArrayList<Tutor> tutorListInPayTableForCombine(int branch_id,int subject_id,String level_ids){
        ArrayList<Tutor> tutorListsWithoutHourlyPay = new ArrayList<>();
        String mysql = "SELECT t.tutor_id,t.tutor_fullname,r.hourly_pay,r.pay_type FROM tutor as t, tutor_hourly_rate as r "
                + "WHERE t.tutor_id = r.tutor_id "
                + "AND t.branch_id = ? AND r.subject_id = ? AND r.additonal_level_id = ? AND combined_class = 1 ORDER BY t.tutor_fullname;";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(mysql)) {
         
            preparedStatement.setInt(1, branch_id);
            preparedStatement.setInt(2, subject_id);
            preparedStatement.setString(3, level_ids);
           
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String fullname = rs.getString(2);
                double pay = rs.getDouble(3);
                int payType = rs.getInt(4);
                Tutor t = new Tutor(id,fullname,pay,payType);
                tutorListsWithoutHourlyPay.add(t);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return tutorListsWithoutHourlyPay;
    }
    
    
    // Combined LEVEL
    public static Boolean massUpdateTutorsHourlyRateForCombine(ArrayList<String> tutorHourlyRateLists) {
        String tutorRate =String.join(",", tutorHourlyRateLists);
        String insertData = "INSERT ignore INTO tutor_hourly_rate(tutor_id,level_id,subject_id,branch_id,hourly_pay,additonal_level_id,combined_class,pay_type) VALUES "+tutorRate;

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
    
    public static Boolean updateTutorPayRateForCombine(int tutor_id,String level_id,int subject_id,int branch_id,double pay){
        String update_pay = "UPDATE tutor_hourly_rate SET hourly_pay=? WHERE tutor_id=? AND additonal_level_id=? AND subject_id=? AND branch_id=? AND combined_class=1";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(update_pay)) {
            preparedStatement.setDouble(1,pay);
            preparedStatement.setInt(2, tutor_id);
            preparedStatement.setString(3,level_id);
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
    
    public static Boolean deleteTutorPayRateForCombine(int tutor_id,String level_id,int subject_id,int branch_id){
        String update_pay = "DELETE FROM tutor_hourly_rate WHERE tutor_id=? AND additonal_level_id=? AND subject_id=? AND branch_id=? AND combined_class=1 ";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(update_pay)) {
            preparedStatement.setInt(1, tutor_id);
            preparedStatement.setString(2,level_id);
            preparedStatement.setInt(3,subject_id);
            preparedStatement.setInt(4,branch_id);
            
            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
}
