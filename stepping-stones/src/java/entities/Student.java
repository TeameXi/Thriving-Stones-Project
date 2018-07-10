/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.*;

public class Student {

    private String nric;
    private String name;
    private int age;
    private int phoneNo;
    private char gender;
    private String address;
    private String level;
    private String[][] grades;
    private double outstandingAmt;
    private double requiredAmt;
    private double paidAmt;
    
    public Student(String nric, String name, int age, int phoneNo, char gender, String address, String level, double requiredAmt) {
        this.nric = nric;
        this.name = name;
        this.age = age;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.address = address;
        this.level = level;
        this.requiredAmt = requiredAmt;
    }

    public String getNric() {
        return this.nric;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
    
    public int getPhoneNo(){
        return this.phoneNo;
    }
    
    public void setPhoneNo(int newPhoneNo){
        this.phoneNo = newPhoneNo;
    }
    
    public char getGender(){
        return this.gender;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public void setAddress(String newAddress){
        this.address = newAddress;
    }
    
    public String getLevel(){
        return this.level;
    }
    
    public void setPaidAmt(double amountPaid){
        this.paidAmt = amountPaid;
    }
    
    public double getOutstandingAmt(){
        outstandingAmt = this.requiredAmt - this.paidAmt;
        if (outstandingAmt > 0){
            return outstandingAmt;
        } else {
            return 0;
        }
    }
    
    public String getGrades(String subject, String level, String location, String test){
        return "";
    }
}
