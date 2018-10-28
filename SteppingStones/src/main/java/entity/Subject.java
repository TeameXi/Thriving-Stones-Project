/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class Subject {
    private int subjectId;
    private String subjectName;
    private int branchId;
    private float fees;

    public Subject(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }
    
    public Subject(int subjectId, String subjectName, int branchId){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.branchId = branchId;
    }
    
    public Subject(int subjectId, String subjectName, float fees){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.fees = fees;
    }
    
    public Subject(String subjectName){
        this.subjectName = subjectName;
    }
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public void setBranchID(int branchId) {
        this.branchId = branchId;
    }
    
    public int getBranchId() {
        return branchId;
    }
    
    public void setFees(float fees){
        this.fees = fees;
    }
    public float getFees(){
        return fees;
    }
}
