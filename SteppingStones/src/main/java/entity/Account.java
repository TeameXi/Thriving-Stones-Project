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
public class Account {
    private int studentCount;
    private double amount;
    private double monthlyFees;
    private double regFees;
    private double deposit;
    
    public Account(int studentCount, double amount){
        this.studentCount = studentCount;
        this.amount = amount;
    }
    
    public Account(int studentCount, double monthlyFees, double regFees, double deposit){
        this.studentCount = studentCount;
        this.monthlyFees = monthlyFees;
        this.regFees = regFees;
        this.deposit = deposit;
    }
    
    public int getStudentCount() {
        return this.studentCount;
    }
    
    public double getAmount() {
        return this.amount;
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
    
    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
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
}
