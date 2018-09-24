package entity;

import java.sql.Timestamp;
public class Lesson {
    private int lessonid;
    private int classid;
    private int tutorid;
    private int tutorAttended;
    private Timestamp lessonDateTime;
    private Timestamp updated;
    private Timestamp startDate;
    private Timestamp endDate;

    public Lesson(int classid, Timestamp lessonDateTime) {
        this.classid = classid;
        this.lessonDateTime = lessonDateTime;
    }

    public Lesson(int lessonid, int classid, int tutorid, int tutorAttended, Timestamp lessonDateTime) {
        this.lessonid = lessonid;
        this.classid = classid;
        this.tutorid = tutorid;
        this.tutorAttended = tutorAttended;
        this.lessonDateTime = lessonDateTime;
    }
    
    public Lesson(int lessonid, int classid, int tutorid, int tutorAttended, Timestamp startDate, Timestamp endDate) {
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

    public Timestamp getLessonDateTime() {
        return lessonDateTime;
    }

    public void setLessonDateTime(Timestamp lessonDateTime) {
        this.lessonDateTime = lessonDateTime;
    }
    
    public String getStartDate(){
        return startDate.toString();
    }
    
    public Timestamp getStart(){
        return startDate;
    }
    
    public String endDate(){
        return endDate.toString();
    }
    
    public Timestamp getEnd(){
        return endDate;
    }
    
    public Timestamp getUpdated() {
        return updated;
    }
    
}
