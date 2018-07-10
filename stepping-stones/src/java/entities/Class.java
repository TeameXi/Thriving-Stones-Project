/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;

public class Class {

    private String level;
    private String subject;
    private String classTime;
    private String classDay;

    public Class(String level, String subject, String classTime, String classDay) {
        this.level = level;
        this.subject = subject;
        this.classTime = classTime;
        this.classDay = classDay;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String newLevel) {
        this.level = newLevel;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String newSubject) {
        this.subject = newSubject;
    }

    public String getClassTime() {
        return this.classTime;
    }

    public void setClassTime(String newClassTime) {
        this.classTime = newClassTime;
    }

    public String getClassDay() {
        return this.classDay;
    }

    public void setClassDay(String newClassDay) {
        this.classDay = newClassDay;
    }
}
