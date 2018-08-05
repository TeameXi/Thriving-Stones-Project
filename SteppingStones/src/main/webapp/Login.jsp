<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/util.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/main.css">
        <title>Login</title>
    </head>
    <body>
        <%
            if (request.getSession().getAttribute("response") != null) {

                Map<String, String> error = (Map<String, String>) request.getSession().getAttribute("response");

                session.invalidate();
        %>
        <script>alert('Login fail.\n<%= error.get("error")%>')</script><%
            }
        %>

        <div class="limiter">
            <div class="signIn"><img style="max-width: 100%;" src="${pageContext.request.contextPath}/styling/img/signIn.png"></img></div>
            <div class="container-login100">
                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
                    <form class="login100-form validate-form" action="LoginServlet" method="post">

                        <div class="wrap-input100 validate-input m-b-23">
                            <span class="label-input100">User Type</span>
                            <select name="type" class="form-control" >
                                <option value="admin" >Admin</option>
                                <option value="tutor">Tutor</option>
                                <option value="student">Student</option>
                                <option value="parent">Parent</option>
                            </select>
                        </div>

                        <div class="wrap-input100 validate-input m-b-23" data-validate = "Username is required">
                            <span class="label-input100">Username</span>
                            <input class="input100" type="text" id ="txtusername" name="username" placeholder="Type your username">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate="Password is required">
                            <span class="label-input100">Password</span>
                            <input class="input100" type="password" name="password" placeholder="Type your password">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>

                        <div class="text-right p-t-8 p-b-31">
                            <a href="ForgotPassword.jsp">
                                Forgot password?
                            </a>
                        </div>


                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn">
                                    Login
                                </button>
                            </div>
                        </div>
                    </form>

                    <div class="txt1 text-center p-t-54 p-b-20">
                        <span>
                            <a href="signUp.html">Or Sign Up </a>
                        </span>
                    </div>
                </div>
            </div>
        </div>   
    </body>
</html>

<script src="https://code.jquery.com/jquery.js"></script>
<script src="${pageContext.request.contextPath}/styling/js/jquery.validation.js"></script>




