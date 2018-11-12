/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Lesson;
import entity.Class;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author xin
 */
@WebServlet(name = "StudentScheduleServlet", urlPatterns = {"/StudentScheduleServlet"})
public class StudentScheduleServlet extends HttpServlet {

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
                    int studentID = Integer.parseInt(request.getParameter("studentID"));
                    JSONArray array = new JSONArray();
                    ArrayList<Class> classes = classDAO.getStudentEnrolledClass(studentID);
                    for (Class c : classes) {
                        ArrayList<Lesson> lessons = lessonDAO.retrieveAllLessonLists(c.getClassID());
                        
                        for(Lesson l : lessons){
                            JSONObject obj = new JSONObject();
                            obj.put("id", l.getLessonid());

                            ArrayList<String> replacements = lessonDAO.retrieveReplacementDates(l.getLessonid());

                            if (replacements != null) {
                                obj.put("start_date", replacements.get(0));
                                obj.put("end_date", replacements.get(1));
                            } else {
                                obj.put("start_date", l.getStartDate());
                                obj.put("end_date", l.getEndDate());
                            }
                            obj.put("text", c.getLevel() + " " + c.getSubject());
                            array.put(obj);
                        }
                    }
                    JSONObject toReturn = new JSONObject().put("data", array);
                    String json = toReturn.toString();
                    out.println(json);
                    break;
                }
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
