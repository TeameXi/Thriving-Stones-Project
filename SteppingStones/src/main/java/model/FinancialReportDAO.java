/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Account;
import entity.BankDeposit;
import entity.Expense;
import entity.Revenue;
import entity.Student;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DEYU
 */
public class FinancialReportDAO {
    public static XSSFCellStyle styleBoldBorder(XSSFWorkbook workbook){
        XSSFCellStyle styleBold = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setFontName("Times New Roman");
        fontBold.setFontHeightInPoints((short)12);
        fontBold.setBold(true);
        styleBold.setFont(fontBold);
        styleBold.setBorderBottom(BorderStyle.MEDIUM);
        styleBold.setBorderTop(BorderStyle.MEDIUM); 
        styleBold.setBorderRight(BorderStyle.THIN); 
        styleBold.setBorderLeft(BorderStyle.THIN);
        
        return styleBold;
    }
    
    public static XSSFCellStyle styleBorder(XSSFWorkbook workbook){
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN); 
        style.setBorderRight(BorderStyle.THIN); 
        style.setBorderLeft(BorderStyle.THIN);
        
        return style;
    }
    
    public static XSSFCellStyle styleTitleUnderLine(XSSFWorkbook workbook){
        XSSFCellStyle titleUnderline = workbook.createCellStyle();
        XSSFFont fontUnderlineBold = workbook.createFont();
        fontUnderlineBold.setFontName("Times New Roman");
        fontUnderlineBold.setFontHeightInPoints((short)12);
        fontUnderlineBold.setUnderline(FontUnderline.SINGLE);
        fontUnderlineBold.setBold(true);
        titleUnderline.setFont(fontUnderlineBold);
        
        return titleUnderline;
    }
    
    public static XSSFCellStyle styleTitleBold(XSSFWorkbook workbook){
        XSSFCellStyle titleBold = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setFontName("Times New Roman");
        fontBold.setFontHeightInPoints((short)14);
        fontBold.setBold(true);
        titleBold.setFont(fontBold);
        
        return titleBold;
    }
    
    public static void revenueCellTitle(Row header, XSSFCellStyle styleBold){
        header.createCell(0).setCellValue("No.");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("No. of Lesson");
        header.createCell(3).setCellValue("Student Count");
        header.createCell(4).setCellValue("Payment Date");
        header.createCell(5).setCellValue("Monthly Fee($)");
        header.createCell(6).setCellValue("Reg. Fee($)");
        header.createCell(7).setCellValue("Deposit ($)");
        for(int i = 0; i < 8; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void expenseCellTitle(Row header, XSSFCellStyle styleBold){
        header.createCell(0).setCellValue("No.");
        header.createCell(1).setCellValue("Items");
        header.createCell(2).setCellValue("Amount($)");
        header.createCell(3).setCellValue("Date");
        for(int i = 0; i < 4; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void studentDepositCellTitle(Row header, XSSFCellStyle styleBold){
        header.createCell(0).setCellValue("No.");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Amount($)");
        header.createCell(3).setCellValue("Student no.");
        header.createCell(4).setCellValue("Date of Payment");
        header.createCell(5).setCellValue("Activation Month");
        for(int i = 0; i < 6; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void summaryDeposit(Row header, XSSFCellStyle styleBold){
        header.createCell(6).setCellValue("No.");
        header.createCell(7).setCellValue("Level");
        header.createCell(8).setCellValue("Student Count");
        header.createCell(9).setCellValue("Amount ($)");
        for(int i = 6; i < 10; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void balanceSheetRevenue(Row header, XSSFCellStyle styleBold){
        header.createCell(0).setCellValue("No.");
        header.createCell(1).setCellValue("Level");
        header.createCell(2).setCellValue("Student Count");
        header.createCell(3).setCellValue("Revenue ($)");
        header.createCell(4).setCellValue("Reg Fees ($)");
        header.createCell(5).setCellValue("Deposit ($)");
        for(int i = 0; i < 6; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void balanceSheetProfitLoss(Row header, XSSFCellStyle styleBold){
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Amount ($)");
        for(int i = 1; i < 3; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void balanceSheetBankDeposit(Row header, XSSFCellStyle styleBold){
        header.createCell(7).setCellValue("No.");
        header.createCell(8).setCellValue("Cash / Cheque / Bank Transfer");
        header.createCell(9).setCellValue("Date");
        header.createCell(10).setCellValue("From");
        header.createCell(11).setCellValue("Amount ($)");
        for(int i = 7; i < 12; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static void balanceSheetBankStatement(Row header, XSSFCellStyle styleBold, String accountNum){
        header.createCell(8).setCellValue("ACCT NO: " + accountNum);
        header.createCell(9).setCellValue("Amount ($)");
        for(int i = 8; i < 10; i++){
            header.getCell(i).setCellStyle(styleBold);
        }
    }
    
    public static HashMap<String, Account> revenue(XSSFSheet revenue, HashMap<String, ArrayList<Revenue>> data, 
            XSSFCellStyle styleTitleBold, XSSFCellStyle styleTitleUnderLine, XSSFCellStyle styleBold, XSSFCellStyle style){
        int revenueRowNum = 0;
        Row title = revenue.createRow(revenueRowNum);
        title.createCell(3).setCellValue("REVENUE");
        title.getCell(3).setCellStyle(styleTitleBold);
        
        HashMap<String, Account> revenues = new HashMap<>();
        Row revenueRow;
        Set<String> keys = data.keySet();
        for(String key: keys){
            revenueRowNum+=2;
            title = revenue.createRow(revenueRowNum);
            title.createCell(1).setCellValue(key);
            title.getCell(1).setCellStyle(styleTitleUnderLine);
            
            revenueRowNum+=2;
            Row header = revenue.createRow(revenueRowNum);
            revenueCellTitle(header, styleBold);
            ArrayList<Revenue> rev = data.get(key);
            
            double monthlyFees = 0;
            double regFees = 0;
            double deposit = 0;
            revenueRowNum++;
            for (int i = 0; i < rev.size(); i++) {
                revenueRow = revenue.createRow(revenueRowNum);
                int revenueColNum = 0;
                Cell cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(i+1);
                
                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(rev.get(i).getStudentName());
                
                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(rev.get(i).getNoOfLessons());

                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(1);

                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(rev.get(i).getpaymentDate());
                
                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(rev.get(i).getMonthlyFees());
                monthlyFees += rev.get(i).getMonthlyFees();
                
                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(rev.get(i).getRegFees());
                regFees += rev.get(i).getRegFees();
                
                cell = revenueRow.createCell(revenueColNum++);
                cell.setCellValue(rev.get(i).getDeposit());
                deposit += rev.get(i).getDeposit();

                for(int j = 0; j < 8; j++){
                    revenueRow.getCell(j).setCellStyle(style);
                }
                revenueRowNum++;
            }
            revenueRow = revenue.createRow(revenueRowNum);
            for(int j = 0; j < 8; j++){
                Cell cell = revenueRow.createCell(j);
                if(j == 2){
                    cell.setCellValue("Total");
                }else if(j == 3){
                    cell.setCellValue(rev.size());
                }else if(j == 5){
                    cell.setCellValue(monthlyFees);
                }else if(j == 6){
                    cell.setCellValue(regFees);
                }else if(j == 7){
                    cell.setCellValue(deposit);
                }
                cell.setCellStyle(styleBold);
            }
            revenueRowNum++;
            
            Account acc = new Account(rev.size(), monthlyFees, regFees, deposit);
            revenues.put(key, acc);
        } 
        return revenues;
    }
    
    public static HashMap<String, Double> expense(XSSFSheet expense, HashMap<String, ArrayList<Expense>> data, 
            XSSFCellStyle styleTitleBold, XSSFCellStyle styleTitleUnderLine, XSSFCellStyle styleBold, XSSFCellStyle style){
        int expenseRowNum = 0;
        Row title = expense.createRow(expenseRowNum);
        title.createCell(1).setCellValue("EXPENSES");
        title.getCell(1).setCellStyle(styleTitleBold);
        
        HashMap<String, Double> expenseData = new HashMap<>();
        Row expenseRow;
        Set<String> keys = data.keySet();
        double totalExpense = 0;
        for(String key: keys){
            expenseRowNum+=2;
            title = expense.createRow(expenseRowNum);
            title.createCell(1).setCellValue(key);
            title.getCell(1).setCellStyle(styleTitleUnderLine);
            
            expenseRowNum+=2;
            Row header = expense.createRow(expenseRowNum);
            expenseCellTitle(header, styleBold);
            
            ArrayList<Expense> exp = data.get(key);
            double amount = 0;
            expenseRowNum++;
            for (int i = 0; i < exp.size(); i++) {
                expenseRow = expense.createRow(expenseRowNum);
                int expenseColNum = 0;
                Cell cell = expenseRow.createCell(expenseColNum++);
                cell.setCellValue(i+1);
                
                cell = expenseRow.createCell(expenseColNum++);
                cell.setCellValue(exp.get(i).getItem());
                
                cell = expenseRow.createCell(expenseColNum++);
                cell.setCellValue(exp.get(i).getAmount());
                amount += exp.get(i).getAmount();
                
                cell = expenseRow.createCell(expenseColNum++);
                cell.setCellValue(exp.get(i).getDate());
                
                for(int j = 0; j < 4; j++){
                    expenseRow.getCell(j).setCellStyle(style);
                }
                expenseRowNum++;
            }
            expenseRow = expense.createRow(expenseRowNum);

            for(int j = 0; j < 4; j++){
                Cell cell = expenseRow.createCell(j);
                if(j == 1){
                    cell.setCellValue("Total");
                }else if(j == 2){
                    cell.setCellValue(amount);
                    totalExpense += amount;
                }
                cell.setCellStyle(styleBold);
            }
            expenseRowNum++;
            if(key.equals("Bank Expenses")){
                expenseData.put(key, amount);
            }else if(key.equals("Teachers' Salary")){
                expenseData.put(key, amount);
            }
        } 
        expenseRowNum++;
        title = expense.createRow(expenseRowNum);
        title.createCell(1).setCellValue("Total Expenses");
        title.getCell(1).setCellStyle(styleTitleBold);
        title.createCell(2).setCellValue(totalExpense);
        title.getCell(2).setCellStyle(styleTitleBold);
        expenseData.put("Total Expenses", totalExpense);
        
        return expenseData;
    }
    
     public static void deposit(XSSFSheet studentDeposit, HashMap<String, ArrayList<Revenue>> data, 
            XSSFCellStyle styleTitleBold, XSSFCellStyle styleTitleUnderLine, XSSFCellStyle styleBold, XSSFCellStyle style){
        int depositRowNum = 0;
        Row title = studentDeposit.createRow(depositRowNum);
        title.createCell(2).setCellValue("DEPOSITS");
        title.getCell(2).setCellStyle(styleTitleBold);
        
        HashMap<String, Account> deposits = new HashMap<>();
        Row depositRow;
        Set<String> keys = data.keySet();
        for(String key: keys){
            depositRowNum+=2;
            title = studentDeposit.createRow(depositRowNum);
            title.createCell(1).setCellValue(key);
            title.getCell(1).setCellStyle(styleTitleUnderLine);
            
            depositRowNum+=2;
            Row header = studentDeposit.createRow(depositRowNum);
            studentDepositCellTitle(header, styleBold);
            
            ArrayList<Revenue> rev = data.get(key);
            double deposit = 0;
            depositRowNum++;
            for (int i = 0; i < rev.size(); i++) {
                depositRow = studentDeposit.createRow(depositRowNum);
                int depositColNum = 0;
                Cell cell = depositRow.createCell(depositColNum++);
                cell.setCellValue(i+1);
                
                cell = depositRow.createCell(depositColNum++);
                cell.setCellValue(rev.get(i).getStudentName());
                
                cell = depositRow.createCell(depositColNum++);
                cell.setCellValue(rev.get(i).getDeposit());
                deposit += rev.get(i).getDeposit();
                
                cell = depositRow.createCell(depositColNum++);
                cell.setCellValue(1);
                
                cell = depositRow.createCell(depositColNum++);
                cell.setCellValue(rev.get(i).getDepositPaymentDate());
                
                cell = depositRow.createCell(depositColNum++);
                cell.setCellValue(rev.get(i).getDepositActivationDate());
                
                for(int j = 0; j < 6; j++){
                    depositRow.getCell(j).setCellStyle(style);
                }
                depositRowNum++;
            }
            depositRow = studentDeposit.createRow(depositRowNum);

            for(int j = 0; j < 6; j++){
                Cell cell = depositRow.createCell(j);
                if(j == 1){
                    cell.setCellValue("Total");
                }else if(j == 2){
                    cell.setCellValue(deposit);
                }else if(j == 3){
                    cell.setCellValue(rev.size());
                }
                cell.setCellStyle(styleBold);
            }
            depositRowNum++;
            
            Account acc = new Account(rev.size(), deposit);
            deposits.put(key, acc);
        } 
        
        depositRowNum = 2;
        if(studentDeposit.getRow(depositRowNum) != null){
            title = studentDeposit.getRow(depositRowNum);
        }else{
            title = studentDeposit.createRow(depositRowNum);
        }
        title.createCell(7).setCellValue("Summary of Deposit");
        title.getCell(7).setCellStyle(styleTitleBold);
        
        depositRowNum+=2;
        Row header;
        if(studentDeposit.getRow(depositRowNum) != null){
            header = studentDeposit.getRow(depositRowNum);
        }else{
            header = studentDeposit.createRow(depositRowNum);
        }
        summaryDeposit(header, styleBold);
        
        int studentCount = 0;
        double finalAmt = 0;
        Set<String> depositKeys = deposits.keySet();
        int num = 1;
        for(String key: depositKeys){
            depositRowNum++;
            int depositColNum = 6;
            Account acc = deposits.get(key);
            if(studentDeposit.getRow(depositRowNum) != null){
                depositRow = studentDeposit.getRow(depositRowNum);
            }else{
                depositRow = studentDeposit.createRow(depositRowNum);
            }
            
            Cell cell = depositRow.createCell(depositColNum++);
            cell.setCellValue(num++);
            
            cell = depositRow.createCell(depositColNum++);
            cell.setCellValue(key);

            cell = depositRow.createCell(depositColNum++);
            cell.setCellValue(acc.getStudentCount());
            studentCount += acc.getStudentCount();
            
            cell = depositRow.createCell(depositColNum++);
            cell.setCellValue(acc.getAmount());
            finalAmt += acc.getAmount();
            
            for(int j = 6; j < 10; j++){
                depositRow.getCell(j).setCellStyle(style);
            }
        }
        depositRowNum++;
        if(studentDeposit.getRow(depositRowNum) != null){
            depositRow = studentDeposit.getRow(depositRowNum);
        }else{
            depositRow = studentDeposit.createRow(depositRowNum);
        }
        for(int j = 6; j < 10; j++){
            Cell cell = depositRow.createCell(j);
            if(j == 7){
                cell.setCellValue("Final Amount");
            }else if(j == 8){
                cell.setCellValue(studentCount);
            }else if(j == 9){
                cell.setCellValue(finalAmt);
            }
            cell.setCellStyle(styleBold);
        }
    }
     
    public static void balanceSheet(XSSFSheet balanceSheet, HashMap<String, Account> revenues, HashMap<String, Double> expenses, ArrayList<BankDeposit> bankDeposit, String accountNum,
            double openingBalance, XSSFCellStyle styleTitleBold, XSSFCellStyle styleTitleUnderLine, XSSFCellStyle styleBold, XSSFCellStyle style){
        int balanceSheetRowNum = 0;
        Row title = balanceSheet.createRow(balanceSheetRowNum);
        title.createCell(3).setCellValue("Balance Sheet");
        title.getCell(3).setCellStyle(styleTitleBold);
        
        balanceSheetRowNum+=2;
        title = balanceSheet.createRow(balanceSheetRowNum);
        title.createCell(1).setCellValue("Revenue");
        title.getCell(1).setCellStyle(styleTitleUnderLine);

        balanceSheetRowNum+=2;
        Row header = balanceSheet.createRow(balanceSheetRowNum);
        balanceSheetRevenue(header, styleBold);
 
        Row balanceSheetRow;
        Set<String> keys = revenues.keySet();
        int num = 1;
        int studentCount = 0;
        double monthlyFees = 0;
        double regFees = 0;
        double deposit = 0;
        for(String key: keys){
            Account acc = revenues.get(key);
            balanceSheetRowNum++;
            balanceSheetRow = balanceSheet.createRow(balanceSheetRowNum);
            int balanceSheetColNum = 0;
            Cell cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(num++);
            
            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(key);

            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(acc.getStudentCount());
            studentCount += acc.getStudentCount();
            
            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(acc.getMonthlyFees());
            monthlyFees += acc.getMonthlyFees();

            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(acc.getRegFees());
            regFees += acc.getRegFees();

            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(acc.getDeposit());
            deposit += acc.getDeposit();

            for(int j = 0; j < 6; j++){
                balanceSheetRow.getCell(j).setCellStyle(style);
            }
        } 
        balanceSheetRowNum++;
        balanceSheetRow = balanceSheet.createRow(balanceSheetRowNum);
        for(int j = 0; j < 6; j++){
            Cell cell = balanceSheetRow.createCell(j);
            if(j == 1){
                cell.setCellValue("Total");
            }else if(j == 2){
                cell.setCellValue(studentCount);
            }else if(j == 3){
                cell.setCellValue(monthlyFees);
            }else if(j == 4){
                cell.setCellValue(regFees);
            }else if(j == 5){
                cell.setCellValue(deposit);
            }
            cell.setCellStyle(styleBold);
        }
        balanceSheetRowNum+=2;
        title = balanceSheet.createRow(balanceSheetRowNum);
        title.createCell(1).setCellValue("Profit & Loss Statement");
        title.getCell(1).setCellStyle(styleTitleUnderLine);

        balanceSheetRowNum+=2;
        header = balanceSheet.createRow(balanceSheetRowNum);
        balanceSheetProfitLoss(header, styleBold);
        
        balanceSheetRowNum++;
        title = balanceSheet.createRow(balanceSheetRowNum);
        title.createCell(1).setCellValue("Total Revenue");
        title.getCell(1).setCellStyle(style);
        title.createCell(2).setCellValue(monthlyFees);
        title.getCell(2).setCellStyle(style);
        
        balanceSheetRowNum++;
        title = balanceSheet.createRow(balanceSheetRowNum);
        title.createCell(1).setCellValue("Total Expenses");
        title.getCell(1).setCellStyle(style);
        title.createCell(2).setCellValue(expenses.get("Total Expenses"));
        title.getCell(2).setCellStyle(style);
        
        balanceSheetRowNum++;
        title = balanceSheet.createRow(balanceSheetRowNum);
        title.createCell(1).setCellValue("Net Profit / (Loss)");
        title.getCell(1).setCellStyle(styleBold);
        title.createCell(2).setCellValue(monthlyFees - expenses.get("Total Expenses"));
        title.getCell(2).setCellStyle(styleBold);
        
        balanceSheetRowNum = 0;
        title = balanceSheet.getRow(balanceSheetRowNum);
        title.createCell(8).setCellValue("BANK DEPOSITS");
        title.getCell(8).setCellStyle(styleTitleUnderLine);
        
        balanceSheetRowNum+=2;
        header = balanceSheet.getRow(balanceSheetRowNum);
        balanceSheetBankDeposit(header, styleBold);
         
        int totalBankDeposit = 0;
        for(int i = 0; i < bankDeposit.size(); i++){
            balanceSheetRowNum++;
            int balanceSheetColNum = 7;
            BankDeposit bDeposit = bankDeposit.get(i);
            if(balanceSheet.getRow(balanceSheetRowNum) != null){
                balanceSheetRow = balanceSheet.getRow(balanceSheetRowNum);
            }else{
                balanceSheetRow = balanceSheet.createRow(balanceSheetRowNum);
            }

            Cell cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(i + 1);
            
            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(bDeposit.getType());
            
            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(bDeposit.getDate());
            
            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(bDeposit.getFrom());
            
            cell = balanceSheetRow.createCell(balanceSheetColNum++);
            cell.setCellValue(bDeposit.getAmount());
            totalBankDeposit += bDeposit.getAmount();
            
            for(int j = 7; j < 12; j++){
                balanceSheetRow.getCell(j).setCellStyle(style);
            }
        }
        
        balanceSheetRowNum++;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            balanceSheetRow = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            balanceSheetRow = balanceSheet.createRow(balanceSheetRowNum);
        }
        for(int j = 7; j < 12; j++){
            Cell cell = balanceSheetRow.createCell(j);
            if(j == 8){
                cell.setCellValue("Total");
            }else if(j == 11){
                cell.setCellValue(totalBankDeposit);
            }
            cell.setCellStyle(styleBold);
        }
        
        balanceSheetRowNum+=3;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            title = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            title = balanceSheet.getRow(balanceSheetRowNum);
        }
        title.createCell(8).setCellValue("Bank Statement");
        title.getCell(8).setCellStyle(styleTitleUnderLine);
        
        balanceSheetRowNum+=2;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            header = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            header = balanceSheet.createRow(balanceSheetRowNum);
        }
        balanceSheetBankStatement(header, styleBold, accountNum);
        
        balanceSheetRowNum++;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            title = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            title = balanceSheet.createRow(balanceSheetRowNum);
        }
        title.createCell(8).setCellValue("Opening Balance");
        title.getCell(8).setCellStyle(style);
        title.createCell(9).setCellValue(openingBalance); 
        title.getCell(9).setCellStyle(style);
        
        balanceSheetRowNum++;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            title = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            title = balanceSheet.createRow(balanceSheetRowNum);
        }
        title.createCell(8).setCellValue("Add: Deposit");
        title.getCell(8).setCellStyle(style);
        title.createCell(9).setCellValue(totalBankDeposit);
        title.getCell(9).setCellStyle(style);
        
        balanceSheetRowNum++;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            title = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            title = balanceSheet.createRow(balanceSheetRowNum);
        }
        double bankExpense = expenses.get("Teachers' Salary") + expenses.get("Bank Expenses");
        title.createCell(8).setCellValue("Less: Bank Expense");
        title.getCell(8).setCellStyle(style);
        title.createCell(9).setCellValue(bankExpense);
        title.getCell(9).setCellStyle(style);
        
        balanceSheetRowNum++;
        if(balanceSheet.getRow(balanceSheetRowNum) != null){
            title = balanceSheet.getRow(balanceSheetRowNum);
        }else{
            title = balanceSheet.createRow(balanceSheetRowNum);
        }
        title.createCell(8).setCellValue("Closing Balance");
        title.getCell(8).setCellStyle(styleBold);
        title.createCell(9).setCellValue(openingBalance + totalBankDeposit - bankExpense);
        title.getCell(9).setCellStyle(styleBold);
    }
    
    public static void FinancialReportGeneration(String filename, HashMap<String, ArrayList<Revenue>> revenueData, HashMap<String, ArrayList<Expense>> expenseData, 
            ArrayList<BankDeposit> bankDeposit, String accountNum, double openingBalance){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet revenue = workbook.createSheet("Revenue");
        XSSFSheet expense = workbook.createSheet("Expenses");
        XSSFSheet studentDeposit = workbook.createSheet("Students Deposits");
        XSSFSheet balanceSheet = workbook.createSheet("Balance Sheet");
        
        XSSFCellStyle styleBold = styleBoldBorder(workbook);
        XSSFCellStyle style = styleBorder(workbook);
        XSSFCellStyle styleTitleUnderLine = styleTitleUnderLine(workbook);
        XSSFCellStyle styleTitleBold = styleTitleBold(workbook);
        //System.out.println("Creating excel");
        HashMap<String, Account> revenues = revenue(revenue, revenueData, styleTitleBold, styleTitleUnderLine, styleBold, style);
        HashMap<String, Double> expenses = expense(expense, expenseData, styleTitleBold, styleTitleUnderLine, styleBold, style);
        deposit(studentDeposit, revenueData, styleTitleBold, styleTitleUnderLine, styleBold, style);
        balanceSheet(balanceSheet, revenues, expenses, bankDeposit, accountNum, openingBalance, styleTitleBold, styleTitleUnderLine, styleBold, style);
        
        for(int i = 0; i < 30; i++){
            revenue.autoSizeColumn(i);
            expense.autoSizeColumn(i);
            studentDeposit.autoSizeColumn(i);
            balanceSheet.autoSizeColumn(i);
            
        }
        
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }

        System.out.println("Done");
    }
}
