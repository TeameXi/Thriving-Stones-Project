/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobScheduler;

import entity.BankDeposit;
import entity.Deposit;
import entity.Expense;
import entity.Revenue;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import model.FinancialReportDAO;
import model.PaymentDAO;
import org.joda.time.DateTime;

/**
 *
 * @author Riana
 */
public class FinancialReportJob implements Runnable {
    public String a;
    
    public FinancialReportJob(String a){
        this.a = a;
    }
    @Override
    public void run() {
        Calendar mCalendar = Calendar.getInstance();    
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + "_" + mCalendar.get(Calendar.YEAR);
        
        //remove this if when debugging
        if(mCalendar.get(Calendar.DATE) != 1){
            return;
        }
        
        String temp = a;
        
        File file = new File(temp);
        if (!file.exists()) {
            file.mkdir();
        }
        
        String AccountExcel = temp + File.separator + "Account_" + month + ".xlsx";
        HashMap<String, ArrayList<Revenue>> revenue = PaymentDAO.retrieveAllRevenueData();
        HashMap<String, ArrayList<Deposit>> deposit = PaymentDAO.retrieveAllDepositData();
        HashMap<String, ArrayList<Expense>> expense = PaymentDAO.retrieveAllExpenseData();   
        ArrayList<BankDeposit> bankDeposit = PaymentDAO.retrieveAllBankDepositData();
        
        FinancialReportDAO.FinancialReportGeneration(AccountExcel,revenue, deposit, expense, bankDeposit, "010-91303-9", 0);
    }

}
