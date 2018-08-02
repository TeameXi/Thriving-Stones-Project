/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Tutor {

    private String tutorID;
    private String nric;
    private String name;
    private int age;
    private String birthday;
    private String address;
    private String image_url;
    private int branch_id;
    private String updated;
    private String phone;
    private String gender;
    private String email;
    private String password;
    
    public Tutor(){
    }
    
    public Tutor(String id, String ic, String name, String phone, String address, String image, String birthday, String gender, String email, String pwd, int branch, String updated){
        this.tutorID = id;
        this.nric = ic;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image_url = image;
        this.birthday = birthday;
        this.gender = gender;
        this.email = email;
        this.branch_id = branch;
        this.updated = updated;
    }

    public Tutor(String tutorID, String name, int age, String phone, String gender, String email, String password) {
        this.tutorID = tutorID;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }
    
    public Tutor(String name, int age, String phone, String gender, String email, String password) {
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
    
    public int getAge(){
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
