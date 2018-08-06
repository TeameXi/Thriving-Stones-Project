/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.google.api.client.util.DateTime;
import java.sql.Timestamp;

/**
 *
 * @author Riana
 */
public class Lesson {
    private int lessonid;
    private int classid;
    private int tutorid;
    private int tutorAttended;
    private Timestamp lessonDateTime;

    public Lesson(int classid, Timestamp lessonDateTime) {
        this.classid = classid;
        this.lessonDateTime = lessonDateTime;
    }

    public int getLessonid() {
        return lessonid;
    }

    public void setLessonid(int lessonid) {
        this.lessonid = lessonid;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public int getTutorid() {
        return tutorid;
    }

    public void setTutorid(int tutorid) {
        this.tutorid = tutorid;
    }

    public int getTutorAttended() {
        return tutorAttended;
    }

    public void setTutorAttended(int tutorAttended) {
        this.tutorAttended = tutorAttended;
    }

    public Timestamp getLessonDateTime() {
        return lessonDateTime;
    }

    public void setLessonDateTime(Timestamp lessonDateTime) {
        this.lessonDateTime = lessonDateTime;
    }
    
}
