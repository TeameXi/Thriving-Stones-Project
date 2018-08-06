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

    public Users retrieveUserByUsername(String type, final String user) {
        try (Connection conn = ConnectionManager.getConnection()) {
            if (type.equals("admin")) {
                PreparedStatement stmt = conn.prepareStatement("select admin_username,admin_password,branch_id from admin where admin_username = '" + user + "'");
                ResultSet rs = stmt.executeQuery();
               
                while (rs.next()) {
                    String username = rs.getString(1);
                    String pwd = rs.getString(2);
                    int branch_id = rs.getInt(3);
                    Users userToReturn = new Users(username, pwd,branch_id);
                    return userToReturn;
                }
               
                return null;

            } else if (type.equals("tutor")) {
                PreparedStatement stmt = conn.prepareStatement("select password from tutor where email = " + user);
                ResultSet rs = stmt.executeQuery();
                String pwd = "";
                while (rs.next()) {
                    pwd = rs.getString(1);
                }
                Users userToReturn = new Users(user, pwd);
                return userToReturn;

            } else if (type.equals("student")) { //name+birthdate.pass
                Scanner sc = new Scanner(user);
                sc.useDelimiter(".");
                String name = sc.next();
                String dob = sc.next();
                sc.close();
                PreparedStatement stmt = conn.prepareStatement("select password from student where student_name = " + name + "and birth_date = " + dob);
                ResultSet rs = stmt.executeQuery();
                String pwd = "";
                while (rs.next()) {
                    pwd = rs.getString(1);
                }
                Users userToReturn = new Users(user, pwd);
                return userToReturn;

            } else if (type.equals("parent")) {//phone.pass
                PreparedStatement stmt = conn.prepareStatement("select password from parent where phone = " + user);
                ResultSet rs = stmt.executeQuery();
                String pwd = "";
                while (rs.next()) {
                    pwd = rs.getString(1);
                }
                Users userToReturn = new Users(user, pwd);
                return userToReturn;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
