<%-- 
    Document   : resetPassword
    Created on : 12 Jul, 2018, 8:29:58 PM
    Author     : Zang Yu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password Page</title>
    </head>
    <body>
        <h1>Forgot Password</h1>
        <form id="register-form" role="form" class="form" method="post" action="ForgotPasswordServlet">
            <h3>Enter Your Email Below</h3>
            <input id="email" name="email" placeholder="Email address" class="form-control"  type="email" required autofocus>
            <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="Get Password" type="submit">
        </form>
    </body>
</html>
