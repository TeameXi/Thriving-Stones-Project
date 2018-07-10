/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

public class Subject {

    private String[] subjects;

    public Subject() {
        subjects = new String[]{"English", "Mathematics", "Science", "A.Maths", "E.Maths", "Biology", "Chemistry", "Physics"};
    }

    public String[] getSubjects() {
        return this.subjects;
    }

}
