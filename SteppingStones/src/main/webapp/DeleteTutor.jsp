<%-- 
    Document   : DeleteTutor
    Created on : Jul 16, 2018, 9:58:52 PM
    Author     : Hui Xin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <form action="DeleteTutorServlet">
            Enter Tutor ID: <input type="text" name="tutorID"><br/>
            <input type="submit" value="Delete">
        </form>
        
        <%
            String status = (String) request.getAttribute("status");
            out.println(status);
        %>
    </body>
</html>
