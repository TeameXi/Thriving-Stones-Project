/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Statement;
import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> retrieveReceipt(int id){
        List<String> returnList = new ArrayList<String>();
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT receipt_date, payment_mode, no, description, amount_paid, total_amount_paid, outstanding_amount, student_id FROM receipt where receipt_id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                returnList.add(rs.getString("receipt_date"));
                returnList.add(rs.getString("payment_mode"));
                returnList.add(rs.getString("no"));
                returnList.add(rs.getString("description"));
                returnList.add(rs.getString("amount_paid"));
                returnList.add(rs.getString("total_amount_paid"));
                returnList.add(rs.getString("outstanding_amount"));
                returnList.add(rs.getString("student_id"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnList;
    }
    public int addReceipt(String receipt_date, String payment_mode, String no, String description, String amount_paid, String total_amount_paid, String outstanding_amount, int student_id) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO receipt(receipt_date,payment_mode,no,description,amount_paid,total_amount_paid,outstanding_amount,student_id) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, receipt_date);
            preparedStatement.setString(2, payment_mode);
            preparedStatement.setString(3, no);
            preparedStatement.setString(4, description);
            preparedStatement.setString(5, amount_paid);
            preparedStatement.setString(6, outstanding_amount);
            preparedStatement.setString(7, total_amount_paid);
            preparedStatement.setInt(8, student_id);
            

            int num = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            System.out.println(generatedKey + " YAYYYY");
            return generatedKey;
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
