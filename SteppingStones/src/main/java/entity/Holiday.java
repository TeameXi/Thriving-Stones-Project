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
public class Holiday {
    private int holidayId;
    private String holidayName;
    private String date;
    
    public Holiday(){
        
    }
    
    public Holiday(int holidayId,String holidayName,String date){
        this.holidayId = holidayId;
        this.holidayName = holidayName;
        this.date = date;
    }

    /**
     * @return the holidayId
     */
    public int getHolidayId() {
        return holidayId;
    }

    /**
     * @param holidayId the holidayId to set
     */
    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    /**
     * @return the holidayName
     */
    public String getHolidayName() {
        return holidayName;
    }

    /**
     * @param holidayName the holidayName to set
     */
    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    
}
