/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

public class Tutor {

    private String name;
    private int phoneNo;
    private char gender;
    private String emailAdd;
    private String password;

    public Tutor(String name, int phoneNo, char gender, String emailAdd, String password) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.emailAdd = emailAdd;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public int getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(int newNumber) {
        this.phoneNo = newNumber;
    }

    public char getGender() {
        return this.gender;
    }

    public String getEmailAdd() {
        return this.emailAdd;
    }

    public void setEmailAdd(String newEmail) {
        this.emailAdd = newEmail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
