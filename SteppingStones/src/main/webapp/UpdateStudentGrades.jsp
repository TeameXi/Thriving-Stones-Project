<%-- 
    Document   : Student_Sign_Up
    Created on : 6 Jul, 2018, 4:03:17 PM
    Author     : DEYU
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="entity.Student"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Student Grades</title>
    </head>
    <body>
        <%
            Map<Integer, String> classSub = (Map<Integer, String>) request.getAttribute("classSub");
            int studentID = (Integer) request.getAttribute("studentID");
            String studentName = (String) request.getAttribute("studentName");
            request.setAttribute("studentID", studentID);
            
            if(!classSub.isEmpty()){     
        %>
        <h1>Update Student's Grade</h1>
        <form action="RetrieveUpdateGradesServlet" method="post">
            <input type="hidden" name="studentID" value="${studentID}">
            <%
                out.println("StudentName: " + studentName + "</br></br>");
            %>
            Subject:
            <select name = "subjects">
                <option value="-1" selected>Select Subject</option>
            <%
                Set<Integer> classes = classSub.keySet();
                for(int cls: classes){
                    out.println("<option value=" + cls + ">" + classSub.get(cls) + "</option>");
                }
                    
            %>
            </select><br>
            Level:
            <select name = "assessmentType">
                <option value="-1" selected>Select Assessment Type</option>
                <option value="CA1">CA1</option>
                <option value="SA1">SA1</option>
                <option value="CA2">CA2</option>
                <option value="SA2">SA2</option>
            </select><br>
            Grade:
            <input type ="text" name ="grade"><br><br>
            
            <button type="submit" name="insert" value="insert">Update</button> 
        </form>         
        
        <%
            }else{
                out.println("No grades to update, please create grade first!");
            }

        %>              
    </body>
</html>
