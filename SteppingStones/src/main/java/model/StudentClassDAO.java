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
    public static boolean saveStudentToRegisterClass(int classID, int studentID, double deposit, double outstandingDeposit, double tuitionFee, 
            double outstandingTuitionFee, String joinDate, double firstInstallment, double outstandingFirstInstallment){
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into class_student_rel(class_id, student_id, deposit_fees, outstanding_deposit, tuition_fees, "
                    + "outstanding_tuition_fee, join_date, first_installment, outstanding_first_installment) value(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
            stmt.setDouble(3, deposit);
            stmt.setDouble(4, outstandingDeposit);
            stmt.setDouble(5, tuitionFee);
            stmt.setDouble(6, outstandingTuitionFee);
            stmt.setString(7, joinDate);
            stmt.setDouble(8, firstInstallment);
            stmt.setDouble(9, outstandingFirstInstallment);
            stmt.executeUpdate(); 
            conn.commit();
            status = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
     }
    
    public static ArrayList<String> listStudentsinSpecificClass(int classID){
        ArrayList<String> studentList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select student_name from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String studentName = rs.getString("student_name");
                studentList.add(studentName);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }
    
    public static boolean deleteStudentClassRel(int studentID){
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from class_student_rel where student_id = ?";
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
    
    public static Map<Integer, String> retrieveStudentClassSub(int studentID){
        Map<Integer, String> classSub = new HashMap<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select c.class_id, subject_name from class_student_rel cs, class c, subject s "
                    + "where cs.class_id = c.class_id and c.subject_id = s.subject_id and student_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int classID = rs.getInt("class_id");
                String subject_name = rs.getString("subject_name");
                classSub.put(classID, subject_name);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return classSub;
    }
  
    public int retrieveNumberOfStudentByClass(int classID){
        int studentCount = 0;
        String sql = "select COUNT(*) from class_student_rel where class_id = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                studentCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return studentCount;
    }
    
    public static ArrayList<Student> listAllStudentsByClass(int classID){
        ArrayList<Student> studentList = new ArrayList<Student>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select * from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){               
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
                String password = rs.getString("password");
                double reqAmt = rs.getDouble("required_amount");
                double outstandingAmt = rs.getDouble("outstanding_amount");
                String level = LevelDAO.retrieveLevel(levelID);
                Student student = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, password, reqAmt, outstandingAmt);
                studentList.add(student);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }
}
