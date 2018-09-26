package entity;

import java.sql.Timestamp;
public class Lesson {
    private int lessonid;
    private int classid;
    private int tutorid;
    private int tutorAttended;
    private Timestamp updated;
    private String startDate;
    private String endDate;
    
    public Lesson(int lessonid, int classid, int tutorid, int tutorAttended, String startDate, String endDate) {
        this.lessonid = lessonid;
        this.classid = classid;
        this.tutorid = tutorid;
        this.tutorAttended = tutorAttended;
        this.startDate = startDate;
        this.endDate = endDate;
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
    
    public String getStartDate(){
        return startDate;
    }
    
    public String getEndDate(){
        return endDate;
    }
    
    public Timestamp getUpdated() {
        return updated;
    }
    
}
