/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DEYU
 */
public class Student {
    private String studentID;
    private String name;
    private int age;
    private String gender;
    private String level;
    private String address;
    private String phone;
    private double reqAmt;
    private double outstandingAmt;
    private Map<String, Map<String, StudentGrade>> grades = new HashMap<>();
    
    public Student(){  
    }
                   
    public Student(String name, int age, String gender, String level, String address, String phone, double reqAmt, double outstandingAmt){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.address = address;
        this.phone = phone;
        this.reqAmt = reqAmt;
        this.outstandingAmt = outstandingAmt;
    }
    
    public Student(String name, int age, String gender, String level, String address, String phone, double reqAmt, double outstandingAmt, Map<String, Map<String, StudentGrade>> grades){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.address = address;
        this.phone = phone;
        this.reqAmt = reqAmt;
        this.outstandingAmt = outstandingAmt;
        this.grades = grades;
    }
    
    public String getStudentID() {
        return this.studentID;
    }
    
    public String getName(){
        return name;
    }
    
    public int getAge(){
        return age;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getLevel(){
        return level;
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
    
    public Map<String, Map<String, StudentGrade>> getGrades(){
        return grades;
    }
    
    public void setName(String newStudentName){
        this.name = newStudentName;
    }
    
    public void setAge(int newAge){
        this.age = newAge;
    }
    
    public void setLevel(String newLevel){
        this.level = newLevel;
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
    
    public void setOutstandingAmt(double newOutstandingAmt){
        this.outstandingAmt = newOutstandingAmt;
    }
    
    public void setGrades(Map<String, Map<String, StudentGrade>> grades){
        this.grades = grades;
    }
    
    public void setStudentID(String id) {
        this.studentID = id;
    }
}
