/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author DEYU
 */
public class StudentDAO {

    public static void insertStudent(String studentNRIC, String studentName, int phone, String address, String BOD, String gender, String stuEmail, String stuPassword, int level_id, int branch_id) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into student(student_name, phone, address, birth_date, gender, email, password, required_amount, outstanding_amount, level_id, branch_id, student_nric)"
                    + " value(?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ? )";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setInt(2, phone);
            stmt.setString(3, address);
            stmt.setString(4, BOD);
            stmt.setString(5, gender);
            stmt.setString(6, stuEmail);
            stmt.setString(7, stuPassword);
            stmt.setDouble(8, 0);
            stmt.setDouble(9, 0);
            stmt.setInt(10, level_id);
            stmt.setInt(11, 1); //replace with branch_id
            stmt.setString(12, studentNRIC);
            stmt.executeUpdate(); 
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static int retrieveStudentID(String studentName){
        int result = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            String sql = "select student_id from student where student_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = rs.getInt("student_id");
            } 
        } catch (SQLException ex) {
            System.out.println("error in retrieveStudentID sql");
        }  
        return result;
    }
    
    public static ArrayList<Student> listAllStudents(){
        ArrayList<Student> studentList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from student where branch_id = ? order by level_id, student_name");
            stmt.setInt(1, 1); // replace with branch_id
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
                String email= rs.getString("email");
                String password = rs.getString("password");
                double reqAmt = rs.getDouble("required_amount");
                double outstandingAmt = rs.getDouble("outstanding_amount");
                String level = LevelDAO.retrieveLevel(levelID);
                Student student = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, password, reqAmt, outstandingAmt);
                studentList.add(student);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return studentList;
    }
    
    public static LinkedHashMap<String, ArrayList<Student>> listAllStudent(){
        LinkedHashMap<String, ArrayList<Student>> students = new LinkedHashMap<>();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from student where branch_id = ? order by level_id, student_name");
            stmt.setInt(1, 1); // replace with branch_id
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
                String email= rs.getString("email");
                String password = rs.getString("password");
                double reqAmt = rs.getDouble("required_amount");
                double outstandingAmt = rs.getDouble("outstanding_amount");
                String level = LevelDAO.retrieveLevel(levelID);
                Student student = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, password, reqAmt, outstandingAmt);
                if(students.get(level) == null){
                    ArrayList<Student> studentList = new ArrayList();
                    studentList.add(student);
                    students.put(level, studentList);
                }else{
                    ArrayList<Student> studentList = students.get(level);
                    studentList.add(student);
                    students.put(level, studentList);
                }
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        
        return students;
    }   
    
    public static Student retrieveStudentbyID(int studentID){
        Student stu  = null;
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from student where student_id = ? and branch_id = ?");
            stmt.setInt(1, studentID);
            stmt.setInt(2, 1); //replace with branchID
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String studentNRIC = rs.getString("student_nric");
                String name = rs.getString("student_name");
                String BOD = rs.getString("birth_date");
                String gender = rs.getString("gender");
                int levelID = rs.getInt("level_id");
                int branchID = rs.getInt("branch_id");
                int phone = rs.getInt("phone");
                String address = rs.getString("address");
                String email= rs.getString("email");
                String password = rs.getString("password");
                double reqAmt = rs.getDouble("required_amount");
                double outstandingAmt = rs.getDouble("outstanding_amount");
                String level = LevelDAO.retrieveLevel(levelID);
                stu = new Student(studentID, studentNRIC, name, BOD, gender, level, branchID, phone, address, email, password, reqAmt, outstandingAmt);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return stu;
    }
    
    public static int retrieveStudentLevelbyName(String studentName){
        int levelID = 0;
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select level_id from student where student_name = ? and branch_id = ?");
            stmt.setString(1, studentName);
            stmt.setInt(2, 1); //replace branch_id
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                levelID = rs.getInt("level_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return levelID;
    }
   
    public static boolean deleteStudentbyID(int studentID){
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
    
    public static boolean updateStudent(int studentID, String name, String lvl, String address, int phone, double req_amount, double out_amount){
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
    
    public static boolean updateStudentFees(int studentID, double reqAmt, double outstandingAmt){
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
}
