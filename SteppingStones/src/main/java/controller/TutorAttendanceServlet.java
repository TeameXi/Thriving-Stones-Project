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
import model.AttendanceDAO;
import org.joda.time.format.DateTimeFormat;

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

            if (action.equals("retrieve")) {
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                
                JSONArray array = new JSONArray();
                LessonDAO lessons = new LessonDAO();
                ArrayList<Tutor> tutors = new TutorDAO().retrieveAllTutorsByBranch(branchID);

                for (Tutor t : tutors) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", t.getTutorId());
                    obj.put("phone", t.getPhone());
                    obj.put("name", t.getName());
                    obj.put("attendance", lessons.retrieveTotalPercentageAttendance(t.getTutorId()) + "%");
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("retrieveClasses")){
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                
                LessonDAO lessonDAO = new LessonDAO();
                ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(tutorID, branchID);
                
                JSONArray array = new JSONArray();
                for (Class c : classes) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", c.getClassID());
                    obj.put("date", c.getClassDay() + " " +  c.getStartTime() + "-" + c.getEndTime());
                    obj.put("attendance", lessonDAO.retrieveNumberTutorAttendancePerClass(c.getClassID(), tutorID) + "%");
                    obj.put("level", c.getLevel());
                    obj.put("subject", c.getSubject());
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                System.out.println(json);
                out.println(json);
            }else if(action.equals("retrieveLessons")){
                int classID = Integer.parseInt(request.getParameter("classID"));
                
                LessonDAO lessonDAO = new LessonDAO();
                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonLists(classID);
                
                JSONArray array = new JSONArray();
                
                for(Lesson l: lessons){
                    JSONObject obj = new JSONObject();
                    String date = l.getStartDate();
                    obj.put("id", l.getLessonid());
                    obj.put("date", date.substring(0, date.indexOf(" ")));
                    
                    if(lessonDAO.retrieveAttendanceForLesson(l.getLessonid())){
                        obj.put("attended", "Present" + " <button class=\"btn btn-default _absent\">Absent</button>" );
                    }else if(lessonDAO.retrieveAttendanceForLessonAbsent(l.getLessonid())){
                        obj.put("attended", "Absent" + " <button class=\"btn btn-default _present\">Present</button>");
                        
                    }
                    
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                System.out.println(json);
                out.println(json);
            }else if(action.equals("mark")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int classID = Integer.parseInt(request.getParameter("classID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                
                boolean status = LessonDAO.updateTutorAttendance(lessonID, 1);
                String attendance = new LessonDAO().retrieveNumberTutorAttendancePerClass(classID, tutorID) + "%";
                
                JSONObject toReturn = new JSONObject().put("data", status);
                toReturn.put("attendance", attendance);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("markAbsent")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int classID = Integer.parseInt(request.getParameter("classID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                
                boolean status = LessonDAO.updateTutorAttendance(lessonID, -1);
                String attendance = new LessonDAO().retrieveNumberTutorAttendancePerClass(classID, tutorID) + "%";
                
                JSONObject toReturn = new JSONObject().put("data", status);
                toReturn.put("attendance", attendance);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("display")){
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                
                JSONArray array = new JSONArray();
                LessonDAO lessons = new LessonDAO();
                ClassDAO classDAO = new ClassDAO();
                ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(tutorID, branchID);
                
                for(Class c: classes){
                    JSONObject obj = new JSONObject();
                    obj.put("id", c.getClassID());
                    obj.put("name", c.getClassDay() + " " +  c.getStartTime() + "-" + c.getEndTime());
                    obj.put("size", classDAO.retrieveStudentsByClass(c.getClassID()).size());
                    obj.put("level", c.getLevel());
                    obj.put("subject", c.getSubject());
                    obj.put("attendance", lessons.retrieveNumberTutorAttendancePerClass(c.getClassID(), tutorID) + "%");
                    array.put(obj);
                }
                
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("displayLessons")){
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int classID = Integer.parseInt(request.getParameter("classID"));
                
                JSONArray array = new JSONArray();
                LessonDAO lessonDAO = new LessonDAO();
                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonListsBeforeCurr(classID);
                
                for(Lesson l:lessons){
                    JSONObject obj = new JSONObject();
                    obj.put("id", l.getLessonid());
                    String date = l.getStartDate();
                    obj.put("date", date.substring(0, date.indexOf(" ")));
                    
                    if(lessonDAO.retrieveAttendanceForLesson(l.getLessonid())){
                        obj.put("attendance", "Present");
                    } else {
                        obj.put("attendance", "Absent");
                    }
                    
                    obj.put("overall", lessonDAO.retrieveNumberTutorAttendancePerClass(classID, tutorID) + "%");
                    array.put(obj);
                }
                
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                System.out.println(json);
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
