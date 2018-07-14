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
    private String username;
    private String pwd;

    public Users(String username, String pwd){
        this.username = username;
        this.pwd = pwd;
    }

    public Users() {
        
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    
    
    public boolean authenticateUser(Users user, String pwd){
       if (user != null && user.getPwd().equals(pwd)) {
            return true;
        }
        return false;
    }
    
    
}
