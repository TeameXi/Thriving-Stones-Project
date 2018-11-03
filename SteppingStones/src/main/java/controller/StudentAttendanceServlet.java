/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Class;
import entity.Lesson;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AttendanceDAO;
import model.ClassDAO;
import model.LessonDAO;
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Riana
 */
@WebServlet(name = "StudentAttendanceServlet", urlPatterns = {"/StudentAttendanceServlet"})
public class StudentAttendanceServlet extends HttpServlet {

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
            AttendanceDAO a = new AttendanceDAO();

            switch (action) {
                case "retrieve": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    String tutorID = request.getParameter("tutorID");
                    ArrayList<Class> classes;
                    
                    if(tutorID != null){
                        int tutor = Integer.parseInt(tutorID);
                        classes = classDAO.listAllClassesByTutorID(tutor, branchID);
                    }else{
                        classes = classDAO.listAllClasses(branchID);
                    }
                    
                    JSONArray array = new JSONArray();
                    for (Class c : classes) {
                        boolean studentsExist = classDAO.checkForStudents(c.getClassID());
                        LinkedList<Lesson> lessons = lessonDAO.retrieveAllLessonListsBeforeCurr(c.getClassID());
                        
                        if(studentsExist && lessons.size() > 0){
                            JSONObject obj = new JSONObject();
                            obj.put("id", c.getClassID());
                   
                            obj.put("name", c.getClassDay() + " " + c.getStartTime() + "-" + c.getEndTime()
                                    + "<br/>" + c.getLevel() + " " + c.getSubject());

                            if(tutorID != null){
                                obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(Integer.parseInt(tutorID)).getName());
                            }else{
                                obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(c.getTutorID()).getName());
                            }
                            array.put(obj);
                        }
                    }
                    JSONObject obj = new JSONObject().put("data", array);
                    out.println(obj.toString());
                    break;
                }
                case "retrieveStudents": {
                    int classID = Integer.parseInt(request.getParameter("classID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    ArrayList<Student> students = classDAO.retrieveStudentsByClass(classID);
                    JSONArray array = new JSONArray();
                    for (Student s : students) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", s.getStudentID());
                        obj.put("name", s.getName());

                        JSONArray lessons = new JSONArray();
                        int index = 0;
                        LinkedList<Lesson> lessonList = lessonDAO.retrieveAllLessonListsBeforeCurr(classID);
               
                        for (Lesson l : lessonList) {
                            JSONObject lesson = new JSONObject();
                            lesson.put("id", l.getLessonid());
                            lesson.put("date", l.getLessonDate());
                            boolean attended = a.retrieveStudentAttendances(s.getStudentID(), l.getLessonid());
                            lesson.put("attended", attended);
                            lessons.put(index, lesson);
                            index++;
                        }
                        obj.put("lessons", lessons);

                        DecimalFormat formatter = new DecimalFormat("#.##");
                        String attendance = formatter.format(a.retrieveNumberOfStudentAttendances(s.getStudentID(), classID) / lessonDAO.retrieveNumberOfLessons(classID) * 100);

                        obj.put("attendance", attendance + "%");
                        array.put(obj);
                    }
                    
                    String json = array.toString();
                    System.out.println(json);
                    out.println(json);
                    break;
                }
                case "mark": {
                    String lessons = request.getParameter("lessons");
                    String[] lesson_student = lessons.split(" ");
                    int classID = Integer.parseInt(request.getParameter("classID"));
                    
                    String tutor = request.getParameter("tutorID");
                    int tutorID = 0;
                    if(tutor != null){
                        tutorID = Integer.parseInt(tutor);
                    }

                    int updated = 0;

                    JSONArray attendances = new JSONArray();

                    for (String s : lesson_student) {
                        int lessonID = Integer.parseInt(s.split("-")[0]);
                        int studentID = Integer.parseInt(s.split("-")[1]);
                        int attended = Integer.parseInt(s.split("-")[2]);

                        if (attended == 1) {
                            a.updateStudentAttendance(studentID, lessonID, classID, tutorID, true);
                            updated++;
                        } else {
                            a.updateStudentAttendance(studentID, lessonID, classID, tutorID, false);
                            updated++;
                        }

                        DecimalFormat formatter = new DecimalFormat("#.##");
                        String attendance = formatter.format(a.retrieveNumberOfStudentAttendances(studentID, classID) / lessonDAO.retrieveNumberOfLessons(classID) * 100);

                        for (int i = 0; i < attendances.length(); i++) {
                            JSONObject obj = attendances.getJSONObject(i);
                            
                            if(obj.has(studentID + "")){
                                attendances.remove(i);
                            }
                        }

                        attendances.put(new JSONObject().put(studentID + "", attendance + "%"));
                    }

                    JSONObject obj = new JSONObject();

                    if (updated == lesson_student.length) {
                        obj.put("data", true);
                    } else {
                        obj.put("data", false);
                    }

                    obj.put("attendance", attendances);
                    out.println(obj.toString());

                    break;
                }
                case "replacement": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                    
                    JSONArray array = new JSONArray();
                    ArrayList<Lesson> replacements = lessonDAO.retrieveReplacementLessons(branchID, tutorID);
                    
                    if(replacements != null){
                        for(Lesson l: replacements){
                            JSONObject obj = new JSONObject();
                            obj.put("id", l.getLessonid());

                            Class cls = classDAO.getClassByID(l.getClassid());
                            obj.put("name", cls.getClassDay() + " " + cls.getStartTime() + "-" + cls.getEndTime() 
                                    + "<br/>" + cls.getCombinedLevel() + " " + cls.getSubject());
                            obj.put("date", l.getLessonDate());
                            array.put(obj);
                        }
                    }
                    
                    JSONObject toReturn = new JSONObject().put("data", array);
                    out.println(toReturn.toString());
                    break;
                }
                case "replacementStudents": {
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    
                    JSONArray array = new JSONArray();
                    Lesson lesson = lessonDAO.getLessonByID(lessonID);
                    ArrayList<Student> students = classDAO.retrieveStudentsByClass(lesson.getClassid());
                    
                    for(Student s: students){
                        JSONObject obj = new JSONObject();
                        obj.put("id", s.getStudentID());
                        obj.put("name", s.getName());
                        obj.put("attended", a.retrieveStudentAttendances(s.getStudentID(), lessonID));
                        array.put(obj);
                    }
                    
                    out.println(array.toString());
                    break;
                }
                case "markReplacement": {
                    String students = request.getParameter("students");
                    String[] studentList = students.split(",");
                    int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                    
                    Lesson lesson = lessonDAO.getLessonByID(lessonID);
                    int updated = 0;

                    JSONArray attendances = new JSONArray();

                    for (String s : studentList) {
                        int attended = 0;
                        String[] status = s.split(" ");
                        int studentID = Integer.parseInt(status[0]);
                        
                        if(status[1].equals("true")){
                            attended = 1;
                        }
                        System.out.println(attended + " YAS " + studentID);
                        if (attended == 1) {
                            a.updateStudentAttendance(studentID, lessonID, lesson.getClassid(), tutorID, true);
                            updated++;
                        } else {
                            a.updateStudentAttendance(studentID, lessonID, lesson.getClassid(), tutorID, false);
                            updated++;
                        }

                    }

                    JSONObject obj = new JSONObject();

                    if (updated == studentList.length) {
                        obj.put("data", true);
                    } else {
                        obj.put("data", false);
                    }

                    out.println(obj.toString());
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
