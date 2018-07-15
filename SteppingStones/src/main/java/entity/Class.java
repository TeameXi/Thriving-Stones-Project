/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Class {

    private String level;
    private String subject;
    private String classTime;
    private String classDay;   
    private double mthlyFees;
    private String startDate;
    
    public Class(){
        
    }

    public Class(String level, String subject, String classTime, String classDay, double mthlyFees, String startDate) {
        this.level = level;
        this.subject = subject;
        this.classTime = classTime;
        this.classDay = classDay;
        this.mthlyFees = mthlyFees;
        this.startDate = startDate;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String newLevel) {
        this.level = newLevel;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String newSubject) {
        this.subject = newSubject;
    }

    public String getClassTime() {
        return this.classTime;
    }

    public void setClassTime(String newClassTime) {
        this.classTime = newClassTime;
    }

    public String getClassDay() {
        return this.classDay;
    }

    public void setClassDay(String newClassDay) {
        this.classDay = newClassDay;
    }
    
    public double getMthlyFees() {
        return this.mthlyFees;
    }

    public void setMthlyFees(double newMthlyFees) {
        this.mthlyFees = newMthlyFees;
    }
    
    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String newStartDate) {
        this.startDate = newStartDate;
    }
    
    @Override
    public String toString(){
        return this.level + "&" + this.subject + "&" + this.classTime + "&" + this.classDay + "&" +  this.mthlyFees + "&" + this.startDate;
    }
}

