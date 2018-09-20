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
public class Tutor_HourlyRate_Rel {
    private int tutor_id;
    private String tutor_name;
    private int level_id;
    private int subject_id;
    private double hourly_pay;
    
    public Tutor_HourlyRate_Rel(int tutor_id,String tutor_name,int subject_id,double hourly_pay,int level_id){
        this.tutor_id = tutor_id;
        this.tutor_name = tutor_name;
        this.subject_id = subject_id;
        this.hourly_pay = hourly_pay;
        this.level_id = level_id;
    }
    
    public Tutor_HourlyRate_Rel(int tutor_id,String tutor_name,int subject_id,double hourly_pay){
        this.tutor_id = tutor_id;
        this.tutor_name = tutor_name;
        this.subject_id = subject_id;
        this.hourly_pay = hourly_pay;
    }


    /**
     * @return the tutor_id
     */
    public int getTutor_id() {
        return tutor_id;
    }

    /**
     * @param tutor_id the tutor_id to set
     */
    public void setTutor_id(int tutor_id) {
        this.tutor_id = tutor_id;
    }

    /**
     * @return the level_id
     */
    public int getLevel_id() {
        return level_id;
    }

    /**
     * @param level_id the level_id to set
     */
    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    /**
     * @return the subject_id
     */
    public int getSubject_id() {
        return subject_id;
    }

    /**
     * @param subject_id the subject_id to set
     */
    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    /**
     * @return the hourly_pay
     */
    public double getHourly_pay() {
        return hourly_pay;
    }

    /**
     * @param hourly_pay the hourly_pay to set
     */
    public void setHourly_pay(double hourly_pay) {
        this.hourly_pay = hourly_pay;
    }

    /**
     * @return the tutor_name
     */
    public String getTutor_name() {
        return tutor_name;
    }
    
    
}
