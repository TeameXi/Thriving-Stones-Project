/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Riana
 */
public class Users {
    private int user_id;
    private String username;
    private String password;
    private String role;
    private int respective_id;
    private int branch_id;
    
    public Users(int user_id, String username, String password, String role, int respective_id) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.respective_id = respective_id;
    }

    public Users(String username, String password, String role, int respective_id, int branch_id) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.respective_id = respective_id;
        this.branch_id = branch_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRespectiveID() {
        return respective_id;
    }

    public void setRespective_id(int respective_id) {
        this.respective_id = respective_id;
    }
    
    /*private int user_id;
    private String email;
    private String password;
    private int branch_id;
    private String mailingAddress;
    

    public Users(String email, String password){
        this.email = email;
        this.password = password;
    }
    
    public Users(String email, String password,int branch_id){
        this.email = email;
        this.password = password;
        this.branch_id = branch_id;
    }
    
    public Users(int user_id,String email, String password, int branch_id){
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.branch_id = branch_id;
    }

    public Users(int user_id, String email, String password, int branch_id, String mailingAddress) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.branch_id = branch_id;
        this.mailingAddress = mailingAddress;
    }

    public Users() {
        
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getBranchId() {
        return branch_id;
    }

    public void setBranchId(int branch_id) {
        this.branch_id = branch_id;
    }
    
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
    
    public boolean authenticateUser(Users user, String password){
       if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }*/

    public int getBranchId() {
        return branch_id;
    }

    public void setBranchId(int branch_id) {
        this.branch_id = branch_id;
    }

    
    
    
}
