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
public class Expense {
    private String item;
    private double amount;
    private String date;
    
    public Expense(String item, double amount, String date){
        this.item = item;
        this.amount = amount;
        this.date = date;
    }
    
    public String getItem() {
        return this.item;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public void setItem(String item) {
        this.item = item;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
}
