<%-- 
    Document   : RetrieveStudentByID
    Created on : 11 Jul, 2018, 8:29:52 PM
    Author     : DEYU
--%>

<%@page import="java.util.LinkedHashMap"%>
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
            Student Name:
            <input type ="text" name ="studentName" required><br>
            <button type="submit" value = "retrieve" name = "retrieve">Retrieve</button> 
            <button type="submit" value = "update" name = "update">Update</button>
        </form>
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                    out.println(status); 
            }else{
                String studentName = (String)request.getAttribute("studentName");
                LinkedHashMap<String, ArrayList<String>> gradeLists = (LinkedHashMap<String, ArrayList<String>>) request.getAttribute("gradeLists");
                
                if(gradeLists != null && !gradeLists.isEmpty()){
                    out.println("<br>Student Name: " + studentName);
                    Set<String> keys = gradeLists.keySet();
                    out.println("<br>Tuition Grades<br>");
                    for(String subject: keys){
                        ArrayList<String> grades = gradeLists.get(subject);
                        out.println(subject);
                        if(grades != null){
                            for(String grade: grades){
                                out.println(grade);
                            }
                        }
                        out.println("<br>");
                    }
                }else{
                    out.println("<br>No Grades Added for " + studentName +  " Yet.");
                }
            }
        %>
    </body>
</html>
