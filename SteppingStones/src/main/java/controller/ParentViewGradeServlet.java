/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Grade;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.StudentDAO;
import model.StudentGradeDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "ParentViewGradeServlet", urlPatterns = {"/ParentViewGradeServlet"})
public class ParentViewGradeServlet extends HttpServlet {

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
            String class_id = request.getParameter("class_id");
            String branch_id = request.getParameter("branch_id");
            String student_id = request.getParameter("student_id");
            String parts[] = student_id.split(" ");
            
            ArrayList<Integer> student_ids = new ArrayList<>();
            
            for (String temp: parts){
                student_ids.add(Integer.parseInt(temp));
            }

            if(class_id != null && !class_id.equals("")){

                int classID = Integer.parseInt(class_id);
                int branchID = Integer.parseInt(branch_id);
                ClassDAO cdao = new ClassDAO();
                ArrayList<Student> classList = cdao.retrieveStudentsByClass(classID);
                
                JSONArray studentsObjListsForClass = new JSONArray();
                
                for (int studentID: student_ids){
                    Student currStud = StudentDAO.retrieveStudentbyID(studentID,branchID);
                    
                    for (Student s: classList){
                        if (s.getStudentID() == currStud.getStudentID()){
                            ArrayList<Grade> gradeList = StudentGradeDAO.listGradesFromSpecificClassIncludingRatioForSpecificStudent(classID, studentID);
                        
                            for(Grade g:gradeList){
                                JSONObject studentObj = new JSONObject();
                                studentObj.put("studentId", g.getStudentId());
                                studentObj.put("studentName",g.getStudentName());
                                studentObj.put("classId", g.getClass_id());

                                // Tution Grade
                                if(g.getCA1_tuition_top() == 0){
                                    studentObj.put("CA1_0", "-");
                                }else{
                                    studentObj.put("CA1_0", g.getCA1_tuition_top()+"/"+g.getCA1_tuition_base()+"-<strong>"+g.getCA1_tuition_grade()+"</strong>");
                                }

                                if(g.getSA1_tuition_top() == 0){
                                    studentObj.put("SA1_0", "-");
                                }else{
                                    studentObj.put("SA1_0", g.getSA1_tuition_top()+"/"+g.getSA1_tuition_base()+"-<strong>"+g.getSA1_tuition_grade()+"</strong>");
                                }

                                if(g.getCA2_tuition_top() == 0){
                                    studentObj.put("CA2_0", "-");
                                }else{
                                    studentObj.put("CA2_0", g.getCA2_tuition_top()+"/"+g.getCA2_tuition_base()+"-<strong>"+g.getCA2_tuition_grade());
                                }

                                if(g.getSA2_tuition_top() == 0){
                                    studentObj.put("SA2_0", "-");
                                }else{
                                    studentObj.put("SA2_0", g.getSA2_tuition_top()+"/"+g.getSA2_tuition_base()+"-<strong>"+g.getSA2_tuition_grade());
                                }

                                // IMPROVEMENT RATE FOR TUTION
                                studentObj.put("CA1_0_improvement_rate",0);
                                studentObj.put("SA1_0_improvement_rate",0);
                                studentObj.put("CA2_0_improvement_rate",0);
                                studentObj.put("SA2_0_improvement_rate",0);

                                if(g.getCA1_tuition_top() != 0 && g.getSA1_tuition_top() != 0){
                                    double SA1_0_calculatedImprovement =100* (double)(g.getSA1_tuition_grade()-g.getCA1_tuition_grade())/g.getCA1_tuition_grade();
                                    studentObj.put("SA1_0_improvement_rate",Double.parseDouble(new DecimalFormat("##.##").format(SA1_0_calculatedImprovement)));
                                }

                                if(g.getSA1_tuition_top() != 0 && g.getCA2_tuition_top() != 0){
                                    double CA2_0_calculatedImprovement =100* (double)(g.getCA2_tuition_grade()-g.getSA1_tuition_grade())/g.getSA1_tuition_grade();
                                    studentObj.put("CA2_0_improvement_rate",Double.parseDouble(new DecimalFormat("##.##").format(CA2_0_calculatedImprovement)));
                                }

                                if(g.getCA2_tuition_top() != 0 && g.getSA2_tuition_top() != 0){
                                    double SA2_0_calculatedImprovement =100* (double)(g.getSA2_tuition_grade()-g.getCA2_tuition_grade())/g.getCA2_tuition_grade();
                                    studentObj.put("SA2_0_improvement_rate",Double.parseDouble(new DecimalFormat("##.##").format(SA2_0_calculatedImprovement)));
                                }



                                // SCHOOL GRADE              
                                if(g.getCA1_school_top() == 0){
                                    studentObj.put("CA1_1", "-");
                                }else{
                                    studentObj.put("CA1_1", g.getCA1_school_top()+"/"+g.getCA1_school_base()+"-<strong>"+g.getCA1_school_grade());
                                }

                                if(g.getSA1_school_top() == 0){
                                    studentObj.put("SA1_1", "-");
                                }else{
                                    studentObj.put("SA1_1", g.getSA1_school_top()+"/"+g.getSA1_school_base()+"-<strong>"+g.getSA1_school_grade());
                                }

                                if(g.getCA2_school_top() == 0){
                                    studentObj.put("CA2_1", "-");
                                }else{
                                    studentObj.put("CA2_1", g.getCA2_school_top()+"/"+g.getCA2_school_base()+"-<strong>"+g.getCA2_school_grade());
                                }

                                if(g.getSA2_school_top() == 0){
                                    studentObj.put("SA2_1", "-");
                                }else{
                                    studentObj.put("SA2_1", g.getSA2_school_top()+"/"+g.getSA2_school_base()+"-<strong>"+g.getSA2_school_grade());
                                }


                                // IMPROVEMENT RATE FOR SCHOOL
                                studentObj.put("CA1_1_improvement_rate",0);
                                studentObj.put("SA1_1_improvement_rate",0);
                                studentObj.put("CA2_1_improvement_rate",0);
                                studentObj.put("SA2_1_improvement_rate",0);

                                if(g.getCA1_school_top() != 0 && g.getSA1_school_top() != 0){
                                    double SA1_1_calculatedImprovement =100* (double)(g.getSA1_school_grade()-g.getCA1_school_grade())/g.getCA1_school_grade();
                                    studentObj.put("SA1_1_improvement_rate",Double.parseDouble(new DecimalFormat("##.##").format(SA1_1_calculatedImprovement)));
                                }

                                if(g.getSA1_school_top() != 0 && g.getCA2_school_top() != 0){
                                    double CA2_1_calculatedImprovement =100* (double)(g.getCA2_school_grade()-g.getSA1_school_grade())/g.getSA1_school_grade();
                                    studentObj.put("CA2_1_improvement_rate",Double.parseDouble(new DecimalFormat("##.##").format(CA2_1_calculatedImprovement)));
                                }

                                if(g.getCA2_school_top() != 0 && g.getSA2_school_top() != 0){
                                    double SA2_1_calculatedImprovement =100* (double)(g.getSA2_school_grade()-g.getCA2_school_grade())/g.getCA2_school_grade();
                                    studentObj.put("SA2_1_improvement_rate",Double.parseDouble(new DecimalFormat("##.####").format(SA2_1_calculatedImprovement)));
                                }
                                studentsObjListsForClass.put(studentObj);
                            }
                        
                        }
                    }
                }
                out.print(studentsObjListsForClass);
                
            }else{
                out.println("-1");
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
