/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shawn
 */
public class ExpenseDAO {

    public static void insertExpense(int tutorID, String tutorName, String subject, String level, double amount) {
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into expense (tutor_id, description, amount, payment_date) values(?,?,?,CURDATE())");
            stmt.setInt(1, tutorID);
            stmt.setString(2, tutorName + " " + subject + " " + level);
            stmt.setDouble(3, amount);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertExpense(List<String> expenseList) {
        try (Connection conn = ConnectionManager.getConnection()) {
            String expenseLists = String.join(",", expenseList);
            String insert_expense = "insert ignore into expense(tutor_id,description,amount,payment_date) values " + expenseLists;
            PreparedStatement insertStatement = conn.prepareStatement(insert_expense);
            insertStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
     public static ArrayList<Expense> listAllExpenses() {
        ArrayList<Expense> expensesList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from expense where tutor_id <= 0");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int tutor_id = rs.getInt("tutor_id");
                int payment_id = rs.getInt("payment_id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                String date = rs.getString("payment_date");
                Expense exp = new Expense(amount, date, tutor_id, description, payment_id);
                expensesList.add(exp);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return expensesList;
    }
     public static boolean updateExpense(int payment_id, int tutor_id, String description, double amount, String date) {
        String update_Tutor = "update expense set tutor_id=?,description=?,amount=?,payment_date=? WHERE payment_id =? ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(update_Tutor)) {
            preparedStatement.setInt(1, tutor_id);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, date);
            preparedStatement.setInt(5, payment_id);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public static boolean deleteExpense(int payment_id){
        String sql = "delete from expense where payment_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, payment_id);
            
            int recordsUpdated = stmt.executeUpdate();
            if(recordsUpdated > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LevelDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return false;
    }
}
