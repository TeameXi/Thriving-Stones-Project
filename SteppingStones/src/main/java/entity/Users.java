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
    private String email;
    private String password;

    public Users(String email, String password){
        this.email = email;
        this.password = password;
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

    
    public boolean authenticateUser(Users user, String password){
       if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    
    
}
