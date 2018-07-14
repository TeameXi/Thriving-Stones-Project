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
    private String phoneNo;
    private String gender;
    private String emailAdd;
    private String password;

    public Tutor(String tutorID, String name, int age, String phoneNo, String gender, String emailAdd, String password) {
        this.tutorID = tutorID;
        this.name = name;
        this.age = age;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.emailAdd = emailAdd;
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
        return this.phoneNo;
    }

    public void setPhoneNo(String newNumber) {
        this.phoneNo = newNumber;
    }

    public String getGender() {
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
