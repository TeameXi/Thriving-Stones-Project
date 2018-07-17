/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Tutor {

    private String tutorID;
    private String name;
    private String age;
    private String phone;
    private String gender;
    private String email;
    private String password;
    
    public Tutor(){
    }

    public Tutor(String tutorID, String name, String age, String phone, String gender, String email, String password) {
        this.tutorID = tutorID;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }
    
    public Tutor(String name, String age, String phone, String gender, String email, String password) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public String getTutorID() {
        return this.tutorID;
    }

    public String getName() {
        return this.name;
    }
    
    public String getAge(){
        return this.age;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String newNumber) {
        this.phone = newNumber;
    }

    public String getGender() {
        return this.gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    public void setID(String id) {
        this.tutorID = id;
    }
}
