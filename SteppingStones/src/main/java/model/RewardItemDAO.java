/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.RewardItem;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Desmond
 */
public class RewardItemDAO {
    public static void insertItems(List<RewardItem> rewardItemList) {
        try (Connection conn = ConnectionManager.getConnection()) {
            for(RewardItem item: rewardItemList){
                String sql = "insert into reward_item (item_name, quantity, image, description, point) values (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, item.getItem_name());
                statement.setInt(2, item.getQuantity());
                if (item.getImageStream() != null) {
                    // fetches input stream of the upload file for the blob column
                    statement.setBlob(3, item.getImageStream());
                }
                statement.setString(4, item.getDescription());
                statement.setInt(5, item.getPoint());
                statement.executeUpdate();
            }
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     public static ArrayList<RewardItem> listAllRewardItem() {
        ArrayList<RewardItem> itemList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from reward_item");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int item_id = rs.getInt("reward_item_id");
                String item_name = rs.getString("item_name");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                Blob image = rs.getBlob("image");
                int point = rs.getInt("point");
                RewardItem item = new RewardItem(item_id, item_name, quantity, point, image, description);
                itemList.add(item);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return itemList;
    }
     public static boolean deleteRewardItem(int item_id){
        String sql = "delete from reward_item where reward_item_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, item_id);
            
            int recordsUpdated = stmt.executeUpdate();
            if(recordsUpdated > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LevelDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return false;
    }
      public static boolean updateReward(int item_id, String item_name, String description, int quantity, int point) {
        String update_Tutor = "update reward_item set item_name=?,description=?,quantity=?,point=? WHERE reward_item_id =? ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(update_Tutor)) {
            preparedStatement.setString(1, item_name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, point);
            preparedStatement.setInt(5, item_id);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
      public static boolean updateQuantity(int item_id, int quantity) {
        String update_Tutor = "update reward_item set quantity=? WHERE reward_item_id =? ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(update_Tutor)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, item_id);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
      public static boolean updateImage(int item_id, InputStream is) {
        String update_Tutor = "update reward_item set image=? WHERE reward_item_id =? ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(update_Tutor)) {
            preparedStatement.setBlob(1, is);;
            preparedStatement.setInt(2, item_id);

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
