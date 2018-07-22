/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
     * @return true if age is an integer
     * false otherwise
     */
    public static boolean isValidAge(int age) {
        return (age >= 7 && age <= 70);
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

    public static boolean isValidName(String name){
        return (name != null && !name.equals(""));
    }
    
    public static boolean isValidAmt(String amt){
        try {
            Double.parseDouble(amt);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public static boolean isValidLevel(String level){
        return level.matches("(Pri|Sec)" + " " + "[1-6]");
    }
    
}
