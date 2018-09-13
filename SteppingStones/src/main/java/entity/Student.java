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
public class Student {
    private int studentID;
    private String studentNRIC;
    private String name;
    private String BOD;
    private String gender;
    private String level;
    private int branch_id;
    private int phone;
    private String address;
    private String email;
    private double reqAmt;
    private double outstandingAmt;
                  
    public Student(int studentID, String studentNRIC, String name, String BOD, String gender, String level, int branch_id, int phone, String address, String email, double reqAmt, double outstandingAmt){
        this.studentID = studentID;
        this.studentNRIC = studentNRIC;
        this.name = name;
        this.BOD = BOD;
        this.gender = gender;
        this.level = level;
        this.branch_id = branch_id;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.reqAmt = reqAmt;
        this.outstandingAmt = outstandingAmt;
    }

    public Student(int studentID, String name, String studentNRIC, String email) {
        this.studentID = studentID;
        this.studentNRIC = studentNRIC;
        this.name = name;
        this.email = email;
    }

    
    
    public int getStudentID() {
        return studentID;
    }
    
    public void setStudentID(int newStudentID){
        this.studentID = newStudentID;
    }
    
    public String getStudentNRIC() {
        return studentNRIC;
    }
    
    public void setStudentNRIC(String newStudentNRIC){
        this.studentNRIC = newStudentNRIC;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String newStudentName){
        this.name = newStudentName;
    }
    
    public String getBOD(){
        return BOD;
    }
    
    public void setBOD(String newBOD){
        this.BOD = newBOD;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getLevel(){
        return level;
    }
    
    public void setLevel(String newLevel){
        this.level = newLevel;
    }
    
    public int getBranchlID(){
        return branch_id;
    }

    public String getAddress(){
        return address;
    }
    
    public void setAddress(String newAddress){
        this.address = newAddress;
    }
    
    public int getPhone(){
        return phone;
    }
    
    public void setPhone(int newPhone){
        this.phone = newPhone;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    
    public double getReqAmt(){
        return reqAmt;
    }
    
    public void setReqAmt(double newReqAmt){
        this.reqAmt = newReqAmt;
    }
    
    public double getOutstandingAmt(){
        return outstandingAmt;
    }
    
    public void setOutstandingAmt(double newOutstandingAmt){
        this.outstandingAmt = newOutstandingAmt;
    }
    
    /*
    private String studentID;
    private String name;
    private int age;
    private String gender;
    private String level;
    private String address;
    private String phone;
    private double reqAmt;
    private double outstandingAmt;
    private Map<String, Map<String, StudentGrade>> grades = new HashMap<>();
    
    public Student(){  
    }
                   
    public Student(String name, int age, String gender, String level, String address, String phone, double reqAmt, double outstandingAmt){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.address = address;
        this.phone = phone;
        this.reqAmt = reqAmt;
        this.outstandingAmt = outstandingAmt;
    }
    
    public Student(String name, int age, String gender, String level, String address, String phone, double reqAmt, double outstandingAmt, Map<String, Map<String, StudentGrade>> grades){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.address = address;
        this.phone = phone;
        this.reqAmt = reqAmt;
        this.outstandingAmt = outstandingAmt;
        this.grades = grades;
    }
    
    public String getStudentID() {
        return this.studentID;
    }
    
    public String getName(){
        return name;
    }
    
    public int getAge(){
        return age;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getLevel(){
        return level;
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public double getReqAmt(){
        return reqAmt;
    }
    
    public double getOutstandingAmt(){
        return outstandingAmt;
    }
    
    public Map<String, Map<String, StudentGrade>> getGrades(){
        return grades;
    }
    
    public void setName(String newStudentName){
        this.name = newStudentName;
    }
    
    public void setAge(int newAge){
        this.age = newAge;
    }
    
    public void setLevel(String newLevel){
        this.level = newLevel;
    }
    
    public void setAddress(String newAddress){
        this.address = newAddress;
    }
    
    public void setPhone(String newPhone){
        this.phone = newPhone;
    }
    
    public void setReqAmt(double newReqAmt){
        this.reqAmt = newReqAmt;
    }
    
    public void setOutstandingAmt(double newOutstandingAmt){
        this.outstandingAmt = newOutstandingAmt;
    }
    
    public void setGrades(Map<String, Map<String, StudentGrade>> grades){
        this.grades = grades;
    }
    
    public void setStudentID(String id) {
        this.studentID = id;
    }*/
}
