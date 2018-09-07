package controller;

import entity.Lesson;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;
import model.SubjectDAO;
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

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
            System.out.println("HEREEEEE");
            JSONArray array = new JSONArray();
            
            String action = request.getParameter("action");
            System.out.println(action);
            if(action.equals("retrieve")){
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                LessonDAO lessons = new LessonDAO();
                ArrayList<Lesson> lessonList = lessons.retrieveLessonsByTutor(tutorID);

                ClassDAO classes = new ClassDAO();

                if(lessonList != null || !lessonList.isEmpty()){
                    for(Lesson l: lessonList){
                        int lessonID = l.getLessonid();
                        if(l.getTutorAttended() == 0){
                            String subject = classes.getClassByID(l.getClassid()).getSubject();
                            String date = l.getLessonDateTime().toString();
                            String level = classes.getClassByID(l.getClassid()).getLevel();


                            JSONObject obj = new JSONObject();
                            obj.put("lessonID", lessonID);
                            obj.put("date", date);
                            obj.put("subject", subject);
                            obj.put("level", level);

                            array.put(obj);
                        }
                    }
                    String json = array.toString();
                    System.out.println(json);
                    out.println(json);
                }
            }else{
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                LessonDAO lessons = new LessonDAO();
                boolean attendance = lessons.retrieveAttendanceForLesson(lessonID);
                boolean present = false;
                if(attendance){
                    lessons.updateTutorAttendance(lessonID, 0);
                } else{
                    lessons.updateTutorAttendance(lessonID, 1);
                    present = true;
                }
                
                JSONObject json = new JSONObject();
                json.put("present", present);
                String jsonString = json.toString();
                out.println(jsonString);
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
