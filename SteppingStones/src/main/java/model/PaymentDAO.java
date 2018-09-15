/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Payment;
import entity.Class;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public static boolean insertOutstandingTuitionFees(int classID, int studentID, String paymentDueDate, int noOfLessons, double amountCharge, double outstandingCharge) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into payment_reminder(class_id, student_id, payment_due_date, no_of_lessons, amount_charged, "
                    + "outstanding_charge) value(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
            stmt.setString(3, paymentDueDate);
            stmt.setInt(4, noOfLessons);
            stmt.setDouble(5, amountCharge);
            stmt.setDouble(6, outstandingCharge);
            stmt.executeUpdate();
            conn.commit();
            status = true;
        } catch (Exception e) {
            System.out.println("Error in insertOutstandingTuitionFees method" + e.getMessage());
        }
        return status;
    }
    
    public static boolean insertPaymentReminderWithAmount(int classID, int studentID, String paymentDueDate, int noOfLessons, double amountCharge) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into payment_reminder(class_id, student_id, payment_due_date, no_of_lessons, amount_charged, "
                    + "outstanding_charge) value(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
            stmt.setString(3, paymentDueDate);
            stmt.setInt(4, noOfLessons);
            stmt.setDouble(5, amountCharge);
            stmt.setDouble(6, amountCharge);
            stmt.executeUpdate();
            conn.commit();
            status = true;
        } catch (Exception e) {
            System.out.println("Error in insertPaymentReminderWithAmount method" + e.getMessage());
        }
        return status;
    }

    public static HashMap<String, Integer> getReminders(int classID, String joinDate) {
        HashMap<String, Integer> reminders = new HashMap<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select start_date, reminder_status from lesson where class_id = ? and reminder_status > 0 and date(start_date) > ?;");
            stmt.setInt(1, classID);
            stmt.setString(2, joinDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String reminderDate = rs.getString("start_date");
                int noOfLessons = rs.getInt("reminder_status");
                reminders.put(reminderDate, noOfLessons);
            }
        } catch (SQLException e) {
            System.out.print("Error in getReminders method" + e.getMessage());
        }
        return reminders;
    }
  
    public static void getStudentRegFeesData(int studentID, ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select reg_fees, outstanding_reg_fees, date(updated) as due_date from student where student_id = ? and outstanding_reg_fees > 0;");
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double chargeAmount = rs.getDouble("reg_fees");
                double outstandingCharge = rs.getDouble("outstanding_reg_fees");
                String dueDate = rs.getString("due_date");
                Payment p = new Payment("Reg Fees", "Reg Fees", dueDate, chargeAmount, outstandingCharge, 0, 0);
                paymentData.add(p);
            }
        } catch (SQLException e) {
            System.out.print("Error in getPaymentData method" + e.getMessage());
        }
    }
    
    public static void getStudentDepositData(int studentID, ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select class_id, date(registration_date) as due_date, deposit_fees, outstanding_deposit, first_installment, "
                    + "outstanding_first_installment from class_student_rel where student_id = ?;");
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getSubject();
                double depositAmount = rs.getDouble("deposit_fees");
                double outstandingDeposit = rs.getDouble("outstanding_deposit");
                double firstInstallment = rs.getDouble("first_installment");
                double outstandingFirstInstallment = rs.getDouble("outstanding_first_installment");
                String dueDate = rs.getString("due_date");
                if(outstandingDeposit > 0){
                    Payment p = new Payment("Deposit", subject, dueDate, depositAmount, outstandingDeposit, classID, 0);
                    paymentData.add(p);
                }
                if(firstInstallment == 0 && outstandingFirstInstallment == 0){
                    Payment p = new Payment("First Installment", subject, dueDate, firstInstallment, outstandingFirstInstallment, classID, 0);
                    paymentData.add(p);
                }else if(firstInstallment != 0 && outstandingFirstInstallment > 0){
                    Payment p = new Payment("First Installment", subject, dueDate, firstInstallment, outstandingFirstInstallment, classID, 0);
                    paymentData.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.print("Error in getPaymentData method" + e.getMessage());
        }
    }
    
    public static void getStudentTutionFeesData(int studentID, ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from payment_reminder where student_id = ? and outstanding_charge > 0  and payment_due_date < curdate();");
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getSubject();
                double chargeAmount = rs.getDouble("amount_charged");
                double outstandingCharge = rs.getDouble("outstanding_charge");
                String dueDate = rs.getString("payment_due_date");
                int noOfLessons = rs.getInt("no_of_lessons");
                Payment p = new Payment("Tuition Fees", subject, dueDate, chargeAmount, outstandingCharge, classID, noOfLessons);
                paymentData.add(p);
            }
        } catch (SQLException e) {
            System.out.print("Error in getPaymentData method" + e.getMessage());
        }
    }
    
    public static boolean updateRegFeesOutstandingAmount(int studentID, double outstandingFees) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set outstanding_reg_fees = ? where student_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, outstandingFees);;
            stmt.setInt(2, studentID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
    public static boolean updateTuitionFeesOutstandingAmount(int studentID, int classID, String paymentDueDate, double outstandingFees) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update payment_reminder set outstanding_charge = ? where student_id = ? and class_id = ? and payment_due_date = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, outstandingFees);
            stmt.setInt(2, studentID);
            stmt.setInt(3, classID);
            stmt.setString(4, paymentDueDate);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
    public static boolean updateDepositOutstandingAmount(int studentID, int classID, double outstandingFees) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set outstanding_deposit = ? where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, outstandingFees);
            stmt.setInt(2, studentID);
            stmt.setInt(3, classID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
    public static boolean updateFirstInstallmentOutstandingAmount(int studentID, int classID, double outstandingFees) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set outstanding_first_installment = ? where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);   
            stmt.setDouble(1, outstandingFees);
            stmt.setInt(2, studentID);
            stmt.setInt(3, classID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
    public static boolean updateFirstInstallmentAmount(int studentID, int classID, double chargeAmount, double outstandingFees) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set first_installment = ?, outstanding_first_installment = ? where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);   
            stmt.setDouble(1, chargeAmount);
            stmt.setDouble(2, outstandingFees);
            stmt.setInt(3, studentID);
            stmt.setInt(4, classID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
    public static boolean insertPaymentToRevenue(String studentName, int noOfLessons, String paymentType, String lvlSubject, double amountPaid) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into revenue(student_name, no_of_lessons, payment_date, payment_type, lvl_subject, amount_paid) value(?, ?, curdate(), ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setInt(2, noOfLessons);
            stmt.setString(3, paymentType);
            stmt.setString(4, lvlSubject);
            stmt.setDouble(5, amountPaid);
            stmt.executeUpdate();
            conn.commit();
            status = true;
        } catch (Exception e) {
            System.out.println("Error in insertPaymentToRevenue method" + e.getMessage());
        }
        return status;
    }
    
}
