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
    public ArrayList retrieveAllBranches() {
        ArrayList <Branch> branches = new ArrayList<>();
        String select_all_branch = "SELECT branch_id,name FROM branch";
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(select_all_branch)) {
         
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Branch branch = new Branch(id,name);
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
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Branch branch = new Branch(id,name);
                return branch;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


    
}
