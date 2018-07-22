/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.Validation;
import java.util.ArrayList;

/**
 *
 * @author DEYU
 */
public class Validator {

    public static ArrayList<String> validateStudent(String studentID, String studentName, int age, String gender, String lvl, String phone, String sub1, String sub2, String sub3) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }
        
        if (!Validation.isValidID(studentName)) {
            errors.add("Invalid Student Name!");
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

    public static ArrayList<String> validateStudentWithAmt(String studentID, String studentName, int age, String gender, String lvl, String phone, String reqAmt, String outstandingAmt) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }
        
        if (!Validation.isValidID(studentName)) {
            errors.add("Invalid Student Name!");
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

    public static ArrayList<String> validateNewTutor(String tutorID, String name, int age, String phone, String gender, String email, String password) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(tutorID)) {
            errors.add("Invalid Tutor ID!");
        }

        if (!Validation.isValidID(name)) {
            errors.add("Invalid Tutor Name!");
        }
        
        if (!Validation.isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!Validation.isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!Validation.isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!Validation.isValidEmail(email)) {
            errors.add("Invalid Email!");
        }

        if (!Validation.isValidAmt(password)) {
            errors.add("Invalid Password!");
        }

        return errors;
    }
    
    public static ArrayList<String> validateUpdateTutor(String tutorID, String name, int age, String phone, String gender, String email) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(tutorID)) {
            errors.add("Invalid Tutor ID!");
        }

        if (!Validation.isValidID(name)) {
            errors.add("Invalid Tutor Name!");
        }
        
        if (!Validation.isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!Validation.isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!Validation.isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!Validation.isValidEmail(email)) {
            errors.add("Invalid Email!");
        }

        return errors;
    }
}
