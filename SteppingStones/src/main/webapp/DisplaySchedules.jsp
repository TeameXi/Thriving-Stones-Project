<%-- 
    Document   : Schedule
    Created on : Aug 9, 2018, 6:55:38 PM
    Author     : Riana
--%>

<%@page import="entity.Lesson"%>
<%@page import="entity.Class"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            List<Class> classList = (List<Class>)request.getAttribute("ClassList");
            List<Lesson> lessonList = (List<Lesson>) request.getAttribute("LessonList");
            System.out.println("classList " + classList);
            System.out.println("lessonList  " + lessonList);
        %>
        <h1>Hello World!</h1>
    </body>
</html>
