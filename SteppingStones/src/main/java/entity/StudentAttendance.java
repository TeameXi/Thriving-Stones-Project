/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


/**
 *
 * @author Zang Yu
 */
public class StudentAttendance {
    private int lessonid;
    private int studentid;  
    private int studentAttended;
   
        
    public StudentAttendance(int lessonid, int studentid, int studentAttended){
        this.lessonid = lessonid;
        this.studentid = studentid;
        this.studentAttended = studentAttended;        
    }
    
    public int getLessonid() {
        return lessonid;
    }
    
    public void setLessonid(int lessonid) {
        this.lessonid = lessonid;
    }
    
    public int getStudentid() {
        return studentid;
    }
    
    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }
    
    public int getStudentAttended() {
        return studentAttended;
    }
    
    public void setStudentAttended(int studentAttended) {
        this.studentAttended = studentAttended;
    }
}
