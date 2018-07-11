/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author DEYU
 */
public class Student {
    private String studentName;
    private int age;
    private String gender;
    private String lvl;
    private String address;
    private String phone;
    private double reqAmt;
    private double outstandingAmt;
    
    public Student(String studentName, int age, String gender, String lvl, String address, String phone){
        this.studentName = studentName;
        this.age = age;
        this.gender = gender;
        this.lvl = lvl;
        this.address = address;
        this.phone = phone;
        this.reqAmt = 0;
        this.outstandingAmt = 0;
    }
    
    public String getName(){
        return studentName;
    }
    
    public int getAge(){
        return age;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getLevel(){
        return lvl;
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public double getReqAmt(){
        return reqAmt;
    }
    
    public double getOutstandingAmt(){
        return outstandingAmt;
    }
    
    public void setName(String newStudentName){
        this.studentName = newStudentName;
    }
    
    public void setAge(int newAge){
        this.age = newAge;
    }
    
    public void setLevel(String newLvl){
        this.lvl = newLvl;
    }
    
    public void setAddress(String newAddress){
        this.address = newAddress;
    }
    
    public void setPhone(String newPhone){
        this.phone = newPhone;
    }
    
    public void setReqAmt(double newReqAmt){
        this.reqAmt = newReqAmt;
    }
    
    public void getOutstandingAmt(double newOutstandingAmt){
        this.outstandingAmt = newOutstandingAmt;
    }
}
