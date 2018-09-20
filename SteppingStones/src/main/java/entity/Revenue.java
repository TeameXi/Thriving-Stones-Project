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
//Revenue need to delete deposit or not??
public class Revenue {
    private String studentName;
    private int noOfLessons;
    private String paymentDate;
    private double monthlyFees;
    private double regFees;
    private double deposit;
    private String depositPaymentDate;
    private String depositActivationDate;
    
    public Revenue(String studentName, int noOfLessons, String paymentDate, double monthlyFees, double regFees, double deposit, String depositPaymentDate, String depositActivationDate){
        this.studentName = studentName;
        this.noOfLessons = noOfLessons;
        this.paymentDate = paymentDate;
        this.monthlyFees = monthlyFees;
        this.regFees = regFees;
        this.deposit = deposit;
        this.depositPaymentDate = depositPaymentDate;
        this.depositActivationDate = depositActivationDate;
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
    
    public double getMonthlyFees() {
        return this.monthlyFees;
    }
    
    public double getRegFees() {
        return this.regFees;
    }
    
    public double getDeposit() {
        return this.deposit;
    }
    
    public String getDepositPaymentDate() {
        return this.depositPaymentDate;
    }
    
    public String getDepositActivationDate() {
        return this.depositActivationDate;
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
    
    public void setMonthlyFees(double monthlyFees) {
        this.monthlyFees = monthlyFees;
    }
    
    public void setRegFees(double regFees) {
        this.regFees = regFees;
    }
    
    public void setDeposit(double deposit) {
        this.deposit = deposit;
    } 
    
    public void setDepositPaymentDate(String depositPaymentDate) {
        this.depositPaymentDate = depositPaymentDate;
    }
    
    public void setDepositActivationDate(String depositActivationDate) {
        this.depositActivationDate = depositActivationDate;
    }
}
