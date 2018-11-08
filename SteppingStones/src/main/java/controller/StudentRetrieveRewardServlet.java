/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import entity.RewardItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.RewardDAO;
import model.RewardItemDAO;
import org.apache.xmlbeans.impl.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "StudentRetrieveRewardServlet", urlPatterns = {"/StudentRetrieveRewardServlet"})
public class StudentRetrieveRewardServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String action = request.getParameter("action");

            if (action.equals("retrieve")) {
                JSONArray array = new JSONArray();
                ArrayList<RewardItem> rewards = RewardItemDAO.listAllRewardItem();
                int studentid = Integer.parseInt(request.getParameter("studentID"));
                 int point =  RewardDAO.countStudentPoint(studentid);
                for (RewardItem e : rewards) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", e.getReward_item_id());
                    obj.put("name", e.getItem_name() );
                    obj.put("description", e.getDescription());
                    obj.put("quantity", e.getQuantity());
                    obj.put("point", e.getPoint());
                    obj.put("status", point >= e.getPoint() ? "Redeemable" : "Not Redeemable");
                     byte[] ba; 
                    try {
                        ba = e.getImage().getBytes(1, (int) e.getImage().length());
                        byte[] img64 = Base64.encode(ba);
                    String photo64 = new String(img64);

                    obj.put("image", photo64);
                    array.put(obj);
                    } catch (SQLException ex) {
                        Logger.getLogger(RetrieveRewardServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // b.length() throws exception, no message, no cause
                    
                }
                JSONObject toReturn = new JSONObject().put("data", array);
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
