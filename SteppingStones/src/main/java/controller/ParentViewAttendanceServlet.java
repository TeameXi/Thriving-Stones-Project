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
import model.LevelDAO;
import model.ParentChildRelDAO;
import model.StudentDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "ParentViewAttendanceServlet", urlPatterns = {"/ParentViewAttendanceServlet"})
public class ParentViewAttendanceServlet extends HttpServlet {

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
                int parentID = Integer.parseInt(request.getParameter("parentID"));

                ArrayList<Integer> children = ParentChildRelDAO.retrieveChildren(parentID);

                JSONArray array = new JSONArray();

                for (int studentID : children) {
                    Student student = StudentDAO.retrieveStudentbyID(studentID);

                    JSONObject obj = new JSONObject();
                    obj.put("id", studentID);
                    obj.put("name", student.getName());
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);

            } else if (action.equals("displayAttendances")) {
                int classID = Integer.parseInt(request.getParameter("classID"));
                int studentID = Integer.parseInt(request.getParameter("studentID"));

                JSONObject obj = new JSONObject();

                JSONArray lessons = new JSONArray();
                int index = 0;
                LinkedList<Lesson> lessonList = lessonDAO.retrieveAllLessonListsBeforeCurr(classID);

                for (Lesson l : lessonList) {
                    JSONObject lesson = new JSONObject();
                    lesson.put("id", l.getLessonid());
                    lesson.put("date", l.getLessonDate());
                    boolean attended = attendanceDAO.retrieveStudentAttendances(studentID, l.getLessonid());
                    lesson.put("attended", attended);
                    lessons.put(index, lesson);
                    index++;
                }
                obj.put("lessons", lessons);

                DecimalFormat formatter = new DecimalFormat("#.##");
                String attendance = formatter.format(attendanceDAO.retrieveNumberOfStudentAttendances(studentID, classID) / lessonDAO.retrieveNumberOfLessons(classID) * 100);

                obj.put("attendance", attendance + "%");

                String json = obj.toString();
                System.out.println(json);
                out.println(json);
            } else if (action.equals("displayClass")) {
                int studentID = Integer.parseInt(request.getParameter("studentID"));

                ArrayList<Class> classes = ClassDAO.getStudentEnrolledClass(studentID);
                JSONArray array = new JSONArray();

                for (Class c : classes) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", c.getClassID());

                    String name = c.getClassDay() + " " + c.getStartTime() + "-" + c.getEndTime() + "<br/>" + c.getSubject();

                    obj.put("name", name);
                    array.put(obj);
                }

                JSONObject obj = new JSONObject().put("data", array);
                out.println(obj.toString());
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
