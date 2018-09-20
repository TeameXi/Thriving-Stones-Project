/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
import entity.Class;
import entity.Lesson;
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
import model.LevelDAO;
import model.SubjectDAO;
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "TutorPaymentServlet", urlPatterns = {"/TutorPaymentServlet"})
public class TutorPaymentServlet extends HttpServlet {

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
                ArrayList<Tutor> tutors = new TutorDAO().retrieveAllTutorsByBranch(branchID);
                
                for (Tutor t : tutors) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", t.getTutorId());
                    obj.put("name", t.getName());
                    obj.put("phone", t.getPhone());
                    ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(t.getTutorId(), branchID);
                    double totalOwed = 0.0;
                    for (Class c : classes) {
                        totalOwed += TutorDAO.calculateLessonCount(t.getTutorId(), c.getClassID()) * ClassDAO.getClassTime(c.getClassID()) * TutorDAO.getHourlyPay(t.getTutorId(), LevelDAO.retrieveLevelID(c.getLevel()), SubjectDAO.retrieveSubjectID(c.getSubject()));   
                    }
                    obj.put("salary", totalOwed);
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
                
            } else if (action.equals("retrieveClasses")) {
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));

                ArrayList<Class> classes = ClassDAO.listAllClassesByTutorID(tutorID, branchID);

                JSONArray array = new JSONArray();
                for (Class c : classes) {
                    JSONObject obj = new JSONObject();

                    obj.put("id", c.getClassID());
                    obj.put("date", c.getClassDay() + " " + c.getClassTime());
                    obj.put("level", c.getLevel());
                    obj.put("subject", c.getSubject());
                    obj.put("hourly_rate", TutorDAO.getHourlyPay(tutorID, LevelDAO.retrieveLevelID(c.getLevel()), SubjectDAO.retrieveSubjectID(c.getSubject())));
                    obj.put("owed_amount", TutorDAO.calculateLessonCount(tutorID, c.getClassID()) * ClassDAO.getClassTime(c.getClassID()) * TutorDAO.getHourlyPay(tutorID, LevelDAO.retrieveLevelID(c.getLevel()), SubjectDAO.retrieveSubjectID(c.getSubject())));
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);

            } else if (action.equals("retrieveLessons")) {
                int classID = Integer.parseInt(request.getParameter("classID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));

                LessonDAO lessonDAO = new LessonDAO();
                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonListsBeforeCurr(classID);

                JSONArray array = new JSONArray();

                for (Lesson l : lessons) {
                    JSONObject obj = new JSONObject();
                    String date = l.getStartDate();
                    obj.put("id", l.getLessonid());
                    obj.put("date", date.substring(0, date.indexOf(" ")));

                    if (lessonDAO.retrievePaymentStatus(l.getLessonid(), tutorID) == 1) {
                        obj.put("paid", "Paid");
                    } else if (lessonDAO.retrievePaymentStatus(l.getLessonid(), tutorID) == 0){
                        obj.put("paid", "Not Paid");
                    }

                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);

            } else if (action.equals("mark")) {
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int classID = Integer.parseInt(request.getParameter("classID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));

                boolean status = TutorDAO.updateTutorPayment(tutorID, classID);

                JSONObject toReturn = new JSONObject().put("data", status);
                String json = toReturn.toString();
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
