/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shawn
 */
public class ExpenseDAO {
    
    public static void insertExpense(int tutorID, String tutorName, String subject, String level, double amount){
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("insert into expense (tutor_id, description, amount, payment_date) values(?,?,?,CURDATE())");
            stmt.setInt(1, tutorID);
            stmt.setString(2, tutorName + " " + subject + " " + level);
            stmt.setDouble(3, amount);
            
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
