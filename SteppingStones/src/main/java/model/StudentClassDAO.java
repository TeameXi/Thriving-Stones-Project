package model;
import connection.ConnectionManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentClassDAO {
    public static boolean saveStudentToRegisterClass(int classID, int studentID){
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into class_student_rel(class_id, student_id) value(?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
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
}
