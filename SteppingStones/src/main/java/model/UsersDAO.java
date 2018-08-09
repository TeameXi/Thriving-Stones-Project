/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
/**
 *
 * @author Riana
 */
public class UsersDAO {

    public Users retrieveUserByUsername(String type,String name) {
        try (Connection conn = ConnectionManager.getConnection()) {
            if (type.equals("admin")) {
                PreparedStatement stmt = conn.prepareStatement("select admin_id,admin_username,admin_password,branch_id from admin where admin_username = ?");
                stmt.setString(1, name.trim());
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
                PreparedStatement stmt = conn.prepareStatement("select tutor_id,tutor_fullname,password,branch_id from tutor where tutor_fullname = ?");
                stmt.setString(1,name.trim());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int tutorId = rs.getInt(1);
                    String username = rs.getString(2);
                    String pwd = rs.getString(3);
                    int branch_id = rs.getInt(4);
                    return new Users(tutorId, username, pwd, branch_id);
                }
                return null;

            } else if (type.equals("student")) { //name+birthdate.pass
                Scanner sc = new Scanner(name);
                sc.useDelimiter(".");
                String username = sc.next();
                String dob = sc.next();
                sc.close();
                PreparedStatement stmt = conn.prepareStatement("select password from student where student_name = " + username + "and birth_date = " + dob);
                ResultSet rs = stmt.executeQuery();
                String pwd = "";
                while (rs.next()) {
                    pwd = rs.getString(1);
                }
                Users userToReturn = new Users(name, pwd);
                return userToReturn;

            } else if (type.equals("parent")) {//phone.pass
                PreparedStatement stmt = conn.prepareStatement("select parent_id,name,password, branch_id from parent where name = ?");
                stmt.setString(1,name.trim());
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
}
