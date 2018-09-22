package model;

import connection.ConnectionManager;
import entity.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO {

    public int addAdmin(Admin admin) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO admin(admin_id,admin_username,email,branch_id) VALUES(?,?,?,?)")) {
            int id = retrieveNoOfAdmin() + 1;
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, admin.getAdmin_username());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setInt(4, admin.getBranch_id());

            int num = preparedStatement.executeUpdate();
            
            if (num != 0) {
                return id;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static boolean updateAdmin(String admin_username,String email,int branch_id,int admin_id) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE admin SET admin_username=?,email=?,branch_id=? WHERE admin_id =? ")) {
            preparedStatement.setString(1, admin_username);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, branch_id);
            preparedStatement.setInt(4, admin_id);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAdmin(int adminId) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM admin WHERE admin_id = ?")) {
            preparedStatement.setInt(1, adminId);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int retrieveNoOfAdmin() {
        int number = 0;
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT count(*) FROM admin")) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                number = rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return number;
    }

    public ArrayList<Admin> retrieveAllAdmins() {
        ArrayList<Admin> admins = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT admin_username,email,branch_id,admin_id FROM admin where branch_id <> 0 order by branch_id")) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String admin_username = rs.getString(1);
                String email = rs.getString(2);
                int branch_id = rs.getInt(3);
                int admin_id = rs.getInt(4);

                Admin branch = new Admin(admin_username, email, branch_id,admin_id);
                admins.add(branch);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return admins;
    }

    public Admin retrieveAdminById(int admin_id) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT admin_username,email,branch_id FROM admin WHERE admin_id = ?")) {
            preparedStatement.setInt(1, admin_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String admin_username = rs.getString(1);
                String email = rs.getString(2);
                int branch_id = rs.getInt(3);
                Admin admin = new Admin(admin_username, email, branch_id,admin_id);
                return admin;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Admin retrieveAdminByName(String username) {
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT admin_username,branch_id FROM admin WHERE admin_username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String admin_username = rs.getString(1);
                int branch_id = rs.getInt(2);
                Admin admin = new Admin(admin_username, branch_id);
                return admin;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public boolean updateAdminPassword(int adminID, String password) {
        String updateTutorPassword = "update users set users.password = MD5(?) where role = 'admin' and user_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateTutorPassword)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2,adminID);
            
            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
