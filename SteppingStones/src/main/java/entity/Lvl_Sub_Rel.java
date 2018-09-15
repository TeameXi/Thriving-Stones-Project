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
public class Lvl_Sub_Rel {
    private int level_id;
    private int subject_id;
    private String level_name;
    private String subject_name;
    
    public Lvl_Sub_Rel(){
        
    }
    
    public Lvl_Sub_Rel(int level_id,int subject_id,String level_name,String subject_name){
        this.level_id = level_id;
        this.subject_id = subject_id;
        this.level_name = level_name;
        this.subject_name = subject_name;
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
     * @return the level_name
     */
    public String getLevel_name() {
        return level_name;
    }

    /**
     * @param level_name the level_name to set
     */
    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    /**
     * @return the subject_name
     */
    public String getSubject_name() {
        return subject_name;
    }

    /**
     * @param subject_name the subject_name to set
     */
    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
    
    
    
}
