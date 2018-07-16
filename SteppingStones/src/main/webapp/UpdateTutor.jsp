<%-- 
    Document   : UpdateTutor
    Created on : Jul 16, 2018, 10:19:14 PM
    Author     : Hui Xin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <form action="UpdateTutorServlet">
            Enter Tutor ID: <input type="text" name="tutorID">
            Enter name: <input type="text" name="name">
            Enter age: <input type="text" name="age">
            Enter gender: <input type="text" name="gender">
            Enter phone: <input type="text" name="phone">
            Enter email: <input type="text" name="email">
            <input type="submit" value="Update">
        </form>
        
        <%
            String status = (String) request.getAttribute("status");
            out.println(status);
        %>
    </body>
</html>
