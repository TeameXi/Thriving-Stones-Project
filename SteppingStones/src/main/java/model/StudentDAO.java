package model;

import connection.ConnectionManager;
import entity.ArchivedObj;
import entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

public class StudentDAO {

//    public static int insertStudent(String studentName, int phone, String stuEmail, int level_id, int branch_id) {
//
//        try (Connection conn = ConnectionManager.getConnection();) {
//            conn.setAutoCommit(false);
//            String sql = "insert ignore into student(student_nric, student_name, phone, email, required_amount, outstanding_amount, level_id, branch_id)"
//                    + " value(null, ?, ?, ?, ?, ?, ? ,?)";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, studentName);
//            stmt.setInt(2, phone);
//            stmt.setString(3, stuEmail);
//            stmt.setDouble(4, 0);
//            stmt.setDouble(5, 0);
//            stmt.setInt(6, level_id);
//            stmt.setInt(7, branch_id);
//            stmt.executeUpdate();
//            conn.commit();
//            ResultSet rs = stmt.getGeneratedKeys();
//            int generatedKey = 0;
//            if (rs.next()) {
//                generatedKey = rs.getInt(1);
//            }
//            return generatedKey;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return 0;
//    }
    
    public static int insertStudent(String studentName, int phone, String stuEmail, int level_id, int branch_id, double regFees, String school, String stream, 
            String nric, String gender, String birthDate, String address) {

        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert ignore into student(student_name, phone, email, school, stream, reg_fees, outstanding_reg_fees, required_amount, outstanding_amount, level_id, branch_id,"
                    + " student_nric, gender, birth_date, address)"
                    + " value(?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setInt(2, phone);
            stmt.setString(3, stuEmail);
            stmt.setString(4, school);
            stmt.setString(5, stream);
            stmt.setDouble(6, regFees);
            stmt.setDouble(7, regFees);
            stmt.setDouble(8, 0);
            stmt.setDouble(9, 0);
            stmt.setInt(10, level_id);
            stmt.setInt(11, branch_id);
            stmt.setString(12, nric);
            stmt.setString(13, gender);
            stmt.setString(14, birthDate);
            stmt.setString(15, address);
            stmt.executeUpdate();
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (Exception e) {
            System.out.println("error in insertStudent method" + e.getMessage());
        }
        return 0;
    }
    
    public static String retrieveStudentName(int studentID) {
        String result = "";
        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "select student_name from student where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            conn.commit();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString("student_name");
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveStudentIDWithPhone sql");
        }
        return result;
    }
    
    public static int retrieveStudentIDWithPhone(String studentName, int phone) {
        int result = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "select student_id from student where student_name = ? and phone = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setInt(2, phone);
            conn.commit();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("student_id");
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveStudentIDWithPhone sql");
        }
        return result;
    }
    
    public static int retrieveStudentIDWithEmail(String studentName, String email) {
        int result = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "select student_id from student where student_name = ? and email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setString(2, email);
            conn.commit();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("student_id");
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveStudentIDWithEmail sql");
        }
        return result;
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
    
    public static ArrayList<String> listAllStudents(int branch_id) {
        ArrayList<String> studentList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from student where branch_id = ?");
            stmt.setInt(1, branch_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("student_name");
                int phone = rs.getInt("phone");
                String email = rs.getString("email");
                String stu = "";
                if(email != null && !email.isEmpty()){
                    stu = name + "  -  " + email;
                }else{
                    stu = name + "  -  " + phone;
                }
                
                studentList.add(stu);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return studentList;
    }
    
    public static ArrayList<Student> listAllStudentsByBranch(int branch_id) {
        ArrayList<Student> studentList = new ArrayList();
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
            System.out.println(stmt);
            while (rs.next()) {
                String studentNRIC = rs.getString("student_nric");
                String name = rs.getString("student_name");
                String BOD = rs.getString("birth_date");
                String gender = rs.getString("gender");
                System.out.println(gender + " hAalpppp");
                int levelID = rs.getInt("level_id");
                int branchID = rs.getInt("branch_id");
                int phone = rs.getInt("phone");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String school = rs.getString("school");
                String stream = rs.getString("stream");
                double reqAmt = rs.getDouble("required_amount");
                double outstandingAmt = rs.getDouble("outstanding_amount");
                String level = LevelDAO.retrieveLevel(levelID);
                stu = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, school, stream, reqAmt, outstandingAmt);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return stu;
    }

    public static int retrieveStudentLevelbyID(int studentID, int branch_id) {
        int levelID = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select level_id from student where student_id = ? and branch_id = ?");
            stmt.setInt(1, studentID);
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
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "SELECT s.level_id,s.gender,s.student_name,s.phone as student_phone,s.address,s.email as student_email,s.school as school,p.name,p.phone,p.email,p_rel.relationship "
                    + "FROM student s,parent as p, parent_child_rel as p_rel WHERE "
                    + "p.parent_id = p_rel.parent_id AND s.student_id = p_rel.child_id  AND child_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            
//            System.out.println(sql);
            ResultSet rs = stmt.executeQuery();
            
            String student_data = "";
            String parent_data = "";
            JSONObject student_obj = new JSONObject();
            JSONObject parent_obj = new JSONObject();
            if(rs.next()){
                student_obj.put("student_name", rs.getString("student_name"));
                student_obj.put("student_phone",rs.getString("student_phone"));
                student_obj.put("address",rs.getString("address"));
                student_obj.put("gender",rs.getString("gender"));
                student_obj.put("student_email", rs.getString("student_email"));
                String school =  rs.getString("school").trim();
                school = school.replace("\u0000","");
                student_obj.put("school",school);
                student_data = student_obj.toString();
                
                parent_obj.put("parent_name",rs.getString("name"));
                parent_obj.put("parent_phone",rs.getString("phone"));
                parent_obj.put("parent_email",rs.getString("email"));
                
                String rel = rs.getString("relationship").trim();
                rel = rel.replace("\u0000","");
                
                parent_obj.put("relationship",rel);
                parent_data = parent_obj.toString();
                
//                System.out.println(parent_data);
//                System.out.println(student_data);
                
                int levelId = rs.getInt("level_id");
                                
                sql = "INSERT INTO archived_students(student_id,lvl_id,student_data,parent_data) VALUES(?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, studentID);
                stmt.setInt(2, levelId);
                stmt.setString(3, student_data);
                stmt.setString(4, parent_data);
                int num = stmt.executeUpdate();
            } 

            sql = "delete from student where student_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            int deletedRecord = stmt.executeUpdate();
            if(deletedRecord > 0){
                deletedStatus = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }

    public static boolean updateStudent(int studentID, String name, String lvl, String address, int phone, String email, double req_amount, double out_amount) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set student_name = ?, level_id = ?, address = ?, phone = ?, email = ?, required_amount = ?, outstanding_amount = ? where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, LevelDAO.retrieveLevelID(lvl));
            stmt.setString(3, address);
            stmt.setInt(4, phone);
            stmt.setString(5, email);
            stmt.setDouble(6, req_amount);
            stmt.setDouble(7, out_amount);
            stmt.setDouble(8 , studentID);
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
    
    public static boolean updateStudentTotalOutstandingFees(int studentID, double outstandingAmt) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set outstanding_amount = ? where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, outstandingAmt);
            stmt.setInt(2, studentID);
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
            String sql = "update users set users.password = MD5(?) where role = 'student' and user_id = ?";
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
    public static boolean promoteStudentLevel(int studentID, int updatedLevel) {
       boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update student set level_id = ? where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, updatedLevel);
            stmt.setInt(2, studentID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }

    public ArrayList<Student> uploadStudent(ArrayList<String> studentLists) {
        //ArrayList<Object> returnList = new ArrayList<>();
        //ArrayList<Student> duplicatedStudents = new ArrayList<>();
        ArrayList<Student> insertedStudents = new ArrayList<>();
        //Map<Integer, Student> nameWithId = new HashMap<Integer, Student>();
        if (studentLists.size() > 0) {
            //String nameList = "'" + String.join("','", studentNameLists) + "'";
            //String emailList = "'" + String.join("','", studentNameLists) + "'";
            
            ArrayList<Student> existingStudents = new ArrayList();
            
            try (Connection conn = ConnectionManager.getConnection();){
                /*PreparedStatement preparedStatement = conn.prepareStatement("SELECT student_id,student_name,student_nric,email FROM student WHERE (student_name IN (" + nameList + ") AND email IN ("+ emailList+")) OR (student_name IN (" + nameList + ") AND phone IN ("+ emailList+"))")) {

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Student a = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                    existingStudents.add(a);
                }*/
                String studentList = String.join(",", studentLists);
                String [] col = {"student_id"};
                PreparedStatement insertStatement = conn.prepareStatement("INSERT ignore INTO student(student_nric,student_name,phone,address,birth_date,gender,email,reg_fees,outstanding_reg_fees,level_id,branch_id, school, stream) VALUES " + studentList, col);
                insertStatement.executeUpdate();
                System.out.println(insertStatement.toString());
                ResultSet a = insertStatement.getGeneratedKeys();
                int count = 0;
                while(a.next()){
                    int id = a.getInt(1);
                    insertedStudents.add(retrieveStudentbyID(id));
                    //nameWithId.put(id, retrieveStudentbyID(id));
                    count++;
                }
                //duplicatedStudents = existingStudents;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //returnList.add(duplicatedStudents);
        //returnList.add(insertedStudents);
        return insertedStudents;
    }
    public static List<HashMap<String,String>> retrieveAllStudent() {
        HashMap<String, String> phoneName = new HashMap<>();
        HashMap<String, String> emailName = new HashMap<>();
        ArrayList<HashMap<String,String>> returnList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select student_name, email, phone from student");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("student_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                if(phone == null || "".equals(phone)){
                    emailName.put(email, name);
                }else{
                    phoneName.put(phone, name);
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        returnList.add(phoneName);
        returnList.add(emailName);
        return returnList;
    }
    public static int retrieveStudentID(List<String> studentDetails) {
        int result = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select student_id from student where (student_name = '" + studentDetails.get(1) + "' and phone = '" + studentDetails.get(0) + "') or (student_name = '" + studentDetails.get(1) + "' and email = '" + studentDetails.get(0) + "')";
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
    
    
    public static ArrayList<ArchivedObj>retrieveArchivedStudentData(){
        ArrayList<ArchivedObj> past_data = new ArrayList<>();
        String sql = "SELECT * FROM archived_students order by lvl_id";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                int studentID = rs.getInt(1);
                int levelID = rs.getInt(2);
                String studentInfo = rs.getString(3);
                String parentInfo = rs.getString(4);
                past_data.add(new ArchivedObj(studentID,levelID,studentInfo,parentInfo));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return past_data;
    }

    public static XSSFWorkbook generateArchievedStudent(String filename){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Past Students");

        //Create title
        XSSFRow row = sheet.createRow(0);
        
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setFontName("Times New Roman");
        fontBold.setFontHeightInPoints((short)11);
        fontBold.setBold(true);
        style.setFont(fontBold);
        
        String [] titleList = {"Student Name","Student Phone","Address","Gender","Student Email","School","level","Parent Name","Contact Details","Parent Email","Relationship"};
        
        for(int i = 0; i < titleList.length; i++){
            row.createCell(i).setCellValue(titleList[i]);
            row.getCell(i).setCellStyle(style);
        }
      
        
        // Load data
        ArrayList<ArchivedObj> student_archived_data =  retrieveArchivedStudentData();
        int r = 1;
        for(ArchivedObj stuObj: student_archived_data){
            // Manipulating Data
            row = sheet.createRow(r);
            String studentData = stuObj.getStudent_data();
            JSONObject studentInfoObj = new JSONObject(studentData);
            
            String parentData = stuObj.getParent_data();
            JSONObject parentInfoObj = new JSONObject(parentData);
            
            System.out.println(parentInfoObj);
            
            // Data Value
            String name = studentInfoObj.getString("student_name");
            String phone = studentInfoObj.getString("student_phone");
            String address = studentInfoObj.getString("address");
            String gender = studentInfoObj.getString("gender");
            String email = studentInfoObj.getString("student_email");
            String school = studentInfoObj.getString("school");
            
            int lvl_id = stuObj.getLevel_id();
            String level  = "";
            if(lvl_id < 7){
                level = "Primary "+lvl_id;
            }else{
                level = "Secondary "+(10-lvl_id);
            }

            String parent_name = parentInfoObj.getString("parent_name");
            String parent_phone = parentInfoObj.getString("parent_phone");
            String parent_email = parentInfoObj.getString("parent_email");
            String relationship = parentInfoObj.getString("relationship");
            
            int column = 0;
            XSSFCell cell = row.createCell(column);
            cell.setCellValue(name);

            cell = row.createCell(++column);
            cell.setCellValue(phone);
            
            cell = row.createCell(++column);
            cell.setCellValue(address);
            
            cell = row.createCell(++column);
            cell.setCellValue(gender);
            
            cell = row.createCell(++column);
            cell.setCellValue(email);
            
            cell = row.createCell(++column);
            cell.setCellValue(school);
            
            cell = row.createCell(++column);
            cell.setCellValue(level);
            
            cell = row.createCell(++column);
            cell.setCellValue(parent_name);
            
            cell = row.createCell(++column);
            cell.setCellValue(parent_phone);
            
            cell = row.createCell(++column);
            cell.setCellValue(parent_email);
            
            cell = row.createCell(++column);
            cell.setCellValue(relationship);
            r++;
        }
        return workbook;
    }
}
