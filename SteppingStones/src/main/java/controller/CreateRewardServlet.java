/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Reward;
import entity.RewardItem;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.ExpenseDAO;
import model.RewardItemDAO;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "CreateRewardServlet", urlPatterns = {"/CreateRewardServlet"})
@MultipartConfig(maxFileSize = 16177215)
public class CreateRewardServlet extends HttpServlet {

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
        
         List<RewardItem> itemList = new ArrayList<>();
        
        boolean stillExist = true;
        int counter = 0;
        while(stillExist){
            String name = request.getParameter("itemName[" + counter + "]");
            String description = request.getParameter("description[" + counter + "]");
            String quantityStr = request.getParameter("quantity[" + counter + "]");
            int quantity = Integer.parseInt(request.getParameter("quantity[" + counter + "]"));
            int point = Integer.parseInt(request.getParameter("point["+counter+"]"));
            InputStream inputStream = null;
            Part filePart = request.getPart("image[" + counter + "]");
            if (filePart != null) {
                // prints out some information for debugging
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                // obtains input stream of the upload file
                inputStream = filePart.getInputStream();
            }
            if(request.getParameter("type[" + (counter + 1) + "]") == null){
                stillExist = false;
            }
            RewardItem item = new RewardItem(name, quantity, point, inputStream, description);
            
            itemList.add(item);
            counter++;
        }
        
        RewardItemDAO.insertItems(itemList);
        
        response.sendRedirect("DisplayReward.jsp");
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
