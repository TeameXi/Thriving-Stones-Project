/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author Shawn
 */
public class Validation {

    /**
     * Method that validates the password format
     *
     * @param password password to validate
     * @return true if password is of valid length and if it does not contain
     * spacing, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return !password.contains(" ") && password.length() >= 8;
    }

    /**
     * Method that validates the phone number format
     *
     * @param phoneNo phone number to validate
     * @return true if phone number is of valid length and if it does not
     * contain spacing, false otherwise
     */
    public static boolean isValidPhoneNo(String phoneNo) {
        try {
            Integer.parseInt(phoneNo);
        } catch (NumberFormatException e) {
            return false;
        }
        return !phoneNo.contains(" ") && phoneNo.length() == 8;
    }

    /**
     * Method that validates the age format
     *
     * @param age age to validate
     * @return true if age is an integer false otherwise
     */
    public static boolean isValidAge(String ageToCheck) {
        try {
            int age = Integer.parseInt(ageToCheck);
            return (age >= 7 && age <= 70);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method that validates the email format
     *
     * @param email email to validate
     * @return true if email is of the valid format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        email = email.toLowerCase();
        return email.matches("[a-z0-9]+" + "(@steppingstones.com.sg)");
    }

    /**
     * Method that validates the gender format
     *
     * @param gender gender to validate
     * @return true if gender is M or F, false otherwise
     */
    public static boolean isValidGender(String gender) {
        if (gender.length() != 1) {
            return false;
        }
        return gender.charAt(0) == 'M' || gender.charAt(0) == 'F';
    }

    /**
     * Method that validates the ID format
     *
     * @param ID id to validate
     * @return true if ID is valid, false otherwise
     */
    public static boolean isValidID(String ID) {
        ID = ID.toUpperCase();
        return ID.matches("^[STFG]{1}" + "[0-9]{7}" + "[A-Z]{1}$");
    }

    public static boolean isValidName(String name) {
        return (name != null && !name.equals(""));
    }

    public static boolean isValidAmt(String amt) {
        try {
            Double.parseDouble(amt);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidLevel(String level) {
        return level.matches("(Pri|Sec)" + " " + "[1-6]");
    }

    public static boolean isValidAssessmentType(String assessmentType) {
        return assessmentType.matches("(ca1|sa1|ca2|sa2)");
    }

    public static boolean isValidSubject(String subject) {
        return subject.matches("(English|Maths|Science|Add-Maths|E-Maths|Chemistry|Biology|Physics)");
    }

    public static boolean isValidScore(String score) {
        int scr;
        try {
            scr = Integer.parseInt(score);
        } catch (NumberFormatException ex) {
            return false;
        }
        return scr >= 0 && scr <= 100;
    }

    public static ArrayList<String> validateStudent(String studentID, String studentName, String age, String gender, String lvl, String phone, String sub1, String sub2, String sub3) {
        ArrayList<String> errors = new ArrayList<>();

        if (!isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!isValidName(studentName)) {
            errors.add("Invalid Student Name!");
        }

        if (!isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!isValidLevel(lvl)) {
            errors.add("Please Select Academic Level!");
        }

        if (!isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        boolean invalidSubject = sub1.equals(sub2) || sub1.equals(sub3) || sub2.equals(sub3);
        if (invalidSubject) {
            errors.add("Please choose different subjects for most recent school result");
        }
        return errors;
    }

    public static ArrayList<String> validateStudentWithAmt(String studentID, String studentName, String age, String gender, String lvl, String phone, String reqAmt, String outstandingAmt) {
        ArrayList<String> errors = new ArrayList<>();

        if (!isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!isValidName(studentName)) {
            errors.add("Invalid Student Name!");
        }

        if (!isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!isValidLevel(lvl)) {
            errors.add("Please Select Academic Level!");
        }

        if (!isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!isValidAmt(reqAmt)) {
            errors.add("Invalid Required Amount!");
        }

        if (!isValidAmt(outstandingAmt)) {
            errors.add("Invalid Outsatnding Amount!");
        }

        return errors;
    }

    public static ArrayList<String> validateUpdateStudent(String studentID, String studentName, String age, String gender, String lvl, String phone, String reqAmt, String outstandingAmt) {
        ArrayList<String> errors = new ArrayList<>();

        if (!isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!isValidName(studentName)) {
            errors.add("Invalid Student Name!");
        }

        if (!isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!isValidLevel(lvl)) {
            errors.add("Please Select Academic Level!");
        }

        if (!isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!isValidAmt(reqAmt)) {
            errors.add("Invalid Required Amount!");
        }

        if (!isValidAmt(outstandingAmt)) {
            errors.add("Invalid Outstanding Amount!");
        }

        return errors;
    }

    public static ArrayList<String> validateNewTutor(String tutorID, String name, String age, String phone, String gender, String email, String password) {
        ArrayList<String> errors = new ArrayList<>();

        if (!isValidID(tutorID)) {
            errors.add("Invalid Tutor ID!");
        }

        if (!isValidName(name)) {
            errors.add("Invalid Tutor Name!");
        }

        if (!isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!isValidEmail(email)) {
            errors.add("Invalid Email!");
        }

        if (!isValidPassword(password)) {
            errors.add("Invalid Password!");
        }

        return errors;
    }

    public static ArrayList<String> validateUpdateTutor(String tutorID, String name, String age, String phone, String gender, String email) {
        ArrayList<String> errors = new ArrayList<>();

        if (!isValidID(tutorID)) {
            errors.add("Invalid Tutor ID!");
        }

        if (!isValidName(name)) {
            errors.add("Invalid Tutor Name!");
        }

        if (!isValidAge(age)) {
            errors.add("Invalid Age!");
        }

        if (!isValidGender(gender)) {
            errors.add("Please Select Gender!");
        }

        if (!isValidPhoneNo(phone)) {
            errors.add("Invalid Phone Number!");
        }

        if (!isValidEmail(email)) {
            errors.add("Invalid Email!");
        }

        return errors;
    }

    public static ArrayList<String> validateCreateGrade(String studentID, String location, String sub, String assessmentType, String grade) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!location.equals("School") && !location.equals("Center")) {
            errors.add("Please Select Location!");
        }
        
        if (!Validation.isValidSubject(sub)) {
            errors.add("Please Select Subject!");
        }

        if (!Validation.isValidAssessmentType(assessmentType)) {
            errors.add("Please Select Assessement Type!");
        }

        if (!Validation.isValidScore(grade)) {
            errors.add("Invalid Grade!");
        }
        return errors;
    }
    
    public static ArrayList<String> validateUpdateGrade(String studentID, String sub, String assessmentType, String grade) {
        ArrayList<String> errors = new ArrayList<>();

        if (!Validation.isValidID(studentID)) {
            errors.add("Invalid Student ID!");
        }

        if (!Validation.isValidSubject(sub)) {
            errors.add("Please Select Subject!");
        }

        if (!Validation.isValidAssessmentType(assessmentType)) {
            errors.add("Please Select Assessement Type!");
        }

        if (!Validation.isValidScore(grade)) {
            errors.add("Invalid Grade!");
        }
        return errors;
    }
}
