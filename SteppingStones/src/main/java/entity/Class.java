/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Class {

    private int classID;
    private String level;
    private String subject;
    private String classTime;
    private String classDay;   
    private double mthlyFees;
    private String startDate;
    private String endDate;
    
    public Class(){
        
    }

    public Class(int classID, String level, String subject, String classTime, String classDay, double mthlyFees, String startDate, String endDate) {
        this.classID = classID;
        this.level = level;
        this.subject = subject;
        this.classTime = classTime;
        this.classDay = classDay;
        this.mthlyFees = mthlyFees;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Class(int classID, String level, String subject, String classTime, String classDay, int mthlyFees, String startDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getClassID() {
        return this.classID;
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
    
     public void setClassID(int id) {
        this.classID = id;
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
        return this.classID + "&" + this.level + "&" + this.subject + "&" + this.classTime + "&" + this.classDay + "&" +  this.mthlyFees + "&" + this.startDate + "&" + this.endDate;
    }
}

