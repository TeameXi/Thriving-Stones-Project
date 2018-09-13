package model;

import connection.ConnectionManager;
import entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudentDAO {

    public static int insertStudent(String studentName, int phone, String stuEmail, String stuPassword, int level_id, int branch_id) {

        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert ignore into student(student_name, phone, email, password, required_amount, outstanding_amount, level_id, branch_id)"
                    + " value(?, ?, ?, MD5(?), ?, ? ,?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setInt(2, phone);
            stmt.setString(3, stuEmail);
            stmt.setString(4, stuPassword);
            stmt.setDouble(5, 0);
            stmt.setDouble(6, 0);
            stmt.setInt(7, level_id);
            stmt.setInt(8, branch_id);
            stmt.executeUpdate();
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static int retrieveStudentID(String studentDetails) {
        int result = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select student_id from student where student_nric = '" + studentDetails + "' or email = '" + studentDetails + "'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("student_id");
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveStudentID sql");
        }
        return result;
    }

    public static ArrayList<Student> listAllStudentsByLimit(int branch_id, int level_id, int start, int limit) {
        ArrayList<Student> studentList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from student where branch_id = ? and level_id = ? order by student_name limit ?, ?");
            stmt.setInt(1, branch_id);
            stmt.setInt(2, level_id);
            stmt.setInt(3, start);
            stmt.setInt(4, limit);
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
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return studentList;
    }

    public static LinkedHashMap<String, ArrayList<Student>> listAllStudent(int branch_id) {
        LinkedHashMap<String, ArrayList<Student>> students = new LinkedHashMap<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from student where branch_id = ? order by level_id, student_name");
            stmt.setInt(1, branch_id);
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
                if (students.get(level) == null) {
                    ArrayList<Student> studentList = new ArrayList();
                    studentList.add(student);
                    students.put(level, studentList);
                } else {
                    ArrayList<Student> studentList = students.get(level);
                    studentList.add(student);
                    students.put(level, studentList);
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

        return students;
    }
    
    public static ArrayList<Student> listStudentsByLevelNotEnrolledInSpecificClassYet(int levelID, int classID){
        ArrayList<Student> studentList = new ArrayList<Student>();
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "select * from student where student_id not in"
                    + "(select distinct s.student_id from student s, class_student_rel cs where s.student_id = cs.student_id and class_id = ?) and level_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, levelID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){               
                int studentID = rs.getInt("student_id");
                String studentNRIC = rs.getString("student_nric");
                String name = rs.getString("student_name");
                String BOD = rs.getString("birth_date");
                String gender = rs.getString("gender");
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

    public static Student retrieveStudentbyID(int studentID, int branch_id) {
        Student stu = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from student where student_id = ? and branch_id = ?");
            stmt.setInt(1, studentID);
            stmt.setInt(2, branch_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
                stu = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, reqAmt, outstandingAmt);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return stu;
    }
    
    public static Student retrieveStudentbyID(int studentID) {
        Student stu = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from student where student_id = ?");
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
                stu = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, reqAmt, outstandingAmt);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return stu;
    }

    public static int retrieveStudentLevelbyName(String studentName, int branch_id) {
        int levelID = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select level_id from student where student_name = ? and branch_id = ?");
            stmt.setString(1, studentName);
            stmt.setInt(2, branch_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                levelID = rs.getInt("level_id");
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return levelID;
    }

    public static boolean deleteStudentbyID(int studentID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from student where student_id = ?";
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

    public static boolean updateStudent(int studentID, String name, String lvl, String address, int phone, double req_amount, double out_amount) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set student_name = ?, level_id = ?, address = ?, phone = ?, required_amount = ?, outstanding_amount = ? where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, LevelDAO.retrieveLevelID(lvl));
            stmt.setString(3, address);
            stmt.setInt(4, phone);
            stmt.setDouble(5, req_amount);
            stmt.setDouble(6, out_amount);
            stmt.setDouble(7, studentID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }

    public static boolean updateStudentFees(int studentID, double reqAmt, double outstandingAmt) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set required_amount = ?, outstanding_amount = ? where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, reqAmt);
            stmt.setDouble(2, outstandingAmt);
            stmt.setInt(3, studentID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }

    public static int retrieveNumberOfStudent() {
        int studentCount = 0;
        String sql = "select COUNT(*) from student";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return studentCount;
    }

    public static int retrieveNumberOfStudentByLevel(int levelID) {
        int studentCount = 0;
        String sql = "select COUNT(*) from student where level_id = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, levelID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return studentCount;
    }

    
     public static int retrieveNumberOfStudentByBranch(int branchID){
        int studentCount = 0;
        String sql = "select COUNT(*) from student where branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                studentCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return studentCount;
    }
    public static boolean updateStudentPassword(int studentID, String password) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set password = MD5(?) where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setInt(2, studentID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }

    public ArrayList<Object> uploadStudent(ArrayList<String> studentLists, ArrayList<String> studentNameLists, ArrayList<String> studentEmailLists) {
        ArrayList<Object> returnList = new ArrayList<>();
        ArrayList<Student> duplicatedStudents = new ArrayList<>();
        ArrayList<Student> insertedStudents = new ArrayList<>();
        //Map<Integer, Student> nameWithId = new HashMap<Integer, Student>();
        if (studentNameLists.size() > 0) {
            String nricList = "'" + String.join("','", studentNameLists) + "'";
            String emailList = "'" + String.join("','", studentNameLists) + "'";
            
            ArrayList<Student> existingStudents = new ArrayList();
            try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT student_id,student_name,student_nric,email FROM student WHERE student_name IN (" + nricList + ") or email IN ("+ emailList+")")) {

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Student a = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                    existingStudents.add(a);
                }

                String studentList = String.join(",", studentLists);
                String [] col = {"student_id"};
                PreparedStatement insertStatement = conn.prepareStatement("INSERT IGNORE INTO student(student_nric,student_name,phone,address,birth_date,gender,email,password,required_amount,outstanding_amount,level_id,branch_id) VALUES " + studentList, col);
                insertStatement.executeUpdate();
                ResultSet a = insertStatement.getGeneratedKeys();
                int count = 0;
                while(a.next()){
                    int id = a.getInt(1);
                    insertedStudents.add(retrieveStudentbyID(id));
                    //nameWithId.put(id, retrieveStudentbyID(id));
                    count++;
                }
                duplicatedStudents = existingStudents;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        returnList.add(duplicatedStudents);
        returnList.add(insertedStudents);
        return returnList;
    }
}
