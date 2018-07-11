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
        this.CA1 = newCA2;
    }
    
    public void setSA1(String newSA1){
        this.SA1 = newSA1;
    }
    
    public void setSA2Grade(String newSA2){
        this.SA2 = newSA2;
    }
    
}
