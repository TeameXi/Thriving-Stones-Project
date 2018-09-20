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
public class Deposit {
    private double deposit;
    private String depositPaymentDate;
    private String depositActivationDate;
    private double activatedAmount;
    
    public Deposit(double deposit, String depositPaymentDate, String depositActivationDate, double activatedAmount){
        this.deposit = deposit;
        this.depositPaymentDate = depositPaymentDate;
        this.depositActivationDate = depositActivationDate;
        this.activatedAmount = activatedAmount;
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
    
    public double getActivatedAmount() {
        return this.activatedAmount;
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
    
    public void setActivatedAmount(double activatedAmount) {
        this.activatedAmount = activatedAmount;
    }
}
