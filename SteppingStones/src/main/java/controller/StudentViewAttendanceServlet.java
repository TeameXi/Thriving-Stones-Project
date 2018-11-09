/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import entity.Class;
import entity.Lesson;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AttendanceDAO;
import model.ClassDAO;
import model.LessonDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "StudentViewAttendanceServlet", urlPatterns = {"/StudentViewAttendanceServlet"})
public class StudentViewAttendanceServlet extends HttpServlet {

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
            Gson gson = new Gson();
            String action = request.getParameter("action");

            ClassDAO classDAO = new ClassDAO();
            LessonDAO lessonDAO = new LessonDAO();
            AttendanceDAO attendanceDAO = new AttendanceDAO();

            if (action.equals("retrieve")) {
                int studentID = Integer.parseInt(request.getParameter("studentID"));

                JSONArray array = new JSONArray();
                ArrayList<Class> classes = classDAO.getStudentEnrolledClass(studentID);

                for (Class c : classes) {
                    LinkedList<Lesson> lessons = lessonDAO.retrieveAllLessonListsBeforeCurr(c.getClassID());

                    if (lessons.size() > 0) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", c.getClassID());
                        obj.put("name", c.getClassDay() + " " + c.getStartTime() + "-" + c.getEndTime() + "<br/>" + c.getLevel() + " " + c.getSubject());
                        array.put(obj);
                    }
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);

            } else if (action.equals("displayAttendances")) {
                int classID = Integer.parseInt(request.getParameter("classID"));
                int studentID = Integer.parseInt(request.getParameter("studentID"));

                JSONObject obj = new JSONObject();
                JSONArray array = new JSONArray();

                Class c = classDAO.getClassByID(classID);
                
                double attendance = attendanceDAO.retrieveNumberOfStudentAttendances(studentID, classID) / lessonDAO.retrieveNumberOfLessons(classID);
                
                DecimalFormat df = new DecimalFormat("#.##");
                
                obj.put("attendance", df.format(attendance) + "%");

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
