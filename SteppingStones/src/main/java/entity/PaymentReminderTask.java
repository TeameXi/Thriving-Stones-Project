/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import model.PaymentDAO;
import model.SendSMS;

/**
 *
 * @author Xin
 */
public class PaymentReminderTask implements Runnable{
    private String date;
    private int classID;
    private boolean late;

    public PaymentReminderTask(String date, int classId, boolean late){
        this.date = date;
        this.classID = classId;
        this.late = late;
    }
    
    @Override
    public void run() {
        PaymentDAO payment = new PaymentDAO();
        ArrayList<String> contacts = new ArrayList<>();
        String msg = "";
        
        if(late){
            contacts = payment.retrieveOverduePayments(classID);
            msg = "Payment for Stepping Stones is due tomorrow!";
        } else {
            contacts = payment.retrieveContactsPayment(classID);
            msg = "Please be reminded to settle payment with Stepping Stones!";
        }
        
        for(String num: contacts){
            num = "+65" + num;
            SendSMS.sendingSMS(num, msg);
        }
    }
    
}
