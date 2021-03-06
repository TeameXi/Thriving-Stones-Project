/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Tutor {
    private int tutorId;
    private String nric;
    private String name;
    private int phone;
    private String address;
    private String qualification;
    private String birth_date;
    private String gender;
    private String email;
    private int branch_id;
    private double pay;
    private int totalClasses;
    private int payType;

    public Tutor() {
    }
    
    
    public Tutor(int tutorId,String name){
        this.tutorId = tutorId;
        this.name = name;
    }
    
    public Tutor(int tutorId,String name,double pay,int payType){
        this.tutorId = tutorId;
        this.name = name;
        this.pay = pay;
        this.payType = payType;
    }
    
    //Tutor Object With Class
    public Tutor(int totalClasses,int tutorId,String name){
        this.totalClasses = totalClasses;
        this.tutorId = tutorId;
        this.name = name;      
    }
    
    public Tutor(String nric,String name,int phone,String address,String qualification,String birth_date,String gender,String email,int branch_id/*, double pay*/) {
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.qualification = qualification;
        this.birth_date = birth_date;
        this.gender = gender;
        this.email = email;
        this.branch_id = branch_id;
        //this.pay = pay;
    }
    
    public Tutor(int tutorId,String nric,String name,int phone,String address,String qualification,String birth_date,String gender,String email,int branch_id/*, double pay*/) {
        this.tutorId = tutorId;
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.qualification = qualification;
        this.birth_date = birth_date;
        this.gender = gender;
        this.email = email;
        this.branch_id = branch_id;
        //this.pay = pay;
    }

    /**
     * @return the nric
     */
    public String getNric() {
        return nric;
    }

    /**
     * @param nric the nric to set
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public int getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

   
    /**
     * @return the birth_date
     */
    public String getBirth_date() {
        return birth_date;
    }

    /**
     * @param birth_date the birth_date to set
     */
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the branch_id
     */
    public int getBranch_id() {
        return branch_id;
    }

    /**
     * @param branch_id the branch_id to set
     */
    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    /**
     * @return the tutorId
     */
    public int getTutorId() {
        return tutorId;
    }

    /**
     * @param tutorId the tutorId to set
     */
    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }
    
    
    @Override
    public String toString(){
        return "NRIC : "+this.nric+", Name: "+this.name+",Address : "+this.address+",DOB :"+this.birth_date+", URL : "+this.qualification+", Email : "+this.email+", Branch : "+this.branch_id/*+", Hourly Rate : "+this.pay*/;
    }

    /**
     * @return the qualification
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * @return the totalClasses
     */
    public int getTotalClasses() {
        return totalClasses;
    }

    /**
     * @param totalClasses the totalClasses to set
     */
    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    /**
     * @return the payType
     */
    public int getPayType() {
        return payType;
    }

    /**
     * @param payType the payType to set
     */
    public void setPayType(int payType) {
        this.payType = payType;
    }


}
