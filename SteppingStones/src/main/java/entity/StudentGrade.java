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
    private String CA1 = "";
    private String CA2 = "";
    private String SA1 = "";
    private String SA2 = "";
    
    public StudentGrade(){
        
    }
    
    public StudentGrade(String CA1, String CA2, String SA1, String SA2){
        this.CA1 = CA1;
        this.CA2 = CA2;
        this.SA1 = SA1;
        this.SA2 = SA2;
    }
    
    public String getCA1(){
        return CA1;
    }
    
    public String getCA2(){
        return CA2;
    }
    
    public String getSA1(){
        return SA1;
    }
    
    public String getSA2(){
        return SA2;
    }
    
    public void setCA1(String newCA1){
        this.CA1 = newCA1;
    }
    
    public void setCA2(String newCA2){
        this.CA2 = newCA2;
    }
    
    public void setSA1(String newSA1){
        this.SA1 = newSA1;
    }
    
    public void setSA2(String newSA2){
        this.SA2 = newSA2;
    }
}
