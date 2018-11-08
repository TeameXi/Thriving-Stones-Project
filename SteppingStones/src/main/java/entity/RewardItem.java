/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.InputStream;
import java.sql.Blob;

/**
 *
 * @author Desmond
 */
public class RewardItem {
    private int reward_item_id;
    private String item_name;
    private int quantity;
    private java.sql.Blob image; 
    private String description;
    private InputStream imageStream;
    private int point;
    
    public RewardItem(String item_name, int quantity, int point, InputStream imageStream, String description) {
        this.item_name = item_name;
        this.quantity = quantity;
        this.point = point;
        this.imageStream = imageStream;
        this.description = description;
    }

    
    public RewardItem(int reward_item_id, String item_name, int quantity, int point, Blob image, String description) {
        this.reward_item_id = reward_item_id;
        this.item_name = item_name;
        this.quantity = quantity;
        this.point = point;
        this.image = image;
        this.description = description;
    }

    public int getReward_item_id() {
        return reward_item_id;
    }

    public void setReward_item_id(int reward_item_id) {
        this.reward_item_id = reward_item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
    
}
