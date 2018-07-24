<%-- 
    Document   : RetrieveStudentByID
    Created on : 11 Jul, 2018, 8:29:52 PM
    Author     : DEYU
--%>

<%@page import="model.StudentDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="entity.StudentGrade"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Retrieve or Update Student's</title>
    </head>
    <body>
        <h1>Retrieve or Update Student's</h1>
        <form action="RetrieveUpdateGradesServlet" method="post">
            StudentID:
            <input type ="text" name ="studentID" required><br>
            <button type="submit" value = "retrieve" name = "retrieve">Retrieve</button> 
            <button type="submit" value = "update" name = "update">Update</button>
        </form>
        <%
            Student stu = (Student)request.getAttribute("StudentData");
            if(stu!= null){
                Map<String, Map<String, StudentGrade>> grades = stu.getGrades();
                Set<String> keys = grades.keySet();
                out.println("</br>Student Name: " + stu.getName() + "</br>");
                for(String schoolOrCenter: keys){
                    if(schoolOrCenter.equals("School")){
                        Map<String, StudentGrade> schoolGrades = grades.get(schoolOrCenter);
                        out.println("</br>School Grades:" + "</br>");
                        Set<String> schoolKeys = schoolGrades.keySet();
                        for(String sub: schoolKeys){
                            StudentGrade subGrades = schoolGrades.get(sub);
                            out.println(sub + " -");
                            if(!subGrades.getCA1().equals("")){
                                out.println("CA1: " + subGrades.getCA1());
                            }
                            if(!subGrades.getSA1().equals("")){
                                out.println("SA1: " + subGrades.getSA1());
                            }
                            if(!subGrades.getCA2().equals("")){
                                out.println("CA2: " + subGrades.getCA2());
                            }
                            if(!subGrades.getSA2().equals("")){
                                out.println("SA2: " + subGrades.getSA2());
                            }
                            out.println("</br>");
                        }
                    }
                    if(schoolOrCenter.equals("Center")){
                        Map<String, StudentGrade> centerGrades = grades.get(schoolOrCenter);
                        out.println("<br>Center Grades:" + "<br/>");
                        Set<String> centerKeys = centerGrades.keySet();
                        for(String sub: centerKeys){
                            StudentGrade subGrades = centerGrades.get(sub);
                            out.println(sub + " -");
                            if(!subGrades.getCA1().equals("")){
                                out.println("CA1: " + subGrades.getCA1());
                            }
                            if(!subGrades.getSA1().equals("")){
                                out.println("SA1: " + subGrades.getSA1());
                            }
                            if(!subGrades.getCA2().equals("")){
                                out.println("CA2: " + subGrades.getCA2());
                            }
                            if(!subGrades.getSA2().equals("")){
                                out.println("SA2: " + subGrades.getSA2());
                            }
                            out.println("</br>");
                        }
                    }
                }
            }
  
            String status = (String) request.getAttribute("status");
            if (status != null) {
                    out.println(status); 
            }
            
            ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
            if(errors != null){
                for(String error: errors){
                    out.println(error);
                }
            }
        %>
    </body>
</html>
