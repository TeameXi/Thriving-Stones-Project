/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Validation;
import java.util.ArrayList;

/**
 *
 * @author DEYU
 */
public class Validator {

    public static ArrayList<String> validateStudent(String studentID, int age, String gender, String lvl, String phone, String sub1, String sub2, String sub3) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!Validation.isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!Validation.isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!Validation.isValidLevel(lvl)) {
            errors.add("Please Select Academic Level!");
        }

        if (!Validation.isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        boolean invalidSubject = sub1.equals(sub2) || sub1.equals(sub3) || sub2.equals(sub3);
        if (invalidSubject) {
            errors.add("Please choose different subjects for most recent school result");
        }
        return errors;
    }

    public static ArrayList<String> validateStudentWithAmt(String studentID, int age, String gender, String lvl, String phone, String reqAmt, String outstandingAmt) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!Validation.isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!Validation.isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!Validation.isValidLevel(lvl)) {
            errors.add("Please Select Academic Level!");
        }

        if (!Validation.isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!Validation.isValidAmt(reqAmt)) {
            errors.add("Invalid Required Amount!");
        }

        if (!Validation.isValidAmt(outstandingAmt)) {
            errors.add("Invalid Outsatnding Amount!");
        }

        return errors;
    }

    /*
    public static boolean idCheck(String ID){
        char arrayID[] = new char[9];
        int arrayWeight[] = new int [7];
        int totalWeight = 0;
        char lastChar = 0;
        int index = 0;
        
        if(ID.length() != 9){
            return false;
        }
        for(int i = 0; i < 9; i++){
            arrayID[i] = ID.charAt(i);        
        }
        
        arrayWeight[0] = Character.getNumericValue(arrayID[1]) * 2;
        arrayWeight[1] = Character.getNumericValue(arrayID[2]) * 7;
        arrayWeight[2] = Character.getNumericValue(arrayID[3]) * 6;
        arrayWeight[3] = Character.getNumericValue(arrayID[4]) * 5;
        arrayWeight[4] = Character.getNumericValue(arrayID[5]) * 4;
        arrayWeight[5] = Character.getNumericValue(arrayID[6]) * 3;
        arrayWeight[6] = Character.getNumericValue(arrayID[7]) * 2;
        
        for(int i = 0; i < 7; i++){
            totalWeight += arrayWeight[i];
        }        
        if(arrayID[0] == 'T' || arrayID[0] == 'G'){
            totalWeight += 4;
        }
        index = totalWeight % 11;
        char[] st = { 'J','Z','I','H','G','F','E','D','C','B','A' };
        char[] fg = { 'X','W','U','T','R','Q','P','N','M','L','K' };
        if(arrayID[0] == 'S' || arrayID[0] == 'T'){
            lastChar = st[index];
        }else if(arrayID[0] == 'F' || arrayID[0] == 'G'){
            lastChar = fg[index];
        }
        return (arrayID[8] == lastChar);
    }
   
    public static boolean phoneCheck(String phone){
        if(phone.length() != 8){
            return false;
        }
        try{
            Integer.parseInt(phone);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
     
    public static boolean amtCheck(String amt) {
        try {
            Double.parseDouble(amt);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
     */
}
