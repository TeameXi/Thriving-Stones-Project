/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Branch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MOH MOH SAN
 */
public class BranchDAO {

    public boolean addBranch(Branch branch) {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO branch(branch_id,name,starting_year,school_address,phone_number) VALUES(?,?,?,?,?)")) {
            preparedStatement.setInt(1, retrieveAllBranches().size() + 1);
            preparedStatement.setString(2, branch.getName());
            preparedStatement.setString(3, branch.getStartDate());
            preparedStatement.setString(4, branch.getAddress());
            preparedStatement.setInt(5, branch.getPhone());
            
            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean updateBranch(String branchID, String branchName, String startDate, String branchAddress, int phoneNo) {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE tutor SET name=?,starting_year=?,school_address=?,phone_number=? WHERE branch_id =? ")) {
            preparedStatement.setString(1,branchName);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3,branchAddress);
            preparedStatement.setInt(4,phoneNo);
            preparedStatement.setString(5,branchID);
            
            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public ArrayList<Branch> retrieveAllBranches() {
        ArrayList<Branch> branches = new ArrayList<>();
        String select_all_branch = "SELECT branch_id,name FROM branch";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_all_branch)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Branch branch = new Branch(id, name);
                branches.add(branch);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return branches;
    }

    public Branch retrieveBranchById(int branch_id) {
        String select_branch = "SELECT branch_id,name FROM branch WHERE branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_branch)) {
            preparedStatement.setInt(1, branch_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Branch branch = new Branch(id, name);
                return branch;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public Branch retrieveBranchByName(String branch_name) {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT branch_id,name FROM branch WHERE name = ?")) {
            preparedStatement.setString(1, branch_name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Branch branch = new Branch(id, name);
                return branch;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
