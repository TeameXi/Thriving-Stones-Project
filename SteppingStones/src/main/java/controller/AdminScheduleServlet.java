/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Lesson;
import entity.Class;
import entity.Level;
import entity.Subject;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;
import model.LevelDAO;
import model.SubjectDAO;
import model.TutorDAO;
import model.TutorHourlyRateDAO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Xin
 */
@WebServlet(name = "AdminScheduleServlet", urlPatterns = {"/AdminScheduleServlet"})
public class AdminScheduleServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");

            LessonDAO lessonDAO = new LessonDAO();
            ClassDAO classDAO = new ClassDAO();
            TutorDAO tutorDAO = new TutorDAO();
            LevelDAO levelDAO = new LevelDAO();
            SubjectDAO subjectDAO = new SubjectDAO();

            switch (action) {
                case "retrieve": {
                    int selectedLevel = Integer.parseInt(request.getParameter("selectedLevel"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    JSONArray array = new JSONArray();
                    ArrayList<Class> classes = new ArrayList<>();

                    if (selectedLevel == 0) {
                        classes = classDAO.listAllClasses(branchID);
                        System.out.println(classes.size());
                    } else {
                        classes = classDAO.getClassesByLevel(selectedLevel, branchID);
                    }

                    for (Class c : classes) {
                        ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonLists(c.getClassID());

                        for (Lesson l : lessons) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", l.getLessonid());

                            ArrayList<String> replacement = lessonDAO.retrieveReplacementDates(l.getLessonid());

                            if (replacement == null) {
                                obj.put("start_date", l.getStartDate());
                                obj.put("end_date", l.getEndDate());
                            } else {
                                obj.put("start_date", replacement.get(0));
                                obj.put("end_date", replacement.get(1));
                            }
                            
                            String text = "";
                            
                            if(c.getCombinedLevel() != null){
                                text += c.getCombinedLevel() + " " + c.getSubject();
                            }else{
                                text += c.getLevel() + " " + c.getSubject();
                            }
                            
                            obj.put("text", text);
                            array.put(obj);
                        }
                    }
                    JSONObject toReturn = new JSONObject().put("data", array);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
                case "retrieveLesson": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));

                    JSONArray tutorList = new JSONArray();

                    Lesson lesson = lessonDAO.getLessonByID(lessonID);
                    Class cls = classDAO.getClassByID(lesson.getClassid());

                    ArrayList<Tutor> tutors = TutorHourlyRateDAO.tutorListInPayTable(branchID, cls.getSubjectID(), LevelDAO.retrieveLevelID(cls.getLevel()));

                    int lessonTutor = 0;
                    JSONObject defaultTutor = new JSONObject();

                    if (lessonDAO.retrieveReplacementTutor(lessonID) == 0) {
                        Tutor defaultT = tutorDAO.retrieveSpecificTutorById(lesson.getTutorid());
                        lessonTutor = lesson.getTutorid();
                        defaultTutor.put("id", defaultT.getTutorId());
                        defaultTutor.put("name", defaultT.getName());
                        tutorList.put(defaultTutor);
                    } else {
                        Tutor defaultT = tutorDAO.retrieveSpecificTutorById(new LessonDAO().retrieveReplacementTutor(lessonID));
                        lessonTutor = defaultT.getTutorId();
                        defaultTutor.put("id", defaultT.getTutorId());
                        defaultTutor.put("name", defaultT.getName());
                        tutorList.put(defaultTutor);
                    }

                    for (Tutor t : tutors) {
                        String start = lesson.getStartDate();
                        String end = lesson.getEndDate();
                        if (lessonTutor != t.getTutorId()) {
                            boolean overlap = lessonDAO.retrieveOverlappingLessonsForTutor(t.getTutorId(), start, end, cls.getClassID());

                            if (!overlap) {
                                JSONObject obj = new JSONObject();
                                obj.put("id", t.getTutorId());
                                obj.put("name", t.getName());
                                tutorList.put(obj);
                            }
                        }
                    }

                    DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

                    String start = "";
                    String end = "";
                    DateTime startFormat = null;
                    DateTime endFormat = null;

                    ArrayList<String> replacement = lessonDAO.retrieveReplacementDates(lessonID);

                    if (replacement == null) {
                        startFormat = pattern.parseDateTime(lesson.getStartDate().substring(0, lesson.getStartDate().length() - 2));
                        endFormat = pattern.parseDateTime(lesson.getEndDate().substring(0, lesson.getEndDate().length() - 2));
                    } else {
                        startFormat = pattern.parseDateTime(replacement.get(0));
                        endFormat = pattern.parseDateTime(replacement.get(1));
                    }

                    JSONObject obj = new JSONObject();
                    obj.put("start", pattern.print(startFormat));
                    obj.put("end", pattern.print(endFormat));
                    obj.put("tutor", lesson.getTutorid());
                    obj.put("tutors", tutorList);
                    String json = obj.toString();
                    out.println(json);
                    break;
                }
                case "updateLesson": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutor"));
                    String start = request.getParameter("start");
                    String end = request.getParameter("end");

                    JSONObject obj = new JSONObject();

                    if (start == null || start.isEmpty()) {
                        obj.put("start", "Please select a start time!");
                    }

                    if (end == null || end.isEmpty()) {
                        obj.put("end", "Please select an end time!");
                    }
                    
                    DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                    DateTime startFormat = null;
                    DateTime endFormat = null;
                    if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
                        startFormat = pattern.parseDateTime(start);
                        endFormat = pattern.parseDateTime(end);
                        if (startFormat.isAfter(endFormat)) {
                            obj.put("invalid_timing", "Please select an end timing that occurs after the start timing!");
                        }
                    }

                    if (obj.length() != 0) {
                        obj.put("status", false);

                        out.println(obj.toString());
                    } else {
                        Lesson lesson = lessonDAO.getLessonByID(lessonID);

                        boolean overlap = lessonDAO.retrieveOverlappingLessonsForTutor(tutorID, start, pattern.print(endFormat.minusMinutes(1)), lesson.getClassid());

                        boolean status = false;

                        if (tutorID == lesson.getTutorid()) {
                            tutorID = 0;
                        }

                        if (!overlap) {
                            status = lessonDAO.updateLessonDate(lessonID, tutorID, start, end);
                        } else {
                            obj.put("invalid_tutor", "The tutor selected is not available in the specified timing.");
                        }

                        obj.put("status", status);
                        String json = obj.toString();
                        out.println(json);
                    }
                    break;
                }
                case "retrieveClass": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    JSONArray tutorList = new JSONArray();

                    Lesson lesson = lessonDAO.getLessonByID(lessonID);
                    Class cls = classDAO.getClassByID(lesson.getClassid());
                    ArrayList<Tutor> tutors = TutorHourlyRateDAO.tutorListInPayTable(branchID, cls.getSubjectID(), LevelDAO.retrieveLevelID(cls.getLevel()));

                    JSONObject defaultTutor = new JSONObject();
                    int lessonTutor = 0;

                    if (lessonDAO.retrieveReplacementTutor(lessonID) == 0) {
                        Tutor defaultT = tutorDAO.retrieveSpecificTutorById(lesson.getTutorid());
                        lessonTutor = lesson.getTutorid();
                        defaultTutor.put("id", defaultT.getTutorId());
                        defaultTutor.put("name", defaultT.getName());
                        tutorList.put(defaultTutor);
                    } else {
                        Tutor defaultT = tutorDAO.retrieveSpecificTutorById(new LessonDAO().retrieveReplacementTutor(lessonID));
                        lessonTutor = defaultT.getTutorId();
                        defaultTutor.put("id", defaultT.getTutorId());
                        defaultTutor.put("name", defaultT.getName());
                        tutorList.put(defaultTutor);
                    }

                    for (Tutor t : tutors) {
                        String start = cls.getStartTime();
                        String end = cls.getEndTime();
                        String start_date = cls.getStartDate();

                        DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
                        DateTime startFormat = pattern.parseDateTime(start_date);

                        if (lessonTutor != t.getTutorId()) {
                            boolean overlap = classDAO.retrieveOverlappingClassesForTutor(t.getTutorId(), start, end, lesson.getClassid(), startFormat.getDayOfWeek());

                            if (!overlap) {
                                JSONObject obj = new JSONObject();
                                obj.put("id", t.getTutorId());
                                obj.put("name", t.getName());
                                tutorList.put(obj);
                            }
                        }
                    }

                    JSONObject obj = new JSONObject();
                    obj.put("start", cls.getStartDate());
                    obj.put("endDate", cls.getEndDate());
                    obj.put("end", cls.getEndTime());
                    obj.put("startTime", cls.getStartTime());
                    obj.put("tutor", cls.getTutorID());
                    obj.put("tutors", tutorList);
                    String json = obj.toString();
                    out.println(json);
                    break;
                }
                case "updateClass": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutor"));
                    String startTime = request.getParameter("startTime");
                    String endTime = request.getParameter("endTime");
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");

                    JSONObject obj = new JSONObject();
                    if (startTime == null || startTime.isEmpty()) {
                        obj.put("start_time_error", "Please enter a start time!");
                    }

                    if (endTime == null || endTime.isEmpty()) {
                        obj.put("end_time_error", "Please enter an end time!");
                    }

                    if (startDate == null || startDate.isEmpty()) {
                        obj.put("start_date_error", "Please enter a start date!");
                    }

                    if (endDate == null || endDate.isEmpty()) {
                        obj.put("end_date_error", "Please enter an end date!");
                    }

                    if (obj.length() == 0) {
                        DateTimeFormatter pattern = DateTimeFormat.forPattern("HH:mm:ss");
                        DateTime start_time = pattern.parseDateTime(startTime);
                        DateTime end_time = pattern.parseDateTime(endTime);

                        DateTimeFormatter date = DateTimeFormat.forPattern("yyyy-MM-dd");
                        DateTime start_date = date.parseDateTime(startDate);
                        DateTime end_date = date.parseDateTime(endDate);

                        if (start_time.isEqual(end_time) || start_time.isAfter(end_time)) {
                            obj.put("invalid_timing", "The end timing has to be after the start timing!");
                        }

                        if (start_date.isAfter(end_date)) {
                            obj.put("invalid_date", "The end date has to be after the start date!");
                        }

                        if (obj.length() == 0) {
                            Lesson lesson = lessonDAO.getLessonByID(lessonID);
                            Class cls = classDAO.getClassByID(lesson.getClassid());

                            boolean sameTutor = tutorID == cls.getTutorID();

                            if (cls.getStartDate().equals(date.print(start_date)) && cls.getEndDate().equals(date.print(end_date))) {
                                if (sameTutor) {
                                    boolean overlapLessons = lessonDAO.retrieveOverlappingLessonsForTutor(tutorID, startDate, pattern.print(end_time.minusMinutes(1)), cls.getClassID());

                                    if (overlapLessons) {
                                        obj.put("invalid_tutor", "The tutor is unavailable at this timing!");
                                        obj.put("status", false);
                                    } else {
                                        ArrayList<Lesson> lessons = lessonDAO.retrieveAllLessonListsAfterCurr(cls.getClassID());

                                        for (Lesson l : lessons) {
                                            if (startTime.equals(cls.getStartTime()) && endTime.equals(cls.getStartTime())) {
                                                lessonDAO.updateLesson(tutorID, lessonID, null, null);
                                            } else {
                                                lessonDAO.updateLesson(tutorID, lessonID, startDate + " " + startTime, endDate + " " + endTime);
                                            }
                                        }
                                    }
                                }
                                //If the end date of the class got extended
                            } else if (cls.getStartDate().equals(startDate)) {
                                String originalEnd = cls.getEndDate();
                                DateTime original = date.parseDateTime(originalEnd);

                                //If the timing of the classes changes
                                if (!startTime.equals(cls.getStartTime()) && !endTime.equals(cls.getEndTime())) {
                                    ArrayList<Lesson> lessons = lessonDAO.retrieveAllLessonListsAfterCurr(cls.getClassID());

                                    //Changes the timing of the lessons after current date that were already inserted
                                    for (Lesson l : lessons) {
                                        if (sameTutor) {
                                            lessonDAO.updateLesson(0, lessonID, l.getLessonDate() + " " + startTime, l.getLessonDate() + " " + endTime);
                                        } else {
                                            lessonDAO.updateLesson(tutorID, lessonID, l.getLessonDate() + " " + startTime, l.getLessonDate() + " " + endTime);
                                        }
                                    }
                                }

                                String day = cls.getClassDay();
                                int dayNum = 0;

                                switch (day) {
                                    case "Mon":
                                        dayNum = 1;
                                        break;
                                    case "Tue":
                                        dayNum = 2;
                                        break;
                                    case "Wed":
                                        dayNum = 3;
                                        break;
                                    case "Thur":
                                        dayNum = 4;
                                        break;
                                    case "Fri":
                                        dayNum = 5;
                                        break;
                                    case "Sat":
                                        dayNum = 6;
                                        break;
                                    case "Sun":
                                        dayNum = 7;
                                        break;
                                }

                                //If the end date got extended
                                if (end_date.isAfter(original)) {
                                    while (end_date.isAfter(original) || end_date.isEqual(original)) {
                                        if (original.getDayOfWeek() == dayNum) {
                                            lessonDAO.createLesson(cls.getClassID(), tutorID, date.print(original) + " " + startTime, date.print(original) + " " + endTime, 0, 0, cls.getType());
                                            original = original.plusWeeks(1);
                                        } else {
                                            original = original.plusDays(1);
                                        }
                                    }
                                } else if (end_date.isBefore(original)) {
                                    ArrayList<Lesson> lessons = lessonDAO.retrieveLessonsAfterDateAndBeforeCurr(endDate, cls.getClassID());

                                    for (Lesson l : lessons) {
                                        lessonDAO.deleteLesson(l.getLessonid());
                                    }

                                    //If the timing of the classes changes
                                    if (!startTime.equals(cls.getStartTime()) && !endTime.equals(cls.getEndTime())) {
                                        lessons = lessonDAO.retrieveAllLessonListsAfterCurr(cls.getClassID());

                                        //Changes the timing of the lessons after current date that were already inserted
                                        for (Lesson l : lessons) {
                                            if (sameTutor) {
                                                lessonDAO.updateLesson(0, lessonID, l.getLessonDate() + " " + startTime, l.getLessonDate() + " " + endTime);
                                            } else {
                                                lessonDAO.updateLesson(tutorID, lessonID, l.getLessonDate() + " " + startTime, l.getLessonDate() + " " + endTime);
                                            }
                                        }
                                    }
                                }
                            }
                            classDAO.updateClass(tutorID, startTime, endTime, startDate, endDate, cls.getClassID());

                            int reminder = cls.getHasReminderForFees();

                            if (reminder == 1) {
                                ArrayList<Lesson> allLessons = lessonDAO.retrieveAllLessonLists(cls.getClassID());

                                int num = 0;
                                String type = cls.getType();
                                
                                HashMap<Integer, Integer> reminders = new HashMap<>();
                                HashMap<Integer, Integer> premiumReminders = new HashMap<>();
                                reminders.put(3, 3);
                                reminders.put(7, 4);
                                reminders.put(10, 3);
                                reminders.put(13, 3);
                                reminders.put(17, 4);
                                reminders.put(20, 3);
                                reminders.put(23, 3);
                                reminders.put(27, 4);
                                reminders.put(30, 3);
                                reminders.put(33, 3);
                                reminders.put(37, 4);
                                reminders.put(40, 3);
                                reminders.put(43, 3);

                                if (type.equals("P")) {
                                    premiumReminders.put(11, 11);
                                    premiumReminders.put(22, 11);
                                    premiumReminders.put(33, 11);
                                    premiumReminders.put(44, 11);
                                }

//                                ArrayList<Integer> reminders = new ArrayList<>();
//                                ArrayList<Integer> premiumReminders = new ArrayList<>();
//
//                                reminders.add(3);
//                                reminders.add(7);
//                                reminders.add(10);
//                                reminders.add(13);
//                                reminders.add(17);
//                                reminders.add(20);
//                                reminders.add(23);
//                                reminders.add(27);
//                                reminders.add(30);
//                                reminders.add(33);
//                                reminders.add(37);
//                                reminders.add(40);
//                                reminders.add(43);
//
//                                if (type.equals("P")) {
//                                    premiumReminders.add(11);
//                                    premiumReminders.add(22);
//                                    premiumReminders.add(33);
//                                    premiumReminders.add(44);
//                                }
                                
                                for (Lesson l : allLessons) {
                                    num++;
                                    
                                    if(reminders.get(num) == null){
                                        lessonDAO.updatePaymentStatus(l.getLessonid(), "N", 0);
                                    }else{
                                        lessonDAO.updatePaymentStatus(l.getLessonid(), "N", reminders.get(num));
                                    }

                                    if (type.equals("P")) {
                                        if(premiumReminders.get(num) == null){
                                            lessonDAO.updatePaymentStatus(l.getLessonid(), type, 0);
                                        }else{
                                            lessonDAO.updatePaymentStatus(l.getLessonid(), type, reminders.get(num));
                                        }
                                    }
                                }

//                                for (Lesson l : allLessons) {
//                                    num++;
//                                    
//                                    if (reminders.contains(num)) {
//                                        lessonDAO.updatePaymentStatus(l.getLessonid(), "N", num);
//                                    } else {
//                                        lessonDAO.updatePaymentStatus(l.getLessonid(), "N", 0);
//                                    }
//
//                                    if (type.equals("P")) {
//                                        if (premiumReminders.contains(num)) {
//                                            lessonDAO.updatePaymentStatus(l.getLessonid(), type, num);
//                                        } else {
//                                            lessonDAO.updatePaymentStatus(l.getLessonid(), type, 0);
//                                        }
//                                    }
//                                }
                            }

                            obj.put("status", true);
                        } else {
                            obj.put("status", false);
                        }
                    } else {
                        obj.put("status", false);
                    }
                    out.println(obj.toString());
                    break;
                }
                case "delete": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    boolean status = new LessonDAO().deleteLesson(lessonID);
                    JSONObject obj = new JSONObject();
                    obj.put("status", status);
                    String json = obj.toString();
                    out.println(json);
                    break;
                }
                case "deleteClass": {
                    boolean status = false;
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));

                    Lesson lesson = lessonDAO.getLessonByID(lessonID);
                    Class cls = classDAO.getClassByID(lesson.getClassid());

                    boolean deleteClass = classDAO.deleteClass(cls.getClassID());
                    boolean deleteLessons = lessonDAO.deleteLessons(cls.getClassID());
                    boolean deleteRelations = classDAO.deleteClassStudentRel(cls.getClassID());

                    if (deleteClass && deleteLessons && deleteRelations) {
                        status = true;
                    }

                    JSONObject obj = new JSONObject();
                    obj.put("status", status);
                    String json = obj.toString();
                    out.println(json);
                    break;
                }
                case "retrieveOptions": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));

                    JSONArray levelOptions = new JSONArray();
                    JSONArray subjectOptions = new JSONArray();

                    ArrayList<Level> levels = levelDAO.retrieveAllLevelLists();
                    ArrayList<Subject> subjects = subjectDAO.retrieveSubjectsByLevel(1);

                    for (Subject s : subjects) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", s.getSubjectId());
                        obj.put("name", s.getSubjectName());
                        subjectOptions.put(obj);
                    }

                    for (Level l : levels) {
                        JSONObject obj = new JSONObject();
                        obj.put("name", l.getLevelName());
                        obj.put("id", l.getLevel_id());
                        levelOptions.put(obj);
                    }

                    JSONObject toReturn = new JSONObject();
                    toReturn.put("level", levelOptions);
                    toReturn.put("subject", subjectOptions);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
                case "retrieveSubjectOptions": {
                    int levelID = Integer.parseInt(request.getParameter("levelID"));

                    JSONArray subjectOptions = new JSONArray();
                    ArrayList<Subject> subjects = subjectDAO.retrieveSubjectsByLevel(levelID);

                    for (Subject s : subjects) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", s.getSubjectId());
                        obj.put("name", s.getSubjectName());
                        subjectOptions.put(obj);
                    }

                    JSONObject toReturn = new JSONObject();
                    toReturn.put("subject", subjectOptions);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
                case "retrieveTutorOptions": {
                    ArrayList<Tutor> tutorList = new ArrayList<>();
                    
                    String levels = request.getParameter("levels");
                    String[] levelsList = levels.split(",");
                    
                    int subjectID = Integer.parseInt(request.getParameter("subjectID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    
                    JSONArray tutors = new JSONArray();

                    for(String level: levelsList){
                        int levelID = Integer.parseInt(level);
                        
                        ArrayList<Tutor> tutor = TutorHourlyRateDAO.tutorListInPayTable(branchID, subjectID, levelID);
                        
                        for(Tutor t: tutor){
                            if(!tutorList.contains(t)){
                                tutorList.add(t);
                            }
                        }
                    }
                    
                    for (Tutor t : tutorList) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", t.getTutorId());
                        obj.put("name", t.getName());
                        tutors.put(obj);
                    }

                    JSONObject toReturn = new JSONObject();
                    toReturn.put("tutor", tutors);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
                case "create": {
                    String levels = request.getParameter("levels");
                    List<String> levelList = Arrays.asList(levels.split(","));

                    String holidays = request.getParameter("holidays");

                    if (holidays == null) {
                        holidays = "";
                    }

                    List<String> holidayDates = Arrays.asList(holidays.split(","));

                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                    int subjectID = Integer.parseInt(request.getParameter("subjectID"));
                    String startDate = request.getParameter("startDate");
                    String startTime = request.getParameter("startTime");
                    String endDate = request.getParameter("endDate");
                    String endTime = request.getParameter("endTime");
                    String reminder = request.getParameter("reminder");
                    String type = request.getParameter("classType");

                    JSONObject obj = new JSONObject();
                    if (startTime == null || startTime.isEmpty()) {
                        obj.put("start_time_error", "Please enter a start time!");
                    }

                    if (endTime == null || endTime.isEmpty()) {
                        obj.put("end_time_error", "Please enter an end time!");
                    }

                    if (startDate == null || startDate.isEmpty()) {
                        obj.put("start_date_error", "Please enter a start date!");
                    }

                    if (endDate == null || endDate.isEmpty()) {
                        obj.put("end_date_error", "Please enter an end date!");
                    }

                    if (type == null || type.isEmpty()) {
                        obj.put("type_error", "Please select a class type!");
                    }

                    if (obj.length() == 0) {
                        DateTimeFormatter pattern = DateTimeFormat.forPattern("HH:mm:ss");
                        DateTime start_time = pattern.parseDateTime(startTime);
                        DateTime end_time = pattern.parseDateTime(endTime);

                        DateTimeFormatter date = DateTimeFormat.forPattern("yyyy-MM-dd");
                        DateTime start_date = date.parseDateTime(startDate);
                        DateTime end_date = date.parseDateTime(endDate);

                        if (start_time.isEqual(end_time) || start_time.isAfter(end_time)) {
                            obj.put("invalid_timing", "The end timing has to be after the start timing!");
                        }

                        if (start_date.isAfter(end_date)) {
                            obj.put("invalid_timing", "The end timing has to be after the start timing!");
                        }

                        if (obj.length() == 0) {
                            int levelID = Integer.parseInt(levelList.get(0));
                            System.out.println(levelID + " YAS");
                            boolean insertLesson = false;
                            boolean insertClass = false;
                            int payment = 0;

                            int day = start_date.getDayOfWeek();

                            String dayOfWeek = "";
                            switch (day) {
                                case 1:
                                    dayOfWeek = "Mon";
                                    break;
                                case 2:
                                    dayOfWeek = "Tue";
                                    break;
                                case 3:
                                    dayOfWeek = "Wed";
                                    break;
                                case 4:
                                    dayOfWeek = "Thur";
                                    break;
                                case 5:
                                    dayOfWeek = "Fri";
                                    break;
                                case 6:
                                    dayOfWeek = "Sat";
                                    break;
                                case 7:
                                    dayOfWeek = "Sun";
                                    break;
                                default:
                                    break;
                            }

                            if (reminder.equals("on")) {
                                payment = 1;
                            }

                            int dayInserted = 0;

                            if (day == 7) {
                                dayInserted = 1;
                            } else {
                                dayInserted = day + 1;
                            }

                            double fees = subjectDAO.retrieveSubjectFees(subjectID, levelID, branchID);
                            boolean overlap = classDAO.retrieveOverlappingClassesForTutor(tutorID, pattern.print(start_time), pattern.print(end_time.minusMinutes(1)), 0, dayInserted);

                            if (!overlap) {
                                int classID = 0;

                                if (levelList.size() > 1) {
                                    classID = classDAO.createClass(type, levelID, subjectID, fees, payment, pattern.print(start_time), pattern.print(end_time.minusMinutes(1)), dayOfWeek, date.print(start_date), date.print(end_date), branchID, tutorID, holidays, levels, true);
                                } else {
                                    classID = classDAO.createClass(type, levelID, subjectID, fees, payment, pattern.print(start_time), pattern.print(end_time.minusMinutes(1)), dayOfWeek, date.print(start_date), date.print(end_date), branchID, tutorID, holidays,"-1", false);
                                }
                                
                                HashMap<Integer, Integer> reminders = new HashMap<>();
                                HashMap<Integer, Integer> premiumReminders = new HashMap<>();
                                reminders.put(3, 3);
                                reminders.put(7, 4);
                                reminders.put(10, 3);
                                reminders.put(13, 3);
                                reminders.put(17, 4);
                                reminders.put(20, 3);
                                reminders.put(23, 3);
                                reminders.put(27, 4);
                                reminders.put(30, 3);
                                reminders.put(33, 3);
                                reminders.put(37, 4);
                                reminders.put(40, 3);
                                reminders.put(43, 3);

                                if (type.equals("P")) {
                                    premiumReminders.put(11, 11);
                                    premiumReminders.put(22, 11);
                                    premiumReminders.put(33, 11);
                                    premiumReminders.put(44, 11);
                                }

//                                ArrayList<Integer> reminders = new ArrayList<>();
//                                ArrayList<Integer> premiumReminders = new ArrayList<>();
//
//                                reminders.add(3);
//                                reminders.add(7);
//                                reminders.add(10);
//                                reminders.add(13);
//                                reminders.add(17);
//                                reminders.add(20);
//                                reminders.add(23);
//                                reminders.add(27);
//                                reminders.add(30);
//                                reminders.add(33);
//                                reminders.add(37);
//                                reminders.add(40);
//                                reminders.add(43);
//
//                                if (type.equals("P")) {
//                                    premiumReminders.add(11);
//                                    premiumReminders.add(22);
//                                    premiumReminders.add(33);
//                                    premiumReminders.add(44);
//                                }

                                if (classID != 0) {
                                    insertClass = true;
                                    int numLesson = 0;
                                    LinkedList<DateTime> weeklyLessons = new LinkedList<>();

                                    while (start_date.isBefore(end_date) || start_date.isEqual(end_date)) {
                                        boolean reachedDay = false;
                                        if (start_date.getDayOfWeek() == day) {
                                            if (!holidayDates.contains(date.print(start_date))) {
                                                weeklyLessons.add(start_date);
                                                reachedDay = true;
                                            }
                                        }

                                        if (reachedDay) {
                                            start_date = start_date.plusWeeks(1);
                                        }
                                    }
                                    System.out.println(weeklyLessons.toString());
                                    ArrayList<Lesson> les = new ArrayList<>();

                                    for (DateTime t : weeklyLessons) {
                                        int reminder_status = 0;
                                        numLesson++;
                                        String lessonStart = date.print(t) + " " + startTime;
                                        String lessonEnd = date.print(t) + " " + endTime;
                                        
                                        if (type.equals("N")) {
                                            if(reminders.get(numLesson) == null){
                                                les.add(new Lesson(classID, tutorID, lessonStart, lessonEnd, 0));
                                            }else{
                                                les.add(new Lesson(classID, tutorID, lessonStart, lessonEnd, reminders.get(numLesson)));
                                            }
                                            
                                        } else {                                           
                                            Lesson less = null;
                                            if(reminders.get(numLesson) == null){
                                                less = new Lesson(classID, tutorID, lessonStart, lessonEnd, 0);
                                            }else{
                                                less = new Lesson(classID, tutorID, lessonStart, lessonEnd, reminders.get(numLesson));
                                            }
                                            
                                            if(premiumReminders.get(numLesson) == null){
                                                less.setReminderTerm(0);
                                            }else{
                                                less.setReminderTerm(premiumReminders.get(numLesson));
                                            }
                                            les.add(less);
                                        }

//                                        if (type.equals("N")) {
//                                            if (reminders.contains(numLesson)) {
//                                                les.add(new Lesson(classID, tutorID, lessonStart, lessonEnd, numLesson));
//                                            } else {
//                                                les.add(new Lesson(classID, tutorID, lessonStart, lessonEnd, 0));
//                                            }
//                                        } else {
//                                            Lesson less = null;
//                                            if (reminders.contains(numLesson)) {
//                                                less = new Lesson(classID, tutorID, lessonStart, lessonEnd, numLesson);
//                                            } else {
//                                                less = new Lesson(classID, tutorID, lessonStart, lessonEnd, 0);
//                                            }
//
//                                            if (premiumReminders.contains(numLesson)) {
//                                                less.setReminderTerm(numLesson);
//                                            } else {
//                                                less.setReminderTerm(0);
//                                            }
//                                            les.add(less);
//                                        }
                                    }

                                    if (weeklyLessons.size() == les.size()) {
                                        for (Lesson le : les) {
                                            lessonDAO.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), le.getReminderStatus(), le.getReminderTerm(), type);
//                                            if (le.getReminderTerm() != 0 && le.getReminderStatus() != 0) {
//                                                lessonDAO.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), le.getReminderTerm(), le.getReminderStatus(), type);
//                                            } else if (le.getReminderStatus() != 0) {
//                                                lessonDAO.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), le.getReminderStatus(), 0, type);
//                                            } else if (le.getReminderTerm() != 0) {
//                                                lessonDAO.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), 0, le.getReminderTerm(), type);
//                                            } else {
//                                                lessonDAO.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), 0, 0, type);
//                                            }
                                        }
                                        obj.put("status", true);
                                    } else {
                                        obj.put("overlap", "The tutor is not available at this timing!");
                                        obj.put("status", false);
                                    }
                                } else {
                                    obj.put("status", false);
                                }
                            } else {
                                obj.put("overlap", "The tutor is not available at this timing!");
                                obj.put("status", false);
                            }

                            String json = obj.toString();
                            out.println(json);
                        } else {
                            obj.put("overlap", "The selected tutor is not available at this timing!");
                            out.println(obj.toString());
                        }
                    } else {
                        out.println(obj.toString());
                    }
                    break;
                }
                case "retrieveByLevel": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    int levelID = Integer.parseInt(request.getParameter("levelID"));

                    JSONArray array = new JSONArray();
                    ArrayList<Class> classes = null;

                    if (levelID != 0) {
                        classes = ClassDAO.getClassesByLevel(levelID, branchID);
                    } else {
                        classes = ClassDAO.listAllClasses(branchID);
                    }

                    for (Class c : classes) {
                        ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonLists(c.getClassID());

                        for (Lesson l : lessons) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", l.getLessonid());
                            obj.put("start_date", l.getStartDate());
                            obj.put("end_date", l.getEndDate());
                            obj.put("text", c.getLevel() + " " + c.getSubject());
                            array.put(obj);
                        }
                    }

                    JSONObject toReturn = new JSONObject().put("data", array);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
                case "editOptions": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    Lesson lesson = lessonDAO.getLessonByID(lessonID);
                    Class cls = classDAO.getClassByID(lesson.getClassid());

                    String lessonStart = lesson.getStartDate().split(" ")[1];
                    String lessonEnd = lesson.getEndDate().split(" ")[1];

                    JSONObject obj = new JSONObject();
                    obj.put("timing", lessonStart.substring(0, lessonStart.length() - 2) + "-" + lessonEnd.substring(0, lessonEnd.length() - 2));
                    
                    System.out.println(cls.getCombinedLevel() + " YAYYY " + cls.getLevel());
                    if(cls.getCombinedLevel() == null || cls.getCombinedLevel().equals("")){    
                        obj.put("level", cls.getLevel());
                    }else{
                        obj.put("level", cls.getCombinedLevel());
                    }
                    obj.put("subject", cls.getSubject());
                    String json = obj.toString();
                    out.println(json);
                    break;
                }
                default:
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
