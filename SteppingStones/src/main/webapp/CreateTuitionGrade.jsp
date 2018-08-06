<%-- 
    Document   : TuitionGradeCreate
    Created on : 22 Jul, 2018, 3:58:23 PM
    Author     : DEYU
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Tuition Grade</title>
    </head>
    <body>
        <h1>Create Tuition Grade</h1>
        <form action="CreateTuitionGradeServlet" method="post" required>
            <%
                ArrayList<Class> classes = ClassDAO.listAllClasses();
                if(classes != null){
                    for(Class cls: classes){
                        out.println(cls.getLevel() + cls.getSubject() + cls.getClassDay() + cls.getClassTime());
                        request.setAttribute("ClassID", cls.getClassID());
            %>           
                <button type="submit" value="${ClassID}" name="select">Select</button><br>                                   
            <%
                    }
                }
            %>  
        </form>
        
        <form action="CreateTuitionGradeServlet" method="post" required>
            <%
                ArrayList<String> students = (ArrayList<String>)request.getAttribute("students");
                Class cls = (Class)request.getAttribute("class");              
                if(cls != null){
                    request.setAttribute("classID", cls.getClassID());
                    request.setAttribute("sub", cls.getSubject());
                    out.println("<h1>Adding Grades to Students In Class '" + cls.getLevel() + " " + cls.getSubject() + " " + cls.getClassDay() + " " + cls.getClassTime() + "'</h1>");
                }
                if(students != null){
                    for(String studentName: students){
                        out.println(studentName + ":");
                        request.setAttribute("studentName", studentName);
            %>    
            <input type ="text" name ="${studentName}"><br>            
            <%
                    }
            %>
            <input type="hidden" name="classID" value="${classID}">
            <br>Assessment Type: 
            <select name = "assessmentType">
                <option value="CA1">CA1</option>
                <option value="SA1">SA1</option>
                <option value="CA2">CA2</option>
                <option value="SA2">SA2</option>
            </select><br>
            <button type="submit" value="insert" name="insert">Insert Grades</button><br>
            <%
                }
            %>            
        </form>
        <%
            String status = (String) request.getAttribute("status");
            if (status != null) {
                out.println(status);
            }
        %>
    </body>
</html>
