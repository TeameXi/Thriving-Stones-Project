<%-- 
    Document   : RegisterForClasses
    Created on : 3 Aug, 2018, 8:59:51 PM
    Author     : DEYU
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entity.Class"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="RegisterForClassesServlet" method="post">
            StudentName:
            <input type ="text" name ="studentName" required><br>
            <button type="submit" value = "search" name = "search">Search For Classes</button> 
        </form><br><br>
        <%
            String errorMsg = (String) request.getAttribute("errorMsg");
            if (errorMsg != null) {
                    out.println(errorMsg); 
            }
            
            String status = (String) request.getAttribute("status");
            if (status != null) {
                    out.println(status); 
            }
            
            ArrayList<Class> classes = (ArrayList<Class>) request.getAttribute("classes");
            ArrayList<Class> enrolledClasses = (ArrayList<Class>) request.getAttribute("enrolledClasses");
            String level = (String) request.getAttribute("level");
            String studentName = (String) request.getAttribute("studentName");
            
            if(classes != null){
                request.setAttribute("studentName", studentName);
        %>
        Student Name: <%out.println(studentName);%><br>
        Level: <%out.println(level);%><br>
        <br>Currently Enrolled Classes:<br>
        <%
                if(enrolledClasses != null){
                    for(Class cls: enrolledClasses){
                        out.println(cls.getSubject()+ ", " + cls.getClassDay() + " " + cls.getClassTime() + 
                            ", StartDate: " + cls.getStartDate() + ", Monthly Fees: " + cls.getMthlyFees() + "<br>");
                    }
                }
        %> 
        <br>Register for Classes: <br>                
        <%
                
                for(Class cls: classes){
                    request.setAttribute("value", cls.getClassID());
        %>
        <form action="RegisterForClassesServlet" method="post">
            
            <input type= "checkbox" name ="classValue" value = "${value}">
            <input type="hidden" name="studentName" value="${studentName}">
        <%
                    out.println(cls.getSubject()+ ", " + cls.getClassDay() + " " + cls.getClassTime() + 
                            ", StartDate: " + cls.getStartDate() + ", Monthly Fees: " + cls.getMthlyFees() + "<br>");
                }
        %>
        <br><button type="submit" value = "select" name = "select">Select</button> 
        </form>
        <%
            }
        %>
    </body>
</html>
