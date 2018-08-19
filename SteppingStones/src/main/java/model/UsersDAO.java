package model;

import connection.ConnectionManager;
import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersDAO {

    public Users retrieveUserByUsername(String type, String name, String password) {
        try (Connection conn = ConnectionManager.getConnection()) {
            if (type.equals("admin")) {
                PreparedStatement stmt = conn.prepareStatement("select admin_id,admin_username,admin_password,branch_id from admin where admin_username = ? and admin_password = MD5(?)");
                stmt.setString(1, name.trim());
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
               
                while (rs.next()) {
                    int adminId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    return new Users(adminId, username, pwd, branch_id);
                }
               
                return null;

            } else if (type.equals("tutor")) {
                PreparedStatement stmt = conn.prepareStatement("select tutor_id,tutor_fullname,password,branch_id from tutor where tutor_fullname = ? and password = MD5(?)");
                stmt.setString(1,name.trim());
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int tutorId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    return new Users(tutorId, username, pwd, branch_id);
                }
                return null;

            } else if (type.equals("student")) {
                PreparedStatement stmt = conn.prepareStatement("select student_id, student_name, password, branch_id from student where student_name = ? and password = MD5(?)");
                stmt.setString(1,name.trim());
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int studentId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    return new Users(studentId, username, pwd, branch_id);
                }
                return null;

            } else if (type.equals("parent")) {//phone.pass
                PreparedStatement stmt = conn.prepareStatement("select parent_id,name,password, branch_id from parent where name = ? and password = MD5(?)");
                stmt.setString(1,name.trim());
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int parentId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branchId = rs.getInt(4);
                    return new Users(parentId, username, pwd, branchId);
                }
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Users retrieveUserByUsernameRole(String type, String name) {
        try (Connection conn = ConnectionManager.getConnection()) {
            if (type.equals("admin")) {
                PreparedStatement stmt = conn.prepareStatement("select admin_id,admin_username,admin_password,branch_id, email from admin where admin_username = ?");
                stmt.setString(1, name.trim());
                ResultSet rs = stmt.executeQuery();
               
                while (rs.next()) {
                    int adminId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    String email = rs.getString(5);
                    return new Users(adminId, username, pwd, branch_id, email);
                }
               
                return null;

            } else if (type.equals("tutor")) {
                PreparedStatement stmt = conn.prepareStatement("select tutor_id,tutor_fullname,password,branch_id,email from tutor where tutor_fullname = ?");
                stmt.setString(1,name.trim());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int tutorId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    String email = rs.getString(5);
                    return new Users(tutorId, username, pwd, branch_id, email);
                }
                return null;

            } else if (type.equals("student")) {
                PreparedStatement stmt = conn.prepareStatement("select student_id, student_name, password, branch_id, email from student where student_name = ?");
                stmt.setString(1,name.trim());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int studentId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    String email = rs.getString(5);
                    return new Users(studentId, username, pwd, branch_id);
                }
                return null;

            } else if (type.equals("parent")) {//phone.pass
                PreparedStatement stmt = conn.prepareStatement("select parent_id,name,password, branch_id, email from parent where name = ?");
                stmt.setString(1,name.trim());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int parentId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branchId = rs.getInt(4);
                    String email = rs.getString(5);
                    return new Users(parentId, username, pwd, branchId, email);
                }
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
