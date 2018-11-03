package controller;

import entity.Lesson;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import entity.Class;
import java.util.Arrays;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "TutorAttendanceServlet", urlPatterns = {"/TutorAttendanceServlet"})
public class TutorAttendanceServlet extends HttpServlet {

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

            ClassDAO classDAO = new ClassDAO();
            LessonDAO lessonDAO = new LessonDAO();

            switch (action) {
                case "retrieve": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    JSONArray array = new JSONArray();

                    ArrayList<Tutor> tutors = new TutorDAO().retrieveAllTutorsByBranch(branchID);
                    for (Tutor t : tutors) {
                        ArrayList<Integer> classes = lessonDAO.checkForExistingLessons(t.getTutorId());
                        
                        if(classes != null){
                            JSONObject obj = new JSONObject();
                            obj.put("id", t.getTutorId());
                            obj.put("phone", t.getPhone());
                            obj.put("name", t.getName());
                            obj.put("attendance", lessonDAO.retrieveTotalPercentageAttendance(t.getTutorId()) + "%");
                            array.put(obj);
                        }
                    }
                    JSONObject toReturn = new JSONObject().put("data", array);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
                case "retrieveClasses": {
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));

                    ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(tutorID, branchID);
                    JSONArray array = new JSONArray();
                    for (Class c : classes) {
                        LinkedList<Lesson> lessonList = lessonDAO.retrieveAllLessonListsBeforeCurr(c.getClassID());

                        if (lessonList.size() > 0) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", c.getClassID());
                            obj.put("name", c.getClassDay() + " " + c.getStartTime() + "-"
                                    + c.getEndTime() + "<br/>" + c.getLevel() + " " + c.getSubject());

                            JSONArray lessons = new JSONArray();

                            for (Lesson l : lessonList) {
                                JSONObject lesson = new JSONObject();
                                lesson.put("id", l.getLessonid());
                                lesson.put("date", l.getLessonDate());
                                lesson.put("attended", lessonDAO.retrieveAttendanceForLesson(l.getLessonid()));
                                lessons.put(lesson);
                            }
                            obj.put("lessons", lessons);
                            array.put(obj);
                        }
                    }
                    String json = array.toString();
                    System.out.println(json);
                    out.println(json);
                    break;
                }
                case "mark": {
                    String lessons = request.getParameter("lessons");
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));

                    String[] lessonIDs = lessons.split(" ");
                    ArrayList<String> lessonList = new ArrayList<>(Arrays.asList(lessonIDs));

                    ArrayList<Lesson> ls = LessonDAO.retrieveLessonsByTutor(tutorID);
                    ArrayList<Integer> ids = new ArrayList<>();

                    for (Lesson l : ls) {
                        ids.add(l.getLessonid());
                    }

                    for (int id : ids) {
                        if (lessonList.contains(id + "")) {
                            LessonDAO.updateTutorAttendance(id, 1);
                        } else {
                            LessonDAO.updateTutorAttendance(id, 0);
                        }
                    }
                    JSONObject obj = new JSONObject().put("status", true);
                    obj.put("attendance", lessonDAO.retrieveTotalPercentageAttendance(tutorID) + "%");
                    out.println(obj.toString());
                    break;
                }
                case "display": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));

                    ArrayList<Class> classes = classDAO.listAllClassesByTutorID(tutorID, branchID);

                    JSONArray array = new JSONArray();
                    for (Class c : classes) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", c.getClassID());
                        System.out.println(c.getClassID());
                        obj.put("name", c.getClassDay() + " " + c.getStartTime() + "-" + c.getEndTime()
                                + "<br/>" + c.getLevel() + " " + c.getSubject());

                        obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(tutorID).getName());

                        array.put(obj);
                    }
                    JSONObject obj = new JSONObject().put("data", array);
                    out.println(obj.toString());
                    break;
                }
                case "displayAttendances": {
                    int classID = Integer.parseInt(request.getParameter("classID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    int tutorID = Integer.parseInt(request.getParameter("tutorID"));

                    JSONObject obj = new JSONObject();
                    JSONArray array = new JSONArray();

                    Class c = classDAO.getClassByID(classID);

                    obj.put("attendance", lessonDAO.retrieveNumberTutorAttendancePerClass(classID, tutorID) + "%");

                    LinkedList<Lesson> lessons = lessonDAO.retrieveAllLessonListsBeforeCurr(classID);

                    for (Lesson l : lessons) {
                        JSONObject lesson = new JSONObject();
                        lesson.put("name", l.getLessonDate());
                        lesson.put("attendance", lessonDAO.retrieveAttendanceForLesson(l.getLessonid()));
                        array.put(lesson);
                    }

                    obj.put("lessons", array);

                    String json = obj.toString();
                    System.out.println(json);
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
