/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.BankDeposit;
import entity.Payment;
import entity.Class;
import entity.Deposit;
import entity.Expense;
import entity.Revenue;
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
    
    public static int retrieveNoOfLessonPaymentReminder(int studentID, int classID) {
        int noOfLesson = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select no_of_lessons from payment_reminder where student_id = ? and class_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                noOfLesson = rs.getInt("no_of_lessons");
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveNoOfLessonPaymentReminder sql");
        }  
        return noOfLesson;
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
    
    public static HashMap<String, Integer> getRemindersForPremiumStudent(int classID, String joinDate) {
        HashMap<String, Integer> reminders = new HashMap<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select start_date, reminder_term from lesson where class_id = ? and reminder_term > 0 and date(start_date) > ?;");
            stmt.setInt(1, classID);
            stmt.setString(2, joinDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String reminderDate = rs.getString("start_date");
                int noOfLessons = rs.getInt("reminder_term");
                reminders.put(reminderDate, noOfLessons);
            }
        } catch (SQLException e) {
            System.out.print("Error in getRemindersForPremiumStudent method" + e.getMessage());
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
                if(cls.getType().equals("P")){
                    subject = cls.getSubject() + " (Premium)";
                }
                double depositAmount = rs.getDouble("deposit_fees");
                double outstandingDeposit = rs.getDouble("outstanding_deposit");
                double firstInstallment = rs.getDouble("first_installment");
                double outstandingFirstInstallment = rs.getDouble("outstanding_first_installment");
                
                System.out.println("Payment DAO depo" + depositAmount + " out depo "+ outstandingDeposit + " first " + firstInstallment + "first out" + outstandingFirstInstallment );
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
            PreparedStatement stmt = conn.prepareStatement("select * from payment_reminder where student_id = ? and outstanding_charge > 0 "
                    + "and (SELECT DATE_ADD(payment_due_date, INTERVAL -7 DAY) as payment_start_date) <= curdate();"); //able to see the charged amount 7 days before due date
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getSubject();
                if(cls.getType().equals("P")){
                    subject = cls.getSubject() + "(Premium)";
                }
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
    
    public static void getAllDueRegFees(ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select student_id, reg_fees, outstanding_reg_fees, date(updated) as due_date from student "
                    + "where outstanding_reg_fees > 0 and updated < curdate();");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                double chargeAmount = rs.getDouble("reg_fees");
                double outstandingCharge = rs.getDouble("outstanding_reg_fees");
                String dueDate = rs.getString("due_date");
                Payment p = new Payment(studentID, StudentDAO.retrieveStudentLevelbyID(studentID), "Reg Fees", dueDate, chargeAmount, outstandingCharge, 0, 0);
                paymentData.add(p);
            }
        } catch (SQLException e) {
            System.out.print("Error in getAllDueRegFees method" + e.getMessage());
        }
    }
    
    public static void getAllDueStudentDeposit(ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select student_id, class_id, date(registration_date) as due_date, deposit_fees, outstanding_deposit, "
                    + "first_installment, outstanding_first_installment from class_student_rel where (outstanding_first_installment > 0 or outstanding_deposit > 0) "
                    + "and registration_date < curdate();");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getLevel() + " (" + cls.getSubject();
                if(cls.getType().equals("P")){
                    subject = cls.getLevel() + " (" + cls.getSubject() + " Premium";
                }
                double depositAmount = rs.getDouble("deposit_fees");
                double outstandingDeposit = rs.getDouble("outstanding_deposit");
                double firstInstallment = rs.getDouble("first_installment");
                double outstandingFirstInstallment = rs.getDouble("outstanding_first_installment");
                
                //System.out.println("Payment DAO depo" + depositAmount + " out depo "+ outstandingDeposit + " first " + firstInstallment + "first out" + outstandingFirstInstallment );
                String dueDate = rs.getString("due_date");
                if(outstandingDeposit > 0){
                    Payment p = new Payment(studentID, "Deposit)", subject, dueDate, depositAmount, outstandingDeposit, classID, 0);
                    paymentData.add(p);
                }
                
                if(outstandingFirstInstallment > 0){
                    Payment p = new Payment(studentID, "First Installment)", subject, dueDate, firstInstallment, outstandingFirstInstallment, classID, 0);
                    paymentData.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.print("Error in getAllDueStudentTutionFees method" + e.getMessage());
        }
    }
    
    public static void getAllDueStudentTutionFees(ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from payment_reminder where outstanding_charge > 0 and payment_due_date < curdate();");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getSubject();
                if(cls.getType().equals("P")){
                    subject = cls.getLevel() + " (" + cls.getSubject() + " Premium";
                }
                double chargeAmount = rs.getDouble("amount_charged");
                double outstandingCharge = rs.getDouble("outstanding_charge");
                String dueDate = rs.getString("payment_due_date");
                int noOfLessons = rs.getInt("no_of_lessons");
                Payment p = new Payment(studentID, "Tuition Fees)", subject, dueDate, chargeAmount, outstandingCharge, classID, noOfLessons);
                paymentData.add(p);
            }
        } catch (SQLException e) {
            System.out.print("Error in getPaymentData method" + e.getMessage());
        }
    }
    
    public static void getAllRegFees(ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select student_id, reg_fees, outstanding_reg_fees, date(updated) as due_date from student "
                    + "where outstanding_reg_fees > 0 and updated > curdate();");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                double chargeAmount = rs.getDouble("reg_fees");
                double outstandingCharge = rs.getDouble("outstanding_reg_fees");
                String dueDate = rs.getString("due_date");
                Payment p = new Payment(studentID, StudentDAO.retrieveStudentLevelbyID(studentID), "Reg Fees", dueDate, chargeAmount, outstandingCharge, 0, 0);
                paymentData.add(p);
            }
        } catch (SQLException e) {
            System.out.print("Error in getAllRegFees method" + e.getMessage());
        }
    }
    
    public static void getAllStudentDeposit(ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select student_id, class_id, date(registration_date) as due_date, deposit_fees, outstanding_deposit, "
                    + "first_installment, outstanding_first_installment from class_student_rel where (outstanding_first_installment > 0 or outstanding_deposit > 0) "
                    + "and registration_date > curdate();");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                System.out.println("machine learning" +studentID);
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getLevel() + " (" + cls.getSubject();
                if(cls.getType().equals("P")){
                    subject = cls.getLevel() + " (" + cls.getSubject() + " Premium";
                }
                double depositAmount = rs.getDouble("deposit_fees");
                double outstandingDeposit = rs.getDouble("outstanding_deposit");
                double firstInstallment = rs.getDouble("first_installment");
                double outstandingFirstInstallment = rs.getDouble("outstanding_first_installment");
                
                String dueDate = rs.getString("due_date");
                if(outstandingDeposit > 0){
                    Payment p = new Payment(studentID, "Deposit)", subject, dueDate, depositAmount, outstandingDeposit, classID, 0);
                    paymentData.add(p);
                }
                
                if(outstandingFirstInstallment > 0){
                    Payment p = new Payment(studentID, "First Installment)", subject, dueDate, firstInstallment, outstandingFirstInstallment, classID, 0);
                    paymentData.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.print("Error in getAllStudentDeposit method" + e.getMessage());
        }
    }
    
    public static void getAllStudentTutionFees(ArrayList<Payment> paymentData){
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from payment_reminder where outstanding_charge > 0 and "
                    + "(SELECT DATE_ADD(payment_due_date, INTERVAL -7 DAY) as payment_start_date) <= curdate() and (not payment_due_date < curdate());");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                int classID = rs.getInt("class_id");
                Class cls = ClassDAO.getClassByID(classID);
                String subject = cls.getSubject();
                if(cls.getType().equals("P")){
                    subject = cls.getLevel() + " (" + cls.getSubject() + " Premium";
                }
                double chargeAmount = rs.getDouble("amount_charged");
                double outstandingCharge = rs.getDouble("outstanding_charge");
                String dueDate = rs.getString("payment_due_date");
                int noOfLessons = rs.getInt("no_of_lessons");
                Payment p = new Payment(studentID, "Tuition Fees)", subject, dueDate, chargeAmount, outstandingCharge, classID, noOfLessons);
                paymentData.add(p);
            }
        } catch (SQLException e) {
            System.out.print("Error in getAllStudentTutionFees method" + e.getMessage());
        }
    }
    
    public static double getStudentTutionFeeToAdd(int studentID){
        double amtToBeAdded = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from payment_reminder where student_id = ? and outstanding_charge = amount_charged "
                    + "and (SELECT DATE_ADD(payment_due_date, INTERVAL -7 DAY) as payment_start_date) <= curdate() order by payment_due_date limit 1;"); 
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                amtToBeAdded = rs.getDouble("outstanding_charge");
            }
        } catch (SQLException e) {
            System.out.print("Error in getStudentTutionFeeToAdd method" + e.getMessage());
        }
        return amtToBeAdded;
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
    
    public static boolean updateTuitionFees(int studentID, int classID, double monthlyFees) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update payment_reminder set amount_charged = ?, outstanding_charge = ? where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, monthlyFees);
            stmt.setDouble(2, monthlyFees);
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
    
    public static boolean updateDepositOutstandingAmount(int studentID, int classID, double outstandingFees, double depositAmt) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set deposit_fees = ?, outstanding_deposit = ? where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, depositAmt);
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
    
    public static double retrieveDepositAmt(int studentID, int classID) {
        double deposit = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select deposit_fees from class_student_rel where student_id = ? and class_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                deposit = rs.getDouble("deposit_fees");
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveDepositAmt sql");
        }  
        return deposit;
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
    
    public static boolean insertPaymentToRevenue(int studentID, String studentName, int noOfLessons, String paymentType, String lvlSubject, double amountPaid, String paymentDate) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            if(paymentDate.isEmpty()){
                String sql = "insert into revenue(student_id, student_name, no_of_lessons, payment_date, payment_type, lvl_subject, amount_paid) value(?, ?, ?, curdate(), ?, ?, ?);";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, studentID);
                stmt.setString(2, studentName);
                stmt.setInt(3, noOfLessons);
                stmt.setString(4, paymentType);
                stmt.setString(5, lvlSubject);
                stmt.setDouble(6, amountPaid);
                stmt.executeUpdate();
                conn.commit();
                status = true;
            }else{
                String sql = "insert into revenue(student_id, student_name, no_of_lessons, payment_date, payment_type, lvl_subject, amount_paid) value(?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, studentID);
                stmt.setString(2, studentName);
                stmt.setInt(3, noOfLessons);
                stmt.setString(4, paymentDate);
                stmt.setString(5, paymentType);
                stmt.setString(6, lvlSubject);
                stmt.setDouble(7, amountPaid);
                stmt.executeUpdate();
                conn.commit();
                status = true;
            }
        } catch (Exception e) {
            System.out.println("Error in insertPaymentToRevenue method" + e.getMessage());
        }
        return status;
    }
    
    public static boolean deletePaymentReminderbyStudentID(int studentID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from payment_reminder where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.executeUpdate();
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public static boolean deleteRevenuebyStudentID(int studentID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from revenue where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.executeUpdate();
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }

    public static HashMap<String, ArrayList<Revenue>> retrieveAllRevenueData(int month, int year) {
        HashMap<String, ArrayList<Revenue>> revenueData = new HashMap<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select * from revenue where EXTRACT(MONTH FROM payment_date) = ?  and EXTRACT(YEAR FROM payment_date) = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String levelSubject = rs.getString("lvl_subject");
                String studentName = rs.getString("student_name");
                int noOfLessons = rs.getInt("no_of_lessons");
                String paymentDate = rs.getString("payment_date");
                String paymentType = rs.getString("payment_type");
                double paidAmount = rs.getDouble("amount_paid");
                Revenue revenue = new Revenue(studentName, paymentType, noOfLessons, paymentDate, paidAmount);
                ArrayList<Revenue> revenueArray = new ArrayList<>();

                if (revenueData.containsKey(levelSubject)) {  
                    revenueArray = revenueData.get(levelSubject);  
                    revenueArray.add(revenue);
                } else {
                    revenueArray.add(revenue);
                }
                revenueData.put(levelSubject, revenueArray);
            }
        } catch (SQLException e) {
            System.out.println("Error in retrieveAllRevenueData method" + e.getMessage());
        }
        return revenueData;
    }
    
    public static HashMap<String, ArrayList<Deposit>> retrieveAllDepositData(int year) {
        HashMap<String, ArrayList<Deposit>> depositData = new HashMap<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select * from class_student_rel where EXTRACT(YEAR FROM deposit_payment_date) = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                int classID = rs.getInt("class_id");
                double depositFees = rs.getDouble("deposit_fees");
                double outstandingDeposit = rs.getDouble("outstanding_deposit");
                String depositPaymentDate = rs.getString("deposit_payment_date");
                System.out.println(depositPaymentDate);
                String depositActivationDate = rs.getString("deposit_activation_date");
                double activatedAmount = rs.getDouble("deposit_activated_amount");
                String levelSubject = ClassDAO.retrieveClassLevelSubject(classID);
                String studentName = StudentDAO.retrieveStudentName(studentID);
                double remainingDeposit = depositFees - outstandingDeposit - activatedAmount;
                
                Deposit deposit = new Deposit(studentName, remainingDeposit, depositPaymentDate, depositActivationDate, activatedAmount);
                ArrayList<Deposit> depositArray = new ArrayList<>();

                if (depositData.containsKey(levelSubject)) {  
                    depositArray = depositData.get(levelSubject);  
                    depositArray.add(deposit);
                } else {
                    depositArray.add(deposit); 
                }
                depositData.put(levelSubject, depositArray);
            }
        } catch (SQLException e) {
            System.out.println("Error in retrieveAllDepositData method" + e.getMessage());
        }
        return depositData;
    }
    
    public static HashMap<String, ArrayList<Expense>> retrieveAllExpenseData(int month, int year) {
        HashMap<String, ArrayList<Expense>> expenseData = new HashMap<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select * from expense where EXTRACT(MONTH FROM payment_date) = ?  and EXTRACT(YEAR FROM payment_date) = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int tutorID = rs.getInt("tutor_id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                String paymentDate = rs.getString("payment_date");
                
                String category = "";
                if(tutorID == 0){
                    category = "Bank Expenses";
                }else if(tutorID == -1){
                    category = "Cash Box Expenses";
                }else{
                    category = "Teachers' Salary";
                }
                
                Expense expense = new Expense(tutorID, description, amount, paymentDate);
                ArrayList<Expense> expenseArray = new ArrayList<>();

                if (expenseData.containsKey(category)) {  
                    expenseArray = expenseData.get(category);  
                    expenseArray.add(expense);
                } else {
                    expenseArray.add(expense); 
                }
                expenseData.put(category, expenseArray);
            }
        } catch (SQLException e) {
            System.out.println("Error in retrieveAllExpenseData method" + e.getMessage());
        }
        return expenseData;
    }
    
    public static ArrayList<BankDeposit> retrieveAllBankDepositData(int month, int year) {
        ArrayList<BankDeposit> bankDepositData = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select * from bank_deposit_revenue where EXTRACT(MONTH FROM payment_date) = ?  and EXTRACT(YEAR FROM payment_date) = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type").trim();
                type = type.replace("\u0000","");
                String paymentDate = rs.getString("payment_date").trim();
                paymentDate = paymentDate.replace("\u0000","");
                String fromAccount = rs.getString("from_account").trim();
                fromAccount = fromAccount.replace("\u0000","");
                double amount = rs.getDouble("amount");
                BankDeposit bankDeposit = new BankDeposit(type, paymentDate, fromAccount, amount);
                bankDepositData.add(bankDeposit);
            }
        } catch (SQLException e) {
            System.out.println("Error in retrieveAllBankDepositData method" + e.getMessage());
        }
        return bankDepositData;
    }
    
    public static boolean deleteStudentClassPaymentReminder(int studentID, int classID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from payment_reminder where student_id = ? and class_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            stmt.executeUpdate();
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public static boolean insertBankDeposit(String type, String date, String from, double amount){
        boolean status = false;
        String sql = "insert into bank_deposit_revenue(type, payment_date,from_account, amount) values(?, ?, ?, ?)";
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, date);
            stmt.setString(3, from);
            stmt.setDouble(4, amount);
            
            stmt.executeUpdate();
            status = true;
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public static boolean updateOutstandingChargeForNextYear(int studentID, double outstandingCharge, int classID) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update payment_reminder set outstanding_charge = ? where student_id = ? and class_id = ? order by payment_due_date limit 1;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, outstandingCharge);
            stmt.setInt(2 , studentID);
            stmt.setInt(3 , classID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
}
