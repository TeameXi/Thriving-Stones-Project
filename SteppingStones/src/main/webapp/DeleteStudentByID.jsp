<%-- 
    Document   : DeleteStudentByID
    Created on : 12 Jul, 2018, 8:20:05 PM
    Author     : DEYU
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Student By ID</title>
    </head>
    <body>
        <h1>Delete Student By ID</h1>
        <form action="DeleteStudentServlet" method="post">
            StudentID:
            <input type ="text" name ="studentID" required><br>
            <button type="submit">Delete</button> 
        </form>
        <%
            ArrayList<String> status = (ArrayList<String>)request.getAttribute("status");
            if(status != null){
                for(int i = 0; i< status.size(); i++){
                    out.println(status.get(i));
                }
            }
        %>
    </body>
</html>
