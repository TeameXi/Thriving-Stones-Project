<%-- 
    Document   : Student_Sign_Up
    Created on : 6 Jul, 2018, 4:03:17 PM
    Author     : DEYU
--%>

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
            ArrayList<String> subs = (ArrayList<String>) request.getAttribute("subjects");
            Student stu = (Student)request.getAttribute("StudentData");
            if(stu != null){
                request.setAttribute("studentID", stu.getStudentID());
            
                    if(!subs.isEmpty()){     
        %>
        <h1>Update Student's Grade</h1>
        <form action="RetrieveUpdateGradesServlet" method="post">
            <input type="hidden" name="studentID" value="${studentID}">
            <%
                        out.println("StudentName: " + stu.getName() + "</br></br>");
            %>
            Subject:
            <select name = "subjects">
                <option value="-1" selected>Select Subject</option>
                <%
                        for(String sub: subs){
                            out.println("<option value=" + sub + ">" + sub + "</option>");
                        }
                    
                %>
            </select><br>
            Level:
            <select name = "assessmentType">
                <option value="-1" selected>Select Assessment Type</option>
                <option value="ca1">CA1</option>
                <option value="sa1">SA1</option>
                <option value="ca2">CA2</option>
                <option value="sa2">SA2</option>
            </select><br>
            Grade:
            <input type ="text" name ="grade"><br><br>
            
            <button type="submit" name="insert" value="insert">Update</button> 
        </form>         
        
        <%
                    }else{
                        out.println("No grades to update, please create grade first!");
                    }
            }else{
                out.println("Student Record Not Found!");
            }
        %>              
    </body>
</html>
