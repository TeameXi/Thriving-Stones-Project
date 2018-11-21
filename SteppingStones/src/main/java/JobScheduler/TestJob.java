/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobScheduler;

import model.SendSMS;

/**
 *
 * @author Shawn
 */
public class TestJob implements Runnable {

    public String a;

    public TestJob(String a) {
        this.a = a;
    }

    @Override
    public void run() {

        SendSMS.sendingSMS("+6592369747", "Please be reminded to submit your child's tuition fees to Stepping Stones Learning Centre during the next lesson.");
        System.out.println("Test");
    }
}
