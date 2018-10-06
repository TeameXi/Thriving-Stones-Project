package model;

import connection.ConnectionManager;
import entity.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class StudentGradeDAO {
    
    public static boolean massSaveTutionGrades(ArrayList<String>gradeLists) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String gradeSQL = String.join(",", gradeLists);
            String sql = "INSERT INTO grade(student_id,class_id,CA1_tuition_top,CA1_tuition_base,CA1_tuition_grade,SA1_tuition_top,SA1_tuition_base,SA1_tuition_grade,CA2_tuition_top,CA2_tuition_base,CA2_tuition_grade,SA2_tuition_top,SA2_tuition_base,SA2_tuition_grade,CA1_school_top,CA1_school_base,CA1_school_grade,SA1_school_top,SA1_school_base,SA1_school_grade,CA2_school_top,CA2_school_base,CA2_school_grade,SA2_school_top,SA2_school_base,SA2_school_grade)"
                    + " VALUES "+gradeSQL +" ON DUPLICATE KEY UPDATE "
                    + " CA1_tuition_top=VALUES(CA1_tuition_top), CA1_tuition_base=VALUES(CA1_tuition_base), CA1_tuition_grade=VALUES(CA1_tuition_grade),"
                    + " SA1_tuition_top=VALUES(SA1_tuition_top), SA1_tuition_base=VALUES(SA1_tuition_base), SA1_tuition_grade=VALUES(SA1_tuition_grade),"
                    + " CA2_tuition_top=VALUES(CA2_tuition_top), CA2_tuition_base=VALUES(CA2_tuition_base), CA2_tuition_grade=VALUES(CA2_tuition_grade),"
                    + " SA2_tuition_top=VALUES(SA2_tuition_top), SA2_tuition_base=VALUES(SA2_tuition_base), SA2_tuition_grade=VALUES(SA2_tuition_grade),"
                    + " CA1_school_top=VALUES(CA1_school_top), CA1_school_base=VALUES(CA1_school_base), CA1_school_grade=VALUES(CA1_school_grade),"
                    + " SA1_school_top=VALUES(SA1_school_top), SA1_school_base=VALUES(SA1_school_base), SA1_school_grade=VALUES(SA1_school_grade),"
                    + " CA2_school_top=VALUES(CA2_school_top), CA2_school_base=VALUES(CA2_school_base), CA2_school_grade=VALUES(CA2_school_grade),"
                    + " SA2_school_top=VALUES(SA2_school_top), SA2_school_base=VALUES(SA2_school_base), SA2_school_grade=VALUES(SA2_school_grade)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            int num = stmt.executeUpdate();
        
            conn.commit();
            if(num > 0){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static boolean deleteStudentTuitionGrade(int studentID){
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from tuition_grade where student_id = ?";
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
    
    public static String retrieveStudentGrade(String studentName){
        String grade = "";
        try(Connection conn = ConnectionManager.getConnection()){
            int studentId = StudentDAO.retrieveStudentID(studentName);
            PreparedStatement stmt = conn.prepareStatement("select grade from tuition_grade where student_id = ?;");
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                grade = rs.getString("grade");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return grade;
    }
    
    public static LinkedHashMap<String, ArrayList<String>> retrieveStudentTuitionGrade(String studentName){
        LinkedHashMap<String, ArrayList<String>> gradeLists = new LinkedHashMap<>();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select subject_name, assessment_type, grade "
                    + "from tuition_grade g, class c, subject s, student stu "
                    + "where g.class_id = c.class_id and s.subject_id = c.subject_id and stu.student_id = g.student_id "
                    + "and student_name = ? and stu.branch_id = ?;");
            stmt.setString(1, studentName);
            stmt.setInt(2, 1); //replace with branchID
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String subjectName = rs.getString("subject_name");
                String assessmentType = rs.getString("assessment_type");
                String grade = rs.getString("grade");
                if(gradeLists.get(subjectName) == null){
                    ArrayList<String> subGrades = new ArrayList<>();
                    subGrades.add(assessmentType + " : " + grade);
                    gradeLists.put(subjectName, subGrades);
                }else{
                    ArrayList<String> subGrades = gradeLists.get(subjectName);
                    subGrades.add(assessmentType + " : " + grade);
                    gradeLists.put(subjectName, subGrades);
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return gradeLists;
    }
    
    public static boolean updateTuitionGrade(int studentID, int classID, String assessmentType, String grade){
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update tuition_grade set grade = ? where student_id = ? and class_id = ? and assessment_type = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, grade);
            stmt.setInt(2, studentID);
            stmt.setInt(3, classID);
            stmt.setString(4, assessmentType);
            stmt.executeUpdate(); 
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }
    
    
      public static ArrayList<Grade> listGradesFromSpecificClass(int classID){
        ArrayList<Grade> studentList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select s.student_id,student_name from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int studentId = rs.getInt(1);
                String studentName = rs.getString("student_name");
                
                String select_grade_sql = "select CA1_tuition_top,CA1_tuition_base,CA1_tuition_grade,SA1_tuition_top,SA1_tuition_base,SA1_tuition_grade,CA2_tuition_top,CA2_tuition_base,CA2_tuition_grade,SA2_tuition_top,SA2_tuition_base,SA2_tuition_grade,CA1_school_top,CA1_school_base,CA1_school_grade,SA1_school_top,SA1_school_base,SA1_school_grade,CA2_school_top,CA2_school_base,CA2_school_grade,SA2_school_top,SA2_school_base,SA2_school_grade from grade where student_id = ? and class_id = ?";
                PreparedStatement stmt2 = conn.prepareStatement(select_grade_sql);
                stmt2.setInt(1, studentId);
                stmt2.setInt(2,classID);
                
                int CA1_tuition_top = 0;int CA1_tuition_base = 100;
                int SA1_tuition_top = 0;int SA1_tuition_base = 100;
                int CA2_tuition_top = 0;int CA2_tuition_base = 100;
                int SA2_tuition_top = 0;int SA2_tuition_base = 100;
                int CA1_school_top = 0;int CA1_school_base = 100;
                int SA1_school_top = 0;int SA1_school_base = 100;
                int CA2_school_top = 0;int CA2_school_base = 100;
                int SA2_school_top = 0;int SA2_school_base = 100;
                ResultSet rs2 = stmt2.executeQuery();

                if(rs2.next()){
                    CA1_tuition_top = rs2.getInt("CA1_tuition_top");CA1_tuition_base=rs2.getInt("CA1_tuition_base");
                    SA1_tuition_top = rs2.getInt("SA1_tuition_top");SA1_tuition_base=rs2.getInt("SA1_tuition_base");
                    CA2_tuition_top = rs2.getInt("CA2_tuition_top");CA2_tuition_base=rs2.getInt("CA2_tuition_base");
                    SA2_tuition_top = rs2.getInt("SA2_tuition_top");SA2_tuition_base=rs2.getInt("SA2_tuition_base");
                    CA1_school_top =  rs2.getInt("CA1_school_top");CA1_school_base = rs2.getInt("CA1_school_base");
                    SA1_school_top =  rs2.getInt("SA1_school_top");SA1_school_base = rs2.getInt("SA1_school_base");
                    CA2_school_top =  rs2.getInt("CA2_school_top");CA2_school_base = rs2.getInt("CA2_school_base");
                    SA2_school_top =  rs2.getInt("SA2_school_top");SA2_school_base = rs2.getInt("SA2_school_base"); 
                }
                
                Grade g = new Grade(studentName,studentId,classID,CA1_tuition_top,CA1_tuition_base,SA1_tuition_top,SA1_tuition_base,CA2_tuition_top,CA2_tuition_base,SA2_tuition_top,SA2_tuition_base,CA1_school_top,CA1_school_base,SA1_school_top,SA1_school_base,CA2_school_top,CA2_school_base,SA2_school_top,SA2_school_base);
                studentList.add(g);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }
      
    public static ArrayList<Grade> listGradesFromSpecificClassIncludingRatio(int classID){
        ArrayList<Grade> studentList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select s.student_id,student_name from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int studentId = rs.getInt(1);
                String studentName = rs.getString("student_name");
                
                String select_grade_sql = "select CA1_tuition_top,CA1_tuition_base,CA1_tuition_grade,SA1_tuition_top,SA1_tuition_base,SA1_tuition_grade,CA2_tuition_top,CA2_tuition_base,CA2_tuition_grade,SA2_tuition_top,SA2_tuition_base,SA2_tuition_grade,CA1_school_top,CA1_school_base,CA1_school_grade,SA1_school_top,SA1_school_base,SA1_school_grade,CA2_school_top,CA2_school_base,CA2_school_grade,SA2_school_top,SA2_school_base,SA2_school_grade from grade where student_id = ? and class_id = ?";
                PreparedStatement stmt2 = conn.prepareStatement(select_grade_sql);
                stmt2.setInt(1, studentId);
                stmt2.setInt(2,classID);
                
                int CA1_tuition_top = 0;int CA1_tuition_base = 100;double CA1_tuition_grade = 0;
                int SA1_tuition_top = 0;int SA1_tuition_base = 100;double SA1_tuition_grade = 0;
                int CA2_tuition_top = 0;int CA2_tuition_base = 100;double CA2_tuition_grade = 0;
                int SA2_tuition_top = 0;int SA2_tuition_base = 100;double SA2_tuition_grade = 0;
                int CA1_school_top = 0;int CA1_school_base = 100;double CA1_school_grade = 0;
                int SA1_school_top = 0;int SA1_school_base = 100;double SA1_school_grade = 0;
                int CA2_school_top = 0;int CA2_school_base = 100;double CA2_school_grade = 0;
                int SA2_school_top = 0;int SA2_school_base = 100;double SA2_school_grade = 0;
                ResultSet rs2 = stmt2.executeQuery();

                if(rs2.next()){
                    CA1_tuition_top = rs2.getInt("CA1_tuition_top");CA1_tuition_base=rs2.getInt("CA1_tuition_base");CA1_tuition_grade = rs2.getDouble("CA1_tuition_grade");
                    SA1_tuition_top = rs2.getInt("SA1_tuition_top");SA1_tuition_base=rs2.getInt("SA1_tuition_base");SA1_tuition_grade = rs2.getDouble("SA1_tuition_grade");
                    CA2_tuition_top = rs2.getInt("CA2_tuition_top");CA2_tuition_base=rs2.getInt("CA2_tuition_base");CA2_tuition_grade = rs2.getDouble("CA2_tuition_grade");
                    SA2_tuition_top = rs2.getInt("SA2_tuition_top");SA2_tuition_base=rs2.getInt("SA2_tuition_base");SA2_tuition_grade = rs2.getDouble("SA2_tuition_grade");
                    CA1_school_top =  rs2.getInt("CA1_school_top");CA1_school_base = rs2.getInt("CA1_school_base");CA1_school_grade = rs2.getDouble("CA1_school_grade");
                    SA1_school_top =  rs2.getInt("SA1_school_top");SA1_school_base = rs2.getInt("SA1_school_base");SA1_school_grade = rs2.getDouble("SA1_school_grade");
                    CA2_school_top =  rs2.getInt("CA2_school_top");CA2_school_base = rs2.getInt("CA2_school_base");CA2_school_grade = rs2.getDouble("CA2_school_grade");
                    SA2_school_top =  rs2.getInt("SA2_school_top");SA2_school_base = rs2.getInt("SA2_school_base");SA2_school_grade = rs2.getDouble("SA2_school_grade");
                }
                
                Grade g = new Grade(studentName,studentId,classID,CA1_tuition_top,CA1_tuition_base,CA1_tuition_grade,SA1_tuition_top,SA1_tuition_base,SA1_tuition_grade,CA2_tuition_top,CA2_tuition_base,CA2_tuition_grade,SA2_tuition_top,SA2_tuition_base,SA2_tuition_grade,CA1_school_top,CA1_school_base,CA1_school_grade,SA1_school_top,SA1_school_base,SA1_school_grade,CA2_school_top,CA2_school_base,CA2_school_grade,SA2_school_top,SA2_school_base,SA2_school_grade);
                studentList.add(g);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }

    public static ArrayList<Grade> listGradesFromSpecificClassIncludingRatioForSpecificStudent(int classID, int studentID){
        ArrayList<Grade> studentList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select s.student_id,student_name from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ? and s.student_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, studentID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int studentId = rs.getInt(1);
                String studentName = rs.getString("student_name");
                
                String select_grade_sql = "select CA1_tuition_top,CA1_tuition_base,CA1_tuition_grade,SA1_tuition_top,SA1_tuition_base,SA1_tuition_grade,CA2_tuition_top,CA2_tuition_base,CA2_tuition_grade,SA2_tuition_top,SA2_tuition_base,SA2_tuition_grade,CA1_school_top,CA1_school_base,CA1_school_grade,SA1_school_top,SA1_school_base,SA1_school_grade,CA2_school_top,CA2_school_base,CA2_school_grade,SA2_school_top,SA2_school_base,SA2_school_grade from grade where student_id = ? and class_id = ?";
                PreparedStatement stmt2 = conn.prepareStatement(select_grade_sql);
                stmt2.setInt(1, studentId);
                stmt2.setInt(2,classID);
                
                int CA1_tuition_top = 0;int CA1_tuition_base = 100;double CA1_tuition_grade = 0;
                int SA1_tuition_top = 0;int SA1_tuition_base = 100;double SA1_tuition_grade = 0;
                int CA2_tuition_top = 0;int CA2_tuition_base = 100;double CA2_tuition_grade = 0;
                int SA2_tuition_top = 0;int SA2_tuition_base = 100;double SA2_tuition_grade = 0;
                int CA1_school_top = 0;int CA1_school_base = 100;double CA1_school_grade = 0;
                int SA1_school_top = 0;int SA1_school_base = 100;double SA1_school_grade = 0;
                int CA2_school_top = 0;int CA2_school_base = 100;double CA2_school_grade = 0;
                int SA2_school_top = 0;int SA2_school_base = 100;double SA2_school_grade = 0;
                ResultSet rs2 = stmt2.executeQuery();

                if(rs2.next()){
                    CA1_tuition_top = rs2.getInt("CA1_tuition_top");CA1_tuition_base=rs2.getInt("CA1_tuition_base");CA1_tuition_grade = rs2.getDouble("CA1_tuition_grade");
                    SA1_tuition_top = rs2.getInt("SA1_tuition_top");SA1_tuition_base=rs2.getInt("SA1_tuition_base");SA1_tuition_grade = rs2.getDouble("SA1_tuition_grade");
                    CA2_tuition_top = rs2.getInt("CA2_tuition_top");CA2_tuition_base=rs2.getInt("CA2_tuition_base");CA2_tuition_grade = rs2.getDouble("CA2_tuition_grade");
                    SA2_tuition_top = rs2.getInt("SA2_tuition_top");SA2_tuition_base=rs2.getInt("SA2_tuition_base");SA2_tuition_grade = rs2.getDouble("SA2_tuition_grade");
                    CA1_school_top =  rs2.getInt("CA1_school_top");CA1_school_base = rs2.getInt("CA1_school_base");CA1_school_grade = rs2.getDouble("CA1_school_grade");
                    SA1_school_top =  rs2.getInt("SA1_school_top");SA1_school_base = rs2.getInt("SA1_school_base");SA1_school_grade = rs2.getDouble("SA1_school_grade");
                    CA2_school_top =  rs2.getInt("CA2_school_top");CA2_school_base = rs2.getInt("CA2_school_base");CA2_school_grade = rs2.getDouble("CA2_school_grade");
                    SA2_school_top =  rs2.getInt("SA2_school_top");SA2_school_base = rs2.getInt("SA2_school_base");SA2_school_grade = rs2.getDouble("SA2_school_grade");
                }
                
                Grade g = new Grade(studentName,studentId,classID,CA1_tuition_top,CA1_tuition_base,CA1_tuition_grade,SA1_tuition_top,SA1_tuition_base,SA1_tuition_grade,CA2_tuition_top,CA2_tuition_base,CA2_tuition_grade,SA2_tuition_top,SA2_tuition_base,SA2_tuition_grade,CA1_school_top,CA1_school_base,CA1_school_grade,SA1_school_top,SA1_school_base,SA1_school_grade,CA2_school_top,CA2_school_base,CA2_school_grade,SA2_school_top,SA2_school_base,SA2_school_grade);
                studentList.add(g);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
