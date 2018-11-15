/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Level;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LevelDAO;
import model.PaymentDAO;
import model.StudentDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "StudentPaymentStatusServlet", urlPatterns = {"/StudentPaymentStatusServlet"})
public class StudentPaymentStatusServlet extends HttpServlet {

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
            
            StudentDAO studentDAO = new StudentDAO();
            
            switch (action) {
                case "retrieve":
                    {
                        int branchID = Integer.parseInt(request.getParameter("branchID"));
                        JSONArray array = new JSONArray();
                        ArrayList<Level> levels = LevelDAO.retrieveAllLevelLists();
                        for (Level l : levels) {
                            JSONObject obj = new JSONObject();
                            obj.put("id", l.getLevel_id());
                            obj.put("level", l.getLevelName());
                            array.put(obj);
                        }       
                        JSONObject toReturn = new JSONObject().put("data", array);
                        String json = toReturn.toString();
                        out.println(json);
                        break;
                    }
                case "retrieveStudent":
                    {
                        int levelID = Integer.parseInt(request.getParameter("levelID"));
                        int branchID = Integer.parseInt(request.getParameter("branchID"));
                        ArrayList<Student> studs = StudentDAO.listStudentsByLevel(levelID, branchID);
                        JSONArray array = new JSONArray();
                        for (Student s : studs) {
                            int studentID = s.getStudentID();
                            double totalOutstandingAmt = PaymentDAO.getStudentTutionFeeToAdd(studentID) + StudentDAO.retrieveStudentOutstandingAmt(studentID);
                            JSONObject obj = new JSONObject();
                            if(totalOutstandingAmt > 0){
                                obj.put("id", studentID);
                                obj.put("studentName", s.getName());
                                obj.put("totalOutstandingAmt", totalOutstandingAmt);
                                array.put(obj);
                            }
                        }       
                        JSONObject toReturn = new JSONObject().put("data", array);
                        String json = toReturn.toString();
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
