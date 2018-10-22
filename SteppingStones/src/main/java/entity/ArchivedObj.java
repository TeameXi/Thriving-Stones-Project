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
public class ArchivedObj {
    private int student_id;
    private int level_id;
    private String student_data;
    private String parent_data;
    
    public ArchivedObj(int student_id,int level_id,String student_data,String parent_data){
        this.student_id = student_id;
        this.level_id = level_id;
        this.student_data = student_data;
        this.parent_data = parent_data;
    }
    
    /**
     * @return the student_id
     */
    public int getStudent_id() {
        return student_id;
    }

    /**
     * @param student_id the student_id to set
     */
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    /**
     * @return the student_data
     */
    public String getStudent_data() {
        return student_data;
    }

    /**
     * @param student_data the student_data to set
     */
    public void setStudent_data(String student_data) {
        this.student_data = student_data;
    }

    /**
     * @return the parent_data
     */
    public String getParent_data() {
        return parent_data;
    }

    /**
     * @param parent_data the parent_data to set
     */
    public void setParent_data(String parent_data) {
        this.parent_data = parent_data;
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
    
    
}
