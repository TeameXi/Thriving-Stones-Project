package entity;

public class Class {

    private int classID;
    private String level;
    private String subject;
    private int term;
    private String startTime;
    private String endTime;
    private String classDay;
    private double mthlyFees;
    private int hasReminderForFees;
    private String startDate;
    private String endDate;
    private int branchID;
    private int year;
    private int subjectID;
    private int tutorID;
    private String holidayDate;
    private String type;
    private String combinedLevel;
    
    private double tutorRate;
    private String className;
    
    public Class(int classID,String level, int subjectID, String startTime, String endTime, String classDay, double mthlyFees, String startDate, String endDate,String holidayDate,int tutorID) {
        this.classID = classID;
        this.level = level;
        this.subjectID = subjectID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classDay = classDay;
        this.mthlyFees = mthlyFees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidayDate = holidayDate;
        this.tutorID = tutorID;
    }

    public Class(int classID, String level, String subject, int term, String startTime, String endTime, String classDay, double mthlyFees, String startDate, String endDate, String type) {
        this.classID = classID;
        this.level = level;
        this.subject = subject;
        this.term = term;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classDay = classDay;
        this.mthlyFees = mthlyFees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }
    
    public Class(int classID, String level, String subject, int term, String startTime, String endTime, String classDay, 
            double mthlyFees, String startDate, String endDate, String type, String combinedLevel) {
        this.classID = classID;
        this.level = level;
        this.subject = subject;
        this.term = term;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classDay = classDay;
        this.mthlyFees = mthlyFees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.combinedLevel= combinedLevel;
    }
    
    public Class(int classID, int subjectID, String startTime, String endTime, String classDay, double mthlyFees, String startDate, String endDate, int tutorID, String type) {
        this.classID = classID;
        this.subjectID = subjectID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classDay = classDay;
        this.mthlyFees = mthlyFees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tutorID = tutorID;
        this.type = type;  
    }
    
    
    // Payment Object
    public Class(int classID,String className,double tutorRate,String level,String subjet){
        this.classID = classID;
        this.className = className;
        this.tutorRate = tutorRate;
        this.level = level;
        this.subject = subjet;
    }
    
    public String getCombinedLevel() {
        return this.combinedLevel;
    }

    public void setTerm(String combinedLevel) {
        this.combinedLevel = combinedLevel;
    }
    
    public int getTerm() {
        return this.term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getClassID() {
        return this.classID;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String newLevel) {
        this.level = newLevel;
    }

    public String getSubject() {
        return this.subject;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setType(String type){
        this.type = type;
    }

    public void setSubject(String newSubject) {
        this.subject = newSubject;
    }
    
    public String getStartTime(){
        return startTime;
    }
    
    public String getEndTime(){
        return endTime;
    }

    public String getClassDay() {
        return this.classDay;
    }

    public void setClassID(int id) {
        this.classID = id;
    }

    public void setClassDay(String newClassDay) {
        this.classDay = newClassDay;
    }

    public double getMthlyFees() {
        return this.mthlyFees;
    }

    public void setMthlyFees(double newMthlyFees) {
        this.mthlyFees = newMthlyFees;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String newStartDate) {
        this.startDate = newStartDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String newEndDate) {
        this.endDate = newEndDate;
    }

    @Override
    public String toString() {
        return this.classID + "&" + this.level + "&" + this.subject + "&" + this.startTime + "-" + this.endTime + "&" + this.classDay + "&" + this.mthlyFees + "&" + this.startDate + "&" + this.endDate;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the branchID
     */
    public int getBranchID() {
        return branchID;
    }

    /**
     * @param branchID the branchID to set
     */
    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    /**
     * @return the subjectID
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the tutorID
     */
    public int getTutorID() {
        return tutorID;
    }

    /**
     * @param tutorID the tutorID to set
     */
    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

    /**
     * @return the holidayDate
     */
    public String getHolidayDate() {
        return holidayDate;
    }

    /**
     * @return the hasReminderForFees
     */
    public int getHasReminderForFees() {
        return hasReminderForFees;
    }

    /**
     * @param hasReminderForFees the hasReminderForFees to set
     */
    public void setHasReminderForFees(int hasReminderForFees) {
        this.hasReminderForFees = hasReminderForFees;
    }
    
    public void setCombined(String combined){
        this.combinedLevel = combined;
    }

    /**
     * @return the tutorRate
     */
    public double getTutorRate() {
        return tutorRate;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

}
