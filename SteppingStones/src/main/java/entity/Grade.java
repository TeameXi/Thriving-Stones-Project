


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


public class Grade {
    private String studentName;
    private int studentId;
    private int class_id;
    
    private int CA1_tuition_top;
    private int CA1_tuition_base;
    private double CA1_tuition_grade;
    
    private int SA1_tuition_top;
    private int SA1_tuition_base;
    private double SA1_tuition_grade;
    
    private int CA2_tuition_top;
    private int CA2_tuition_base;
    private double CA2_tuition_grade;
    
    private int SA2_tuition_top;
    private int SA2_tuition_base;
    private double SA2_tuition_grade;
    
    private int CA1_school_top;
    private int CA1_school_base;
    private double CA1_school_grade;
    
    private int SA1_school_top;
    private int SA1_school_base;
    private double SA1_school_grade;
    
    private int CA2_school_top;
    private int CA2_school_base;
    private double CA2_school_grade;
    
    private int SA2_school_top;
    private int SA2_school_base;
    private double SA2_school_grade;
    
    private String level;
    
    public Grade(){
        
    }
    
    
    public Grade(String studentName,int studentId,int class_id,int CA1_tuition_top,int CA1_tuition_base,int SA1_tuition_top,int SA1_tuition_base,int CA2_tuition_top,int CA2_tuition_base,int SA2_tuition_top,int SA2_tuition_base,
            int CA1_school_top,int CA1_school_base,int SA1_school_top,int SA1_school_base,int CA2_school_top,int CA2_school_base,int SA2_school_top,int SA2_school_base){
        this.studentName = studentName;
        this.studentId = studentId;
        this.class_id = class_id;
        
        this.CA1_tuition_top = CA1_tuition_top;
        this.CA1_tuition_base = CA1_tuition_base;
        this.SA1_tuition_top = SA1_tuition_top;
        this.SA1_tuition_base = SA1_tuition_base;
        this.CA2_tuition_top = CA2_tuition_top;
        this.CA2_tuition_base = CA2_tuition_base;
        this.SA2_tuition_top = SA2_tuition_top;
        this.SA2_tuition_base = SA2_tuition_base;
        
        this.CA1_school_top = CA1_school_top;
        this.CA1_school_base = CA1_school_base;
        this.SA1_school_top = SA1_school_top;
        this.SA1_school_base = SA1_school_base;
        this.CA2_school_top = CA2_school_top;
        this.CA2_school_base = CA2_school_base;
        this.SA2_school_top = SA2_school_top;
        this.SA2_school_base = SA2_school_base;
       
    }
    
    
    // With Ration Grade
    public Grade(String studentName,int studentId,int class_id,
            int CA1_tuition_top,int CA1_tuition_base,double CA1_tuition_grade,
            int SA1_tuition_top,int SA1_tuition_base,double SA1_tuition_grade,
            int CA2_tuition_top,int CA2_tuition_base,double CA2_tuition_grade,
            int SA2_tuition_top,int SA2_tuition_base,double SA2_tuition_grade,
            int CA1_school_top,int CA1_school_base,double CA1_school_grade,
            int SA1_school_top,int SA1_school_base,double SA1_school_grade,
            int CA2_school_top,int CA2_school_base,double CA2_school_grade,
            int SA2_school_top,int SA2_school_base,double SA2_school_grade){
        this.studentName = studentName;
        this.studentId = studentId;
        this.class_id = class_id;
        
        this.CA1_tuition_top = CA1_tuition_top;this.CA1_tuition_base = CA1_tuition_base;this.CA1_tuition_grade = CA1_tuition_grade;
        this.SA1_tuition_top = SA1_tuition_top;this.SA1_tuition_base = SA1_tuition_base;this.SA1_tuition_grade = SA1_tuition_grade;
        this.CA2_tuition_top = CA2_tuition_top;this.CA2_tuition_base = CA2_tuition_base;this.CA2_tuition_grade = CA2_tuition_grade;
        this.SA2_tuition_top = SA2_tuition_top;this.SA2_tuition_base = SA2_tuition_base;this.SA2_tuition_grade = SA2_tuition_grade;
        
        this.CA1_school_top = CA1_school_top;this.CA1_school_base = CA1_school_base;this.CA1_school_grade = CA1_school_grade;
        this.SA1_school_top = SA1_school_top;this.SA1_school_base = SA1_school_base;this.SA1_school_grade = SA1_school_grade;
        this.CA2_school_top = CA2_school_top;this.CA2_school_base = CA2_school_base;this.CA2_school_grade = CA2_school_grade;
        this.SA2_school_top = SA2_school_top;this.SA2_school_base = SA2_school_base;this.SA2_school_grade = SA2_school_grade;
    }

    /**
     * @return the studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param studentName the studentName to set
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @return the class_id
     */
    public int getClass_id() {
        return class_id;
    }

    /**
     * @param class_id the class_id to set
     */
    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    /**
     * @return the CA1_tuition_top
     */
    public int getCA1_tuition_top() {
        return CA1_tuition_top;
    }

    /**
     * @param CA1_tuition_top the CA1_tuition_top to set
     */
    public void setCA1_tuition_top(int CA1_tuition_top) {
        this.CA1_tuition_top = CA1_tuition_top;
    }

    /**
     * @return the CA1_tuition_base
     */
    public int getCA1_tuition_base() {
        return CA1_tuition_base;
    }

    /**
     * @param CA1_tuition_base the CA1_tuition_base to set
     */
    public void setCA1_tuition_base(int CA1_tuition_base) {
        this.CA1_tuition_base = CA1_tuition_base;
    }

    /**
     * @return the CA1_tuition_grade
     */
    public double getCA1_tuition_grade() {
        return CA1_tuition_grade;
    }

    /**
     * @param CA1_tuition_grade the CA1_tuition_grade to set
     */
    public void setCA1_tuition_grade(double CA1_tuition_grade) {
        this.CA1_tuition_grade = CA1_tuition_grade;
    }

    /**
     * @return the SA1_tuition_top
     */
    public int getSA1_tuition_top() {
        return SA1_tuition_top;
    }

    /**
     * @param SA1_tuition_top the SA1_tuition_top to set
     */
    public void setSA1_tuition_top(int SA1_tuition_top) {
        this.SA1_tuition_top = SA1_tuition_top;
    }

    /**
     * @return the SA1_tuition_base
     */
    public int getSA1_tuition_base() {
        return SA1_tuition_base;
    }

    /**
     * @param SA1_tuition_base the SA1_tuition_base to set
     */
    public void setSA1_tuition_base(int SA1_tuition_base) {
        this.SA1_tuition_base = SA1_tuition_base;
    }

    /**
     * @return the SA1_tuition_grade
     */
    public double getSA1_tuition_grade() {
        return SA1_tuition_grade;
    }

    /**
     * @param SA1_tuition_grade the SA1_tuition_grade to set
     */
    public void setSA1_tuition_grade(double SA1_tuition_grade) {
        this.SA1_tuition_grade = SA1_tuition_grade;
    }

    /**
     * @return the CA2_tuition_top
     */
    public int getCA2_tuition_top() {
        return CA2_tuition_top;
    }

    /**
     * @param CA2_tuition_top the CA2_tuition_top to set
     */
    public void setCA2_tuition_top(int CA2_tuition_top) {
        this.CA2_tuition_top = CA2_tuition_top;
    }

    /**
     * @return the CA2_tuition_base
     */
    public int getCA2_tuition_base() {
        return CA2_tuition_base;
    }

    /**
     * @param CA2_tuition_base the CA2_tuition_base to set
     */
    public void setCA2_tuition_base(int CA2_tuition_base) {
        this.CA2_tuition_base = CA2_tuition_base;
    }

    /**
     * @return the CA2_tuition_grade
     */
    public double getCA2_tuition_grade() {
        return CA2_tuition_grade;
    }

    /**
     * @param CA2_tuition_grade the CA2_tuition_grade to set
     */
    public void setCA2_tuition_grade(double CA2_tuition_grade) {
        this.CA2_tuition_grade = CA2_tuition_grade;
    }

    /**
     * @return the SA2_tuition_top
     */
    public int getSA2_tuition_top() {
        return SA2_tuition_top;
    }

    /**
     * @param SA2_tuition_top the SA2_tuition_top to set
     */
    public void setSA2_tuition_top(int SA2_tuition_top) {
        this.SA2_tuition_top = SA2_tuition_top;
    }

    /**
     * @return the SA2_tuition_base
     */
    public int getSA2_tuition_base() {
        return SA2_tuition_base;
    }

    /**
     * @param SA2_tuition_base the SA2_tuition_base to set
     */
    public void setSA2_tuition_base(int SA2_tuition_base) {
        this.SA2_tuition_base = SA2_tuition_base;
    }

    /**
     * @return the SA2_tuition_grade
     */
    public double getSA2_tuition_grade() {
        return SA2_tuition_grade;
    }

    /**
     * @param SA2_tuition_grade the SA2_tuition_grade to set
     */
    public void setSA2_tuition_grade(double SA2_tuition_grade) {
        this.SA2_tuition_grade = SA2_tuition_grade;
    }

    /**
     * @return the CA1_school_top
     */
    public int getCA1_school_top() {
        return CA1_school_top;
    }

    /**
     * @param CA1_school_top the CA1_school_top to set
     */
    public void setCA1_school_top(int CA1_school_top) {
        this.CA1_school_top = CA1_school_top;
    }

    /**
     * @return the CA1_school_base
     */
    public int getCA1_school_base() {
        return CA1_school_base;
    }

    /**
     * @param CA1_school_base the CA1_school_base to set
     */
    public void setCA1_school_base(int CA1_school_base) {
        this.CA1_school_base = CA1_school_base;
    }

    /**
     * @return the CA1_school_grade
     */
    public double getCA1_school_grade() {
        return CA1_school_grade;
    }

    /**
     * @param CA1_school_grade the CA1_school_grade to set
     */
    public void setCA1_school_grade(double CA1_school_grade) {
        this.CA1_school_grade = CA1_school_grade;
    }

    /**
     * @return the SA1_school_top
     */
    public int getSA1_school_top() {
        return SA1_school_top;
    }

    /**
     * @param SA1_school_top the SA1_school_top to set
     */
    public void setSA1_school_top(int SA1_school_top) {
        this.SA1_school_top = SA1_school_top;
    }

    /**
     * @return the SA1_school_base
     */
    public int getSA1_school_base() {
        return SA1_school_base;
    }

    /**
     * @param SA1_school_base the SA1_school_base to set
     */
    public void setSA1_school_base(int SA1_school_base) {
        this.SA1_school_base = SA1_school_base;
    }

    /**
     * @return the SA1_school_grade
     */
    public double getSA1_school_grade() {
        return SA1_school_grade;
    }

    /**
     * @param SA1_school_grade the SA1_school_grade to set
     */
    public void setSA1_school_grade(double SA1_school_grade) {
        this.SA1_school_grade = SA1_school_grade;
    }

    /**
     * @return the CA2_school_top
     */
    public int getCA2_school_top() {
        return CA2_school_top;
    }

    /**
     * @param CA2_school_top the CA2_school_top to set
     */
    public void setCA2_school_top(int CA2_school_top) {
        this.CA2_school_top = CA2_school_top;
    }

    /**
     * @return the CA2_school_base
     */
    public int getCA2_school_base() {
        return CA2_school_base;
    }

    /**
     * @param CA2_school_base the CA2_school_base to set
     */
    public void setCA2_school_base(int CA2_school_base) {
        this.CA2_school_base = CA2_school_base;
    }

    /**
     * @return the CA2_school_grade
     */
    public double getCA2_school_grade() {
        return CA2_school_grade;
    }

    /**
     * @param CA2_school_grade the CA2_school_grade to set
     */
    public void setCA2_school_grade(double CA2_school_grade) {
        this.CA2_school_grade = CA2_school_grade;
    }

    /**
     * @return the SA2_school_top
     */
    public int getSA2_school_top() {
        return SA2_school_top;
    }

    /**
     * @param SA2_school_top the SA2_school_top to set
     */
    public void setSA2_school_top(int SA2_school_top) {
        this.SA2_school_top = SA2_school_top;
    }

    /**
     * @return the SA2_school_base
     */
    public int getSA2_school_base() {
        return SA2_school_base;
    }

    /**
     * @param SA2_school_base the SA2_school_base to set
     */
    public void setSA2_school_base(int SA2_school_base) {
        this.SA2_school_base = SA2_school_base;
    }

    /**
     * @return the SA2_school_grade
     */
    public double getSA2_school_grade() {
        return SA2_school_grade;
    }

    /**
     * @param SA2_school_grade the SA2_school_grade to set
     */
    public void setSA2_school_grade(double SA2_school_grade) {
        this.SA2_school_grade = SA2_school_grade;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

}
