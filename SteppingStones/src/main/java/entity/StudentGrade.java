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
public class StudentGrade {
    private String ca1 = "";
    private String ca2 = "";
    private String sa1 = "";
    private String sa2 = "";
    
    public StudentGrade(){
        
    }
    
    public StudentGrade(String CA1, String CA2, String SA1, String SA2){
        this.ca1 = CA1;
        this.ca2 = CA2;
        this.sa1 = SA1;
        this.sa2 = SA2;
    }
    
    public String getCA1(){
        return ca1;
    }
    
    public String getCA2(){
        return ca2;
    }
    
    public String getSA1(){
        return sa1;
    }
    
    public String getSA2(){
        return sa2;
    }
    
    public void setCA1(String newCA1){
        this.ca1 = newCA1;
    }
    
    public void setCA2(String newCA2){
        this.ca2 = newCA2;
    }
    
    public void setSA1(String newSA1){
        this.sa1 = newSA1;
    }
    
    public void setSA2(String newSA2){
        this.sa2 = newSA2;
    }
}
