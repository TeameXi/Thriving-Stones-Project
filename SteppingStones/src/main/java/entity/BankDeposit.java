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
public class BankDeposit {
    private String type;
    private String date;
    private String from;
    private double amount;
    
    public BankDeposit(String type, String date, String from, double amount){
        this.type = type;
        this.date = date;
        this.from = from;
        this.amount = amount;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public String getFrom() {
        return this.from;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
