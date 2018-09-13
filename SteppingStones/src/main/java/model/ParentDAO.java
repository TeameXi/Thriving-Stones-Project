package model;

import connection.ConnectionManager;
import entity.Parent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParentDAO {

    public static int insertParent(String name, int phone, String email, String password, int branchID) {
        
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "insert ignore into parent(name, phone, email, password, branch_id)"
                    + " value(?, ?, ?, MD5(?), ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setInt(5, branchID);
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

    public static int retrieveParentID(int parentPhone) {
        int result = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select parent_id from parent where phone = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, parentPhone);
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
                int branch_id = rs.getInt(8);
                Parent parent = new Parent(id, name, nationality, company, designation, phone, email, branch_id);
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

    public ArrayList<Object> uploadParent(ArrayList<String> parentLists, ArrayList<Integer> parentMobileLists) {
        ArrayList<Object> returnList = new ArrayList<>();
        ArrayList<String> duplicatedParents = new ArrayList<>();
        ArrayList<Parent> insertedParents = new ArrayList<>();
        if (parentMobileLists.size() > 0) {
            String mobileList = "'" + String.join("','", parentMobileLists.toString()) + "'";
            mobileList = mobileList.replace("[", "");
            mobileList = mobileList.replace("]", "");
            ArrayList<String> existingParents = new ArrayList();
            try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT parent_id,name FROM parent WHERE phone in (" + mobileList + ")")) {
                String x = preparedStatement.toString();
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String parent_name = rs.getString(2);
                    existingParents.add(parent_name);
                }

                String parentList = String.join(",", parentLists);
                String [] col = {"parent_id"};
                PreparedStatement insertStatement = conn.prepareStatement("INSERT IGNORE INTO parent(name,nationality,company,designation,phone,email,password,branch_id) VALUES " + parentList, col);
                insertStatement.executeUpdate();
                ResultSet a = insertStatement.getGeneratedKeys();
                int count = 0;
                while(a.next()){
                    int id = a.getInt(1);
                    insertedParents.add(retrieveSpecificParentById(id));
                    count++;
                }
                duplicatedParents = existingParents;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        returnList.add(duplicatedParents);
        returnList.add(insertedParents);
        return returnList;
    }

}
