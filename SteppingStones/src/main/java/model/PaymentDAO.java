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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Xin
 */
public class PaymentDAO {
    public void insertPaymentDate(String date, int classID) {
        String sql = "insert into payment_date(payment_date,class_id) values(?,?)";
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setInt(2, classID);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String> retrieveContactsPayment(int classID) {
        ArrayList<String> contacts = new ArrayList<>();
        String sql = "select distinct phone from parent where parent_id in (select parent_id from parent_child_rel where student_id in (select student_id from class_student_rel where class_id = ?))";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                contacts.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contacts;
    }
    
    public ArrayList<String> retrieveOverduePayments(int classID) {
        ArrayList<String> contacts = new ArrayList<>();
        String sql = "select phone from parent where parent_id in (select parent_id from parent_child_rel where student_id in (select student_id from student where student_id in (select student_id from class_student_rel where class_id = ?) and outstanding_amount > 0))";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                contacts.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contacts;
    }
}
