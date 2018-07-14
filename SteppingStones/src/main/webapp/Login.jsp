<%-- 
    Document   : Login
    Created on : Jul 14, 2018, 12:51:34 PM
    Author     : Riana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="LoginServlet" method="post">
            <input type="text" name="username">
            <input type="password" name="password">
            <input type="submit" name="btnSubmit" value="Submit">
        </form>
    </body>
</html>
