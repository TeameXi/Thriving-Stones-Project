/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Parent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DEYU
 */
public class ParentDAO {

    public static boolean insertParent(String name, String nationality, String company, String designation, int phone, String email, String password, int branchID) {
        boolean status = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert into parent(name, nationality, company, designation, phone, email, password, branch_id)"
                    + " value(?, ?, ?, ?, ?, ?, MD5(?), ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, nationality);
            stmt.setString(3, company);
            stmt.setString(4, designation);
            stmt.setInt(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, password);
            stmt.setInt(8, branchID);
            stmt.executeUpdate();
            conn.commit();
            status = true;
            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public static int retrieveParentID(String parentName) {
        int result = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select parent_id from parent where name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, parentName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("parent_id");
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveParentID sql");
        }
        return result;
    }

    public static boolean deleteParent(int parentID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from parent where parent_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parentID);
            stmt.executeUpdate();
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }

    public static String retrieveParentByStudentID(int studentID) {
        String parentInfo = "";
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select name, phone from parent_child_rel pc, parent p where pc.parent_id = p.parent_id and child_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                parentInfo = parentInfo + "Guadiance<br>" + rs.getString("name") + " " + rs.getString("phone");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return parentInfo;
    }

    public Parent retrieveSpecificParentById(int parentId) {
        String select_tutor = "SELECT * FROM parent WHERE parent_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {
            preparedStatement.setInt(1, parentId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String nationality = rs.getString(3);
                String company = rs.getString(4);
                String designation = rs.getString(5);
                int phone = rs.getInt(6);
                String email = rs.getString(7);
                String password = rs.getString(8);
                int branch_id = rs.getInt(9);
                Parent parent = new Parent(id, name, nationality, company, designation, phone, email, password, branch_id);
                return parent;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean updateParent(int parentID, String nationality, String company, String designation, int phone, String email) {
        String update_Tutor = "UPDATE parent SET nationality=?,company=?,designation=?,phone=?,email=? WHERE parent_id =? ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(update_Tutor)) {
            preparedStatement.setString(1, nationality);
            preparedStatement.setString(2, company);
            preparedStatement.setString(3, designation);
            preparedStatement.setInt(4, phone);
            preparedStatement.setString(5, email);
            preparedStatement.setInt(6, parentID);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static boolean updateParentPassword(int parentID, String password) {
        boolean updatedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update parent set password = MD5(?) where parent_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setInt(2, parentID);
            stmt.executeUpdate();
            conn.commit();
            updatedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedStatus;
    }

    public ArrayList<String> uploadParent(ArrayList<String> parentLists, ArrayList<String> parentNameLists) {
        ArrayList<String> duplicatedParents = new ArrayList<>();
        if (parentNameLists.size() > 0) {
            String nameList = "'" + String.join("','", parentNameLists) + "'";

            ArrayList<String> existingParents = new ArrayList();
            try (Connection conn = ConnectionManager.getConnection();
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT parent_id,name FROM parent WHERE = name IN (" + nameList + ")")) {

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String parent_name = rs.getString(2);
                    existingParents.add(parent_name);
                }

                String parentList = String.join(",", parentLists);
                PreparedStatement insertStatement = conn.prepareStatement("INSERT IGNORE INTO parent(name,nationality,company,designation,phone,email,password,branch_id) VALUES " + parentList);
                int num = insertStatement.executeUpdate();
                duplicatedParents = existingParents;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return duplicatedParents;
    }

}
