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
public class Payment {

    private int studentID;
    private String paymentType;
    private String details;
    private String dueDate;
    private double outstandingCharges;
    private double chargeAmount;
    private int classID;
    private int noOfLessons;

    public Payment(String paymentType, String details, String dueDate, double chargeAmount, double outstandingCharges, int classID, int noOfLessons) {
        this.paymentType = paymentType;
        this.details = details;
        this.dueDate = dueDate;
        this.outstandingCharges = outstandingCharges;
        this.chargeAmount = chargeAmount;
        this.classID = classID;
        this.noOfLessons = noOfLessons;
    }
    
    public Payment(int studentID, String paymentType, String details, String dueDate, double chargeAmount, double outstandingCharges, int classID, int noOfLessons) {
        this.studentID = studentID;
        this.paymentType = paymentType;
        this.details = details;
        this.dueDate = dueDate;
        this.outstandingCharges = outstandingCharges;
        this.chargeAmount = chargeAmount;
        this.classID = classID;
        this.noOfLessons = noOfLessons;
    }

    
    public int getStudentID() {
        return this.studentID;
    }
    
    public String getPaymentType() {
        return this.paymentType;
    }

    public String getDetails() {
        return this.details;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public double getOutstandingCharges() {
        return this.outstandingCharges;
    }

    public double getChargeAmount() {
        return this.chargeAmount;
    }
    
    public int getClassID() {
        return this.classID;
    }
    
    public int getNoOfLessons() {
        return this.noOfLessons;
    }
    
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setOutstandingCharges(double outstandingCharges) {
        this.outstandingCharges = outstandingCharges;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
    
    public void setClassID(int classID) {
        this.classID = classID;
    }
    
    public void setNoOfLessons(int noOfLessons) {
        this.noOfLessons = noOfLessons;
    }
}
