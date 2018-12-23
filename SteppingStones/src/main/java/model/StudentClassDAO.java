package model;

import connection.ConnectionManager;
import entity.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentClassDAO {
    
//    public static boolean saveStudentToRegisterClass(int classID, int studentID, double deposit, double outstandingDeposit, double tuitionFee, 
//            double outstandingTuitionFee, String joinDate, double firstInstallment, double outstandingFirstInstallment){
//        boolean status = false;
//        try (Connection conn = ConnectionManager.getConnection();) {
//            conn.setAutoCommit(false);
//            String sql = "insert into class_student_rel(class_id, student_id, deposit_fees, outstanding_deposit, tuition_fees, "
//                    + "outstanding_tuition_fee, join_date, first_installment, outstanding_first_installment) value(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, classID);
//            stmt.setInt(2, studentID);
//            stmt.setDouble(3, deposit);
//            stmt.setDouble(4, outstandingDeposit);
//            stmt.setDouble(5, tuitionFee);
//            stmt.setDouble(6, outstandingTuitionFee);
//            stmt.setString(7, joinDate);
//            stmt.setDouble(8, firstInstallment);
//            stmt.setDouble(9, outstandingFirstInstallment);
//            stmt.executeUpdate(); 
//            conn.commit();
//            status = true;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return status;
//    }
    
    
    public static int retrieveStudentClassStatus(int classID, int studentID){ //check if student have register in the particular class before
        int status = 0;
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "SELECT count(*) FROM class_student_rel WHERE class_id = ? and student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                status = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }
    
    public static boolean saveStudentToRegisterClass(int classID, int studentID, double deposit, double outstandingDeposit, String joinDate) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            if(deposit == 0){
                String sql = "insert into class_student_rel(class_id, student_id, deposit_fees, outstanding_deposit, "
                    + "join_date, first_installment, outstanding_first_installment) value(?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, classID);
                stmt.setInt(2, studentID);
                stmt.setDouble(3, deposit);
                stmt.setDouble(4, outstandingDeposit);
                stmt.setString(5, joinDate);
                stmt.setDouble(6, 0);
                stmt.setDouble(7, 0);
                stmt.executeUpdate();
                conn.commit();
                status = true;
            }else{
                String sql = "insert into class_student_rel(class_id, student_id, deposit_fees, outstanding_deposit, deposit_payment_date, "
                    + "join_date, first_installment, outstanding_first_installment) value(?, ?, ?, ?, curdate(), ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, classID);
                stmt.setInt(2, studentID);
                stmt.setDouble(3, deposit);
                stmt.setDouble(4, outstandingDeposit);
                stmt.setString(5, joinDate);
                stmt.setDouble(6, 0);
                stmt.setDouble(7, 0);
                stmt.executeUpdate();
                conn.commit();
                status = true;
            }
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }
    
    public static double getTotalOutstandingAmt(int classID, int studentID) { //get the totalOutstandingFees if student have register in the particular class before
        double totalOutstandingAmt = 0;
        double outstandingDeposit = 0;
        double outstandingFirstInstallment = 0;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql  = "select outstanding_deposit, outstanding_first_installment from class_student_rel where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                outstandingDeposit = rs.getDouble(1);
                outstandingFirstInstallment = rs.getDouble(2);
                totalOutstandingAmt = outstandingDeposit + outstandingFirstInstallment;
            }

        } catch (Exception e) {
            System.out.println("Error in getTotalOutstandingAmt method" + e.getMessage());
        }
        return totalOutstandingAmt;
    }
    
    public static boolean updateFirstInsatllment(int classID, int studentID) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set first_installment = -1 where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println("Error in updateDepositpaymentDate method" + e.getMessage());
        }
        return updatedStatus;
    }

    public static boolean saveStudentsToRegisterClass(int classID, int studentID, double deposit, double outstandingDeposit, double tuitionFee,
            double outstandingTuitionFee, String joinDate) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into class_student_rel(class_id, student_id, deposit_fees, deposit_activated_amount, outstanding_deposit, monthly_fees, "
                    + "outstanding_tuition_fee, join_date) value(?, ?, ?, 0, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
            stmt.setDouble(3, deposit);
            stmt.setDouble(4, outstandingDeposit);
            stmt.setDouble(5, tuitionFee);
            stmt.setDouble(6, outstandingTuitionFee);
            stmt.setString(7, joinDate);
            stmt.executeUpdate();
            conn.commit();
            status = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }
    
//    public static boolean updateDepositPaymentDate(int studentID, int classID){
//        boolean updatedStatus = false;
//        try (Connection conn = ConnectionManager.getConnection();) {
//            conn.setAutoCommit(false);
//            String sql = "update class_student_rel set deposit_payment_date = curdate() where student_id = ? and class_id = ?;";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, studentID);
//            stmt.setInt(2, classID);
//            stmt.executeUpdate();
//            conn.commit();
//            updatedStatus = true;
//        } catch (Exception e) {
//            System.out.println("Error in updateDepositpaymentDate method" + e.getMessage());
//        }
//        return updatedStatus;
//    }

    public static ArrayList<String> listStudentsinSpecificClass(int classID) {
        ArrayList<String> studentList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select student_name from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String studentName = rs.getString("student_name");
                studentList.add(studentName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }

    public static boolean deleteStudentClassRel(int studentID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from class_student_rel where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            int delete = stmt.executeUpdate();
            conn.commit();
            //if(delete > 0){
                deletedStatus = true;
            //}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public static boolean updateStudentClassRelStatus(int studentID, int classID, double deposit) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set status = 1, deposit_activation_date = curdate(), "
                    + "deposit_activated_amount = ? where student_id = ? and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, deposit);
            stmt.setInt(2, studentID);
            stmt.setInt(3, classID);
            int update = stmt.executeUpdate();
            conn.commit();
            if(update > 0){
                updatedStatus = true;
            }
        } catch (Exception e) {
            System.out.println("Error in updateStudentClassRelStatus method" + e.getMessage());
        }
        return updatedStatus;
    }
    
    public static boolean deleteStudentClassRel(int studentID, int classID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from class_student_rel where student_id = ? and class_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            int delete = stmt.executeUpdate();
            conn.commit();
            if(delete > 0){
                deletedStatus = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public static double retrieveStudentDepositAmt(int studentID, int classID) {
        double depositAmt = 0;
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select deposit_fees, outstanding_deposit from class_student_rel where student_id = ? and class_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double deposit = rs.getDouble("deposit_fees");
                double outstanding = rs.getDouble("outstanding_deposit");
                depositAmt = deposit - outstanding;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return depositAmt;
    }
    
    public static boolean updateStatus(int studentID, int status) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update class_student_rel set status = 1, outstanding_deposit = 0 where student_id = ? and status = ?;";                    
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, studentID);
            stmt.setInt(2, status);           
            int update = stmt.executeUpdate();
            conn.commit();
            if(update > 0){
                updatedStatus = true;
            }
        } catch (Exception e) {
            System.out.println("Error in updateStatus method" + e.getMessage());
        }
        return updatedStatus;
    }
    
    public static double retrieveStudentTotalDepositAmt(int studentID) {
        double totalDeposit = 0;
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "SELECT SUM(deposit_fees  - outstanding_deposit) AS 'totalDeposit' FROM class_student_rel WHERE status=0 AND student_id=? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalDeposit = rs.getDouble("totalDeposit");                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return totalDeposit;
    }
    
    

    public static Map<Integer, String> retrieveStudentClassSub(int studentID) {
        Map<Integer, String> classSub = new HashMap<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select c.class_id, subject_name from class_student_rel cs, class c, subject s "
                    + "where cs.class_id = c.class_id and c.subject_id = s.subject_id and student_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                String subject_name = rs.getString("subject_name");
                classSub.put(classID, subject_name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return classSub;
    }

    public int retrieveNumberOfStudentByClass(int classID) {
        int studentCount = 0;
        String sql = "select COUNT(*) from class_student_rel where class_id = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return studentCount;
    }

    public static ArrayList<Student> listAllStudentsByClass(int classID) {
        ArrayList<Student> studentList = new ArrayList<Student>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select * from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("student_id");
                String studentNRIC = rs.getString("student_nric");
                String name = rs.getString("student_name");
                String BOD = rs.getString("birth_date");
                String gender = rs.getString("gender");
                int levelID = rs.getInt("level_id");
                int branchID = rs.getInt("branch_id");
                int phone = rs.getInt("phone");
                String address = rs.getString("address");
                String email = rs.getString("email");
                double reqAmt = rs.getDouble("required_amount");
                double outstandingAmt = rs.getDouble("outstanding_amount");
                String level = LevelDAO.retrieveLevel(levelID);
                Student student = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, reqAmt, outstandingAmt);
                studentList.add(student);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }
    
    public static String retrieveJoinDateOfStudentByClass(int studentID, int classID) {
        String joinDate = "";
        String sql = "select join_date from class_student_rel where student_id = ? and class_id = ?;";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                joinDate = rs.getString("join_date");
            }
        } catch (Exception e) {
            System.out.println("Error in retrieveJoinDateOfStudentByClass method" + e.getMessage());
        }

        return joinDate;
    }
}
