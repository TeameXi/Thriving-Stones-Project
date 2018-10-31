/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author MOH MOH SAN
 */
public class TutorPay {
    private int classId;
    private int tutorId;
    private String lessonName;
    private double monthlySalary;
    private String paidStatus;
    private int month;
    private int year;
    private int totalLesson;
    
    public TutorPay(int tutorId,int classId,String lessonName,double monthlySalary,String paidStatus,int month,int year,int totalLesson){
        this.tutorId=tutorId;
        this.classId = classId;
        this.lessonName = lessonName;
        this.monthlySalary = monthlySalary;
        this.paidStatus = paidStatus;
        this.month = month;
        this.year = year;
        this.totalLesson = totalLesson;
    }

    /**
     * @return the classId
     */
    public int getClassId() {
        return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(int classId) {
        this.classId = classId;
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

    /**
     * @return the lessonName
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * @param lessonName the lessonName to set
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    /**
     * @return the monthlySalary
     */
    public double getMonthlySalary() {
        return monthlySalary;
    }

    /**
     * @param monthlySalary the monthlySalary to set
     */
    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    /**
     * @return the paidStatus
     */
    public String getPaidStatus() {
        return paidStatus;
    }

    /**
     * @param paidStatus the paidStatus to set
     */
    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the totalLesson
     */
    public int getTotalLesson() {
        return totalLesson;
    }

    /**
     * @param totalLesson the totalLesson to set
     */
    public void setTotalLesson(int totalLesson) {
        this.totalLesson = totalLesson;
    }
    
}
