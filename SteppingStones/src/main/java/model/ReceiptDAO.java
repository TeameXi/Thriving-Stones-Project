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

/**
 *
 * @author Riana
 */
public class ReceiptDAO {
    public int retrieveReceiptNo() {
        int number = 0;
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT max(receipt_id) FROM receipt")) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return number;
    }
    public int addReceipt(String receipt_date, String payment_mode, String no, String description, String amount_paid, String total_amount_paid, int student_id) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO receipt(receipt_date,payment_mode,no,description,amount_paid,total_amount_paid,student_id) VALUES(?,?,?,?,?,?,?)")) {
            
            preparedStatement.setString(1, receipt_date);
            preparedStatement.setString(2, payment_mode);
            preparedStatement.setString(3, no);
            preparedStatement.setString(4, description);
            preparedStatement.setString(5, amount_paid);
            preparedStatement.setString(6, total_amount_paid);
            preparedStatement.setInt(7, student_id);
            

            int num = preparedStatement.executeUpdate();
            
            if (num != 0) {
                return num;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
