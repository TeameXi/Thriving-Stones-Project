/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Tutor {

    private String tutorID;
    private String name;
    private int age;
    private String phone;
    private String gender;
    private String email;
    private String password;

    public Tutor(String tutorID, String name, int age, String phoneNo, String gender, String emailAdd, String password) {
        this.tutorID = tutorID;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.email = emailAdd;
        this.password = password;
    }

    public String getTutorID() {
        return this.tutorID;
    }

    public String getName() {
        return this.name;
    }
    
    public int getAge(){
        return this.age;
    }

    public String getPhoneNo() {
        return this.phone;
    }

    public void setPhoneNo(String newNumber) {
        this.phone = newNumber;
    }

    public String getGender() {
        return this.gender;
    }

    public String getEmailAdd() {
        return this.email;
    }

    public void setEmailAdd(String newEmail) {
        this.email = newEmail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
