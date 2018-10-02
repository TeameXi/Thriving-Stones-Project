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
public class Revenue {
    private String studentName;
    private String type;
    private int noOfLessons;
    private String paymentDate;
    private double amount;
    
    public Revenue(String studentName, String type, int noOfLessons, String paymentDate, double amount){
        this.studentName = studentName;
        this.type = type;
        this.noOfLessons = noOfLessons;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
    
    public String getStudentName() {
        return this.studentName;
    }
    
    public int getNoOfLessons() {
        return this.noOfLessons;
    }
    
    public String getpaymentDate() {
        return this.paymentDate;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public void setNoOfLessons(int noOfLessons) {
        this.noOfLessons = noOfLessons;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void setDepositActivationDate(String type) {
        this.type = type;
    }
}
