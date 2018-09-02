/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionManager;
import entity.Holiday;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MOH MOH SAN
 */
public class HolidayDAO {
    public static ArrayList<Holiday> getHolidayLists(int year,int term){
        ArrayList<Holiday> holidayLists = new ArrayList<>();
        String sql = "SELECT holiday_id,holiday_name,weekday(holiday_date)+1 as holiday_date FROM holidays WHERE holiday_year in (?,?) and Month(holiday_date) between ? and ?";
        
        int startMonth;
        int endMonth;
        switch(term){
            case 2: 
                startMonth = 4;
                endMonth = 6;
                break;
            case 3:
                startMonth = 7;
                endMonth = 9;
                break;
            case 4:
                startMonth = 10;
                endMonth = 12;
                break;
            default:
                startMonth = 1;
                endMonth = 3;
                break;  
        }
       
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 0);
            stmt.setInt(2,year);
            stmt.setInt(3,startMonth);
            stmt.setInt(4, endMonth);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int holidayID = rs.getInt("holiday_id");
                String holidayName = rs.getString("holiday_name");
                String holidayDate = rs.getString("holiday_date");
                Holiday holiday = new Holiday(holidayID,holidayName,holidayDate);
                holidayLists.add(holiday);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return holidayLists;
    }
    
    
    public static ArrayList<String> getHolidayDateLists(String holiday){
        ArrayList<String> holidayDateLists = new ArrayList<>();
        String sql = "SELECT holiday_date FROM holidays WHERE holiday_id IN (" + holiday + ") order by holiday_date";
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                holidayDateLists.add(rs.getString(1));
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return holidayDateLists;
    }
    
    
    public static String getHolidayDate(String holiday_date){
        String date = "";
        String sql = "SELECT holiday_date FROM holidays WHERE holiday_date = ?";
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,holiday_date);
           
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                date = rs.getString(1);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return date;
    }
}
