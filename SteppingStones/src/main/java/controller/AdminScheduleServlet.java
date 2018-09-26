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

            if (action.equals("retrieve")) {
                int selectedLevel = Integer.parseInt(request.getParameter("selectedLevel"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                JSONArray array = new JSONArray();
                ArrayList<Class> classes = new ArrayList<>();

                if (selectedLevel == 0) {
                    classes = ClassDAO.listAllClasses(branchID);
                } else {
                    classes = ClassDAO.getClassesByLevel(selectedLevel, branchID);
                }
                
                System.out.println(classes + " YAY");
                
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
                System.out.println(json);
                out.println(json);
            } else if (action.equals("retrieveLesson")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));

                JSONArray tutorList = new JSONArray();

                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                Class cls = ClassDAO.getClassByID(lesson.getClassid());
                ArrayList<Tutor> tutors = TutorHourlyRateDAO.tutorListInPayTable(branchID, cls.getSubjectID(), LevelDAO.retrieveLevelID(cls.getLevel()));

                for (Tutor t : tutors) {
                    boolean overlap = false;
                    String start = lesson.getStartDate();
                    String end = lesson.getEndDate();
                    if (lesson.getTutorid() != t.getTutorId()) {
                        overlap = new LessonDAO().retrieveOverlappingLessonsForTutor(t.getTutorId(), start, end, lessonID);
                    }

                    if (!overlap) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", t.getTutorId());
                        obj.put("name", t.getName());
                        tutorList.put(obj);
                    }
                }

                DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime startFormat = pattern.parseDateTime(lesson.getStartDate().substring(0, lesson.getStartDate().length() - 2));
                DateTime endFormat = pattern.parseDateTime(lesson.getEndDate().substring(0, lesson.getEndDate().length() - 2));

                JSONObject obj = new JSONObject();
                obj.put("start", pattern.print(startFormat));
                obj.put("end", pattern.print(endFormat));
                obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(lesson.getTutorid()).getTutorId());
                obj.put("tutors", tutorList);
                String json = obj.toString();
                out.println(json);
            } else if (action.equals("updateLesson")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int tutorID = Integer.parseInt(request.getParameter("tutor"));
                String start = request.getParameter("start");
                String end = request.getParameter("end");
                LessonDAO l = new LessonDAO();
                Lesson lesson = LessonDAO.getLessonByID(lessonID);

                boolean overlap = l.retrieveOverlappingLessonsForTutor(tutorID, start, end, lessonID);
                boolean status = false;

                if (tutorID == lesson.getTutorid()) {
                    tutorID = 0;
                }

                if (!overlap) {
                    status = l.updateLessonDate(lessonID, tutorID, start, end);
                }

                JSONObject obj = new JSONObject();
                obj.put("status", status);
                String json = obj.toString();
                out.println(json);
            } else if (action.equals("retrieveClass")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));

                JSONArray tutorList = new JSONArray();

                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                Class cls = ClassDAO.getClassByID(lesson.getClassid());
                ArrayList<Tutor> tutors = TutorHourlyRateDAO.tutorListInPayTable(branchID, cls.getSubjectID(), LevelDAO.retrieveLevelID(cls.getLevel()));

                for (Tutor t : tutors) {
                    boolean overlap = false;
                    String start = cls.getStartTime();
                    String end = cls.getEndTime();
                    String start_date = cls.getStartDate();
                    DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
                    DateTime startFormat = pattern.parseDateTime(start_date);
                    if (lesson.getTutorid() != t.getTutorId()) {
                        overlap = new ClassDAO().retrieveOverlappingClassesForTutor(t.getTutorId(), start, end, lesson.getClassid(), startFormat.getDayOfWeek());
                    }

                    if (!overlap) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", t.getTutorId());
                        obj.put("name", t.getName());
                        tutorList.put(obj);
                    }
                }

                JSONObject obj = new JSONObject();
                obj.put("start", cls.getStartDate());
                obj.put("endDate", cls.getEndDate());
                obj.put("end", cls.getEndTime());
                obj.put("startTime", cls.getStartTime());
                obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(lesson.getTutorid()).getTutorId());
                obj.put("tutors", tutorList);
                String json = obj.toString();
                out.println(json);
            } else if (action.equals("updateClass")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int tutorID = Integer.parseInt(request.getParameter("tutor"));
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");

                DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime start = pattern.parseDateTime(startDate);
                DateTime end = pattern.parseDateTime(endDate);

                LessonDAO l = new LessonDAO();
                ClassDAO c = new ClassDAO();
                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                Class cls = c.getClassByID(lesson.getClassid());
                String type = cls.getType();
                int reminder = cls.getHasReminderForFees();

                boolean overlap = c.retrieveOverlappingClassesForTutor(tutorID, startTime, endTime, lesson.getClassid(), start.getDayOfWeek());
                boolean status = false;

                if (!overlap) {
                    LinkedList<DateTime> weeklyLessons = new LinkedList<>();
                    int day = start.getDayOfWeek();
                    boolean reachedDay = false;

                    while (start.isBefore(end)) {
                        if (start.getDayOfWeek() == day) {
                            weeklyLessons.add(start);
                            reachedDay = true;
                        }

                        if (reachedDay) {
                            start = start.plusWeeks(1);
                        }
                    }

                    boolean deleted = l.deleteLessons(cls.getClassID());

                    if (reminder == 1) {
                        ArrayList<Integer> reminders = new ArrayList<>();

                        if (type.equals("N")) {
                            reminders.add(3);
                            reminders.add(7);
                            reminders.add(10);
                            reminders.add(13);
                            reminders.add(17);
                            reminders.add(20);
                            reminders.add(23);
                            reminders.add(27);
                            reminders.add(30);
                            reminders.add(33);
                            reminders.add(37);
                            reminders.add(40);
                            reminders.add(43);
                        } else {
                            reminders.add(11);
                            reminders.add(22);
                            reminders.add(33);
                            reminders.add(44);
                        }

                        if (deleted) {
                            int numLesson = 0;
                            for (DateTime d : weeklyLessons) {
                                numLesson++;
                                String start_date = pattern.print(d) + " " + startTime;
                                String end_date = pattern.print(d) + " " + endTime;

                                if (reminders.contains(numLesson)) {
                                    l.createLesson(cls.getClassID(), tutorID, start_date, end_date, numLesson, type);
                                } else {
                                    l.createLesson(cls.getClassID(), tutorID, start_date, end_date, 0, type);
                                }
                            }
                            status = c.updateClass(tutorID, LevelDAO.retrieveLevelID(cls.getLevel()), SubjectDAO.retrieveSubjectID(cls.getSubject()), startTime, endTime, pattern.print(start), pattern.print(end));
                        }
                    } else {
                        for (DateTime d : weeklyLessons) {
                            String start_date = pattern.print(d) + " " + startTime;
                            String end_date = pattern.print(d) + " " + endTime;
                            l.createLesson(cls.getClassID(), tutorID, start_date, end_date, 0, type);
                        }
                        status = c.updateClass(tutorID, LevelDAO.retrieveLevelID(cls.getLevel()), SubjectDAO.retrieveSubjectID(cls.getSubject()), startTime, endTime, pattern.print(start), pattern.print(end));
                    }
                }
                JSONObject obj = new JSONObject();
                obj.put("status", status);
                String json = obj.toString();
                out.println(json);
            } else if (action.equals("delete")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));

                boolean status = new LessonDAO().deleteLesson(lessonID);

                JSONObject obj = new JSONObject();
                obj.put("status", status);
                String json = obj.toString();

                out.println(json);
            } else if (action.equals("deleteClass")) {
                boolean status = false;

                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                ClassDAO c = new ClassDAO();
                LessonDAO l = new LessonDAO();
                Lesson lesson = l.getLessonByID(lessonID);
                Class cls = c.getClassByID(lesson.getClassid());

                boolean deleteClass = c.deleteClass(cls.getClassID());
                boolean deleteLessons = l.deleteLessons(cls.getClassID());

                if (deleteClass && deleteLessons) {
                    status = true;
                }

                JSONObject obj = new JSONObject();
                obj.put("status", status);
                String json = obj.toString();

                out.println(json);
            } else if (action.equals("retrieveOptions")) {
                int branchID = Integer.parseInt(request.getParameter("branchID"));

                JSONArray levelOptions = new JSONArray();
                JSONArray tutorOptions = new JSONArray();
                JSONArray subjectOptions = new JSONArray();
                ArrayList<Level> levels = new LevelDAO().retrieveAllLevelLists();
                ArrayList<Tutor> tutors = new TutorDAO().retrieveAllTutorsByBranch(branchID);
                ArrayList<Subject> subjects = new SubjectDAO().retrieveSubjectsByLevel(1);

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

                for (Tutor t : tutors) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", t.getName());
                    obj.put("id", t.getTutorId());
                    tutorOptions.put(obj);
                }

                JSONObject toReturn = new JSONObject();
                toReturn.put("level", levelOptions);
                toReturn.put("tutor", tutorOptions);
                toReturn.put("subject", subjectOptions);
                String json = toReturn.toString();
                out.println(json);
            } else if (action.equals("retrieveSubjectOptions")) {
                int levelID = Integer.parseInt(request.getParameter("levelID"));

                JSONArray subjectOptions = new JSONArray();
                ArrayList<Subject> subjects = new SubjectDAO().retrieveSubjectsByLevel(levelID);

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
            } else if (action.equals("create")) {
                boolean status = false;
                String holidays = request.getParameter("holidays");

                if (holidays == null) {
                    holidays = "";
                }

                List<String> holidayDates = Arrays.asList(holidays.split(","));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int levelID = Integer.parseInt(request.getParameter("levelID"));
                int subjectID = Integer.parseInt(request.getParameter("subjectID"));
                String startDate = request.getParameter("startDate");
                String startTime = request.getParameter("startTime");
                String endDate = request.getParameter("endDate");
                String endTime = request.getParameter("endTime");
                String recurring = request.getParameter("recurring");
                String reminder = request.getParameter("reminder");
                String type = request.getParameter("classType");

                DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime start = pattern.parseDateTime(startDate);
                DateTime end = pattern.parseDateTime(endDate);

                ClassDAO c = new ClassDAO();
                LessonDAO l = new LessonDAO();
                SubjectDAO s = new SubjectDAO();
                boolean overlap = c.retrieveOverlappingClassesForTutor(tutorID, startTime, endTime, 0, start.getDayOfWeek() - 1);
                boolean insertLesson = false;
                boolean insertClass = false;
                int recur = 0;
                int payment = 0;

                if (!overlap) {
                    int day = start.getDayOfWeek();
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
                    if (recurring.equals("on")) {
                        recur = 1;
                    }

                    if (reminder.equals("on")) {
                        payment = 1;
                    }

                    double fees = s.retrieveSubjectFees(subjectID, levelID, branchID);
                    int classID = c.createClass(type, levelID, subjectID, fees, payment, startTime, endTime, dayOfWeek, pattern.print(start), pattern.print(end), branchID, tutorID, holidays);
                    ArrayList<Integer> reminders = new ArrayList<>();

                    if (type.equals("N")) {
                        reminders.add(3);
                        reminders.add(7);
                        reminders.add(10);
                        reminders.add(13);
                        reminders.add(17);
                        reminders.add(20);
                        reminders.add(23);
                        reminders.add(27);
                        reminders.add(30);
                        reminders.add(33);
                        reminders.add(37);
                        reminders.add(40);
                        reminders.add(43);
                    } else {
                        reminders.add(11);
                        reminders.add(22);
                        reminders.add(33);
                        reminders.add(44);
                    }

                    if (classID != 0) {
                        if (recur != 0) {
                            insertClass = true;
                            int numLesson = 0;
                            LinkedList<DateTime> weeklyLessons = new LinkedList<>();
                            boolean reachedDay = false;

                            while (start.isBefore(end)) {
                                if (start.getDayOfWeek() == day) {
                                    if (!holidayDates.contains(pattern.print(start))) {
                                        weeklyLessons.add(start);
                                        reachedDay = true;
                                    }
                                }

                                if (reachedDay) {
                                    start = start.plusWeeks(1);
                                }
                            }
                            for (DateTime t : weeklyLessons) {
                                int reminder_status = 0;
                                numLesson++;
                                String lessonStart = pattern.print(t) + " " + startTime;
                                String lessonEnd = pattern.print(t) + " " + endTime;
                                
                                if (reminders.contains(numLesson)) {
                                    insertLesson = l.createLesson(classID, tutorID, lessonStart, lessonEnd, numLesson, type);
                                } else {
                                    insertLesson = l.createLesson(classID, tutorID, lessonStart, lessonEnd, 0, type);
                                }
                            }
                            
                        }else{
                            String lessonStart = pattern.print(pattern.parseDateTime(startDate)) + " " + startTime;
                            String lessonEnd = pattern.print(pattern.parseDateTime(startDate)) + " " + endTime;
                            insertLesson = l.createLesson(classID, tutorID, lessonStart, lessonEnd, 0, type);
                        }
                    } else {
                        insertClass = false;
                    }
                }
                JSONObject obj = new JSONObject();

                if (recur != 0 && insertLesson && insertClass) {
                    obj.put("status", true);
                } else if (recur == 0 && insertClass) {
                    obj.put("status", true);
                } else {
                    obj.put("status", false);
                }

                String json = obj.toString();
                out.println(json);
            } else if (action.equals("retrieveByLevel")) {
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                int levelID = Integer.parseInt(request.getParameter("levelID"));
                JSONArray array = new JSONArray();
                ArrayList<Class> classes = ClassDAO.getClassesByLevel(levelID, branchID);

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
            }else if(action.equals("editOptions")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                
                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                Class cls = ClassDAO.getClassByID(lesson.getClassid());
                String lessonStart = lesson.getStartDate().split(" ")[1];
                String lessonEnd = lesson.getEndDate().split(" ")[1];
                
                JSONObject obj = new JSONObject();
                obj.put("timing", lessonStart.substring(0, lessonStart.length() - 2) + "-" + lessonEnd.substring(0, lessonEnd.length() - 2));
                obj.put("level", cls.getLevel());
                obj.put("subject", cls.getSubject());
                String json = obj.toString();
                out.println(json);
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
