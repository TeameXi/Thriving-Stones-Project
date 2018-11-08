/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import entity.Reward;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LevelDAO;
import model.RewardDAO;
import model.RewardItemDAO;
import model.StudentDAO;
import org.json.JSONObject;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "RedeemRewardServlet", urlPatterns = {"/RedeemRewardServlet"})
public class RedeemRewardServlet extends HttpServlet {

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
        
        if (request.getParameter("search") != null) {
            if (request.getParameter("branch_id") == null) {
            response.sendRedirect("RedeemReward.jsp");
            return;
        }
        int branchID = Integer.parseInt(request.getParameter("branch_id"));
            String student = (String) request.getParameter("student");
            String[] parts = student.split("-");
            String studentName = "";
            String studentEmail = "";
            int phone = 0;

            if (parts.length == 2) {
                studentName = parts[0].trim();
                if (parts[1].contains("@")) {
                    studentEmail = parts[1].trim();
                } else {
                    phone = Integer.parseInt(parts[1].trim());
                }
            }
//            System.out.println(phone + "& " + studentEmail);
            int studentID = 0;
            if (studentEmail.isEmpty()) {
                studentID = StudentDAO.retrieveStudentIDWithPhone(studentName, phone);
            } else {
                studentID = StudentDAO.retrieveStudentIDWithEmail(studentName, studentEmail);
            }
//            System.out.println(studentID);
            int levelID = StudentDAO.retrieveStudentLevelbyID(studentID, branchID);
            if (levelID == 0) {
                request.setAttribute("errorMsg", studentName + " Not Exists in Database, Please Create Student First.");
            } else {

                //List<Reward> rewardList = RewardDAO.listAllRewardsByStudent(studentID);
                request.setAttribute("pointAvail", RewardDAO.countStudentPoint(studentID));
                request.setAttribute("level", LevelDAO.retrieveLevel(levelID));
                request.setAttribute("studentName", studentName);
                request.setAttribute("student_id", studentID);

            }
            RequestDispatcher view = request.getRequestDispatcher("RedeemReward.jsp");
            view.forward(request, response);
        }
        if ("redeem".equals(request.getParameter("action"))) {
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                int studentid = Integer.parseInt(request.getParameter("studentID"));
                int rewardid = Integer.parseInt(request.getParameter("rewardID"));
                int point = Integer.parseInt(request.getParameter("point"));
                String name = request.getParameter("name");

                boolean success = RewardDAO.insertReward(studentid, 0, "redeem for " + name, 0 - point) > 0;
                JSONObject toReturn = new JSONObject().put("data", success);
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
