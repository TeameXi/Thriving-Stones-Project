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
    private double amount;
    private String date;
    private int tutorID;
    private String description;
    private int paymentID;
    
    public Expense(int tutorID, String description, double amount, String date){
        this.tutorID = tutorID;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Expense(double amount, String date, int tutorID, String description, int paymentID) {
        this.amount = amount;
        this.date = date;
        this.tutorID = tutorID;
        this.description = description;
        this.paymentID = paymentID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    
    
    public double getAmount(){
        return this.amount;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public int getTutorID(){
        return this.tutorID;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
