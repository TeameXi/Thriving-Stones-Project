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
            } else if (action.equals("retrieveLesson")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));

                JSONArray tutorList = new JSONArray();

                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                Class cls = ClassDAO.getClassByID(lesson.getClassid());
                ArrayList<Tutor> tutors = TutorHourlyRateDAO.tutorListInPayTable(branchID, cls.getSubjectID(), LevelDAO.retrieveLevelID(cls.getLevel()));

                int lessonTutor = 0;

                JSONObject defaultTutor = new JSONObject();
                if (new LessonDAO().retrieveReplacementTutor(lessonID) == 0) {
                    Tutor defaultT = new TutorDAO().retrieveSpecificTutorById(lesson.getTutorid());
                    lessonTutor = lesson.getTutorid();
                    defaultTutor.put("id", defaultT.getTutorId());
                    defaultTutor.put("name", defaultT.getName());
                    tutorList.put(defaultTutor);
                } else {
                    Tutor defaultT = new TutorDAO().retrieveSpecificTutorById(new LessonDAO().retrieveReplacementTutor(lessonID));
                    lessonTutor = defaultT.getTutorId();
                    defaultTutor.put("id", defaultT.getTutorId());
                    defaultTutor.put("name", defaultT.getName());
                    tutorList.put(defaultTutor);
                }

                for (Tutor t : tutors) {
                    String start = lesson.getStartDate();
                    String end = lesson.getEndDate();
                    if (lessonTutor != t.getTutorId()) {
                        boolean overlap = new LessonDAO().retrieveOverlappingLessonsForTutor(t.getTutorId(), start, end, cls.getClassID());

                        if (!overlap) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", t.getTutorId());
                            obj.put("name", t.getName());
                            tutorList.put(obj);
                        }
                    }
                }

                DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime startFormat = pattern.parseDateTime(lesson.getStartDate().substring(0, lesson.getStartDate().length() - 2));
                DateTime endFormat = pattern.parseDateTime(lesson.getEndDate().substring(0, lesson.getEndDate().length() - 2));

                JSONObject obj = new JSONObject();
                obj.put("start", pattern.print(startFormat));
                obj.put("end", pattern.print(endFormat));
                obj.put("tutor", lesson.getTutorid());
                obj.put("tutors", tutorList);
                String json = obj.toString();
                out.println(json);
            } else if (action.equals("updateLesson")) {
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

                if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
                    DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                    DateTime startFormat = pattern.parseDateTime(start);
                    DateTime endFormat = pattern.parseDateTime(end);

                    if (startFormat.isAfter(endFormat)) {
                        obj.put("invalid_timing", "Please select an end timing that occurs after the start timing!");
                    }
                }

                if (obj.length() != 0) {
                    obj.put("status", false);

                    out.println(obj.toString());
                } else {

                    LessonDAO l = new LessonDAO();
                    Lesson lesson = LessonDAO.getLessonByID(lessonID);

                    boolean overlap = l.retrieveOverlappingLessonsForTutor(tutorID, start, end, lesson.getClassid());

                    boolean status = false;

                    if (tutorID == lesson.getTutorid()) {
                        tutorID = 0;
                    }

                    if (!overlap) {
                        status = l.updateLessonDate(lessonID, tutorID, start, end);
                    } else {
                        obj.put("invalid_tutor", "The tutor selected is not available in the specified timing.");
                    }

                    obj.put("status", status);
                    String json = obj.toString();
                    out.println(json);
                }
            } else if (action.equals("retrieveClass")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));

                JSONArray tutorList = new JSONArray();

                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                Class cls = ClassDAO.getClassByID(lesson.getClassid());
                ArrayList<Tutor> tutors = TutorHourlyRateDAO.tutorListInPayTable(branchID, cls.getSubjectID(), LevelDAO.retrieveLevelID(cls.getLevel()));

                JSONObject defaultTutor = new JSONObject();
                int lessonTutor = 0;
                if (new LessonDAO().retrieveReplacementTutor(lessonID) == 0) {
                    Tutor defaultT = new TutorDAO().retrieveSpecificTutorById(lesson.getTutorid());
                    lessonTutor = lesson.getTutorid();
                    defaultTutor.put("id", defaultT.getTutorId());
                    defaultTutor.put("name", defaultT.getName());
                    tutorList.put(defaultTutor);
                } else {
                    Tutor defaultT = new TutorDAO().retrieveSpecificTutorById(new LessonDAO().retrieveReplacementTutor(lessonID));
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
                        boolean overlap = new ClassDAO().retrieveOverlappingClassesForTutor(t.getTutorId(), start, end, lesson.getClassid(), startFormat.getDayOfWeek());

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
            } else if (action.equals("updateClass")) {
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

                    if (start_date.isEqual(end_date) || start_date.isAfter(end_date)) {
                        obj.put("invalid_date", "The end date has to be after the start date!");
                    }

                    if (obj.length() == 0) {
                        LessonDAO l = new LessonDAO();
                        ClassDAO c = new ClassDAO();
                        Lesson lesson = LessonDAO.getLessonByID(lessonID);
                        Class cls = c.getClassByID(lesson.getClassid());
                        String type = cls.getType();
                        int reminder = cls.getHasReminderForFees();
                        DateTime defaultS = start_date;
                        LinkedList<DateTime> weeklyLessons = new LinkedList<>();
                        int day = start_date.getDayOfWeek();
                        boolean reachedDay = false;

                        while (start_date.isBefore(end_date)) {
                            if (start_date.getDayOfWeek() == day) {
                                weeklyLessons.add(start_date);
                                reachedDay = true;
                            }

                            if (reachedDay) {
                                start_date = start_date.plusWeeks(1);
                            }
                        }

                        ArrayList<Integer> reminders = new ArrayList<>();
                        ArrayList<Integer> premiumReminders = new ArrayList<>();

                        if (reminder == 1) {
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

                            if (type.equals("P")) {
                                premiumReminders.add(11);
                                premiumReminders.add(22);
                                premiumReminders.add(33);
                                premiumReminders.add(44);
                            }
                        }

                        int numLesson = 0;
                        ArrayList<Lesson> toBeAdded = new ArrayList<>();

                        for (DateTime d : weeklyLessons) {
                            numLesson++;
                            String lessonStart = date.print(d) + " " + pattern.print(start_time);
                            String lessonEnd = date.print(d) + " " + pattern.print(end_time);

                            boolean overlap = l.retrieveOverlappingLessonsForTutor(tutorID, lessonStart, lessonEnd, cls.getClassID());
                            if (!overlap) {
                                if (type.equals("N")) {
                                    if (reminders.contains(numLesson)) {

                                        toBeAdded.add(new Lesson(cls.getClassID(), tutorID, lessonStart, lessonEnd, numLesson));
                                    } else {
                                        toBeAdded.add(new Lesson(cls.getClassID(), tutorID, lessonStart, lessonEnd, 0));
                                    }
                                } else {
                                    Lesson less = null;
                                    if (reminders.contains(numLesson)) {
                                        less = new Lesson(cls.getClassID(), tutorID, lessonStart, lessonEnd, numLesson);
                                    } else {
                                        less = new Lesson(cls.getClassID(), tutorID, lessonStart, lessonEnd, 0);
                                    }

                                    if (premiumReminders.contains(numLesson)) {
                                        less.setReminderTerm(numLesson);
                                    } else {
                                        less.setReminderTerm(0);
                                    }
                                    toBeAdded.add(less);
                                }
                            }
                        }

                        if (weeklyLessons.size() == toBeAdded.size()) {
                            l.deleteLessons(cls.getClassID());
                            for (Lesson les : toBeAdded) {
                                if (les.getReminderTerm() != 0 && les.getReminderStatus() != 0) {
                                    l.createLesson(les.getClassid(), tutorID, les.getStartDate(), les.getEndDate(), les.getReminderTerm(), les.getReminderStatus(), type);
                                } else if (les.getReminderStatus() != 0) {
                                    l.createLesson(les.getClassid(), tutorID, les.getStartDate(), les.getEndDate(), les.getReminderStatus(), 0, type);
                                } else if (les.getReminderTerm() != 0) {
                                    l.createLesson(les.getClassid(), tutorID, les.getStartDate(), les.getEndDate(), 0, les.getReminderTerm(), type);
                                } else {
                                    l.createLesson(les.getClassid(), tutorID, les.getStartDate(), les.getEndDate(), 0, 0, type);
                                }
                            }
                            c.updateClass(tutorID, LevelDAO.retrieveLevelID(cls.getLevel()), cls.getSubjectID(), pattern.print(start_time), pattern.print(end_time), date.print(defaultS), date.print(end_date));
                            obj.put("status", true);
                        } else {
                            obj.put("invalid_tutor", "The tutor is not available at this timing!");
                            obj.put("status", false);
                        }
                    } else {
                        obj.put("status", false);
                    }
                } else {
                    obj.put("status", false);
                }
                out.println(obj.toString());
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
                boolean deleteRelations = c.deleteClassStudentRel(cls.getClassID());

                if (deleteClass && deleteLessons && deleteRelations) {
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

                    if (start_date.isEqual(end_date) || start_date.isAfter(end_date)) {
                        obj.put("invalid_date", "The end date has to be after the start date!");
                    }

                    if (obj.length() == 0) {
                        ClassDAO c = new ClassDAO();
                        LessonDAO l = new LessonDAO();
                        SubjectDAO s = new SubjectDAO();

                        boolean insertLesson = false;
                        boolean insertClass = false;
                        int recur = 0;
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
                        if (recurring.equals("on")) {
                            recur = 1;
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

                        double fees = s.retrieveSubjectFees(subjectID, levelID, branchID);
                        boolean overlap = c.retrieveOverlappingClassesForTutor(tutorID, pattern.print(start_time), pattern.print(end_time), levelID, dayInserted);

                        if (!overlap) {
                            int classID = c.createClass(type, levelID, subjectID, fees, payment, pattern.print(start_time), pattern.print(end_time), dayOfWeek, date.print(start_date), date.print(end_date), branchID, tutorID, holidays);

                            ArrayList<Integer> reminders = new ArrayList<>();
                            ArrayList<Integer> premiumReminders = new ArrayList<>();

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

                            if (type.equals("P")) {
                                premiumReminders.add(11);
                                premiumReminders.add(22);
                                premiumReminders.add(33);
                                premiumReminders.add(44);
                            }

                            if (classID != 0) {
                                if (recur != 0) {
                                    insertClass = true;
                                    int numLesson = 0;
                                    LinkedList<DateTime> weeklyLessons = new LinkedList<>();
                                    boolean reachedDay = false;

                                    while (start_date.isBefore(end_date)) {
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

                                    ArrayList<Lesson> les = new ArrayList<>();

                                    for (DateTime t : weeklyLessons) {
                                        int reminder_status = 0;
                                        numLesson++;
                                        String lessonStart = date.print(t) + " " + startTime;
                                        String lessonEnd = date.print(t) + " " + endTime;

                                        if (type.equals("N")) {
                                            if (reminders.contains(numLesson)) {
                                                les.add(new Lesson(classID, tutorID, lessonStart, lessonEnd, numLesson));
                                            } else {
                                                les.add(new Lesson(classID, tutorID, lessonStart, lessonEnd, 0));
                                            }
                                        } else {
                                            Lesson less = null;
                                            if (reminders.contains(numLesson)) {
                                                less = new Lesson(classID, tutorID, lessonStart, lessonEnd, numLesson);
                                            } else {
                                                less = new Lesson(classID, tutorID, lessonStart, lessonEnd, 0);
                                            }

                                            if (premiumReminders.contains(numLesson)) {
                                                less.setReminderTerm(numLesson);
                                            } else {
                                                less.setReminderTerm(0);
                                            }
                                            les.add(less);
                                        }
                                    }

                                    if (weeklyLessons.size() == les.size()) {
                                        for (Lesson le : les) {
                                            if (le.getReminderTerm() != 0 && le.getReminderStatus() != 0) {
                                                l.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), le.getReminderTerm(), le.getReminderStatus(), type);
                                            } else if (le.getReminderStatus() != 0) {
                                                l.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), le.getReminderStatus(), 0, type);
                                            } else if (le.getReminderTerm() != 0) {
                                                l.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), 0, le.getReminderTerm(), type);
                                            } else {
                                                l.createLesson(le.getClassid(), tutorID, le.getStartDate(), le.getEndDate(), 0, 0, type);
                                            }
                                        }
                                        obj.put("status", true);
                                    } else {
                                        obj.put("overlap", "The tutor is not available at this timing!");
                                        obj.put("status", false);
                                    }
                                } else {
                                    String lessonStart = date.print(start_date) + " " + startTime;
                                    String lessonEnd = date.print(end_date) + " " + endTime;
                                    l.createLesson(classID, tutorID, lessonStart, lessonEnd, 0, 0, type);
                                    obj.put("status", true);
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

            } else if (action.equals("retrieveByLevel")) {
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
            } else if (action.equals("editOptions")) {
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
